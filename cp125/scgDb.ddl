           CREATE TABLE clients (
             id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
             name VARCHAR(30) NOT NULL,
             street VARCHAR(30) NOT NULL,
             city VARCHAR(30) NOT NULL,
             state CHAR(2) NOT NULL,
             postal_code VARCHAR(10),
             contact_last_name VARCHAR(30),
             contact_first_name VARCHAR(30),
             contact_middle_name VARCHAR(30),
             PRIMARY KEY (id),
             CONSTRAINT CLIENT_UNIQUE UNIQUE (name)
           );

          CREATE TABLE non_billable_accounts (
            name VARCHAR(30) NOT NULL,
            PRIMARY KEY (name)
          );

          CREATE TABLE consultants (
            id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
            last_name VARCHAR(30) NOT NULL,
            first_name VARCHAR(30) NOT NULL,
            middle_name VARCHAR(30),
            PRIMARY KEY (id),
            CONSTRAINT CONSULTANT_UNIQUE UNIQUE (last_name, first_name, middle_name)
         );

         CREATE TABLE skills (
           name VARCHAR(30) NOT NULL,
           rate INTEGER NOT NULL,
           PRIMARY KEY (name)
         );

         CREATE TABLE timecards (
           id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
           consultant_id INTEGER NOT NULL,
           start_date DATE NOT NULL,
           PRIMARY KEY (id),
           FOREIGN KEY (consultant_id) references consultants(id),
           CONSTRAINT TIMECARD_UNIQUE UNIQUE (consultant_id, start_date)
         );

         CREATE TABLE billable_hours (
           id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
           client_id INTEGER NOT NULL,
           timecard_id INTEGER NOT NULL,
           date DATE NOT NULL,
           skill VARCHAR(30) NOT NULL,
           hours INTEGER NOT NULL,
           PRIMARY KEY (id),
           FOREIGN KEY (client_id) references clients(id),
           FOREIGN KEY (skill) references skills(name),
           FOREIGN KEY (timecard_id) references timecards(id)
         );

         CREATE TABLE non_billable_hours (
           id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
           account_name VARCHAR(30) NOT NULL,
           timecard_id INTEGER NOT NULL,
           date DATE NOT NULL,
           hours INTEGER NOT NULL,
           PRIMARY KEY (id),
           FOREIGN KEY (account_name) references non_billable_accounts(name),
           FOREIGN KEY (timecard_id) references timecards(id)
         );

         INSERT INTO skills (name, rate) VALUES('PROJECT_MANAGER', 250);
         INSERT INTO skills (name, rate) VALUES('SYSTEM_ARCHITECT', 200);
         INSERT INTO skills (name, rate) VALUES ('SOFTWARE_ENGINEER', 150);
         INSERT INTO skills (name, rate) VALUES ('SOFTWARE_TESTER', 100);
         INSERT INTO skills (name, rate) VALUES ('UNKNOWN_SKILL', 0);
         INSERT INTO non_billable_accounts (name) VALUES ('VACATION');
         INSERT INTO non_billable_accounts (name) VALUES ('SICK_LEAVE');
         INSERT INTO non_billable_accounts (name) VALUES ('BUSINESS_DEVELOPMENT');

