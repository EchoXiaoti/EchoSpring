package com.echo.commons.base;

/**
 * sessionDTO类
 *
 * @author Echo-cxt
 * @create 2017-11-08 5:20 PM
 **/
public class SessionDTO {

    private String txnId = null;

    private String userId = null;

    static final long serialVersionUID = 1L;

    public SessionDTO(){}

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SessionDTO{" +
                "txnId='" + txnId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
