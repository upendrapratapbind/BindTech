package com.elearning.bindtech.utils;

import java.io.Serializable;

public class CommonResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int respCd;
    private String respMsg;
    private String data;

    public static int getRespCd() {
        return respCd;
    }

    public void setRespCd(int respCd) {
        CommonResponse.respCd = respCd;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}