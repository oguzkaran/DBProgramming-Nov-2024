create database dpn24_car_care_service_db

use dpn24_car_care_service_db

CREATE TABLE customers (
    customer_id INT PRIMARY KEY,
    first_name NVARCHAR(50) NOT NULL,
    last_name NVARCHAR(50) NOT NULL,
    title NVARCHAR(50),
    details NVARCHAR(MAX)
);

CREATE TABLE contact_info (
    contact_id INT PRIMARY KEY,
    customer_id INT NOT NULL,
    email NVARCHAR(100),
    phone_number CHAR(11),
    address_line1 NVARCHAR(50),
    address_line2 NVARCHAR(50),
    address_line3 NVARCHAR(50),
    city NVARCHAR(50),
    state NVARCHAR(50),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

CREATE TABLE manufacturers (
    manufacturer_id INT PRIMARY KEY,
    name NVARCHAR(50) NOT NULL,
    details NVARCHAR(MAX)
);

CREATE TABLE models (
    model_id INT PRIMARY KEY,
    manufacturer_id INT NOT NULL,
    daily_hire_rate INT NOT NULL,
    name NVARCHAR(50),
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(manufacturer_id)
);

CREATE TABLE cars (
    licence_number INT PRIMARY KEY,
    customer_id INT NOT NULL,
    model_id INT NOT NULL,
    current_mileage BIGINT,
    engine_size NVARCHAR(10),
    details NVARCHAR(MAX),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (model_id) REFERENCES models(model_id)
);

CREATE TABLE bookings (
    booking_id INT PRIMARY KEY,
    licence_number INT NOT NULL,
    datetime_of_service DATETIME NOT NULL,
    payment_received BIT NOT NULL,
    details NVARCHAR(MAX),
    FOREIGN KEY (licence_number) REFERENCES cars(licence_number)
);

CREATE TABLE mechanics (
    mechanic_id INT PRIMARY KEY,
    booking_id INT NOT NULL,
    name NVARCHAR(100) NOT NULL,
    details NVARCHAR(100),
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id)
);