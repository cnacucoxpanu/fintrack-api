CREATE TYPE transaction_direction AS ENUM ('INCOME', 'EXPENSE');

-- 1. Сначала создаем кастомный тип данных ENUM (если его еще нет)
DO $$
BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'transaction_direction') THEN
CREATE TYPE transaction_direction AS ENUM ('INCOME', 'EXPENSE');
END IF;
END $$;

-- 2. Создаем основную таблицу transactions
CREATE TABLE IF NOT EXISTS transactions (
                                            id BIGSERIAL PRIMARY KEY,
                                            amount NUMERIC(38, 2), -- В Java это BigDecimal, в базе обычно numeric
    direction transaction_direction, -- Твой кастомный ENUM
    created_at TIMESTAMP WITH TIME ZONE, -- В Java это OffsetDateTime
                             account_id BIGINT, -- Связь @ManyToOne
                             category_id BIGINT -- Связь @ManyToOne
                             );

-- 3. Создаем связующую таблицу для @ManyToMany (Transaction <-> Tag)
CREATE TABLE IF NOT EXISTS transaction_tags (
                                                transaction_id BIGINT NOT NULL,
                                                tag_id BIGINT NOT NULL,
                                                PRIMARY KEY (transaction_id, tag_id)
    );
