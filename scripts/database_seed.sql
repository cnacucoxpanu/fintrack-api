-- Очистка таблиц (если нужно)
TRUNCATE TABLE transaction_tags, transactions, tags, categories, accounts, users RESTART IDENTITY CASCADE;

-- Пользователи
INSERT INTO users (username, email, full_name) VALUES
('john_doe', 'john@example.com', 'John Doe'),
('jane_smith', 'jane@example.com', 'Jane Smith'),
('bob_wilson', 'bob@example.com', 'Bob Wilson'),
('alice_brown', 'alice@example.com', 'Alice Brown');

-- Счета (OneToMany: User → Accounts)
INSERT INTO accounts (name, balance, currency, user_id) VALUES
('Main Checking', 5000.00, 'USD', 1),
('Savings Account', 15000.00, 'USD', 1),
('Credit Card', -1200.00, 'USD', 1),
('Business Account', 8500.00, 'USD', 2),
('Personal Wallet', 3200.00, 'EUR', 2),
('Investment Account', 25000.00, 'USD', 3),
('Emergency Fund', 10000.00, 'USD', 4),
('Travel Fund', 2500.00, 'USD', 4);

-- Категории
INSERT INTO categories (name, description) VALUES
('Salary', 'Monthly salary and bonuses'),
('Groceries', 'Food and household items'),
('Transport', 'Gas, public transport, taxi'),
('Entertainment', 'Movies, games, hobbies'),
('Utilities', 'Electricity, water, internet'),
('Healthcare', 'Medical expenses and insurance'),
('Shopping', 'Clothing and personal items'),
('Restaurants', 'Dining out and cafes'),
('Freelance', 'Freelance project income'),
('Investment', 'Dividends and returns');

-- Теги (для ManyToMany)
INSERT INTO tags (name, color) VALUES
('urgent', '#ef4444'),
('recurring', '#3b82f6'),
('business', '#8b5cf6'),
('personal', '#10b981'),
('tax-deductible', '#f59e0b'),
('subscription', '#ec4899'),
('one-time', '#06b6d4'),
('planned', '#84cc16');

-- Транзакции (OneToMany: Account → Transactions, Category → Transactions)
INSERT INTO transactions (amount, description, date, direction, account_id, category_id) VALUES
-- John's transactions
(3500.00, 'Monthly Salary - April', '2026-04-01', 'INCOME', 1, 1),
(150.50, 'Weekly groceries at Walmart', '2026-04-05', 'EXPENSE', 1, 2),
(45.00, 'Gas station fill-up', '2026-04-06', 'EXPENSE', 1, 3),
(89.99, 'Netflix and Spotify subscriptions', '2026-04-07', 'EXPENSE', 1, 4),
(120.00, 'Electricity bill', '2026-04-08', 'EXPENSE', 1, 5),
(250.00, 'Doctor visit and medication', '2026-04-10', 'EXPENSE', 1, 6),
(4200.00, 'Freelance web development project', '2026-04-12', 'INCOME', 2, 9),
(180.00, 'New running shoes', '2026-04-13', 'EXPENSE', 2, 7),
(65.50, 'Dinner at Italian restaurant', '2026-04-14', 'EXPENSE', 2, 8),
(75.00, 'Monthly gym membership', '2026-04-15', 'EXPENSE', 1, 4),

-- Jane's transactions
(5500.00, 'Monthly Salary - April', '2026-04-01', 'INCOME', 4, 1),
(2000.00, 'Client consulting payment', '2026-04-15', 'INCOME', 4, 9),
(95.00, 'Office supplies and stationery', '2026-04-16', 'EXPENSE', 4, 7),
(200.00, 'Groceries and household items', '2026-04-05', 'EXPENSE', 5, 2),
(50.00, 'Uber rides', '2026-04-08', 'EXPENSE', 5, 3),
(35.00, 'Coffee shop', '2026-04-10', 'EXPENSE', 5, 8),

-- Bob's transactions
(500.00, 'Investment dividend payment', '2026-04-17', 'INCOME', 6, 10),
(1500.00, 'Stock market gains', '2026-04-15', 'INCOME', 6, 10),
(300.00, 'Groceries for the month', '2026-04-03', 'EXPENSE', 6, 2),
(80.00, 'Internet and phone bill', '2026-04-05', 'EXPENSE', 6, 5),

-- Alice's transactions
(4000.00, 'Monthly Salary - April', '2026-04-01', 'INCOME', 7, 1),
(500.00, 'Emergency car repair', '2026-04-10', 'EXPENSE', 7, 3),
(150.00, 'Groceries', '2026-04-06', 'EXPENSE', 7, 2),
(1000.00, 'Vacation savings transfer', '2026-04-15', 'INCOME', 8, 1),
(200.00, 'Flight tickets booking', '2026-04-16', 'EXPENSE', 8, 4);

-- ManyToMany связи: Транзакции ↔ Теги
INSERT INTO transaction_tags (transaction_id, tag_id) VALUES
-- John's transactions
(1, 2), (1, 4), -- Salary: recurring, personal
(2, 2), (2, 4), (2, 8), -- Groceries: recurring, personal, planned
(3, 4), -- Gas: personal
(4, 2), (4, 6), (4, 4), -- Subscriptions: recurring, subscription, personal
(5, 2), (5, 4), (5, 8), -- Electricity: recurring, personal, planned
(6, 1), (6, 4), -- Doctor: urgent, personal
(7, 3), (7, 5), (7, 7), -- Freelance: business, tax-deductible, one-time
(8, 4), (8, 7), -- Shoes: personal, one-time
(9, 4), -- Restaurant: personal
(10, 2), (10, 6), (10, 4), -- Gym: recurring, subscription, personal

-- Jane's transactions
(11, 2), (11, 4), -- Salary: recurring, personal
(12, 3), (12, 5), (12, 7), -- Consulting: business, tax-deductible, one-time
(13, 3), (13, 5), -- Office supplies: business, tax-deductible
(14, 2), (14, 4), (14, 8), -- Groceries: recurring, personal, planned
(15, 4), -- Uber: personal
(16, 4), -- Coffee: personal

-- Bob's transactions
(17, 3), (17, 2), -- Dividend: business, recurring
(18, 3), (18, 7), -- Stock gains: business, one-time
(19, 4), (19, 8), -- Groceries: personal, planned
(20, 2), (20, 4), (20, 8), -- Bills: recurring, personal, planned

-- Alice's transactions
(21, 2), (21, 4), -- Salary: recurring, personal
(22, 1), (22, 7), -- Car repair: urgent, one-time
(23, 2), (23, 4), (23, 8), -- Groceries: recurring, personal, planned
(24, 8), (24, 7), -- Vacation savings: planned, one-time
(25, 8), (25, 7); -- Flight tickets: planned, one-time
