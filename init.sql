CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    amount DECIMAL NOT NULL CHECK (amount >= 0)
);

CREATE TABLE transactions (
    transaction_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    amount DECIMAL NOT NULL,
    currency VARCHAR(10) NOT NULL,
    sender_id UUID NOT NULL,
    receiver_id UUID NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES users(id),
    CONSTRAINT fk_receiver FOREIGN KEY (receiver_id) REFERENCES users(id)
);

CREATE OR REPLACE FUNCTION process_transaction()
RETURNS TRIGGER AS $$
DECLARE sender_balance DECIMAL;
BEGIN
    SELECT amount INTO sender_balance FROM users WHERE id = NEW.sender_id;

    IF sender_balance < NEW.amount THEN
        UPDATE transactions SET status = 'failed', updated_at = NOW() WHERE transaction_id = NEW.transaction_id;
        RETURN NEW;
    END IF;

    UPDATE users SET amount = amount - NEW.amount WHERE id = NEW.sender_id;
    UPDATE users SET amount = amount + NEW.amount WHERE id = NEW.receiver_id;
    UPDATE transactions SET status = 'completed', updated_at = NOW() WHERE transaction_id = NEW.transaction_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER transaction_trigger
AFTER INSERT ON transactions
FOR EACH ROW EXECUTE FUNCTION process_transaction();
