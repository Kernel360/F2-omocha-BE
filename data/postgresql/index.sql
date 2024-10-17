-- auction table index 조회
SELECT *
FROM pg_indexes
WHERE tablename = 'auction';

-- auction table index 추가
CREATE INDEX index_auction_endDate_status ON auction (end_date);

-- auction table index 삭제
DROP INDEX index_auction_endDate_status;

-- auction table 조회 속도 측정용 query
SELECT *
FROM auction
WHERE end_date < now()
  AND auction_status LIKE 'CONCLUDED';


-- bid table index 조회
SELECT *
FROM pg_indexes
WHERE tablename = 'bid';

-- bid table index 추가
CREATE INDEX index_bid_auction_id ON bid (auction_id);

-- bid table index 삭제
DROP INDEX index_bid_auction_id;

-- bid table 조회 속도 측정용 query
SELECT *
FROM bid
WHERE auction_id = 1;