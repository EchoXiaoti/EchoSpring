package com.echo.commons.utils;

import com.echo.commons.base.SessionDTO;
import org.apache.log4j.NDC;

import java.util.HashMap;
import java.util.Map;

import static org.apache.log4j.NDC.clear;

/**
 * @author Echo-cxt
 * @create 2017-11-08 4:31 PM
 **/
public class MDCUtil {

    public MDCUtil(){}

    public static Map peekMap(){
        return peek().getDatas();
    }

    public static MDCData getDatas(){
        return peek();
    }

    public static MDCData peek(){
        return new MDCData(NDC.peek());
    }

    public static SessionDTO peekSessionDTO(){
        return peekSessionDTO(null);
    }

    public static SessionDTO peekSessionDTO(SessionDTO s){
        if(s == null){
            s = new SessionDTO();
        }
        MDCData d = peek();
        if(d != null){
            if((s.getTxnId() == null || (s.getTxnId().length() == 0))){
                s.setTxnId(d.getRequestId());
            }
            if((s.getUserId() == null || (s.getUserId().length() == 0))){
                s.setUserId(d.getUid());
            }
        }
        return s;
    }

    public static void setBySessionDTO(SessionDTO s){
        if((s != null) && (s.getTxnId() != null)){
            set(s.getTxnId(), s.getUserId());
        }else{
            MDCData data = peek();
            if(data.getRequestId() == null){
                data.setRequestId(generateRequestId());
            }
        }
    }

    public static void set(Map mapDatas){
        if(mapDatas != null)
            new MDCData(mapDatas);
        else
            clear();
    }

    public static void set(String requestId){
        set(requestId, null);
    }

    public static void set(String requestId, String uid){
        Map mapDatas = new HashMap();
        if(requestId == null)
            requestId = generateRequestId();
        mapDatas.put("T", requestId);
        if(uid != null)
            mapDatas.put("U", uid);
        new MDCData(mapDatas);
    }

    public static String generateRequestId(){
        return IdMaker.generate();
    }

    public static void clear(){}

}
