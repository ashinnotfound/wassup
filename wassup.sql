CREATE SCHEMA wassup;

create table account
(
    id        int auto_increment
        primary key,
    user_name varchar(20)  not null,
    password  varchar(255) not null
);
create index idx_user_name
    on account (user_name);

create table user_info
(
    id        int         not null
        primary key,
    user_name varchar(20) not null comment '用户名',
    nick_name varchar(20) not null comment '昵称',
    avatar    varchar(32) not null comment '头像'
);
create index user_info_user_name_index
    on user_info (user_name);

create table post
(
    id        int auto_increment
        primary key,
    content   varchar(500) null comment '帖子内容',
    has_media tinyint(1) not null comment '是否有媒体文件',
    user_id   int      not null comment '用户 ID',
    post_time datetime not null comment '发帖时间'
);
create index idx_user_id
    on post (user_id);

create table media
(
    id      int auto_increment
        primary key,
    post_id int         not null comment '帖子 ID',
    md5     varchar(32) not null comment '媒体文件md5'
        constraint media_post_id_fk
        foreign key (post_id) references post (id)
);
create index idx_post_id
    on media (post_id);
