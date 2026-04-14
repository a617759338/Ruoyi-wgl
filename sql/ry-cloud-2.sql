-- ----------------------------
-- 人员表（sys_user）添加 工号 + 直接上级
-- ----------------------------
ALTER TABLE sys_user
    ADD COLUMN work_code   varchar(30)    DEFAULT NULL COMMENT '工号',
    ADD COLUMN superior    varchar(100)   DEFAULT NULL COMMENT '直接上级';

-- ----------------------------
-- 部门表（sys_dept）添加 总监 + 经理 + 助理
-- ----------------------------
ALTER TABLE sys_dept
    ADD COLUMN director    varchar(20)    DEFAULT NULL COMMENT '部门总监',
    ADD COLUMN manager     varchar(20)    DEFAULT NULL COMMENT '部门经理',
    ADD COLUMN assistant   varchar(20)    DEFAULT NULL COMMENT '部门助理';