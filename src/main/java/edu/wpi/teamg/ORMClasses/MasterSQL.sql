drop table if exists iteration2.MealRequest;
drop table if exists iteration2.ConferenceRoomRequest;
drop table if exists iteration2.flowerrequest;
drop table if exists iteration2.Account;
drop table if exists iteration2.Request;
drop table if exists iteration2.Employee;
drop type if exists iteration2.enum1;
drop table if exists iteration2.Move;
drop table if exists iteration2.Edge;
drop table if exists iteration2.LocationName;
drop table if exists iteration2.Node;

create table iteration2.Node(
                                        nodeID int primary key,
                                        xcoord int,
                                        ycoord int,
                                        floor char(2),
                                        building varchar(40)
);

create table iteration2.Edge(
                                        startNode int,
                                        endNode int,
                                        PRIMARY KEY (startNode, endNode),
                                        foreign key (startNode) references iteration2.node(nodeID) ON DELETE CASCADE ON UPDATE CASCADE,
                                        foreign key (endNode) references iteration2.node(nodeID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table iteration2.LocationName(
                                                longName varchar(100) primary key,
                                                shortName varchar(55),
                                                nodeType char(4)
);

create table iteration2.Move(
                                        nodeID int,
                                        longName varchar(100),
                                        date date,
                                        PRIMARY KEY (nodeID, longName, date),
                                        foreign key (nodeID) references iteration2.node(nodeID) ON DELETE CASCADE ON UPDATE CASCADE,
                                        foreign key (longName) references iteration2.LocationName(longName) ON DELETE CASCADE ON UPDATE CASCADE
);

create type iteration2.enum1 as enum('blank', 'processing', 'done');

create table iteration2.Employee(
                                            empID int primary key,
                                            firstName varchar(50),
                                            lastName varchar(50),
                                            email varchar(254),
                                            can_serve varchar(254)
);

create table iteration2.Account(
                                           empID int primary key,
                                           password varchar(100),
                                           is_admin boolean,
                                           foreign key (empID) references iteration2.Employee(empID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table iteration2.Request (
                reqID int primary key,
                reqType varchar(2),
                empID int,
                location int,
                serveBy int,
                status iteration2.enum1,
                requestDate date,
                requestTime time,
                foreign key (empID) references iteration2.Employee(empID) ON DELETE CASCADE ON UPDATE CASCADE,
                foreign key (location) references iteration2.node(nodeID) ON DELETE CASCADE ON UPDATE CASCADE,
                foreign key (serveBy) references iteration2.Employee(empID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table iteration2.ConferenceRoomRequest(
                                         reqID int primary key,
                                         endTime time,
                                         purpose varchar(255),
                                         foreign key (reqID) references iteration2.Request(reqID) ON DELETE CASCADE ON UPDATE CASCADE

);

create table iteration2.MealRequest(
           reqID int primary key,
           recipient varchar(50),
           mealOrder varchar(255),
           note varchar(255),
           foreign key (reqID) references iteration2.Request(reqID) ON DELETE CASCADE ON UPDATE CASCADE

);

create table iteration2.flowerrequest(
    reqID int primary key,
    flowerType varchar(350),
    numFlower int,
    recipient varchar(50),
    note varchar(225),
    foreign key (reqID) references iteration2.request(reqID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table iteration2.account(
    username text  primary key,
    empID int,
    hashPassword text,
    salt bytea NOT NULL,
    is_admin bool,
    foreign key (empID) references iteration2.Employee(empID) ON DELETE CASCADE ON UPDATE CASCADE);


INSERT INTO iteration2.Employee (empID, firstName, lastName, email, can_serve)
VALUES
    (1, 'John', 'Doe', 'johndoe@example.com', 'Conference Room Request'),
    (2, 'Jane', 'Doe', 'janedoe@example.com', 'Meal Request'),
    (3, 'Bob', 'Smith', 'bobsmith@example.com', 'Flower Request'),
    (4, 'Alice', 'Johnson', 'alicejohnson@example.com', 'Office Supplies Request'),
    (5, 'Bruce','Wayne' ,'batman@example.com' , 'Furniture Request'),
    (6, 'Itta','Patell','Ipatell@example.com','Conference Request'),
    (7, 'Diana','Wells','Dwells@example.com','Conference Request'),
    (8, 'Mark', 'Specter', 'moonknight@example.com', 'Conference Request');

-- Table: iteration2.Account
INSERT INTO iteration2.Account (empID, password, is_admin)
VALUES
    (1, 'password123', true),
    (2, '123password', false),
    (3, 'password456', false),
    (4, '456password', true);

-- Table: iteration2.Request
INSERT INTO iteration2.request (reqID, reqType, empID, location, serveBy, status, requestDate, requestTime)
VALUES
    (1, 'M', 1, 105, 1, 'blank', '2023-04-15', '13:00:00'),
    (2, 'M', 2, 110, 2, 'processing', '2023-04-15', '13:00:00'),
    (3, 'M', 3, 115, 3, 'done', '2023-04-15', '13:00:00'),
    (4, 'M', 4, 120, 4, 'blank', '2023-04-15', '13:00:00'),
    (5, 'CR', 5, 105, 1, 'blank', '2023-04-15', '13:00:00'),
    (6, 'CR', 6, 110, 2, 'processing', '2023-04-16', '14:30:00'),
    (7, 'CR', 7, 115, 3, 'done', '2023-04-17', '10:00:00'),
    (8, 'CR', 8, 120, 4, 'blank', '2023-04-18', '15:00:00'),
    (9, 'FL', 1, 115, 3, 'processing', '2023-04-17', '10:00:00'),
    (10, 'FL', 2, 115, 3, 'blank', '2023-04-17', '10:00:00'),
    (11, 'FL', 3, 115, 3, 'done', '2023-04-17', '10:00:00'),
    (12, 'FL', 4, 115, 3, 'processing', '2023-04-17', '10:00:00');

-- Table: iteration2.MealRequest
INSERT INTO iteration2.mealrequest (reqID, recipient, mealOrder, note)
VALUES
    (1, 'John Doe', 'Grilled chicken sandwich', 'No onions'),
    (2, 'Jane Doe', 'Vegetarian pizza', 'Extra cheese'),
    (3, 'Bob Smith', 'Fish and chips', 'Tartar sauce on the side'),
    (4, 'Alice Johnson', 'Caesar salad', 'No croutons');

-- Table: iteration2.ConferenceRoomRequest
INSERT INTO iteration2.ConferenceRoomRequest (reqID, endTime, purpose)
VALUES
    (5, '15:00:00', 'Team meeting'),
    (6, '16:30:00', 'Client presentation'),
    (7, '14:00:00', 'Interview'),
    (8, '16:00:00', 'Training session');

-- Table: iteration2.FlowerRequest
INSERT INTO iteration2.FlowerRequest (reqID, flowerType, numFlower, recipient, note)
VALUES
    (9, 'Rose, Tulip, Daisy', 10, 'John', 'Happy Birthday!'),
    (10, 'Tulip, Rose', 5, 'Emily', 'Get well soon!'),
    (11, 'Daisy, Tulip', 20, 'Sarah', 'Congratulations on your new job!'),
    (12, 'Lily, Rose', 15, 'Michael', 'With deepest sympathy');

