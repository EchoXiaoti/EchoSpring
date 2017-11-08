package com.echo.commons.utils;

import org.apache.log4j.NDC;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * MDCData封装
 *
 * @author Echo-cxt
 * @create 2017-11-08 4:40 PM
 **/
public class MDCData implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String KEY_UID = "U";
    public static final String KEY_REQUEST_ID = "T";
    public Map datas;

    protected MDCData(String str){
        this.datas = stringToMap(str);
    }

    protected MDCData(Map datas){
        this.datas = datas;
        refresh();
    }

    protected Map getDatas(){
        return this.datas;
    }

    public void set(String key, String value){
        if (this.datas == null){
            this.datas = new HashMap(4);
        }
        this.datas.put(key, value);
        refresh();
    }

    private void refresh(){
        NDC.clear();
        NDC.push(toString());
    }

    public String get(String key){
        if(key == null)
            return null;
        if(this.datas == null)
            return null;
        Object v = this.datas.get(key);
        return v == null ? null : v.toString();
    }

    public String toString(){
        return toString(this.datas);
    }

    private String toString(Map datas){
        if((datas == null) || (datas.size() == 0))
            return null;
        StringBuilder buf = new StringBuilder(24);
        buf.append("<");
        mapToString(buf, datas);
        buf.append(">");
        return buf.toString();
    }

    private void mapToString(StringBuilder buf, Map datas){
        if(datas == null)
            return;
        Iterator i = datas.entrySet().iterator();
        if(buf.length() > 2)
            buf.append(",");
        boolean hasNext = i.hasNext();
        while(hasNext){
            Map.Entry e = (Map.Entry)i.next();
            buf.append(e.getKey()).append('=').append(e.getValue());
            hasNext = i.hasNext();
            if(hasNext)
                buf.append(",");
        }
    }
     private Map stringToMap(String str){
        if((str == null) || ((str = str.trim()).length() < 3)){
            return null;
        }
        str = str.substring(1, str.length() -1);
        Map datas = new HashMap();
        String[] temp = str.split(",");
        for(int i = 0; i < temp.length; i++){
            String[] t2 = temp[i].split("=");
            if((t2 != null) && (t2.length == 2)){
                datas.put(t2[0], t2[1]);
            }
        }
        return datas;
     }

     public String getRequestId(){
         return get("T");
     }

     public void setRequestId(String requestId){
         set("T", requestId);
     }

     public int size(){
         if(this.datas == null)
             return 0;
         return this.datas.size();
     }

     public String getUid(){
         return get("U");
     }

     public void setUid(String uid){
         set("U", uid);
     }
}
