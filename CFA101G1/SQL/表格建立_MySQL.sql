CREATE DATABASE IF NOT EXISTS CFA101G1;

use CFA101G1;

drop table if exists ItemCollection;
drop table if exists ItemPic;
drop table if exists ItemDetail;
drop table if exists ItemOrder;
drop table if exists Item;
drop table if exists TransRec;
drop table if exists Mem;
drop table if exists ProductKind;

create table Mem (
	MemNo int not null comment '會員編號' primary key auto_increment,
	MemAccount varchar(20) not null comment '會員帳號'  ,
	MemPassword varchar(20) not null comment '會員密碼' ,
	MemStatus int(1) not null comment '帳號狀態'  ,
	MemVrfed int(1) null comment '驗證狀態' ,
	MemNoVrftime datetime null comment '會員驗證完成時間'  ,
	MemName varchar(100) not null comment '姓名' ,
	MemMobile varchar(10) not null comment '電話'  ,
	MemCity varchar(20) not null comment '地址縣市' ,
	MemDist varchar(20) not null comment '地址區域'  ,
	MemAdd varchar(100) not null comment '地址' ,
	MemEmail varchar(50) not null comment 'E-Mail'  ,
	MemBirth datetime not null comment '生日' ,
	MemJointime datetime null comment '加入時間'  ,
	UsderStatus int(1) not null comment '賣家功能狀態' ,
	ECash int(10) not null comment '錢包結餘'  
) auto_increment= 11001;
insert into Mem (MemNo,MemAccount,MemPassword,MemStatus,MemVrfed,MemNoVrftime,MemName,MemMobile,MemCity,MemDist,MemAdd,MemEmail,MemBirth,MemJointime,UsderStatus,ECash)
value (11001,'abc101','123456',1,1,'2021-05-21 12:24:00','Jax','0988123456','桃園市','桃園區','北桃路1號','abc101@gmail.com','1992-07-26 00:00:00','2021-05-21 12:14:00',0,41000);
insert into Mem (MemNo,MemAccount,MemPassword,MemStatus,MemVrfed,MemNoVrftime,MemName,MemMobile,MemCity,MemDist,MemAdd,MemEmail,MemBirth,MemJointime,UsderStatus,ECash)
value (11002,'abc102','123456',1,1,'2021-04-21 12:10:00','Diana','0988111222','桃園市','龜山區','北桃路2號','abc102@gmail.com','1990-01-14 00:00:00','2021-04-21 12:00:00',0,2300);
insert into Mem (MemNo,MemAccount,MemPassword,MemStatus,MemVrfed,MemNoVrftime,MemName,MemMobile,MemCity,MemDist,MemAdd,MemEmail,MemBirth,MemJointime,UsderStatus,ECash)
value (11003,'abc103','123456',1,1,'2021-05-01 15:15:00','Draven','0988123111','桃園市','八德區','北桃路3號','abc103@gmail.com','1962-01-28 00:00:00','2021-05-01 15:11:00',0,0);
insert into Mem (MemNo,MemAccount,MemPassword,MemStatus,MemVrfed,MemNoVrftime,MemName,MemMobile,MemCity,MemDist,MemAdd,MemEmail,MemBirth,MemJointime,UsderStatus,ECash)
value (11004,'abc104','123456',1,1,'2021-05-22 16:23:00','Garen','0988333456','桃園市','大溪區','北桃路4號','abc104@gmail.com','1978-11-02 00:00:00','2021-05-22 16:21:00',0,21400);
insert into Mem (MemNo,MemAccount,MemPassword,MemStatus,MemVrfed,MemNoVrftime,MemName,MemMobile,MemCity,MemDist,MemAdd,MemEmail,MemBirth,MemJointime,UsderStatus,ECash)
value (11005,'abc105','123456',1,1,'2021-05-11 12:13:00','Khazix','0988456123','桃園市','蘆竹區','北桃路5號','abc105@gmail.com','1995-04-22 00:00:00','2021-05-11 12:10:00',0,0);
insert into Mem (MemNo,MemAccount,MemPassword,MemStatus,MemVrfed,MemNoVrftime,MemName,MemMobile,MemCity,MemDist,MemAdd,MemEmail,MemBirth,MemJointime,UsderStatus,ECash)
value (11006,'abc106','123456',1,1,'2021-04-07 17:42:00','Gragas','0988123555','桃園市','大園區','北桃路6號','abc106@gmail.com','1997-05-12 00:00:00','2021-04-07 17:40:00',0,700);
insert into Mem (MemNo,MemAccount,MemPassword,MemStatus,MemVrfed,MemNoVrftime,MemName,MemMobile,MemCity,MemDist,MemAdd,MemEmail,MemBirth,MemJointime,UsderStatus,ECash)
value (11007,'abc107','123456',1,1,'2021-05-04 19:21:00','Nnnu','0988234764','桃園市','中壢區','南桃路1號','abc107@gmail.com','1992-09-05 00:00:00','2021-05-04 19:20:00',0,30000);
insert into Mem (MemNo,MemAccount,MemPassword,MemStatus,MemVrfed,MemNoVrftime,MemName,MemMobile,MemCity,MemDist,MemAdd,MemEmail,MemBirth,MemJointime,UsderStatus,ECash)
value (11008,'abc108','123456',1,1,'2021-04-29 15:51:00','Lux','0988997651','桃園市','龍潭區','南桃路2號','abc108@gmail.com','1988-06-21 00:00:00','2021-04-29 15:50:00',0,40000);
insert into Mem (MemNo,MemAccount,MemPassword,MemStatus,MemVrfed,MemNoVrftime,MemName,MemMobile,MemCity,MemDist,MemAdd,MemEmail,MemBirth,MemJointime,UsderStatus,ECash)
value (11009,'abc109','123456',1,1,'2021-03-29 12:07:00','Morgana','0988556612','桃園市','平鎮區','南桃路3號','abc109@gmail.com','1991-03-27 00:00:00','2021-03-29 12:06:00',0,200);
insert into Mem (MemNo,MemAccount,MemPassword,MemStatus,MemVrfed,MemNoVrftime,MemName,MemMobile,MemCity,MemDist,MemAdd,MemEmail,MemBirth,MemJointime,UsderStatus,ECash)
value (11010,'abc110','123456',1,1,'2021-03-21 08:12:00','Nidalee','0988334422','桃園市','楊梅區','南桃路4號','abc110@gmail.com','1994-06-08 00:00:00','2021-03-21 08:10:00',0,0);
insert into Mem (MemNo,MemAccount,MemPassword,MemStatus,MemVrfed,MemNoVrftime,MemName,MemMobile,MemCity,MemDist,MemAdd,MemEmail,MemBirth,MemJointime,UsderStatus,ECash)
value (11011,'abc111','123456',1,1,'2021-04-17 08:04:00','Leona','0988885123','桃園市','新屋區','南桃路5號','abc111@gmail.com','1996-01-09 00:00:00','2021-04-17 07:54:00',0,0);
insert into Mem (MemNo,MemAccount,MemPassword,MemStatus,MemVrfed,MemNoVrftime,MemName,MemMobile,MemCity,MemDist,MemAdd,MemEmail,MemBirth,MemJointime,UsderStatus,ECash)
value (11012,'abc112','123456',1,1,'2021-05-15 09:12:00','Brand','0988135791','桃園市','觀音區','南桃路6號','abc112@gmail.com','1972-11-21 00:00:00','2021-05-15 09:05:00',0,0);


