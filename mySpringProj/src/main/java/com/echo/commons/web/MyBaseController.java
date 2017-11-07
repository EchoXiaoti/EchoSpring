package com.echo.commons.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.echo.commons.utils.Constants;
import com.echo.commons.view.RetCode;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 系统通用Controller基类
 */
public class MyBaseController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public static final String FLAG = "flag";
    public static final String MSG = "msg";
    public static final String DATA = "data";

    private static Validator OVAL_VALIDATOR = new Validator();

    protected <T> T respSuccess(T respObj){
        return respFor(respObj, RetCode.SUCCESS, RetCode.GetMsg(RetCode.SUCCESS));
    }

    protected <T> T respSuccess(T respObj, String flag, String msg){
        return respFor(respObj, flag, msg);
    }

    protected <T> T respFail(T respObj){
        return respFor(respObj, RetCode.FAIL, RetCode.GetMsg(RetCode.FAIL));
    }

    protected <T> T respFail(T respObj, String msg){
        return respFor(respObj, RetCode.FAIL, msg);
    }

    protected <T> T respValidateFail(T resp, String validateMsg){
        return respFor(resp, RetCode.FORM_VALIDATE_FAIL, validateMsg);
    }

    /**
     * Controller通用设置接口返回码、返回Msg
     * @param respObj
     * @param flag
     * @param msg
     * @param <T>
     * @return
     */
    protected <T> T respFor(T respObj, int flag, String msg){
        try{
            PropertyDescriptor flagPd = new PropertyDescriptor(FLAG, respObj.getClass());
            flagPd.getWriteMethod().invoke(respObj, flag + "");
            if(StringUtils.isNotEmpty(msg)){
                PropertyDescriptor msgPd = new PropertyDescriptor(MSG, respObj.getClass());
                msgPd.getWriteMethod().invoke(respObj, msg);
            }
        }catch (Exception e){
            super.logger.error("Set flag msg Value error.", e);
        }
        return respObj;
    }

    /**
     * Controller通用设置接口返回码、返回Msg
     * @param respObj
     * @param flag
     * @param msg
     * @param <T>
     * @return
     */
    protected <T> T respFor(T respObj, String flag, String msg){
        try{
            PropertyDescriptor flagPd = new PropertyDescriptor(FLAG, respObj.getClass());
            flagPd.getWriteMethod().invoke(respObj, flag + "");
            if(StringUtils.isNotEmpty(msg)){
                PropertyDescriptor msgPd = new PropertyDescriptor(MSG, respObj.getClass());
                msgPd.getWriteMethod().invoke(respObj, msg);
            }
        }catch (Exception e){
            super.logger.error("Set flag msg Value error.", e);
        }
        return respObj;
    }

    /**
     * 返回jsonP
     * @param request
     * @param response
     * @param result
     */
    protected void respJsonp(HttpServletRequest request, HttpServletResponse response, Object result){
        String callback = Constants.Jsonp.getJsonpCallback(request);
        String jsonpRet = JSON.toJSONString(result, new SerializerFeature[]{SerializerFeature.WriteMapNullValue});
        if(StringUtils.isNotBlank(callback)){
            jsonpRet = callback + "(" + jsonpRet + ")";
        }
        super.logger.info("Restful Response is + " + jsonpRet);
        try{
            PrintWriter out = response.getWriter();
            out.write(jsonpRet);
            out.flush();
            out.close();
        }catch (IOException e){
            super.logger.error("Error response jsonp.", e);
        }
    }

    /**
     * 获取浏览器类型
     * @param request
     * @return
     */
    protected String getUA(HttpServletRequest request){
        String ua = request.getHeader("User-Agent");
        return ua;
    }

    /**
     * 参数校验
     * @param req
     * @return
     */
    protected String daValidator(Object req){
        long begin = System.nanoTime();
        String ret = null;
        try{
            List<ConstraintViolation> lst = OVAL_VALIDATOR.validate(req);
            if(CollectionUtils.isNotEmpty(lst)){
                ret = lst.get(0).getMessage();
            }
        }catch (Exception e){
            logger.error("传入参数校验失败, 校验对象：" + req.getClass().getName());
            ret = e.getMessage();
        }
        logger.info("Success validate request: " + req.getClass().getSimpleName() + "cost: " + (System.nanoTime() - begin)/1000/1000 + "ms.");
        return ret;
    }

}
