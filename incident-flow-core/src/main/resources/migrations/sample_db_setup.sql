-- Creating oncall_user table
CREATE TABLE oncall_user (
     id INT AUTO_INCREMENT PRIMARY KEY,
     user_id VARCHAR(255)
);

-- Creating active_oncall_group table
CREATE TABLE active_oncall_group (
     id INT AUTO_INCREMENT PRIMARY KEY,
     primary_user_id INT,
     secondary_user_id INT,
     role ENUM('PRIMARY', 'SECONDARY'),
     FOREIGN KEY (primary_user_id) REFERENCES oncall_user(id),
     FOREIGN KEY (secondary_user_id) REFERENCES oncall_user(id)
);

-- Creating oncall_tracker table
CREATE TABLE oncall_tracker (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(255),
    active_oncall_group_id INT,
    oncall_status VARCHAR(255),
    status VARCHAR(255),
    priority ENUM('P0', 'P1', 'P2', 'P3'),
    FOREIGN KEY (active_oncall_group_id) REFERENCES active_oncall_group(id)
);

-- Creating oncall_suggestions table
CREATE TABLE oncall_suggestions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    oncall_tracker_id INT,
    suggestion VARCHAR(255),
    FOREIGN KEY (oncall_tracker_id) REFERENCES oncall_tracker(id)
);

-- Creating historical_users table
CREATE TABLE historical_users (
      id INT AUTO_INCREMENT PRIMARY KEY,
      oncall_tracker_id INT,
      user_id VARCHAR(255),
      FOREIGN KEY (oncall_tracker_id) REFERENCES oncall_tracker(id)
);

-- Creating oncall_users table
CREATE TABLE current_users (
   id INT AUTO_INCREMENT PRIMARY KEY,
   oncall_tracker_id INT,
   user_id VARCHAR(255),
   FOREIGN KEY (oncall_tracker_id) REFERENCES oncall_tracker(id)
);

ALTER TABLE oncall_user
    ADD COLUMN name VARCHAR(255);

-- Dropping 'role' column from active_oncall_group table
ALTER TABLE active_oncall_group
DROP COLUMN role;

-- Adding 'start_date' and 'end_date' columns to active_oncall_group table
ALTER TABLE active_oncall_group
    ADD COLUMN start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN end_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Altering the data type of 'status' column to ENUM
ALTER TABLE oncall_tracker
    MODIFY COLUMN status ENUM('ACTIVE', 'CLOSED');

-- Adding 'start_date' and 'end_date' columns to active_oncall_group table
ALTER TABLE active_oncall_group
    ADD COLUMN start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN end_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Altering the data type of 'status' column to ENUM
ALTER TABLE oncall_tracker
    MODIFY COLUMN status ENUM('ACTIVE', 'CLOSED');

-- Adding 'created_at' and 'updated_at' columns to oncall_user table
ALTER TABLE oncall_user
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- Adding 'created_at' and 'updated_at' columns to active_oncall_group table
ALTER TABLE active_oncall_group
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- Adding 'created_at' and 'updated_at' columns to oncall_tracker table
ALTER TABLE oncall_tracker
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- Adding 'created_at' and 'updated_at' columns to oncall_suggestions table
ALTER TABLE oncall_suggestions
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- Adding 'created_at' and 'updated_at' columns to historical_users table
ALTER TABLE historical_users
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- Adding 'created_at' and 'updated_at' columns to oncall_users table
ALTER TABLE current_users
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

CREATE TABLE alerts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    message VARCHAR(255),
    is_acknowledged ENUM('YES', 'NO'),
    priority ENUM('P0', 'P1', 'P2', 'P3')
);

