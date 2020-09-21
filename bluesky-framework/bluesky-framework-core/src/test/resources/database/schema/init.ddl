drop table if exists `account`;
create table `account` (
    `id`               bigint       not null auto_increment comment '主键id',
    `name`             varchar(30)  not null comment '姓名',
    `nick_name`        varchar(30)  default null comment '昵称',
    `head_img`         varchar(500) default null comment '头像',
    `mobile`           varchar(30)  default null comment '手机号',
    `role_type`        tinyint      not null comment '类型：1平台系统/2政务单位/3个人认证/4企业认证',
    `role_id`          bigint       not null comment '角色id',
    `organization_id`  bigint       default null comment '单位id',
    `department`       varchar(60)  default null comment '所在部门',
    `id_card`          varchar(40)  default null comment '身份证号',
    `password`         varchar(60)  not null comment '密码',
    `last_login_ip`    varchar(60)  default null comment '上次登录ip',
    `last_login_time`  datetime     default null comment '上次登录时间',
    `authorities_text` varchar(100) not null comment '角色权限',
    `sort`             mediumint    default null comment '顺序',
    `status`           tinyint      not null comment '状态:0启用，-5禁用，-10锁定，-15删除',
    `created_at`       datetime     not null comment '创建时间',
    `updated_at`       datetime     not null comment '更新时间',
    primary key (`id`),
    index organization_id (organization_id),
    index role_id (role_id)
) engine = innodb
  default charset = utf8mb4 comment '账户';


