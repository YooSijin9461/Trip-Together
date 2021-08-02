use ssafy_web_db;

drop table if exists chat;
drop table if exists conference_category;
drop table if exists notice;
drop table if exists comments;
drop table if exists board;
drop table if exists user;
drop table if exists conference_room;

create table if not exists conference_room (
	conference_no int not null primary key auto_increment,
    conference_category varchar(20) not null,
    owner varchar(20) not null,
    start_time datetime default current_timestamp,
    thumbnail_url varchar(100),
    title varchar(60) not null,
    description text not null,
    is_active bool,
    limit_users_num int,
    conference_password varchar(20)
);

create table if not exists user (
	user_id varchar(20) not null primary key,
    password varchar(100) not null,
    user_name varchar(30) not null,
    gender char(1) not null,
    phone_num varchar(20) not null,
    email varchar(50) not null,
    age int not null,
    img varchar(100),
    mbti varchar(10),
    avg_score double,
    conference_room_no int,
    is_guide bool,
    foreign key (conference_room_no) references conference_room(conference_no)
);

create table if not exists board (
	board_no int not null primary key auto_increment,
    user_id varchar(20) not null,
    board_title varchar(50) not null,
    board_content text not null,
    board_rate double,
    foreign key (user_id) references user(user_id)
);

create table if not exists notice (
	notice_no int not null primary key auto_increment,
    notice_title varchar(50) not null,
    notice_content text not null
);

create table if not exists comments (
	comment_no int not null primary key auto_increment,
    user_id varchar(20) not null,
	comment text not null,
    comment_time datetime default current_timestamp,
    board_no int not null, 
    foreign key (user_id) references user(user_id),
    foreign key (board_no) references board(board_no)
);

create table if not exists chat (
	chat_no int not null primary key auto_increment,
    user_id varchar(20) not null,
    chatting text not null,
    chat_time datetime default current_timestamp,
    conference_no int not null,
    foreign key (user_id) references user(user_id),
    foreign key (conference_no) references conference_room(conference_no)
);