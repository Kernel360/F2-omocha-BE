-- member 테이블의 컬럼 추가
alter table member
    add column IF NOT EXISTS email_verified boolean;