drop table if exists `personal_user_verification`;
create table `personal_user_verification` (
    `id`              bigint       not null auto_increment comment '主键id',
    `account_id`      bigint      default null comment '核验通过生成账户id',
    `name`            varchar(30)  not null comment '姓名',
    `mobile`          varchar(30) default null comment '手机号',
    `id_card`         varchar(40) default null comment '身份证号',
    `nationality`     varchar(50)  not null comment '名族',
    `connect_address` varchar(100) not null comment '联系地址',
    `email`           varchar(50)  not null comment '邮箱',
    `status`          tinyint      not null comment ' 状态：0待核验，5核验通过，-5核验失败',
    `verification_at` datetime     not null comment '核验时间',
    `created_at`      datetime     not null comment '创建时间',
    `updated_at`      datetime     not null comment '更新时间',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4 comment '个人认证用户核验';


drop table if exists `role`;
create table `role` (
    `id`         bigint      not null auto_increment comment '主键id',
    `type`       tinyint     not null comment '类型：1平台系统/2政务单位/3个人认证/4企业认证',
    `level`      tinyint     not null comment '级别：-1无/1省级/2市级/3区县级',
    `name`       varchar(30) not null comment '名称',
    `status`     tinyint     not null default 0 comment '状态：0正常，-5删除',
    `created_at` datetime    not null comment '创建时间',
    `updated_at` datetime    not null comment '更新时间',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4 comment '角色';


drop table if exists `organization`;
create table `organization` (
    `id`                         bigint      not null auto_increment comment '主键id',
    `code`                       varchar(30) not null comment '编码',
    `name`                       varchar(30) not null comment '单位名称',
    `full_name`                  varchar(60) not null comment '单位全称',
    `unified_social_credit_code` varchar(30)  default null comment '统一社会信用代码',
    `parent_id`                  bigint       default null comment '上级单位id',
    `area_code`                  varchar(15) not null comment '地区编码',
    `region_id`                  bigint      not null comment '省市区id',
    `status`                     tinyint     not null comment '状态：0启用，-5禁用，-10删除',
    `sort`                       mediumint   not null comment '顺序',
    `memo`                       varchar(500) default null comment '备注信息',
    `created_at`                 datetime    not null comment '创建时间',
    `updated_at`                 datetime    not null comment '更新时间',
    primary key (`id`),
    index region_id (region_id)
) engine = innodb
  default charset = utf8mb4 comment '单位';


drop table if exists `region`;
create table `region` (
    `id`          bigint      not null auto_increment comment '主键id',
    `name`        varchar(30) not null comment '名称',
    `level`       tinyint     not null comment '级别',
    `code`        varchar(15) not null comment '行政区域代码',
    `parent_id`   bigint               default null comment '父级id',
    `parent_code` varchar(15)          default null comment '父级编码',
    `top`         boolean     not null comment '顶级节点：0非最顶级节点，1是最顶级节点',
    `leaf`        boolean     not null comment '末节点:0非末节点，1末节点',
    `status`      tinyint     not null default 0 comment '状态：0正常，-5删除',
    `created_at`  datetime    not null comment '创建时间',
    `updated_at`  datetime    not null comment '更新时间',
    primary key (`id`),
    index parent_id (parent_id),
    unique key `region_code` (code)
) engine = innodb
  default charset = utf8mb4 comment '省市区';


drop table if exists `account_operate_log`;
create table `account_operate_log` (
    `id`                bigint      not null auto_increment comment '主键id',
    `account_id`        bigint      not null comment '账户id',
    `account_name`      varchar(30) not null comment '账户名称',
    `organization_id`   bigint       default null comment '单位id',
    `organization_name` varchar(30)  default null comment '单位名称',
    `mobile`            varchar(30)  default null comment '手机号',
    `ip`                varchar(30) not null comment 'ip地址',
    `ip_area`           varchar(50)  default null comment 'ip地址所在区域',
    `step_code`         varchar(50) not null comment '步骤编码',
    `step`              varchar(30) not null comment '步骤',
    `memo`              varchar(200) default null comment '说明',
    `created_at`        datetime    not null comment '创建时间',
    `updated_at`        datetime    not null comment '更新时间',
    primary key (`id`),
    index organization_id (organization_id)
) engine = innodb
  default charset = utf8mb4 comment '账户操作日志';


drop table if exists `account_login_log`;
create table `account_login_log` (
    `id`                bigint      not null auto_increment comment '主键id',
    `account_id`        bigint      not null comment '账户id',
    `account_name`      varchar(30) not null comment '账户名称',
    `organization_id`   bigint       default null comment '单位id',
    `organization_name` varchar(30)  default null comment '单位名称',
    `mobile`            varchar(30)  default null comment '手机号',
    `ip`                varchar(30) not null comment 'ip地址',
    `ip_area`           varchar(50)  default null comment 'ip地址所在区域',
    `type`              tinyint     not null comment '1登录/2注销',
    `success`           tinyint     not null comment '1成功/-1失败',
    `memo`              varchar(200) default null comment '说明',
    `created_at`        datetime    not null comment '创建时间',
    `updated_at`        datetime    not null comment '更新时间',
    primary key (`id`),
    index organization_id (organization_id)
) engine = innodb
  default charset = utf8mb4 comment '账户操作日志';

-- 角色权限
create table if not exists `role_authority` (
    `id`           bigint       not null auto_increment comment '主键id',
    `role_id`      bigint       not null comment '角色ID',
    `operation_id` bigint       not null comment '操作ID',
    `operation`    varchar(100) not null comment '操作',
    `created_at`   datetime     null comment '创建时间',
    `updated_at`   datetime     null comment '更新时间',
    primary key (`id`),
    index index_role_id (role_id)
) engine = innodb
  default charset = utf8mb4 comment '角色权限';

-- 用户权限
create table if not exists `account_authority` (
    `id`           bigint       not null auto_increment comment '主键id',
    `account_id`   bigint       not null comment '用户ID',
    `operation_id` bigint       not null comment '操作ID',
    `operation`    varchar(100) not null comment '操作',
    `created_at`   datetime     null comment '创建时间',
    `updated_at`   datetime     null comment '更新时间',
    primary key (`id`),
    index index_account_id (account_id)
) engine = innodb
  default charset = utf8mb4 comment '用户权限';


-- setting
create table if not exists page_setting
(
    id          bigint auto_increment comment 'id' primary key,
    page        varchar(50)  null comment '页面',
    type        varchar(50)  null comment '类型',
    value       varchar(512) null comment '值',
    operator_id bigint       null comment '操作人ID',
    created_at  datetime     null comment '创建时间',
    updated_at  datetime     null comment '修改时间'
)
    comment '页面配置'
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

create table if not exists menu_source
(
    id               bigint auto_increment comment 'id' primary key,
    code             varchar(50)  null comment '菜单编码',
    name             varchar(50)  null comment '菜单名称',
    url              varchar(256) null comment '菜单URL链接',
    background_color varchar(50)  null comment '首页展示背景颜色',
    icon             varchar(50)  null comment '图标',
    icon_color       varchar(50)  null comment '图标颜色',
    color            varchar(50)  null comment '首页名称颜色',
    system_menu      tinyint(1)   null comment '是否系统菜单',
    parent_id        bigint       null comment '父菜单ID',
    seq              int          null comment '顺序',
    created_at       datetime     null comment '创建时间',
    updated_at       datetime     null comment '修改时间',
    KEY `index_parent_id` (`parent_id`)
)
    comment '菜单'
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;


create table if not exists menu_source_operation
(
    id              bigint auto_increment comment 'id' primary key,
    menu_id         bigint       null comment '所属菜单',
    parent_menu_ids varchar(256) null comment '所属菜单的父IDs',
    operation       varchar(100) null comment '操作',
    operation_name  varchar(100) null comment '操作名称',
    created_at      datetime     null comment '创建时间',
    updated_at      datetime     null comment '修改时间',
    KEY `index_menu_id` (`menu_id`)
)
    comment '菜单操作项'
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

create table if not exists custom_index
(
    id         bigint auto_increment comment 'id' primary key,
    account_id bigint       null comment '账户ID',
    menu_ids   varchar(256) null comment '菜单资源ID',
    created_at datetime     null comment '创建时间',
    KEY `index_account_id` (`account_id`)
)
    comment '首页个性化'
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;


-- 初始化数据
-- account
insert into role
    (id, type, level, name, status, created_at, updated_at)
values (1000, 1, -1, '系统管理员', 0, now(), now()),
       (1002, 3, -1, '个人认证用户', 0, now(), now()),
       (1001, 4, -1, '企业认证用户', 0, now(), now()),
       (1003, 2, 1, '省信用办管理员', 0, now(), now()),
       (1004, 2, 1, '省级成员单位管理员', 0, now(), now()),
       (1005, 2, 1, '省级成员单位操作员', 0, now(), now()),
       (1006, 2, 1, '省级成员单位查询用户', 0, now(), now()),
       (1007, 2, 2, '市信用办管理员', 0, now(), now()),
       (1008, 2, 2, '市级成员单位管理员', 0, now(), now()),
       (1009, 2, 2, '市级成员单位操作员', 0, now(), now()),
       (1010, 2, 2, '市级成员单位查询用户', 0, now(), now()),
       (1011, 2, 3, '区县信用办管理员', 0, now(), now()),
       (1012, 2, 3, '区县成员单位管理员', 0, now(), now()),
       (1013, 2, 3, '区县成员单位操作员', 0, now(), now()),
       (1014, 2, 3, '区县成员单位查询用户', 0, now(), now());


insert into account
    (id, name, nick_name, head_img, mobile, role_type, role_id, organization_id, department, id_card, password, last_login_ip, last_login_time, authorities_text, sort, status, created_at, updated_at)
values (1000, '系统管理员', '系统管理员', null, 13000000000, 1, 1000, null, null, null, '$2a$10$3ARITjXyIWR4SmuNNKHC6u2q89c1GJXmdtfCq43qhE7./a5Vz5Pgy', null, null, 'ROLE_PLATFORM,ROLE_ADMIN', 1, 0, now(), now());

-- setting
insert into page_setting(id, page, type, value, operator_id, created_at, updated_at)
values (1, 'login', 'logo', 'login_logo.jpg', null, now(), now()),
       (2, 'login', 'background', 'login_background.jpg', null, now(), now()),
       (3, 'login', 'background_color', 'rgb(0, 1, 31)', null, now(), now()),
       (4, 'login', 'login_type', 'password,captcha,wechat', null, now(), now()),
       (5, 'index', 'logo', 'index_logo.jpg', null, now(), now()),
       (6, 'index', 'query_background', 'index_query_background.jpg', null, now(), now()),
       (7, 'index', 'query_background_color', '#1890FF', null, now(), now()),
       (8, 'work', 'logo', 'work_logo.jpg', null, now(), now()),
       (9, 'work', 'theme_color', '#1890FF', null, now(), now()),
       (10, 'copyRight', 'organizer', '杭州湛联科技有限公司', null, now(), now()),
       (11, 'copyRight', 'support', '杭州湛联科技有限公司', null, now(), now()),
       (12, 'default', 'upload_dir', 'D:\\deploy_web\\crm\\img', null, now(), now()),
       (13, 'default', 'system_name', '杭州湛联科技有限公司', null, now(), now()),
       (14, 'index', 'app_style_type', '1', null, now(), now());

insert into menu_source(id, code, name, url, background_color, icon, icon_color, color, system_menu, parent_id, seq,
                        created_at, updated_at)
values (1, 'organization_manage', '用户管理', null, null, null, null, null, 1, 0, 1, now(), now()),
       (2, 'system_manage', '系统管理', null, null, null, null, null, 1, 0, 2, now(), now()),
       (3, 'om_region_manage', '行政区管理', null, null, null, null, null, 1, 1, 1, now(), now()),
       (4, 'om_role_manage', '角色管理', null, null, null, null, null, 1, 1, 2, now(), now()),
       (5, 'om_org_manage', '单位管理', null, null, null, null, null, 1, 1, 3, now(), now()),
       (6, 'om_user_manage', '用户管理', null, null, null, null, null, 1, 1, 4, now(), now()),
       (7, 'om_auth_manage', '权限管理', null, null, null, null, null, 1, 1, 5, now(), now()),
       -- (8, 'om_personal_manage', '个人认证用户管理', null, null, null, null, null, 1, 1, 6, now(), now()),
       -- (9, 'om_ent_manage', '企业认证用户管理', null, null, null, null, null, 1, 1, 7, now(), now()),
       -- (10, 'om_api_manage', '接口用户管理', null, null, null, null, null, 1, 1, 8, now(), now()),
       (11, 'om_log_manage', '用户日志', null, null, null, null, null, 1, 1, 9, now(), now()),
       (12, 'sm_login_manage', '登录页管理', null, null, null, null, null, 1, 2, 1, now(), now()),
       (13, 'sm_index_manage', '首页管理', null, null, null, null, null, 1, 2, 2, now(), now()),
       (14, 'sm_work_manage', '工作页管理', null, null, null, null, null, 1, 2, 3, now(), now()),
       (15, 'sm_copyright_manage', '版权信息管理', null, null, null, null, null, 1, 2, 4, now(), now()),
       (16, 'sm_menu_manage', '菜单管理', null, null, null, null, null, 1, 2, 5, now(), now());

insert into menu_source_operation(id, menu_id, parent_menu_ids, operation, operation_name, created_at, updated_at)
values (1, 3, '1,3', 'om_region_manage', '管理', now(), now()),
       (2, 3, '1,3', 'om_region_view', '查看', now(), now()),
       (3, 4, '1,4', 'om_role_manage', '管理', now(), now()),
       (4, 4, '1,4', 'om_role_view', '查看', now(), now()),
       (5, 5, '1,5', 'om_org_add', '新增', now(), now()),
       (6, 5, '1,5', 'om_org_open', '启用/停用', now(), now()),
       (7, 5, '1,5', 'om_org_delete', '删除', now(), now()),
       (8, 5, '1,5', 'om_org_update', '修改', now(), now()),
       (9, 5, '1,5', 'om_org_view', '查看', now(), now()),
       (10, 6, '1,6', 'om_user_add', '新增', now(), now()),
       (11, 6, '1,6', 'om_user_open', '启用/停用', now(), now()),
       (12, 6, '1,6', 'om_user_delete', '删除', now(), now()),
       (13, 6, '1,6', 'om_user_export', '导出', now(), now()),
       (14, 6, '1,6', 'om_user_auth_manage', '权限设置', now(), now()),
       (15, 6, '1,6', 'om_user_view', '查看', now(), now()),
       (16, 6, '1,6', 'om_user_update', '修改', now(), now()),
       (17, 7, '1,7', 'om_auth_manage', '管理', now(), now()),
       (18, 7, '1,7', 'om_auth_view', '查看', now(), now()),
       -- (19, 8, '1,8', 'om_personal_check', '核验', now(), now()),
       -- (20, 8, '1,8', 'om_personal_view', '查看', now(), now()),
       -- (21, 9, '1,9', 'om_ent_check', '核验', now(), now()),
       -- (22, 9, '1,9', 'om_ent_view', '查看', now(), now()),
       -- (23, 10, '1,10', 'om_api_add', '新建', now(), now()),
       -- (24, 10, '1,10', 'om_api_delete', '删除', now(), now()),
       -- (25, 10, '1,10', 'om_api_view', '查看', now(), now()),
       (26, 11, '1,11', 'om_log_view', '查看', now(), now()),
       (27, 12, '1,12', 'sm_login_manage', '管理', now(), now()),
       (28, 12, '1,12', 'sm_login_view', '查看', now(), now()),
       (29, 13, '1,13', 'sm_index_manage', '管理', now(), now()),
       (30, 13, '1,13', 'sm_index_view', '查看', now(), now()),
       (31, 14, '1,14', 'sm_work_manage', '管理', now(), now()),
       (32, 14, '1,14', 'sm_work_view', '查看', now(), now()),
       (33, 15, '1,15', 'sm_copyright_manage', '管理', now(), now()),
       (34, 15, '1,15', 'sm_copyright_view', '查看', now(), now()),
       (35, 16, '1,16', 'sm_menu_manage', '管理', now(), now()),
       (36, 16, '1,16', 'sm_menu_view', '查看', now(), now());