create table TransRec (
	TransRecNo int not null comment '錢包交易紀錄編號' primary key auto_increment,
	MemNo int not null comment '會員編號' ,
	FOREIGN KEY (MemNo) REFERENCES MeM(MemNo),
	TransAmount int(5) not null comment '交易金額'  ,
	TransRecTime datetime not null comment '交易紀錄時間' ,
	TransState int(1) not null comment '交易狀態'  ,
	MallName varchar(24) not null comment '商城名稱' ,
	OrderNo int(16) null comment '訂單編號/商品編號'  ,
	TransCont varchar(100) not null comment '訂單網址連結'  
) auto_increment= 15001;
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15001,11003,22000,'2021-6-1 17:16:30',2,'系統儲值',null,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15002,11003,-21400,'2021-6-2 17:21:30',1,'拍賣商城(拍賣)',47001,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15003,11007,30000,'2021-6-18 15:07:20',2,'系統儲值',null,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15004,11007,-26900,'2021-6-18 16:30:45',1,'拍賣商城(拍賣)',47002,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15005,11007,26900,'2021-6-20 14:23:39',0,'拍賣商城(拍賣)',47002,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15006,11007,30000,'2021-6-21 17:00:00',2,'系統儲值',null,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15007,11007,-30000,'2021-6-22 10:00:00',1,'一般商城',24002,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15008,11001,35000,'2021-6-23 08:50:47',2,'系統儲值',null,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15009,11001,-30900,'2021-6-25 09:02:47',1,'拍賣商城(拍賣)',47003,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15010,11008,50000,'2021-6-28 17:40:21',2,'系統儲值',null,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15011,11008,-30000,'2021-7-1 10:00:00',1,'二手商城',31001,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15012,11009,20000,'2021-7-1 11:10:23',2,'系統儲值',null,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15013,11009,-19900,'2021-7-1 12:10:24',1,'二手商城',31002,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15014,11008,-10000,'2021-7-1 12:10:25',1,'二手商城',31003,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15015,11008,30000,'2021-7-1 15:00:00',0,'二手商城',31001,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15016,11005,5000,'2021-7-20 10:06:48',2,'系統儲值',null,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15017,11005,-4690,'2021-7-21 22:20:48',1,'拍賣商城(拍賣)',47004,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15018,11005,14690,'2021-7-22 12:00:00',2,'系統儲值',null,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15019,11005,-15000,'2021-7-23 17:00:00',1,'一般商城',24004,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15020,11002,50000,'2021-7-13 10:29:37',2,'系統儲值',null,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15021,11004,30000,'2021-7-13 10:32:02',2,'系統儲值',null,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15022,11002,-47700,'2021-7-13 10:33:11',1,'拍賣商城(競標)',51001,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15023,11006,35000,'2021-7-13 10:39:26',2,'系統儲值',null,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15024,11006,-34300,'2021-7-13 10:43:00',1,'拍賣商城(競標)',51002,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15025,11004,-28600,'2021-7-13 10:48:03',1,'拍賣商城(競標)',51003,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15026,11003,41400,'2021-7-16 14:05:53',2,'系統儲值',null,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15027,11003,-42000,'2021-7-16 14:12:30',1,'拍賣商城(競標)',51004,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15028,11009,25000,'2021-7-20 18:00:00',2,'系統儲值',null,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15029,11009,-25000,'2021-7-21 14:00:00',1,'一般商城',24003,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15030,11009,15000,'2021-7-23 18:11:50',2,'系統儲值',null,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15031,11009,-14900,'2021-7-26 10:11:48',1,'拍賣商城(拍賣)',47005,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15032,11004,20000,'2021-8-11 14:00:00',2,'系統儲值',null,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15033,11004,-21400,'2021-8-11 15:00:00',1,'一般商城',24001,'');
insert into TransRec (TransRecNo,MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont)
value (15034,11004,21400,'2021-8-11 17:00:00',0,'一般商城',24001,'');




