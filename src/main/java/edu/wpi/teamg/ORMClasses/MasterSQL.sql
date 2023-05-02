drop table if exists iteration4.MealRequest;
drop table if exists iteration4.ConferenceRoomRequest;
drop table if exists iteration4.FurnitureRequest;
drop table if exists iteration4.officesupplyrequest;
drop table if exists iteration4.flowerrequest;
drop table if exists iteration4.MaintenanceRequest;
drop table if exists iteration4.Request;
drop type if exists  iteration4.enum1;
drop table if exists iteration4.Move;
drop table if exists iteration4.Edge;
drop table if exists iteration4.LocationName;
drop table if exists iteration4.Node;
drop table if exists iteration4.notification;
drop table if exists iteration4.signage;
drop table if exists iteration4.account;
drop table if exists iteration4.Employee;


create table iteration4.Node(
                nodeID int primary key,
                xcoord int,
                ycoord int,
                floor char(2),
                building varchar(40)
);

create table iteration4.Edge(
                                        startNode int,
                                        endNode int,
                                        PRIMARY KEY (startNode, endNode),
                                        foreign key (startNode) references iteration4.node(nodeID) ON DELETE CASCADE ON UPDATE CASCADE,
                                        foreign key (endNode) references iteration4.node(nodeID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table iteration4.LocationName(
                                                longName varchar(100) primary key,
                                                shortName varchar(55),
                                                nodeType char(4)
);

create table iteration4.Move(
                                        nodeID int,
                                        longName varchar(100),
                                        date date,
                                        PRIMARY KEY (nodeID, longName, date),
                                        foreign key (nodeID) references iteration4.node(nodeID) ON DELETE CASCADE ON UPDATE CASCADE,
                                        foreign key (longName) references iteration4.LocationName(longName) ON DELETE CASCADE ON UPDATE CASCADE
);
create table iteration4.signage(
                                   kiosknum int,
                                   specDate Date,
                                   arrow text,
                                   month text,
                                   is_spec bool,
                                   location text,
                                   primary key (kiosknum,arrow,month)
);

create type iteration4.enum1 as enum('blank', 'processing', 'done');

create table iteration4.Employee(
                                            empID int primary key,
                                            firstName varchar(50),
                                            lastName varchar(50),
                                            email varchar(254),
                                            can_serve varchar(254)
);

create table iteration4.Request (
                reqID int primary key,
                reqType varchar(2),
                empID int,
                location int,
                serveBy int,
                status iteration4.enum1,
                requestDate date,
                requestTime time,
                foreign key (empID) references iteration4.Employee(empID) ON DELETE CASCADE ON UPDATE CASCADE,
                foreign key (location) references iteration4.node(nodeID) ON DELETE CASCADE ON UPDATE CASCADE,
                foreign key (serveBy) references iteration4.Employee(empID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table iteration4.ConferenceRoomRequest(

                 reqID int primary key,
                 endTime time,
                 purpose varchar(255),
                 foreign key (reqID) references iteration4.Request(reqID) ON DELETE CASCADE ON UPDATE CASCADE


);

create table iteration4.MealRequest(
           reqID int primary key,
           recipient varchar(50),
           mealOrder varchar(255),
           note varchar(255),
           foreign key (reqID) references iteration4.Request(reqID) ON DELETE CASCADE ON UPDATE CASCADE

);

create table iteration4.flowerrequest(
    reqID int primary key,
    flowerType varchar(350),
    numFlower int,
    recipient varchar(50),
    note varchar(225),
    foreign key (reqID) references iteration4.request(reqID) ON DELETE CASCADE ON UPDATE CASCADE
);
create table iteration4.FurnitureRequest(
    reqID int primary key,
    furnitureType varchar(200),
    note varchar(225),
    recipient varchar(200),
    foreign key (reqID) references iteration4.Request(reqID) ON DELETE CASCADE ON UPDATE CASCADE

);

create table iteration4.officesupplyrequest(
    reqID int primary key,
    supplyType varchar(400),
    note varchar(225),
    recipient varchar(200),
    foreign key (reqID) references  iteration4.request(reqid) on delete cascade on update cascade
);
create table iteration4.MaintenanceRequest(
    reqId int primary key,
    recipient varchar(100),
    phoneNumber varchar(100),
    type varchar(100),
    specified varchar(200),
    note varchar(225),
    foreign key (reqId) references iteration4.request(reqid) on delete cascade on update cascade

);

create table iteration4.account(
    username text  primary key,
    empID int,
    hashPassword text,
    salt bytea NOT NULL,
    is_admin bool,
    foreign key (empID) references iteration4.Employee(empID) ON DELETE CASCADE ON UPDATE CASCADE);

create table iteration4.notification(
    alertid int primary key,
    notifDate Date,
    notifTime Time,
    notifType text,
    empID int,
    recipients text,
    message text,
    notifheader varchar(50),
    dismissible boolean,
    foreign key (empID) references iteration4.employee(empID) on delete cascade on update cascade);




INSERT INTO iteration4.Employee (empID, firstName, lastName, email, can_serve)
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


-- Table: iteration4.Account

INSERT INTO iteration4.request (reqID, reqType, empID, location, serveBy, status, requestDate, requestTime)
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

-- Table: iteration4.MealRequest
INSERT INTO iteration4.mealrequest (reqID, recipient, mealOrder, note)
VALUES
    (1, 'John Doe', 'Grilled chicken sandwich', 'No onions'),
    (2, 'Jane Doe', 'Vegetarian pizza', 'Extra cheese'),
    (3, 'Bob Smith', 'Fish and chips', 'Tartar sauce on the side'),
    (4, 'Alice Johnson', 'Caesar salad', 'No croutons');

-- Table: iteration4.ConferenceRoomRequest
INSERT INTO iteration4.ConferenceRoomRequest (reqID, endTime, purpose)
VALUES
    (5, '15:00:00', 'Team meeting'),
    (6, '16:30:00', 'Client presentation'),
    (7, '14:00:00', 'Interview'),
    (8, '16:00:00', 'Training session');

-- Table: iteration4.FlowerRequest
INSERT INTO iteration4.FlowerRequest (reqID, flowerType, numFlower, recipient, note)
VALUES
    (9, 'Rose, Tulip, Daisy', 10, 'John', 'Happy Birthday!'),
    (10, 'Tulip, Rose', 5, 'Emily', 'Get well soon!'),
    (11, 'Daisy, Tulip', 20, 'Sarah', 'Congratulations on your new job!'),
    (12, 'Lily, Rose', 15, 'Michael', 'With deepest sympathy');

insert into iteration4.signage (kiosknum, specdate, arrow, month, is_spec) values
    (1,null, 'information_R_6','APR-23',false),
    (1,null, 'Shapiro Admitting_R_7','APR-23',false),
    (1,null, 'Shapiro Procedural Check-in_R_8','APR-23',false),
    (1,null, 'Watkins Clinics A & B (this floor)_L_1','APR-23',false),
    (1,null, 'Watkins Clinic C (up to 3rd floor)_L_2','APR-23',false),
    (1,null, 'Rehabilitation Services(down to first floor)_L_4','APR-23',false),
    (2,null, 'Watkins Clinics A & B (this floor)_U_1','APR-23',false),
    (2,null, 'Watkins Clinic C (EP & Echo) (up to 3rd fl)_U_6','APR-23',false),
    (2,null, 'Brigham Circle Medical Associates (up to 3rd floor)_U_2','APR-23',false),
    (2,null, 'L2PRU (down to Lower Level "L2")_D_5','APR-23',false);
insert into iteration4.signage (kiosknum, specdate, arrow, month, is_spec, location) values
    (1,null, 'information_R_6','MAY-23',false,'Innovation Hub'),
    (1,null, 'Shapiro Admitting_R_7','MAY-23',false,'Innovation Hub'),
    (1,null, 'Shapiro Procedural Check-in_R_8','MAY-23',false,'Innovation Hub'),
    (1,null, 'Watkins Clinics A & B (this floor)_L_1','MAY-23',false,'Innovation Hub'),
    (1,null, 'Watkins Clinic C (up to 3rd floor)_L_2','MAY-23',false,'Innovation Hub'),
    (1,null, 'Rehabilitation Services(down to first floor)_L_4','MAY-23',false,null),
    (2,null, 'Watkins Clinics A & B (this floor)_U_1','MAY-23',false,null),
    (2,null, 'Watkins Clinic C (EP & Echo) (up to 3rd fl)_U_6','MAY-23',false,null),
    (2,null, 'Brigham Circle Medical Associates (up to 3rd floor)_U_2','MAY-23',false,null),
    (2,null, 'L2PRU (down to Lower Level "L2")_D_5','MAY-23',false,null);
insert into iteration4.signage (kiosknum, specdate, arrow, month, is_spec) values
    (1,null, 'Stop here for_N_N','JUL-23',false),
    (1,null, 'Information_N_N','JUL-23',false),
    (1,null, 'Shapiro Admitting_N_N','JUL-23',false),
    (1,null, 'Shapiro Procedural Check-in_N_N','JUL-23',false),
    (2,null, 'Watkins Clinics A & B (this floor)_U_1','JUL-23',false),
    (2,null, 'Watkins Clinic C (EP & Echo) (up to 3rd fl)_U_6','JUL-23',false),
    (2,null, 'Brigham Circle Medical Associates (up to 3rd floor)_U_2','JUL-23',false),
    (2,null, 'L2PRU (down to Lower Level "L2")_D_5','JUL-23',false);
insert into iteration4.signage (kiosknum, specdate, arrow, month, is_spec) values
    (1,null, 'Stop here for_N_N','NOV-23',false),
    (1,null, 'Information_N_N','NOV-23',false),
    (1,null, 'Shapiro Admitting_N_N','NOV-23',false),
    (1,null, 'Shapiro Procedural Check-in_N_N','NOV-23',false),
    (2,null, 'Watkins Clinic & EP (this floor)_U_1','NOV-23',false),
    (2,null, 'Echocardiography (this floor)_R_8','NOV-23',false),
    (2,null, 'Brigham Circle Medical Associates (Hale Building)_L_3','NOV-23',false),
    (2,null, 'L2PRU (down to Lower Level "L2")_D_5','NOV-23',false);

-- Query All Location Names for Meal and Flower Delivery Request
SELECT Move.nodeID, LocationName.longName, LocationName.nodetype
FROM iteration4.Move
JOIN iteration4.LocationName ON Move.longName = LocationName.longName
WHERE LocationName.nodeType = 'CONF'
   OR LocationName.nodeType = 'DEPT'
   OR LocationName.nodeType = 'INFO'
   OR LocationName.nodeType = 'SERV'
   OR LocationName.nodeType = 'LABS'
   OR LocationName.nodeType = 'RETL'