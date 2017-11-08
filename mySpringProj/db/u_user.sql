-- Table: public.u_user

-- DROP TABLE public.u_user;

CREATE TABLE public.u_user
(
    id_user character(32) COLLATE pg_catalog."default" NOT NULL DEFAULT 'sys_guid()'::bpchar,
    user_name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    toa_client_no character varying(32) COLLATE pg_catalog."default",
    register_source character varying(10) COLLATE pg_catalog."default" NOT NULL,
    register_phone character varying(13) COLLATE pg_catalog."default" NOT NULL,
    register_time timestamp(5) without time zone,
    is_id_checked boolean NOT NULL,
    is_work_checked boolean NOT NULL,
    init_basic_status character varying(2) COLLATE pg_catalog."default" NOT NULL,
    init_label_status character varying(2) COLLATE pg_catalog."default" NOT NULL,
    img_head character varying(100) COLLATE pg_catalog."default" NOT NULL,
    last_login_date timestamp(5) without time zone,
    last_login_device character varying(20) COLLATE pg_catalog."default",
    CONSTRAINT u_user_pkey PRIMARY KEY (id_user)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.u_user
    OWNER to postdata;
COMMENT ON TABLE public.u_user
    IS '用户表';

COMMENT ON COLUMN public.u_user.user_name
    IS '用户名';

COMMENT ON COLUMN public.u_user.toa_client_no
    IS '一账通唯一标识';

COMMENT ON COLUMN public.u_user.register_source
    IS '注册来源';

COMMENT ON COLUMN public.u_user.register_phone
    IS '注册手机号';

COMMENT ON COLUMN public.u_user.register_time
    IS '注册时间';

COMMENT ON COLUMN public.u_user.is_id_checked
    IS '是否实名认证';

COMMENT ON COLUMN public.u_user.is_work_checked
    IS '是否公司认证';

COMMENT ON COLUMN public.u_user.init_basic_status
    IS '是否初始设置基本资料';

COMMENT ON COLUMN public.u_user.init_label_status
    IS '是否初始设置个人标签';

COMMENT ON COLUMN public.u_user.img_head
    IS '个人头像';

COMMENT ON COLUMN public.u_user.last_login_date
    IS '最后登陆时间';

COMMENT ON COLUMN public.u_user.last_login_device
    IS '最后登陆设备';