CREATE TABLE ProductKind(
 KindNo         int not null auto_increment,
 KindName       varchar(20) not null,

 PRIMARY KEY (KindNo));

INSERT INTO ProductKind(KindName) VALUES ('手機');
INSERT INTO ProductKind(KindName) VALUES ('電腦');
INSERT INTO ProductKind(KindName) VALUES ('手錶');
INSERT INTO ProductKind(KindName) VALUES ('相機');
INSERT INTO ProductKind(KindName) VALUES ('配件');

set auto_increment_offset=21001;

set auto_increment_increment=1; 

CREATE TABLE Item (
	ItemNo int auto_increment not null comment '商品編號',
	KindNo int not null comment '商品類別編號',
	ItemName varchar(40) not null comment '商品名稱',
	ItemPrice int comment '商品單價',
	ItemState int(1) comment '上架狀態',
	SoldTime datetime comment '下架時間',
	LaunchedTime datetime DEFAULT CURRENT_TIMESTAMP not null comment '上架時間',
	WarrantyDate decimal(5,1) not null comment '商品保固年限',
	ItemProdDescription varchar(1000) not null comment '商品敘述',
	constraint Item_ItemNo_PK primary key (ItemNo),
	constraint Item_KindNo_FK foreign key (KindNo) references ProductKind (KindNo) ON DELETE CASCADE
) AUTO_INCREMENT = 21001;

set auto_increment_offset=22001;

set auto_increment_increment=1;

CREATE TABLE ItemPic (
	ItemPicNo int auto_increment not null comment '商品照片編號',
	ItemPic LongBlob not null comment '商城商品照片',
	ItemNo int not null comment '商品編號',
	constraint ItemPic_ItemPicNo_PK primary key (ItemPicNo),
	constraint ItemPic_ItemNo_FK foreign key (ItemNo) references Item (ItemNo)	 ON DELETE CASCADE
) AUTO_INCREMENT = 22001;

set auto_increment_offset=25001;

set auto_increment_increment=1; 

CREATE TABLE ItemCollection (
	MemNo int not null  comment '會員編號',
	ItemNo int not null  comment '商品編號',
	constraint itemcollection_memnoAnditemno_PK primary key(Memno, Itemno),
	constraint itemcollection_Memno_FK foreign key (Memno) references Mem (Memno) ON DELETE CASCADE,
	constraint itemcollection_Itemno_FK foreign key (Itemno) references Item (Itemno) ON DELETE CASCADE
) AUTO_INCREMENT = 25001;

set auto_increment_offset=24001;

set auto_increment_increment=1;
CREATE TABLE ItemOrder (
	OrderNo int auto_increment  not null comment '一般商城訂單編號',
	MemNo int not null comment '購買會員編號',
	TranTime datetime DEFAULT CURRENT_TIMESTAMP not null comment '商品售出日期',
	OrderTotal int comment '訂單金額',
	OrderState int not null comment '訂單狀態',
	ReceiverName varchar(10) not null comment '收件人姓名',
	ReceiverAddress varchar(100) not null comment '收件人地址',
	ReceiverPhone varchar(10) not null comment '收件人電話',
	constraint ItemOrder_OrderNo_PK primary key (OrderNo),
	constraint ItemOrder_MemNo_FK foreign key (MemNo) references Mem (MemNo) 
) AUTO_INCREMENT = 24001;

set auto_increment_offset=23001;

set auto_increment_increment=1;

CREATE TABLE ItemDetail (
	OrderNo int not null comment '訂單編號',
	ItemNo int not null comment '商品編號',
	ItemSales int comment '商品銷量',
	ItemPrice int comment '商品單價',
	constraint ItemDetail_OrderAndItem_PK primary key(OrderNo, ItemNo),
	constraint ItemDetail_OrderNo_FK foreign key (OrderNo) references ItemOrder (OrderNo),
	constraint ItemDetail_ItemNo_FK foreign key (ItemNo) references Item (ItemNo)
) AUTO_INCREMENT = 23001;


insert into item(kindno, itemname, itemprice, itemstate, soldtime, launchedtime, warrantydate, itemproddescription) values (1, 'CMIS60PRO智慧型手機', 50, 1, (20210511120000), (20210510110000), 1.0, '待補');
insert into item(kindno, itemname, itemprice, itemstate, soldtime, launchedtime, warrantydate, itemproddescription) values (2, 'CMIPB60-94TU2TA', 30000, 1, null, (20210511120000), 1.0, '待補');
insert into item(kindno, itemname, itemprice, itemstate, soldtime, launchedtime, warrantydate, itemproddescription) values (2, 'CMIH-S340MF-I7970020T', 25000, 1, null, (20210512130000), 1.0, '待補');
insert into item(kindno, itemname, itemprice, itemstate, soldtime, launchedtime, warrantydate, itemproddescription) values (3, 'CMI金屬電子手錶(GM-5600SCM-1)', 15000, 1, null, (20210513140000), 1.0, '待補');
insert into item(kindno, itemname, itemprice, itemstate, soldtime, launchedtime, warrantydate, itemproddescription) values (5, 'CMI3.5吋USB3.1外接硬碟', 2000, 1, null, (20210514150000), 1.0, '待補');
insert into item(kindno, itemname, itemprice, itemstate, soldtime, launchedtime, warrantydate, itemproddescription) values (4, 'CMIDSC-RX99M7數位相機', 8000, 1, null, (20210515160000), 1.0, '待補');
insert into item(kindno, itemname, itemprice, itemstate, soldtime, launchedtime, warrantydate, itemproddescription) values (4, 'CMIPowerShotG8MarkIV', 20000, 1, null, (20210516170000), 1.0, '待補');


