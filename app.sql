CREATE TABLE theatre (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL
);

CREATE TABLE movie (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(255),
    language VARCHAR(255),
    duration INT,
    release_date DATE
);

CREATE TABLE show (
    id SERIAL PRIMARY KEY,
    show_time TIMESTAMP NOT NULL,
    movie_id BIGINT NOT NULL,
    theatre_id BIGINT NOT NULL,
    available_seats INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_movie FOREIGN KEY (movie_id) REFERENCES movie(id),
    CONSTRAINT fk_theatre FOREIGN KEY (theatre_id) REFERENCES theatre(id)
);

CREATE TABLE booking (
    id SERIAL PRIMARY KEY,
    booking_time TIMESTAMP NOT NULL,
    show_id BIGINT NOT NULL,
    number_of_seats INT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_show FOREIGN KEY (show_id) REFERENCES show(id)
);

CREATE TABLE seat (
    id SERIAL PRIMARY KEY,
    seat_number VARCHAR(10) NOT NULL,
    is_booked BOOLEAN NOT NULL,
    show_id BIGINT,
    booking_id BIGINT,
    CONSTRAINT fk_show FOREIGN KEY (show_id) REFERENCES show(id),
    CONSTRAINT fk_booking FOREIGN KEY (booking_id) REFERENCES booking(id)
);

CREATE TABLE city (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Insert data into movie table
INSERT INTO movie (title, genre, language, duration, release_date) VALUES
('Inception', 'Sci-Fi', 'English', 148, '2010-07-16'),
('The Dark Knight', 'Action', 'English', 152, '2008-07-18');

-- Insert data into theatre table
INSERT INTO theatre (name, city, location) VALUES
('AMC Empire 25', 'New York', '234 W 42nd St'),
('AMC Lincoln Square 13', 'New York', '1998 Broadway'),
('Regal LA Live', 'Los Angeles', '1000 W Olympic Blvd');

-- Insert data into show table with ticket price
INSERT INTO show (show_time, movie_id, theatre_id, available_seats, price) VALUES
('2023-10-01 14:00:00', 1, 1, 100, 10.00),
('2023-10-01 18:00:00', 1, 1, 100, 12.00),
('2023-10-01 16:00:00', 1, 2, 120, 15.00),
('2023-10-01 20:00:00', 2, 3, 150, 20.00);

INSERT INTO seat (seat_number, show_id, is_booked, booking_id) VALUES
('A1', 1, FALSE, NULL),
('A2', 1, FALSE, NULL),
('B1', 2, FALSE, NULL),
('B2', 2, FALSE, NULL),
('C1', 3, FALSE, NULL),
('C2', 3, FALSE, NULL),
('D1', 4, FALSE, NULL),
('D2', 4, FALSE, NULL);

-- Insert data into city table
INSERT INTO city (name) VALUES
('New York'),
('Los Angeles');

CREATE TABLE booking_response (
    id SERIAL PRIMARY KEY,
    request_id BIGINT NOT NULL,
    booking_id BIGINT NOT NULL,
    CONSTRAINT fk_booking FOREIGN KEY (booking_id) REFERENCES booking(id)
);


COMMIT;