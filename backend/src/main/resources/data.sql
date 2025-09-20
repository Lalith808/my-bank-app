-- Insert user with EXPLICIT column names and values
INSERT INTO users (
    account_number,
    password,
    first_name,
    last_name,
    date_of_birth,
    gender,
    address,
    phone_number,
    email,
    citizenship_number
) VALUES (
    'ACC1234567',
    'password123',
    'Alice',
    'Smith',
    '1990-05-15',
    'Female',
    '123 Main St, City',
    '555-1234',
    'alice@example.com',
    'CIT123456789'
);

-- Insert account
INSERT INTO accounts (user_id, balance) VALUES (1, 5000.00);

-- Insert transactions
INSERT INTO transactions (from_account_id, to_account_id, amount, transaction_type, remarks, transaction_date, account_id)
VALUES 
(1, NULL, 2000.00, 'DEPOSIT', 'Initial Deposit', CURRENT_TIMESTAMP - INTERVAL '10' DAY, 1),
(1, NULL, 150.00, 'TRANSFER', 'Sent to friend', CURRENT_TIMESTAMP - INTERVAL '5' DAY, 1),
(1, NULL, 85.50, 'WITHDRAWAL', 'ATM Withdrawal', CURRENT_TIMESTAMP - INTERVAL '2' DAY, 1);