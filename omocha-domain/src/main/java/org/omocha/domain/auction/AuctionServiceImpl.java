package org.omocha.domain.auction;

import java.util.List;
import java.util.stream.Collectors;

import org.omocha.domain.exception.AuctionHasBidException;
import org.omocha.domain.exception.AuctionImageNotFoundException;
import org.omocha.domain.exception.CategoryNotFoundException;
import org.omocha.domain.exception.MemberInvalidException;
import org.omocha.domain.image.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

	private final AuctionStore auctionStore;
	private final AuctionImagesFactory auctionImagesFactory;
	private final AuctionReader auctionReader;
	private final CategoryReader categoryReader;
	private final CategoryStore categoryStore;

	@Override
	@Transactional
	public Long addAuction(AuctionCommand.AddAuction addCommand) {
		if (addCommand.images() == null || addCommand.images().isEmpty()) {
			throw new AuctionImageNotFoundException(addCommand.memberId());
		}

		Auction auction = auctionStore.store(addCommand.toEntity());
		auctionImagesFactory.store(auction, addCommand);
		categoryStore.auctionCategoryStore(auction, addCommand);

		return auction.getAuctionId();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AuctionInfo.SearchAuction> searchAuction(
		AuctionCommand.SearchAuction searchAuction,
		Pageable pageable
	) {

		List<Long> subCategoryIds = categoryReader.getSubCategoryIds(searchAuction.categoryId());

		Page<AuctionInfo.SearchAuction> auctionList = auctionReader.getAuctionList(searchAuction, subCategoryIds,
			pageable);

		List<AuctionInfo.SearchAuction> categoryAuctions = auctionList.getContent().stream()
			.map(auctionInfo -> {
				List<CategoryInfo.CategoryResponse> categoryHierarchy = categoryReader.getCategoryHierarchyUpwards(
					searchAuction.categoryId());
				return categoryHierarchy != null ? auctionInfo.withCategoryHierarchy(categoryHierarchy) : auctionInfo;
			})
			.collect(Collectors.toList());

		return PageableExecutionUtils.getPage(
			categoryAuctions,
			pageable,
			auctionList::getTotalElements
		);

	}

	@Override
	@Transactional(readOnly = true)
	public AuctionInfo.RetrieveAuction retrieveAuction(AuctionCommand.RetrieveAuction retrieveCommand) {
		Auction auction = auctionReader.getAuction(retrieveCommand.auctionId());

		List<String> imagePaths = auction.getImages().stream()
			.map(Image::getImagePath)
			.collect(Collectors.toList());

		// TODO : auction.getAuctionCategories를 list로 받고 처리하도록 수정해야함
		AuctionCategory auctionCategory = auction.getAuctionCategories()
			.stream()
			.findFirst()
			.orElseThrow(
				() -> new CategoryNotFoundException(auction.getAuctionId(), retrieveCommand.memberId()));

		Long selectedCategoryId = auctionCategory.getCategory().getCategoryId();

		List<CategoryInfo.CategoryResponse> categoryHierarchy =
			categoryReader.getCategoryHierarchyUpwards(selectedCategoryId);

		return new AuctionInfo.RetrieveAuction(auction, imagePaths, categoryHierarchy);
	}

	@Override
	@Transactional
	public void removeAuction(AuctionCommand.RemoveAuction removeCommand) {
		Auction auction = auctionReader.getAuction(removeCommand.auctionId());

		Long memberId = removeCommand.memberId();

		if (!auction.getMemberId().equals(memberId)) {
			throw new MemberInvalidException(memberId);
		}

		if (auction.getBidCount() != null && auction.getBidCount() != 0) {
			throw new AuctionHasBidException(auction.getAuctionId());
		}

		auctionReader.removeAuction(auction);
	}

}
