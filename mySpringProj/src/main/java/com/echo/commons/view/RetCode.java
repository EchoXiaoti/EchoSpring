package com.echo.commons.view;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统统一返回码
 *
 * @author Echo-cxt
 * @create 2017-11-07 3:52 PM
 **/
public class RetCode {
    /** 操作成功 **/
    public static int SUCCESS = 1;
    /** 操作失败 **/
    public static int FAIL = 2;
    /** 系统繁忙 **/
    public static int SYSTEM_BUSY = 8;
    /** 会话超时 **/
    public static int SESSION_TIMEOUT = 9;
    /** 参数校验失败 **/
    public static int FORM_VALIDATE_FAIL = 900504;

    private static Map<Object, String> CODE_MAP = new HashMap<Object, String>();

    static {
        CODE_MAP.put(SUCCESS, "操作成功");
        CODE_MAP.put(FAIL, "操作失败");
        CODE_MAP.put(SYSTEM_BUSY, "系统繁忙");
        CODE_MAP.put(SESSION_TIMEOUT, "会话超时，请重新登陆");
        CODE_MAP.put(FORM_VALIDATE_FAIL, "传入参数校验失败");
    }

    public static String GetMsg(Object code){
        return CODE_MAP.get(code);
    }

}
