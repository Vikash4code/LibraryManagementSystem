-- Seed data, runs on every startup. INSERT IGNORE keeps it idempotent
-- (rows are skipped when the unique email / isbn already exists).

-- default admin -> email: admin@library.com / password: admin123
INSERT IGNORE INTO users (name, email, password, role, created_at)
VALUES ('Admin', 'admin@library.com', '$2a$10$znrJqRR3MjNt3iM6dEE5rO2OuI7Kc2niUxzGT9.32ckJXqPhzOPPC', 'ADMIN', NOW());

INSERT IGNORE INTO books (title, author, isbn, category, total_copies, available_copies, cover_image_url, description) VALUES
('Clean Code', 'Robert C. Martin', '9780132350884', 'Programming', 5, 5, 'https://covers.openlibrary.org/b/isbn/9780132350884-M.jpg', 'A handbook of agile software craftsmanship with practical advice on writing readable, maintainable code.'),
('The Pragmatic Programmer', 'Andrew Hunt, David Thomas', '9780201616224', 'Programming', 4, 4, 'https://covers.openlibrary.org/b/isbn/9780201616224-M.jpg', 'Classic guide covering the core of what it means to be a modern software developer.'),
('Effective Java', 'Joshua Bloch', '9780134685991', 'Programming', 3, 3, 'https://covers.openlibrary.org/b/isbn/9780134685991-M.jpg', 'Best practices for the Java platform, covering language and library features.'),
('To Kill a Mockingbird', 'Harper Lee', '9780061120084', 'Fiction', 4, 4, 'https://covers.openlibrary.org/b/isbn/9780061120084-M.jpg', 'A novel about justice and childhood in the American South.'),
('1984', 'George Orwell', '9780451524935', 'Fiction', 6, 6, 'https://covers.openlibrary.org/b/isbn/9780451524935-M.jpg', 'A dystopian story of surveillance, control and the struggle for truth.'),
('The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 'Fiction', 3, 3, 'https://covers.openlibrary.org/b/isbn/9780743273565-M.jpg', 'A portrait of the Jazz Age and the American dream.'),
('Pride and Prejudice', 'Jane Austen', '9780141439518', 'Fiction', 4, 4, 'https://covers.openlibrary.org/b/isbn/9780141439518-M.jpg', 'A witty story of love and social standing in Regency England.'),
('Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', '9780062316097', 'History', 5, 5, 'https://covers.openlibrary.org/b/isbn/9780062316097-M.jpg', 'How Homo sapiens came to dominate the world, from the Stone Age to today.'),
('A Brief History of Time', 'Stephen Hawking', '9780553380163', 'Science', 3, 3, 'https://covers.openlibrary.org/b/isbn/9780553380163-M.jpg', 'An exploration of the universe, black holes and the nature of time.'),
('The Selfish Gene', 'Richard Dawkins', '9780198788607', 'Science', 2, 2, 'https://covers.openlibrary.org/b/isbn/9780198788607-M.jpg', 'A gene-centred view of evolution that changed how we think about life.'),
('Atomic Habits', 'James Clear', '9780735211292', 'Self-Help', 6, 6, 'https://covers.openlibrary.org/b/isbn/9780735211292-M.jpg', 'A proven framework for building good habits and breaking bad ones.'),
('Deep Work', 'Cal Newport', '9781455586691', 'Self-Help', 3, 3, 'https://covers.openlibrary.org/b/isbn/9781455586691-M.jpg', 'Rules for focused success in a distracted world.'),
('The Alchemist', 'Paulo Coelho', '9780062315007', 'Fiction', 5, 5, 'https://covers.openlibrary.org/b/isbn/9780062315007-M.jpg', 'A fable about following your dream and listening to your heart.'),
('Wings of Fire', 'A.P.J. Abdul Kalam', '9788173711466', 'Biography', 4, 4, 'https://covers.openlibrary.org/b/isbn/9788173711466-M.jpg', 'The autobiography of India''s missile man and former president.'),
('The Da Vinci Code', 'Dan Brown', '9780307474278', 'Thriller', 4, 4, 'https://covers.openlibrary.org/b/isbn/9780307474278-M.jpg', 'A fast-paced mystery of codes, art and secret societies.');
