CREATE TABLE event_registration (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255),
    member_type_id BIGINT,
    gender VARCHAR(50),
    heard_source_id BIGINT,
    state_id BIGINT,
    event_id BIGINT,
    address TEXT,
    zipcode VARCHAR(20),
    added_by ENUM('admin', 'self') NOT NULL DEFAULT 'self',
    qr_code_path VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (member_type_id) REFERENCES event_member_types(id),
    FOREIGN KEY (heard_source_id) REFERENCES social_media_sources(id),
    FOREIGN KEY (state_id) REFERENCES states(id),
    FOREIGN KEY (event_id) REFERENCES events(id)
);


-----------------------------------------------------------------------------------

CREATE TABLE social_media_sources (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL -- e.g., Facebook, Instagram, Friend, Advertisement
);

INSERT INTO social_media_sources (name) VALUES
('Facebook'),
('Instagram'),
('Friend'),
('Advertisement'),
('LinkedIn'),
('Twitter'),
('WhatsApp'),
('Email'),
('Company Website'),
('Other');

---------------------------------------------------------------------------


CREATE TABLE countries (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

INSERT INTO countries (name, country_code) VALUES
('India', '+91'),
('United States', '+1'),
('United Kingdom', '+44'),
('Canada', '+1'),
('Australia', '+61'),
('Germany', '+49'),
('France', '+33'),
('Japan', '+81'),
('China', '+86'),
('Brazil', '+55')
-----------------------------------------------------------------------------------
CREATE TABLE states (
     id INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     country_id INT NOT NULL,
     FOREIGN KEY (country_id) REFERENCES countries(id)
 );
 
INSERT INTO states (name, country_id) VALUES
('Andhra Pradesh', 1),
('Arunachal Pradesh', 1),
('Assam', 1),
('Bihar', 1),
('Chhattisgarh', 1),
('Goa', 1),
('Gujarat', 1),
('Haryana', 1),
('Himachal Pradesh', 1),
('Jharkhand', 1),
('Karnataka', 1),
('Kerala', 1),
('Madhya Pradesh', 1),
('Maharashtra', 1),
('Manipur', 1),
('Meghalaya', 1),
('Mizoram', 1),
('Nagaland', 1),
('Odisha', 1),
('Punjab', 1),
('Rajasthan', 1),
('Sikkim', 1),
('Tamil Nadu', 1),
('Telangana', 1),
('Tripura', 1),
('Uttar Pradesh', 1),
('Uttarakhand', 1),
('West Bengal', 1),
('Andaman and Nicobar Islands', 1),
('Chandigarh', 1),
('Dadra and Nagar Haveli and Daman and Diu', 1),
('Delhi', 1),
('Jammu and Kashmir', 1),
('Ladakh', 1),
('Lakshadweep', 1),
('Puducherry', 1);
