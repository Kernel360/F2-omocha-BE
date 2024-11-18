package org.omocha.infra.category;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.category.Category;
import org.omocha.domain.category.CategoryReader;
import org.omocha.domain.category.CategoryStore;
import org.omocha.infra.category.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryStoreImpl implements CategoryStore {

	private final CategoryRepository categoryRepository;
	private final CategoryReader categoryReader;

	@Override
	public Category categoryStore(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public void auctionCategoryStore(Auction auction, AuctionCommand.AddAuction addCommand) {
		if (addCommand.categoryIds() != null && !addCommand.categoryIds().isEmpty()) {
			for (Long categoryId : addCommand.categoryIds()) {
				Category category = categoryReader.getCategory(categoryId);
				auction.addCategory(category);
			}
		}

	}

}