CREATE TABLE `followups` (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `oncall_tracker_id` int(11) NOT NULL,
     `message` varchar(255) DEFAULT NULL,
     `is_acknowledged` enum('YES','NO') DEFAULT NULL,
     PRIMARY KEY (`id`),
     KEY `oncall_tracker_id` (`oncall_tracker_id`),
     CONSTRAINT `followups_ibfk_1` FOREIGN KEY (`oncall_tracker_id`) REFERENCES `oncall_tracker` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE watch_emails (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL
);

CREATE TABLE message_configuration (
   id INT AUTO_INCREMENT PRIMARY KEY,
   subject_regex VARCHAR(255),
   frequency INT
);

ALTER TABLE followups
    ADD COLUMN priority ENUM('P0', 'P1', 'P2', 'P3');

-- For the 'alerts' table
ALTER TABLE alerts
DROP COLUMN is_acknowledged;

-- For the 'followup' table
ALTER TABLE followups
DROP COLUMN is_acknowledged;

ALTER TABLE oncall_tracker
    ADD COLUMN rca_doc varchar(255);

-- Adding records to oncall_user table
INSERT INTO oncall_user (user_id, name) VALUES
    ("lokesh.kumar2", "Lokesh Kumar"),
    ("sneha.gupta", "Sneha Gupta"),
    ("alokit.kumar", "Alokit Kumar"),
    ("sagar.bhatt", "Sagar Bhatt"),
    ("mani.kk", "Mani Kandan"),
    ("amitrajit.bose", "Amitrajit Bose"),
    ("shreyas.jain", "Shreyas Jain");

-- Adding records to active_oncall_group table
INSERT INTO active_oncall_group (id, primary_user_id, secondary_user_id, start_date, end_date) VALUES
    (1, 1, 2, '2023-10-22', '2023-10-29');

-- Adding records to oncall_tracker table
INSERT INTO oncall_tracker (title, description, active_oncall_group_id, oncall_status, status, priority) VALUES
     ("Mobile Model Failure", "Mobile Model Failed At Trigger_DSP Step", 1, "Waiting for response from ds-oncall", "ACTIVE", "P1"),
     ("Duplicacy in Retail Forecast", "Records present multiple times for the same fsn x day x cluster combination", 1, "Engg team working on the fix", "ACTIVE", "P0"),
     ("Grocery Flow Not Getting Killed", "Kill button not working. Grocery flow running from 2 days", 1, "Will be taken as sprint task", "CLOSED", "P3"),
     ("Event Calendar Not Updated", "All Flows are failing because event calendar is not updated.", 1, "Waiting for response from ankitkumar.pandey", "ACTIVE", "P1"),
     ("LTA Model Failure", "LTA Post Processing Flow Failed", 1, "Fix Made By Engg Team", "CLOSED", "P1"),
     ("Validation Failure For Grooming Sell Plan", "Grooming Sell Plan Validation Failure Observed due to user-error", 1, "Waiting for stakeholders to make correction", "ACTIVE", "P0");


-- Adding records to oncall_suggestions table
INSERT INTO oncall_suggestions (oncall_tracker_id, suggestion) VALUES
       (1, "It possibly failed because event_calendar is outdated, check it"),
       (1, "Check sales_history_base_fact, whether hive copy is in sync with fdp_fact. Out of sync sales table can generate model failures"),
       (1, "It has historically failed due to error in user_upload in demand_plan. Check the latest uploaded date of demand_plan");

-- Adding records to oncall_suggestions table
INSERT INTO oncall_suggestions (oncall_tracker_id, suggestion) VALUES
       (2, "Check sell_plan, the user may have uploaded duplicate values"),
       (2, "If there is a recent deployment in the retail pipeline, check the code of overridden logic"),
       (3, "Investigate Delphi Kill API for the max_threshold allowed time to kill"),
       (3, "Check whether Demand Forecast Backend Service received the request for the kill"),
       (4, "Ask stakeholders for the update"),
       (4, "If stakeholders do not know, tag the product for the query"),
       (5, "It possibly failed because the event_calendar is outdated, check it"),
       (5, "Check sales_history_base_fact, whether the hive copy is in sync with fdp_fact. Out-of-sync sales table can generate model failures"),
       (5, "It has historically failed due to error in user_upload in demand_plan. Check the latest uploaded date of demand_plan"),
       (6, "Check whether stakeholders uploaded the wrong start and end_date"),
       (6, "Check whether stakeholders entered SC/BU name in the wrong case");


-- Adding records to historical_users table
INSERT INTO historical_users (oncall_tracker_id, user_id) VALUES
      (1, "lokesh.kumar2"),
      (1, "sagar.bhatt"),
      (2, "pravi.malviya"),
      (2, "prateek.sharma"),
      (2, "rohit.verma"),
      (3, "ankit.kumarpandey"),
      (3, "sneha.gupta"),
      (3, "roshan.sharma"),
      (3, "binoh.arora"),
      (4, "alokit.kumar"),
      (4, "manikandan"),
      (5, "shourya.sharma"),
      (5, "yogesh.kumar"),
      (6, "roshan.kapoor"),
      (6, "mamta.devi"),
      (6, "ramesh.chowdhary");


-- Adding records to current_users table with reduced entries
INSERT INTO current_users (oncall_tracker_id, user_id) VALUES
       (1, "lokesh.kumar2"),
       (1, "sagar.bhatt"),
       (2, "amitrajit.bose"),
       (3, "alokit.kumar"),
       (4, "sagar.bhatt"),
       (5, "manikandan"),
       (6, "roshan.kapoor");

-- Inserting data into active_oncall_group table
INSERT INTO active_oncall_group (primary_user_id, secondary_user_id, start_date, end_date)
VALUES
    (3, 4, '2023-10-11', '2023-10-21'),
    (5, 6, '2023-10-1', '2023-10-10');

INSERT INTO alerts (title, message, is_acknowledged, priority)
VALUES
    ('Mobile Forecast Model did not run from last 1 month', 'Please investigate urgently', 'NO', 'P0'),
    ('HF Forecast Model did not run from last 2 weeks', 'Please investigate urgently', 'NO', 'P1'),
    ('No sell plan uploaded for Lifestyle from last 2 months', 'Please investigate the issue, and talk to stakeholders', 'NO', 'P2'),
    ('LTA Model failed 5 times with the same error', 'LTA Model failed 5 times, with same error, event_calendar issue. Please investigate', 'NO', 'P3'),
    ('No update in demand plan, since last month', 'Please talk to the stakeholders', 'NO', 'P2');

INSERT INTO followups (oncall_tracker_id, message, is_acknowledged)
VALUES
    (1, 'No Response since past 7 Days', 'NO', 'P1'),
    (2, 'No Response since past 15 Days', 'NO', 'P0');




