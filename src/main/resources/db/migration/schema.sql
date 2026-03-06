CREATE TABLE IF NOT EXISTS url_records (
    id BIGSERIAL PRIMARY KEY,
    original_url TEXT NOT NULL,
    short_code VARCHAR(20) UNIQUE NOT NULL,
    click_count BIGINT DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    expires_in TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_short_code
ON url_records(short_code);

CREATE INDEX IF NOT EXISTS idx_original_url
ON url_records(original_url);