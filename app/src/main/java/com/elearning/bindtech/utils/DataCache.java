package com.elearning.bindtech.utils;

import java.io.Serializable;

public class DataCache extends CommonResponse implements Serializable {

    private static DataCache dataCacheDTO;
    private String uriData;

    public static DataCache getInstance() {
        if (dataCacheDTO == null) {
            dataCacheDTO = new DataCache();
        }
        return dataCacheDTO;
    }

    public String getUriData() {
        return uriData;
    }

    public void setUriData(String uriData) {
        this.uriData = uriData;
    }
}
