DO $$
BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'transaction_direction') THEN
CREATE TYPE transaction_direction AS ENUM ('INCOME', 'EXPENSE');
END IF;
END $$;

CREATE TABLE IF NOT EXISTS transactions (
                                            id BIGSERIAL PRIMARY KEY,
                                            amount NUMERIC(38, 2),
    direction transaction_direction,
    created_at TIMESTAMP WITH TIME ZONE,
                             account_id BIGINT,
                             category_id BIGINT
                             );

CREATE TABLE IF NOT EXISTS transaction_tags (
                                                transaction_id BIGINT NOT NULL,
                                                tag_id BIGINT NOT NULL,
                                                PRIMARY KEY (transaction_id, tag_id)
    );