
CREATE TYPE loan_status AS ENUM('ACTIVE', 'PENDING', 'CANCELLED', 'COMPLETED');
CREATE TABLE loans (
    id INTEGER NOT NULL PRIMARY KEY,
    status loan_status NOT NULL,
    loan_date DATE NOT NULL,
    return_date DATE,
    user_id INTEGER,
    book_id INTEGER,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);