insert into itempic (itempic, itemno) values ('null', 21001);
insert into itempic (itempic, itemno) values ('null', 21002);
insert into itempic (itempic, itemno) values ('null', 21003);
insert into itempic (itempic, itemno) values ('null', 21004);
insert into itempic (itempic, itemno) values ('null', 21005);
insert into itempic (itempic, itemno) values ('null', 21006);
insert into itempic (itempic, itemno) values ('null', 21007);

insert into ItemCollection (MemNo, ItemNo) values (11004, 21001);
insert into ItemCollection (MemNo, ItemNo) values (11005, 21002);
insert into ItemCollection (MemNo, ItemNo) values (11007, 21003);
insert into ItemCollection (MemNo, ItemNo) values (11009, 21002);
insert into ItemCollection (MemNo, ItemNo) values (11009, 21005);

insert into ItemOrder(MemNo, TranTime, OrderTotal, OrderState, ReceiverName, ReceiverAddress, ReceiverPhone) values (11004, (20210815), 21400, 0, 'Garen', '桃園市大溪區北桃路4號','0988333456' );
insert into ItemOrder(MemNo, TranTime, OrderTotal, OrderState, ReceiverName, ReceiverAddress, ReceiverPhone) values (11007, (20210816), 30000, 4, 'Nnnu', '桃園市中壢區南桃路1號','0988234764' );
insert into ItemOrder(MemNo, TranTime, OrderTotal, OrderState, ReceiverName, ReceiverAddress, ReceiverPhone) values (11009, (20210816), 25000, 2, 'Morgana', '桃園市平鎮區南桃路3號', '0988556612');
insert into ItemOrder(MemNo, TranTime, OrderTotal, OrderState, ReceiverName, ReceiverAddress, ReceiverPhone) values (11004, (20210816), 21400, 3, 'Garen', '桃園市大溪區北桃路4號', '0988333456' );
insert into ItemOrder(MemNo, TranTime, OrderTotal, OrderState, ReceiverName, ReceiverAddress, ReceiverPhone) values (11005, (20210817), 15000, 1, 'Khazix', '桃園市蘆竹區北桃路5號', '0988456123' );

insert into ItemDetail (OrderNo, ItemNo, ItemSales, ItemPrice) values (24001, 21001, 1, 21400);
insert into ItemDetail (OrderNo, ItemNo, ItemSales, ItemPrice) values (24002, 21002, 1, 30000);
insert into ItemDetail (OrderNo, ItemNo, ItemSales, ItemPrice) values (24003, 21003, 1, 25000);
insert into ItemDetail (OrderNo, ItemNo, ItemSales, ItemPrice) values (24004, 21004, 1, 15000);



DROP TABLE IF EXISTS Bid;

