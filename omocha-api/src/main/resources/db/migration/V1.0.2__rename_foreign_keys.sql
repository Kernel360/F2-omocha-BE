-- V1.0.2__rename_foreign_keys.sql

-- 1. answer 테이블 외래 키 제약조건 이름 변경
DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fk8frr4bcabmmeyyu60qt7iiblo'
          AND table_name = 'answer'
          AND constraint_type = 'FOREIGN KEY'
    ) THEN
ALTER TABLE public.answer
    RENAME CONSTRAINT fk8frr4bcabmmeyyu60qt7iiblo TO fk_answer_question;
END IF;
END$$;

-- 2. question 테이블 외래 키 제약조건 이름 변경
DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fk1nuuke7olg7b9fkyp2ba9d5bx'
          AND table_name = 'question'
          AND constraint_type = 'FOREIGN KEY'
    ) THEN
ALTER TABLE public.question
    RENAME CONSTRAINT fk1nuuke7olg7b9fkyp2ba9d5bx TO fk_question_member;
END IF;
END$$;

-- 3. category 테이블 외래 키 제약조건 이름 변경
DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fk2y94svpmqttx80mshyny85wqr'
          AND table_name = 'category'
          AND constraint_type = 'FOREIGN KEY'
    ) THEN
ALTER TABLE public.category
    RENAME CONSTRAINT fk2y94svpmqttx80mshyny85wqr TO fk_category_parent;
END IF;
END$$;

-- 4. likes 테이블 외래 키 제약조건 이름 변경
DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fk33wrsbcjwgmtejka8cr2sylci'
          AND table_name = 'likes'
          AND constraint_type = 'FOREIGN KEY'
    ) THEN
ALTER TABLE public.likes
    RENAME CONSTRAINT fk33wrsbcjwgmtejka8cr2sylci TO fk_likes_auction;
END IF;
END$$;

-- 5. auction_category 테이블 외래 키 제약조건 이름 변경
DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fk74rpubea9n5lfcehiaoy48gj4'
          AND table_name = 'auction_category'
          AND constraint_type = 'FOREIGN KEY'
    ) THEN
ALTER TABLE public.auction_category
    RENAME CONSTRAINT fk74rpubea9n5lfcehiaoy48gj4 TO fk_auction_category_auction;
END IF;

    IF EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fkio3g0qd141hw19el0u9tiauss'
          AND table_name = 'auction_category'
          AND constraint_type = 'FOREIGN KEY'
    ) THEN
ALTER TABLE public.auction_category
    RENAME CONSTRAINT fkio3g0qd141hw19el0u9tiauss TO fk_auction_category_category;
END IF;
END$$;

-- 6. conclude 테이블 외래 키 제약조건 이름 변경
DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fk8yapmasage02mlf90n3f63d2c'
          AND table_name = 'conclude'
          AND constraint_type = 'FOREIGN KEY'
    ) THEN
ALTER TABLE public.conclude
    RENAME CONSTRAINT fk8yapmasage02mlf90n3f63d2c TO fk_conclude_auction;
END IF;

    IF EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fkbb7irc6603yk409lcsr1bl9gt'
          AND table_name = 'conclude'
          AND constraint_type = 'FOREIGN KEY'
    ) THEN
ALTER TABLE public.conclude
    RENAME CONSTRAINT fkbb7irc6603yk409lcsr1bl9gt TO fk_conclude_buyer_member;
END IF;
END$$;

-- 7. review 테이블 외래 키 제약조건 이름 변경
DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fkd6olodb87t163x91q898a0glb'
          AND table_name = 'review'
          AND constraint_type = 'FOREIGN KEY'
    ) THEN
ALTER TABLE public.review
    RENAME CONSTRAINT fkd6olodb87t163x91q898a0glb TO fk_review_reviewer_member;
END IF;

    IF EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fkjsjurbqf42uwmoqeim6fb5303'
          AND table_name = 'review'
          AND constraint_type = 'FOREIGN KEY'
    ) THEN
ALTER TABLE public.review
    RENAME CONSTRAINT fkjsjurbqf42uwmoqeim6fb5303 TO fk_review_auction;
END IF;

    IF EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fknffnirl78jdjsl8s5m6speys2'
          AND table_name = 'review'
          AND constraint_type = 'FOREIGN KEY'
    ) THEN
ALTER TABLE public.review
    RENAME CONSTRAINT fknffnirl78jdjsl8s5m6speys2 TO fk_review_recipient_member;
END IF;
END$$;

-- 8. bid 테이블 외래 키 제약조건 이름 변경
DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fkhexc6i4j8i0tmpt8bdulp6g3g'
          AND table_name = 'bid'
          AND constraint_type = 'FOREIGN KEY'
    ) THEN
ALTER TABLE public.bid
    RENAME CONSTRAINT fkhexc6i4j8i0tmpt8bdulp6g3g TO fk_bid_auction;
END IF;

    IF EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fksoi7tf9vbgxl7d2i46a2ecixu'
          AND table_name = 'bid'
          AND constraint_type = 'FOREIGN KEY'
    ) THEN
ALTER TABLE public.bid
    RENAME CONSTRAINT fksoi7tf9vbgxl7d2i46a2ecixu TO fk_bid_buyer_member;
END IF;
END$$;

-- 9. image 테이블 외래 키 제약조건 이름 변경
DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fksiflb2bv43tsv56raj5lqhi9l'
          AND table_name = 'image'
          AND constraint_type = 'FOREIGN KEY'
    ) THEN
ALTER TABLE public.image
    RENAME CONSTRAINT fksiflb2bv43tsv56raj5lqhi9l TO fk_image_auction;
END IF;
END$$;

-- 10. likes 테이블 외래 키 제약조건 이름 변경 (member_id)
DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fka4vkf1skcfu5r6o5gfb5jf295'
          AND table_name = 'likes'
          AND constraint_type = 'FOREIGN KEY'
    ) THEN
ALTER TABLE public.likes
    RENAME CONSTRAINT fka4vkf1skcfu5r6o5gfb5jf295 TO fk_likes_member;
END IF;
END$$;

-- 11. question 테이블 외래 키 제약조건 이름 변경
DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fksx2rd80gnai0sdw0kh86rkf9x'
          AND table_name = 'question'
          AND constraint_type = 'FOREIGN KEY'
    ) THEN
ALTER TABLE public.question
    RENAME CONSTRAINT fksx2rd80gnai0sdw0kh86rkf9x TO fk_question_auction;
END IF;
END$$;