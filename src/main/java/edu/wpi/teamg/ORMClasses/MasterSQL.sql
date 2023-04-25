drop table if exists iteration3.MealRequest;
drop table if exists iteration3.ConferenceRoomRequest;
drop table if exists iteration3.FurnitureRequest;
drop table if exists iteration3.officesupplyrequest;
drop table if exists iteration3.flowerrequest;
drop table if exists iteration3.Request;
drop type if exists  iteration3.enum1;
drop table if exists iteration3.Move;
drop table if exists iteration3.Edge;
drop table if exists iteration3.LocationName;
drop table if exists iteration3.Node;
drop table if exists iteration3.notification;
drop table if exists iteration3.account;
drop table if exists iteration3.Employee;


create table iteration3.Node(
                nodeID int primary key,
                xcoord int,
                ycoord int,
                floor char(2),
                building varchar(40)
);

create table iteration3.Edge(
                                        startNode int,
                                        endNode int,
                                        PRIMARY KEY (startNode, endNode),
                                        foreign key (startNode) references iteration3.node(nodeID) ON DELETE CASCADE ON UPDATE CASCADE,
                                        foreign key (endNode) references iteration3.node(nodeID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table iteration3.LocationName(
                                                longName varchar(100) primary key,
                                                shortName varchar(55),
                                                nodeType char(4)
);

create table iteration3.Move(
                                        nodeID int,
                                        longName varchar(100),
                                        date date,
                                        PRIMARY KEY (nodeID, longName, date),
                                        foreign key (nodeID) references iteration3.node(nodeID) ON DELETE CASCADE ON UPDATE CASCADE,
                                        foreign key (longName) references iteration3.LocationName(longName) ON DELETE CASCADE ON UPDATE CASCADE
);

create type iteration3.enum1 as enum('blank', 'processing', 'done');

create table iteration3.Employee(
                                            empID int primary key,
                                            firstName varchar(50),
                                            lastName varchar(50),
                                            email varchar(254),
                                            can_serve varchar(254)
);

create table iteration3.Request (
                reqID int primary key,
                reqType varchar(2),
                empID int,
                location int,
                serveBy int,
                status iteration3.enum1,
                requestDate date,
                requestTime time,
                foreign key (empID) references iteration3.Employee(empID) ON DELETE CASCADE ON UPDATE CASCADE,
                foreign key (location) references iteration3.node(nodeID) ON DELETE CASCADE ON UPDATE CASCADE,
                foreign key (serveBy) references iteration3.Employee(empID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table iteration3.ConferenceRoomRequest(

                 reqID int primary key,
                 endTime time,
                 purpose varchar(255),
                 foreign key (reqID) references iteration3.Request(reqID) ON DELETE CASCADE ON UPDATE CASCADE


);

create table iteration3.MealRequest(
           reqID int primary key,
           recipient varchar(50),
           mealOrder varchar(255),
           note varchar(255),
           foreign key (reqID) references iteration3.Request(reqID) ON DELETE CASCADE ON UPDATE CASCADE

);

create table iteration3.flowerrequest(
    reqID int primary key,
    flowerType varchar(350),
    numFlower int,
    recipient varchar(50),
    note varchar(225),
    foreign key (reqID) references iteration3.request(reqID) ON DELETE CASCADE ON UPDATE CASCADE
);
create table iteration3.FurnitureRequest(
    reqID int primary key,
    furnitureType varchar(200),
    note varchar(225),
    recipient varchar(200),
    foreign key (reqID) references iteration3.Request(reqID) ON DELETE CASCADE ON UPDATE CASCADE

);

create table iteration3.officesupplyrequest(
    reqID int primary key,
    supplyType varchar(400),
    note varchar(225),
    recipient varchar(200),
    foreign key (reqID) references  iteration3.request(reqid) on delete cascade on update cascade
);

create table iteration3.account(
    username text  primary key,
    empID int,
    hashPassword text,
    salt bytea NOT NULL,
    is_admin bool,
    foreign key (empID) references iteration3.Employee(empID) ON DELETE CASCADE ON UPDATE CASCADE);

create table iteration3.notification(
    alertid int primary key,
    notifDate Date,
    notifTime Time,
    notifType text,
    empID int,
    recipients text,
    message text,
    foreign key (empID) references iteration3.employee(empID) on delete cascade on update cascade);


INSERT INTO iteration3.Employee (empID, firstName, lastName, email, can_serve)
VALUES
    (1, 'John', 'Doe', 'johndoe@example.com', 'Meal Request'),
    (2, 'Jane', 'Doe', 'janedoe@example.com', 'Meal Request'),
    (3, 'Bob', 'Smith', 'bobsmith@example.com', 'Meal Request'),
    (4, 'Alice', 'Johnson', 'alicejohnson@example.com', 'Meal Request'),
    (5, 'Bruce','Wayne' ,'batman@example.com' , 'Conference Room Request'),
    (6, 'Itta','Patell','Ipatell@example.com','Conference Room Request'),
    (7, 'Diana','Wells','Dwells@example.com','Conference Room Request'),
    (8, 'Mark', 'Specter', 'moonknight@example.com', 'Conference Room Request'),
    (9, 'John', 'Doe', 'johndoe@example.com', 'Flowers Request'),
    (10, 'Jane', 'Doe', 'janedoe@example.com', 'Flowers Request'),
    (11, 'Aaron', 'Mar', 'teamGoat1@example.com', 'Flowers Request'),
    (12, 'Thomas', 'McDonagh', 'teamGoat@example.com', 'Flowers Request'),
    (13, 'Rishi','Patel' ,'batman@example.com' , 'Office Supplies Request'),
    (14, 'Barry','Allen','theflash@example.com','Office Supplies Request'),
    (15, 'Hal','Jordan','greenlantern@example.com','Office Supplies Request'),
    (16, 'Clark', 'Kent', 'superman@example.com', 'Office Supplies Request'),
    (17, 'Diana', 'Prince', 'wonderwoman@example.com', 'Furniture Request'),
    (18, 'Tom', 'Brady', 'eggballgoat@example.com', 'Furniture Request'),
    (19, 'Messi', 'Lionel', 'GOAT@example.com', 'Furniture Request'),
    (20, 'Ronaldo', 'Cristiano', 'goat@example.com', 'Furniture Request');


-- Table: iteration3.Account

INSERT INTO iteration3.request (reqID, reqType, empID, location, serveBy, status, requestDate, requestTime)
VALUES
    (1, 'M', 12, 1290, 1, 'blank', '2023-04-15', '13:00:00'),
    (2, 'M', 11, 1295, 2, 'processing', '2023-04-15', '13:00:00'),
    (3, 'M', 10, 1300, 3, 'done', '2023-04-15', '13:00:00'),
    (4, 'M', 9, 1305, 4, 'blank', '2023-04-15', '13:00:00'),
    (5, 'CR', 8, 1335, 5, 'blank', '2023-04-15', '13:00:00'),
    (6, 'CR', 7, 1685, 6, 'processing', '2023-04-16', '14:30:00'),
    (7, 'CR', 6, 1690, 7, 'done', '2023-04-17', '10:00:00'),
    (8, 'CR', 5, 1695, 8, 'blank', '2023-04-18', '15:00:00'),
    (9, 'FL', 4, 1345, 9, 'processing', '2023-04-17', '10:00:00'),
    (10, 'FL', 3, 1350, 10, 'blank', '2023-04-17', '10:00:00'),
    (11, 'FL', 2, 1355, 11, 'done', '2023-04-17', '10:00:00'),
    (12, 'FL', 1, 1360, 12, 'processing', '2023-04-17', '10:00:00');

-- Table: iteration3.MealRequest
INSERT INTO iteration3.mealrequest (reqID, recipient, mealOrder, note)
VALUES
    (1, 'John Doe', 'Grilled chicken sandwich', 'No onions'),
    (2, 'Jane Doe', 'Vegetarian pizza', 'Extra cheese'),
    (3, 'Bob Smith', 'Fish and chips', 'Tartar sauce on the side'),
    (4, 'Alice Johnson', 'Caesar salad', 'No croutons');

-- Table: iteration3.ConferenceRoomRequest
INSERT INTO iteration3.ConferenceRoomRequest (reqID, endTime, purpose)
VALUES
    (5, '15:00:00', 'Team meeting'),
    (6, '16:30:00', 'Client presentation'),
    (7, '14:00:00', 'Interview'),
    (8, '16:00:00', 'Training session');

-- Table: iteration3.FlowerRequest
INSERT INTO iteration3.FlowerRequest (reqID, flowerType, numFlower, recipient, note)
VALUES
    (9, 'Rose, Tulip, Daisy', 10, 'John', 'Happy Birthday!'),
    (10, 'Tulip, Rose', 5, 'Emily', 'Get well soon!'),
    (11, 'Daisy, Tulip', 20, 'Sarah', 'Congratulations on your new job!'),
    (12, 'Lily, Rose', 15, 'Michael', 'With deepest sympathy');

-- Query All Location Names for Meal and Flower Delivery Request
SELECT Move.nodeID, LocationName.longName, LocationName.nodetype
FROM iteration3.Move
JOIN iteration3.LocationName ON Move.longName = LocationName.longName
WHERE LocationName.nodeType = 'CONF'
   OR LocationName.nodeType = 'DEPT'
   OR LocationName.nodeType = 'INFO'
   OR LocationName.nodeType = 'SERV'
   OR LocationName.nodeType = 'LABS'
   OR LocationName.nodeType = 'RETL'