SET auto_increment_offset=51001;
SET auto_increment_increment=1;
CREATE TABLE Bid (
	BidProdNo			INT AUTO_INCREMENT NOT NULL COMMENT '競標商品編號',
    KindNo				INT NOT NULL COMMENT '商品類別編號',
    TransRecNo			INT COMMENT '錢包交易紀錄編號',
    BidWinnerNo			INT COMMENT '得標會員編號',
    BidProdName			VARCHAR(50) NOT NULL COMMENT '競標商品名稱',
    BidProdDescription	VARCHAR(1000) COMMENT '競標商品敘述',
    BidProdStartPrice	INT(5) NOT NULL COMMENT '競標商品起標價',
    BidState			INT(2) NOT NULL COMMENT '競標商品競標狀態',
    BidProdStartTime	DATETIME NOT NULL COMMENT '競標商品起標時間',
    BidProdEndTime		DATETIME NOT NULL COMMENT '競標商品截標時間',
    BidWinnerPrice		INT(5) COMMENT '競標商品出價紀錄(最高價)',
    BidPriceIncrement	INT(5) NOT NULL COMMENT '競標商品最低增額',
    ReceiverName		VARCHAR(10) COMMENT '收件人姓名',
    ReceiverAddress		VARCHAR(100) COMMENT '收件人地址',
    ReceiverPhone		VARCHAR(10) COMMENT '收件人電話',
    BidProdState		INT(2) NOT NULL COMMENT '競標商品訂單狀態',
    CONSTRAINT Bid_KindNo_FK FOREIGN KEY (KindNo) REFERENCES ProductKind (KindNo),
    CONSTRAINT Bid_TransRecNo_FK FOREIGN KEY (TransRecNo) REFERENCES TransRec (TransRecNo),
    CONSTRAINT Bid_BidWinnerNo_FK FOREIGN KEY (BidWinnerNo) REFERENCES Mem (MemNo),
    CONSTRAINT Bid_BidProdNo_PK PRIMARY KEY (BidProdNo)
) AUTO_INCREMENT = 51001;
-- nullable cols?
INSERT INTO Bid (KindNo, TransRecNo, BidWinnerNo, BidProdName, BidProdDescription, BidProdStartPrice, BidState, BidProdStartTime, BidProdEndTime, BidWinnerPrice, BidPriceIncrement, ReceiverName, ReceiverAddress, ReceiverPhone, BidProdState) VALUES (1, 15014, 11002, 'CMI3310', '地球上最硬的手機', 3310, 1, '2021/7/13 8:00:00', '2021/7/13 10:30:00', 47700, 500, 'Diana', '桃園市龜山區北桃路2號', '0988111222', 2);
INSERT INTO Bid (KindNo, TransRecNo, BidWinnerNo, BidProdName, BidProdDescription, BidProdStartPrice, BidState, BidProdStartTime, BidProdEndTime, BidWinnerPrice, BidPriceIncrement, ReceiverName, ReceiverAddress, ReceiverPhone, BidProdState) VALUES (2, 15016, 11006, 'IBN5100', '穿越時空的經典電腦', 5100, 1, '2021/7/13 8:30:00', '2021/7/13 10:30:00', 34300, 100, 'Gragas', '桃園市大園區北桃路6號', '0988123555', 2);
INSERT INTO Bid (KindNo, TransRecNo, BidWinnerNo, BidProdName, BidProdDescription, BidProdStartPrice, BidState, BidProdStartTime, BidProdEndTime, BidWinnerPrice, BidPriceIncrement, ReceiverName, ReceiverAddress, ReceiverPhone, BidProdState) VALUES (3, 15017, 11004, '玫瑰金機械錶', '集巧妙設計、經典優雅和精密機械於一身的限量鑽錶', 15000, 1, '2021/7/13 8:30:00', '2021/7/13 10:30:00', 28600, 100, 'Garen', '桃園市大溪區北桃路4號', '0988333456', 2);
INSERT INTO Bid (KindNo, TransRecNo, BidWinnerNo, BidProdName, BidProdDescription, BidProdStartPrice, BidState, BidProdStartTime, BidProdEndTime, BidWinnerPrice, BidPriceIncrement, ReceiverName, ReceiverAddress, ReceiverPhone, BidProdState) VALUES (4, 15019, 11003, 'TakeMeIn', '獨創品牌首款星空級相機', 8120, 1, '2021/7/16 12:00:00', '2021/7/16 14:00:00', 42000, 500, 'Draven', '桃園市八德區北桃路3號', '0988123111', 2);
INSERT INTO Bid (KindNo, TransRecNo, BidWinnerNo, BidProdName, BidProdDescription, BidProdStartPrice, BidState, BidProdStartTime, BidProdEndTime, BidWinnerPrice, BidPriceIncrement, ReceiverName, ReceiverAddress, ReceiverPhone, BidProdState) VALUES (5, null, null, '康米印表機', '獨家專屬印表機，50吋觸控式操作，具定時影印功能', 3600, 0, '2021/8/11 19:00:00', '2021/8/12 22:00:00', 4950, 100, null, null, null, 0);
INSERT INTO Bid (KindNo, TransRecNo, BidWinnerNo, BidProdName, BidProdDescription, BidProdStartPrice, BidState, BidProdStartTime, BidProdEndTime, BidWinnerPrice, BidPriceIncrement, ReceiverName, ReceiverAddress, ReceiverPhone, BidProdState) VALUES (1, null, null, 'MemePhone曜石黑限定款', '具K990高性能處理器與16GB記憶體，5000mAH電池容量', 17900, 0, '2021/8/13 19:00:00', '2021/8/13 22:00:00', null, 500, null, null, null, 0);
INSERT INTO Bid (KindNo, TransRecNo, BidWinnerNo, BidProdName, BidProdDescription, BidProdStartPrice, BidState, BidProdStartTime, BidProdEndTime, BidWinnerPrice, BidPriceIncrement, ReceiverName, ReceiverAddress, ReceiverPhone, BidProdState) VALUES (1, null, null, 'MemePhone炫彩銀限定款', '具K990高性能處理器與16GB記憶體，5000mAH電池容量', 17900, 0, '2021/8/14 19:00:00', '2021/8/14 22:00:00', null, 500, null, null, null, 0);
INSERT INTO Bid (KindNo, TransRecNo, BidWinnerNo, BidProdName, BidProdDescription, BidProdStartPrice, BidState, BidProdStartTime, BidProdEndTime, BidWinnerPrice, BidPriceIncrement, ReceiverName, ReceiverAddress, ReceiverPhone, BidProdState) VALUES (1, null, null, 'MemePhone深海藍限定款', '具K990高性能處理器與16GB記憶體，5000mAH電池容量', 17900, 0, '2021/8/15 19:00:00', '2021/8/15 22:00:00', null, 500, null, null, null, 0);
INSERT INTO Bid (KindNo, TransRecNo, BidWinnerNo, BidProdName, BidProdDescription, BidProdStartPrice, BidState, BidProdStartTime, BidProdEndTime, BidWinnerPrice, BidPriceIncrement, ReceiverName, ReceiverAddress, ReceiverPhone, BidProdState) VALUES (2, null, null, 'IBN5200', '穿越時空的經典電腦', 5200, 1, '2021/8/16 8:30:00', '2021/8/16 10:30:00', null, 500, null, null, null, 0);


DROP TABLE IF EXISTS BidPic;

SET auto_increment_offset=52001;
SET auto_increment_increment=1;

