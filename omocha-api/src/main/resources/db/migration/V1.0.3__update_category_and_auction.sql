-- V1.0.3__update_category_and_auction.sql

-- 1. category 테이블에 order_index 컬럼 추가
ALTER TABLE public.category
    ADD COLUMN IF NOT EXISTS order_index INT;

-- 2. auction 테이블에 category_id 컬럼 추가 (일단 NULL 허용)
ALTER TABLE public.auction
    ADD COLUMN IF NOT EXISTS category_id BIGINT;

-- 3. auction_category 테이블의 데이터를 auction 테이블로 마이그레이션
UPDATE public.auction a
SET category_id = ac.category_id
    FROM public.auction_category ac
WHERE a.auction_id = ac.auction_id;

-- 4. auction 테이블의 category_id 컬럼을 NOT NULL로 변경
ALTER TABLE public.auction
    ALTER COLUMN category_id SET NOT NULL;

-- 5. auction_category 테이블의 기본 키 및 고유 키 제약조건 삭제
ALTER TABLE public.auction_category
DROP CONSTRAINT IF EXISTS fk_auction_category_auction;

-- 6. auction_category 테이블의 기본 키 및 고유 키 제약조건 삭제
ALTER TABLE public.auction_category
DROP CONSTRAINT IF EXISTS fk_auction_category_category;

-- 7. auction_category 테이블 삭제
DROP TABLE IF EXISTS public.auction_category;
