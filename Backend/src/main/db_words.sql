create table admin
(
    id          int auto_increment comment '主键id',
    username    varchar(20)                  not null comment '用户名username',
    password    varchar(20) default '123456' not null comment '密码password',
    create_time datetime                     not null comment '创建时间',
    update_time datetime                     not null comment '最后更新时间',
    constraint admin_pk
        primary key (id),
    constraint admin_uk
        unique (username)
)
    comment '管理员表';

create table users
(
    id          int auto_increment comment '主键id',
    username    varchar(20)                  not null comment '用户名username',
    password    varchar(20) default '123456' not null comment '密码password',
    create_time datetime                     not null comment '创建时间',
    update_time datetime                     not null comment '最后更新时间',
    constraint admin_pk
        primary key (id),
    constraint admin_uk
        unique (username)
)
    comment '用户表';

create table words
(
    id            int auto_increment comment '主键id',
    word          varchar(30)   not null comment '英文单词',
    classes       tinyint       not null comment '词性',
    meaning       varchar(100)  not null comment '中文释义',
    description   varchar(100)  not null comment '英文词义',
    example       varchar(300)  null comment '例句',
    type          tinyint       not null comment '分类属性',
    queries_times int default 0 not null comment '查询次数',
    create_time   datetime      not null comment '创建时间',
    update_time   datetime      not null comment '最后更新时间',
    constraint words_pk
        primary key (id),
    constraint words_uk
        unique (word)
)
    comment '单词表';


