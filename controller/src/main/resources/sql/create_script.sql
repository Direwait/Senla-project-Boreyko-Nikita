CREATE TABLE users (
    user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,      
    user_username VARCHAR(50) UNIQUE, 					
    user_password VARCHAR(255),  						
    user_email VARCHAR(100) UNIQUE,            					
	user_description TEXT,                    					
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
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
	book_quantity INT												
);

----
CREATE TABLE catalogs (
    catalog_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,       	
    catalog_title VARCHAR(255) NOT NULL,          					
    catalog_parent_id INT,               							
    FOREIGN KEY (catalog_parent_id) REFERENCES catalogs(catalog_id) ON DELETE CASCADE
);

----
CREATE TABLE book_catalog (
	book_catalog_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,	
	book_id INT NOT NULL,                							
    catalog_id INT NOT NULL,             							
    FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE,
    FOREIGN KEY (catalog_id) REFERENCES catalogs(catalog_id) ON DELETE CASCADE
);

----
CREATE TABLE rental (
    rental_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,     
    user_id INT NOT NULL,                						
    book_id INT NOT NULL,                						
    rental_date DATE NOT NULL,           						
    rental_return_date DATE,                    						
    rental_actual_return_date DATE,             						
	rental_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE
);

----
CREATE TABLE request (
    request_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    request_date DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (book_id) REFERENCES book(book_id)
);

DROP TABLE request;
DROP TABLE rental;
DROP TABLE book_catalog;
DROP TABLE catalogs;
DROP TABLE book;
DROP TABLE users;