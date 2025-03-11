CREATE TABLE users (
    user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_username VARCHAR(50) UNIQUE,
    user_password VARCHAR(255),
    user_email VARCHAR(100) UNIQUE,
	user_description TEXT,
    role VARCHAR(20) NOT NULL DEFAULT 'user',
    user_registration_date DATE DEFAULT CURRENT_DATE
);

----
CREATE TABLE book (
    book_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    book_title VARCHAR(255) NOT NULL,
    book_author VARCHAR(255) NOT NULL,
    book_publication INT,
    book_isbn VARCHAR(20) UNIQUE,
    book_description TEXT,
    book_available BOOLEAN DEFAULT TRUE,
	book_quantity INT,
    created_at DATE DEFAULT CURRENT_DATE
);

----
CREATE TABLE catalogs (
    catalog_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    catalog_title VARCHAR(255) NOT NULL,
    parent_catalog_id INT,
    FOREIGN KEY (parent_catalog_id) REFERENCES catalogs(catalog_id) ON DELETE CASCADE
);

----
CREATE TABLE book_catalog (
	booc_catalog_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	book_id INT NOT NULL,
    catalog_id INT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE,
    FOREIGN KEY (catalog_id) REFERENCES Catalogs(catalog_id) ON DELETE CASCADE
);

----
CREATE TABLE rental (
    rental_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    rental_date DATE NOT NULL,
    return_date DATE,
    actual_return_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE
);

----
CREATE TABLE request (
    request_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    request_date DATE DEFAULT CURRENT_DATE,
    request_status VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (book_id) REFERENCES book(book_id)
);

DROP TABLE request;
DROP TABLE rental;
DROP TABLE book_catalog;
DROP TABLE catalogs;
DROP TABLE book;
DROP TABLE users;