CREATE TABLE BidPic (
	BidProdPicNo		INT AUTO_INCREMENT NOT NULL COMMENT '競標商品照片編號',
    BidProdNo			INT NOT NULL COMMENT '競標商品編號',
    BidProdPicContent	BLOB NOT NULL COMMENT '競標商品照片內容',
    CONSTRAINT BidPic_BidProdNo_FK FOREIGN KEY (BidProdNo) REFERENCES Bid (BidProdNo),
    CONSTRAINT BidPic_BidProdPicNo_PK PRIMARY KEY (BidProdPicNo)
) AUTO_INCREMENT=52001;

DROP TABLE IF EXISTS BidRecord;

SET auto_increment_offset=53001;
SET auto_increment_increment=1;

CREATE TABLE BidRecord (
	BidRecordNo	INT AUTO_INCREMENT NOT NULL COMMENT '出價編號',
	BidProdNo	INT NOT NULL COMMENT '競標商品編號',
	MemNo		INT NOT NULL COMMENT '會員編號',
	BidPrice	INT(5) NOT NULL COMMENT '出價金額',
	BidTime		DATETIME NOT NULL COMMENT '出價時間',
	CONSTRAINT BidRecord_BidProdNo_FK FOREIGN KEY (BidProdNo) REFERENCES Bid (BidProdNo),
	CONSTRAINT BidRecord_MemNo_FK FOREIGN KEY (MemNo) REFERENCES Mem (MemNo),
    CONSTRAINT BidRecord_BidRecordNo_PK PRIMARY KEY (BidRecordNo)
) AUTO_INCREMENT = 53001;

INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51001, 11002, 7900, '2021/7/13 8:03:15');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51001, 11004, 9000, '2021/7/13 8:11:20');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51001, 11002, 18000, '2021/7/13 8:17:32');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51001, 11007, 25500, '2021/7/13 8:28:03');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51002, 11004, 9000, '2021/7/13 8:31:33');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51002, 11001, 15000, '2021/7/13 8:32:59');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51001, 11005, 32000, '2021/7/13 8:38:33');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51003, 11003, 15100, '2021/7/13 8:39:11');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51003, 11002, 16100, '2021/7/13 8:40:57');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51002, 11004, 25000, '2021/7/13 9:12:23');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51001, 11007, 39900, '2021/7/13 9:28:16');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51003, 11008, 20000, '2021/7/13 9:35:09');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51002, 11001, 28000, '2021/7/13 9:43:30');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51002, 11004, 32000, '2021/7/13 9:44:16');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51002, 11001, 32700, '2021/7/13 9:48:17');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51002, 11006, 33300, '2021/7/13 9:55:42');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51003, 11002, 24000, '2021/7/13 10:11:04');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51002, 11001, 33800, '2021/7/13 10:20:01');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51001, 11005, 45000, '2021/7/13 10:21:35');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51003, 11003, 26600, '2021/7/13 10:24:40');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51003, 11004, 28600, '2021/7/13 10:27:31');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51002, 11006, 34300, '2021/7/13 10:27:39');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51001, 11002, 46000, '2021/7/13 10:27:39');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51001, 11005, 46800, '2021/7/13 10:28:54');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51001, 11002, 47700, '2021/7/13 10:29:26');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51004, 11005, 9000, '2021/7/16 12:00:18');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51004, 11007, 12000, '2021/7/16 12:02:23');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51004, 11005, 13300, '2021/7/16 12:11:36');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51004, 11003, 20000, '2021/7/16 12:17:56');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51004, 11005, 21000, '2021/7/16 12:19:40');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51004, 11007, 29500, '2021/7/16 12:26:53');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51004, 11005, 33300, '2021/7/16 12:32:22');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51004, 11003, 36000, '2021/7/16 12:45:07');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51004, 11007, 38000, '2021/7/16 13:32:29');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51004, 11003, 42000, '2021/7/16 13:36:13');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51005, 11002, 3850, '2021/8/11 20:25:11');
INSERT INTO BidRecord (BidProdNo, MemNo, BidPrice, BidTime) VALUES (51005, 11001, 4950, '2021/8/11 20:30:21');

drop table used;
CREATE TABLE Used (
 UsedNo                  INT NOT NULL AUTO_INCREMENT,
 KindNo                  INT NOT NULL,
 BuyerNo                 INT,
 SellerNo                INT NOT NULL,
 TransRecNo              INT,
 UsedName                VARCHAR (50) NOT NULL,
 UsedPrice               INT (6) NOT NULL,
 UsedState               INT (2),
 UsedLaunchedTime        DATETIME NOT NULL,
 SoldTime                DATETIME,
 ReceiverName            VARCHAR (10),
 ReceiverAddress         VARCHAR (100),
 ReceiverPhone           VARCHAR (10),
 UsedProdDescription     VARCHAR (1000) NOT NULL,
 CONSTRAINT Used_PRIMARY_KEY PRIMARY KEY (UsedNo));
 
 
INSERT INTO Used VALUES (NULL, 1, NULL, 11001, NULL, '二手手機', 3000, NULL, '2021-01-01', NULL, '收件人1', '收件地址1', '0900000000', '商品描述1');
INSERT INTO Used VALUES (NULL, 2, 11002, 11003, NULL, '二手電腦', 12000, NULL, '2021-03-03', NULL, '收件人2', '收件地址2', '0911111111', '商品描述2');
INSERT INTO Used VALUES (NULL, 3, NULL, 11004, NULL, '二手手錶', 2000, NULL, '2021-03-03', NULL, '收件人3', '收件地址3', '0922222222', '商品描述3');
INSERT INTO Used VALUES (NULL, 4, NULL, 11005, NULL, '二手相機', 6000, NULL, '2021-03-03', NULL, '收件人4', '收件地址4', '0933333333', '商品描述4');
INSERT INTO Used VALUES (NULL, 5, 11002, 11001, NULL, '二手隨身碟', 600, NULL, '2021-03-03', NULL, '收件人5', '收件地址5', '0944444444', '商品描述5');

