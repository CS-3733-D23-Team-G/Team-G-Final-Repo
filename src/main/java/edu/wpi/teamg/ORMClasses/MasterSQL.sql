drop table if exists iteration1.MealRequest;
drop table if exists iteration1.ConferenceRoomRequest;
drop table if exists iteration1.Account;
drop table if exists iteration1.Request;
drop table if exists iteration1.Employee;
drop type if exists iteration1.enum1;
drop table if exists iteration1.Move;
drop table if exists iteration1.Edge;
drop table if exists iteration1.LocationName;
drop table if exists iteration1.Node;

create table iteration1.Node(
                                        nodeID int primary key,
                                        xcoord int,
                                        ycoord int,
                                        floor char(2),
                                        building varchar(40)
);

create table iteration1.Edge(
                                        startNode int,
                                        endNode int,
                                        PRIMARY KEY (startNode, endNode),
                                        foreign key (startNode) references iteration1.node(nodeID) ON DELETE CASCADE ON UPDATE CASCADE,
                                        foreign key (endNode) references iteration1.node(nodeID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table iteration1.LocationName(
                                                longName varchar(100) primary key,
                                                shortName varchar(55),
                                                nodeType char(4)
);

create table iteration1.Move(
                                        nodeID int,
                                        longName varchar(100),
                                        date date,
                                        PRIMARY KEY (nodeID, longName, date),
                                        foreign key (nodeID) references iteration1.Node(nodeID) ON DELETE CASCADE ON UPDATE CASCADE,
                                        foreign key (longName) references iteration1.LocationName(longName) ON DELETE CASCADE ON UPDATE CASCADE
);

create type iteration1.enum1 as enum('blank', 'processing', 'done');

create table iteration1.Employee(
                                            empID int primary key,
                                            firstName varchar(20),
                                            lastName varchar(20),
                                            email varchar(254),
                                            can_serve varchar(20)
);

create table iteration1.Account(
                                           empID int primary key,
                                           password varchar(100),
                                           is_admin boolean,
                                           foreign key (empID) references iteration1.Employee(empID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table iteration1.Request (
                                            reqID int primary key,
                                            empID int,
                                            location int,
                                            serv_by int,
                                            status iteration1.enum1,
                                            foreign key (empID) references iteration1.Employee(empID) ON DELETE CASCADE ON UPDATE CASCADE,
                                            foreign key (location) references iteration1.node(nodeID) ON DELETE CASCADE ON UPDATE CASCADE,
                                            foreign key (serv_by) references iteration1.Employee(empID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table iteration1.ConferenceRoomRequest(
                                                         reqID int primary key,
                                                         meeting_date date,
                                                         meeting_time time,
                                                         purpose varchar(255),
                                                         foreign key (reqID) references iteration1.Request(reqID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table iteration1.MealRequest(
                                               reqID int primary key,
                                               deliveryDate date,
                                               deliveryTime time,
                                               recipient varchar(50),
                                               mealOrder varchar(255),
                                               note varchar(255),
                                               foreign key (reqID) references iteration1.Request(reqID) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO iteration1.Employee (empID, firstName, lastName, email, can_serve)
VALUES
    (1, 'John', 'Doe', 'johndoe@example.com', 'Coffee'),
    (2, 'Jane', 'Doe', 'janedoe@example.com', 'Lunch'),
    (3, 'Bob', 'Smith', 'bobsmith@example.com', 'Dinner'),
    (4, 'Alice', 'Johnson', 'alicejohnson@example.com', 'All'),
    (5,'Bruce','Wanyne' ,'batman@example.com' , 'Dinner'),
    (6,'Itta','Patell','Ipatell@example.com','Lunch'),
    (7, 'Diana','Wells','Dwells@example.com','All'),
    (8,'Mark', 'Specter', 'moonknight@example.com', 'Coffee');

-- Table: iteration1.Account
INSERT INTO iteration1.Account (empID, password, is_admin)
VALUES
    (1, 'password123', true),
    (2, '123password', false),
    (3, 'password456', false),
    (4, '456password', true);

-- Table: iteration1.Request
INSERT INTO iteration1.request (reqID, empID, location, serv_by, status)
VALUES
    (1, 1, 105, 1, 'blank'),
    (2, 2, 110, 2, 'processing'),
    (3, 3, 115, 3, 'done'),
    (4, 4, 120, 4, 'blank'),
    (5, 5, 105, 1, 'blank'),
    (6, 6, 110, 2, 'processing'),
    (7, 7, 115, 3, 'done'),
    (8, 8, 120, 4, 'blank');

-- Table: iteration1.ConferenceRoomRequest
INSERT INTO iteration1.ConferenceRoomRequest (reqID, meeting_date, meeting_time, purpose)
VALUES
    (5, '2023-04-15', '13:00:00', 'Team meeting'),
    (6, '2023-04-16', '14:30:00', 'Client presentation'),
    (7, '2023-04-17', '10:00:00', 'Interview'),
    (8, '2023-04-18', '15:00:00', 'Training session');

-- Table: iteration1.MealRequest
INSERT INTO iteration1.mealrequest (reqID, deliveryDate, deliveryTime, recipient, mealOrder, note)
VALUES
    (1, '2023-04-15', '13:00:00','John Doe', 'Grilled chicken sandwich', 'No onions'),
    (2, '2023-04-15', '13:00:00','Jane Doe', 'Vegetarian pizza', 'Extra cheese'),
    (3, '2023-04-15', '13:00:00','Bob Smith', 'Fish and chips', 'Tartar sauce on the side'),
    (4, '2023-04-15', '13:00:00', 'Alice Johnson', 'Caesar salad', 'No croutons');

