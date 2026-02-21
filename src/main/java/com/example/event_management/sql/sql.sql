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

use event_management

INSERT INTO social_media_sources (name) VALUES
('Facebook'),
('Instagram'),
('Friend'),
('Advertisement'),
('LinkedIn'),
('Twitter'),
('WhatsApp'),
('Email'),
('Company Website');

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
('Brazil', '+55');
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


-------------------------------------------------

INSERT INTO `admins` (`id`, `email`, `name`, `password`, `status`) VALUES
(1, 'admin@admin.com', 'Admin', '$2a$10$WX48QmkAsF/.LCbncsT8x.L3tcOCMALdztI4Ji6crsUeeM2.sGe0m', 'ACTIVE');

CREATE TABLE otp_transactions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_otp_id VARCHAR(50) NOT NULL UNIQUE,
    user_role VARCHAR(50),
    type VARCHAR(20) NOT NULL,       -- PHONE or EMAIL
    type_value VARCHAR(100) NOT NULL,
    otp VARCHAR(6) NOT NULL,
    otp_status VARCHAR(20) DEFAULT 'PENDING', -- PENDING, EXPIRED, USED
    otp_expiry TIMESTAMP NOT NULL,
    otp_count INT DEFAULT 0,         -- send + resend
    created_by VARCHAR(50),
    modified_by VARCHAR(50),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

use event_management

desc events



---------------------------------------------------

INSERT INTO vendor_types (name, status)
VALUES
('DJ', 'ACTIVE'),
('Catering', 'ACTIVE'),
('Decoration', 'ACTIVE'),
('Photography', 'ACTIVE'),
('Videography', 'ACTIVE'),
('Makeup Artist', 'ACTIVE'),
('Choreographer', 'ACTIVE'),
('Wedding Planner', 'ACTIVE'),
('Invitation Designer', 'ACTIVE'),
('Sound & Lighting', 'ACTIVE'),
('Stage Designer', 'ACTIVE'),
('Venue Decorator', 'ACTIVE'),
('Mehndi Artist', 'ACTIVE'),
('Florist', 'ACTIVE'),
('Band', 'ACTIVE'),
('Car Rental', 'ACTIVE'),
('Pandit / Priest', 'ACTIVE'),
('Entertainment (Singer / Performer)', 'ACTIVE'),
('Gift & Return Favors', 'ACTIVE'),
('Event Coordinator', 'ACTIVE');


-----------------------------------


INSERT INTO wedding_side_master (side_name, description, status)
VALUES
('Groom', 'Represents the groom side for wedding events', 'ACTIVE'),
('Bride', 'Represents the bride side for wedding events', 'ACTIVE'),
('Joint', 'Represents both groom and bride sides together', 'ACTIVE');