CREATE TABLE UsedPic (
 UsedPicNo               INT NOT NULL AUTO_INCREMENT,
 UsedNo                  INT NOT NULL,
 UsedPic                 LONGBLOB NOT NULL,
 CONSTRAINT UsedPic_PRIMARY_KEY PRIMARY KEY (UsedPicNo));
 

INSERT INTO UsedPic VALUES (NULL, 1001, "");

CREATE TABLE UsedMsg (
 UsedMsgNo               INT NOT NULL AUTO_INCREMENT,
 MemNo                   INT NOT NULL,
 UsedNo                  INT NOT NULL,
 UsedMsgText             VARCHAR (50) NOT NULL,
 CONSTRAINT UsedMsg_PRIMARY_KEY PRIMARY KEY (UsedMsgNo));


INSERT INTO UsedMsg VALUES (NULL, 1001, 2002, '留言');

CREATE TABLE UsedReport (
 MemNo                   INT NOT NULL,
 UsedNo                  INT NOT NULL,
 ReportedMemNo           INT NOT NULL,
 UsedReportState         INT(1),
 UsedReportTime          DATETIME NOT NULL,
 UsedReportReason        VARCHAR (100) NOT NULL,
 UsedReportNotice        VARCHAR (50),
 CONSTRAINT UsedReport_PRIMARY_KEY PRIMARY KEY (MemNo, UsedNo));
 

INSERT INTO UsedReport VALUES (1001, 1001, 2002, NULL, '2021-01-01', '檢舉原因', '被檢舉人通知');



create table AuctAct (
	AuctActNo int not null comment '拍賣活動編號' primary key auto_increment,
	AuctActName varchar(50) not null comment '拍賣活動名稱'  ,
	AuctActDesc varchar(300) not null comment '拍賣活動說明' ,
	AuctActState int not null comment '拍賣活動狀態'  ,
	AuctStartTime datetime not null comment '拍賣活動起始時間' ,
	AuctEndTime datetime not null comment '拍賣活動結束時間'  
) auto_increment= 43001;

create table AuctProd (
	AuctProdNo int not null comment '拍賣商品編號' primary key auto_increment,
	AuctProdName varchar(50) not null comment '拍賣商品名稱'  ,
	KindNo int not null comment '商品類別編號' ,
	FOREIGN KEY (KindNo) REFERENCES ProductKind(KindNo),
	AuctProdState int(1) not null comment '拍賣商品狀態'  ,
	AuctProdDesc varchar(500) null comment '拍賣商品說明' ,
	AuctProdCreTime datetime not null comment '拍賣商品建立時間'  ,
	AuctProdModTime datetime not null comment '拍賣商品修改時間'  
) auto_increment= 41001;

create table AuctProdPic (
	AuctProdPicNo int not null comment '拍賣商品照片編號' primary key auto_increment,
	AuctProdNo int not null comment '拍賣商品編號' ,
	FOREIGN KEY (AuctProdNo) REFERENCES AuctProd (AuctProdNo),
	AuctProdPicInfo varchar(100) not null comment '拍賣商品照片資訊'  ,
	AuctProdPic LONGBLOB null comment '拍賣商品照片' ,
	AuctProdPicFormat varchar(50) not null comment '拍賣商品照片格式'  
) auto_increment= 42001;

create table AuctActPic (
	AuctActPicNo int not null comment '拍賣活動照片編號' primary key auto_increment,
	AuctActNo int not null comment '拍賣活動編號' ,
	FOREIGN KEY (AuctActNo) REFERENCES AuctAct (AuctActNo),
	AuctActPicInfo varchar(100) not null comment '拍賣活動照片資訊'  ,
	AuctActPic LONGBLOB null comment '拍賣活動照片' ,
	AuctActPicFormat varchar(50) null comment '拍賣活動照片格式'  
) auto_increment= 44001;

create table AuctActProd (
	AuctActProdNo int not null comment '拍賣活動商品編號' primary key auto_increment,
	AuctActNo int not null comment '拍賣活動編號' ,
	FOREIGN KEY (AuctActNo) REFERENCES AuctAct (AuctActNo),
	AuctProdNo int not null comment '拍賣商品編號' ,
	FOREIGN KEY (AuctProdNo) REFERENCES AuctProd (AuctProdNo),
	AuctProdQty int not null comment '拍賣商品數量'  ,
	AuctProdRemain int null comment '拍賣商品目前數量' ,
	AuctState int not null  comment '拍賣狀態'  ,
	AuctProdPrice int(5) not null  comment '拍賣商品價格'  
) auto_increment= 45001;

create table AuctOrd (
	AuctOrdNo int not null comment '拍賣活動訂單編號' primary key auto_increment,
	MemNo int not null comment '會員編號'  ,
	AuctOrdAmount int not null comment '拍賣訂單金額' ,
	ReceName varchar(10) not null comment '收件人姓名'  ,
	ReceAddr varchar(100) not null comment '收件地址' ,
	RecePhone varchar(10) not null comment '收件人電話'  ,
	Note varchar(100) null comment '買家備註' ,
	AuctOrdTime datetime not null comment '拍賣活動訂單時間'  ,
	AuctOrdModTime datetime not null comment '拍賣活動訂單修改時間' ,
	AuctOrdState int not null comment '拍賣活動訂單狀態'  
) auto_increment= 47001;

create table AuctOrdDetl (
    AuctOrdNo int not null comment '拍賣活動訂單編號' ,
	PRIMARY KEY (AuctOrdNo,AuctActProdNo),
	AuctActProdNo int not null comment '拍賣活動商品編號' ,
	FOREIGN KEY (AuctOrdNo) REFERENCES AuctOrd (AuctOrdNo),
	ProdPurQty int not null comment '商品購買數量',
    Price int not null comment '商品價格' ,
	SumPrice int not null comment '總價',
    FOREIGN KEY (AuctActProdNo) REFERENCES AuctActProd (AuctActProdNo)
) ;


insert into AuctAct (AuctActNo,AuctActName,AuctActDesc,AuctActState,AuctStartTime,AuctEndTime)
value (43001,'MyPhone&YourPhone!','MyPhone在手，希望無窮',1,'2021-6-1 00:00:00','2021-6-30 00:00:00');
insert into AuctAct (AuctActNo,AuctActName,AuctActDesc,AuctActState,AuctStartTime,AuctEndTime)
value (43002,'Fun in Home','宅在家不無聊',1,'2021-6-20 00:00:00','2021-7-25 00:00:00');
insert into AuctAct (AuctActNo,AuctActName,AuctActDesc,AuctActState,AuctStartTime,AuctEndTime)
value (43003,'Stay with You','防疫期間也要動滋動滋',1,'2021-7-20 00:00:00','2021-8-25 00:00:00');
insert into AuctAct (AuctActNo,AuctActName,AuctActDesc,AuctActState,AuctStartTime,AuctEndTime)
value (43004,'Wonder Foto','享受旅行，紀錄生活',0,'2021-8-10 00:00:00','2021-9-10 00:00:00');

insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44001,43001,'banner1',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44002,43001,'banner2',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44003,43001,'banner3',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44004,43002,'banner1',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44005,43002,'banner2',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44006,43002,'banner3',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44007,43003,'banner1',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44008,43003,'banner2',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44009,43003,'banner3',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44010,43004,'banner1',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44011,43004,'banner2',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44012,43004,'banner3',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44013,43001,'cart',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44014,43002,'cart',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44015,43003,'cart',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44016,43004,'cart',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44017,43001,'button',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44018,43002,'button',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44019,43003,'button',null,'image/jpeg');
insert into AuctActPic (AuctActPicNo,AuctActNo,AuctActPicInfo,AuctActPic,AuctActPicFormat)
value (44020,43004,'button',null,'image/jpeg');

insert into AuctOrd (AuctOrdNo,MemNo,AuctOrdAmount,ReceName,ReceAddr,RecePhone,Note,AuctOrdTime,AuctOrdModTime,AuctOrdState)
value (47001,11003,21400,'Coco','臺北市中山區南京東路三段219號','0936476253','iPhoneSE：白色、POCO X3：藍色','2021-6-2 17:21:30','2021-6-5 13:30:30',3);
insert into AuctOrd (AuctOrdNo,MemNo,AuctOrdAmount,ReceName,ReceAddr,RecePhone,Note,AuctOrdTime,AuctOrdModTime,AuctOrdState)
value (47002,11007,26900,'Maru','臺北市中正區濟南路一段321號','0918276457','iPhone12：紫色','2021-6-18 16:30:45','2021-6-18 18:30:45',0);
insert into AuctOrd (AuctOrdNo,MemNo,AuctOrdAmount,ReceName,ReceAddr,RecePhone,Note,AuctOrdTime,AuctOrdModTime,AuctOrdState)
value (47003,11001,30900,'Yoyo','桃園市平鎮區福龍路一段100號','0931245678','MacBookAir(2020) ：金色','2021-6-25 09:02:47','2021-6-30 14:23:39',3);
insert into AuctOrd (AuctOrdNo,MemNo,AuctOrdAmount,ReceName,ReceAddr,RecePhone,Note,AuctOrdTime,AuctOrdModTime,AuctOrdState)
value (47004,11005,4690,'Sherry','桃園市中壢區復興路46號9樓','0919234572','Mi腕帶：青、紫、藍、粉、綠各一、MiWatch Sport：白色','2021-7-21 22:20:48','2021-7-26 16:11:48',2);
insert into AuctOrd (AuctOrdNo,MemNo,AuctOrdAmount,ReceName,ReceAddr,RecePhone,Note,AuctOrdTime,AuctOrdModTime,AuctOrdState)
value (47005,11009,14900,'Jack','桃園市平鎮區延平路二段221號','0937893652','Watch SE：太空灰、編織單圈錶環：黑色','2021-7-26 10:13:48','2021-7-27 09:30:04',1);

create table Admini(
	AdminNo int not null auto_increment primary key,
    AdminAccount varchar(20) not null,
    AdminPassword varchar(20) not null,
    AdminName varchar(20) not null,
	AdminPhone varchar(10) not null
)
insert into admin (AdminAccount,AdminPassword,AdminName,AdminPhone) values ('test123','test123','admin','0925165475');

create table AdminAuthrization(
	AdminAuthrizationNo  int not null auto_increment primary key,
    AdminAuthrizationName varchar(20) not null
    
)

create table AdminAuth(
	AdminNo  int not null auto_increment primary key,
    AdminAuthrizationNo int not null
    
)




