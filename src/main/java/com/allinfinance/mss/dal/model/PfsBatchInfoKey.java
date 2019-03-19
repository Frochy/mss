package com.allinfinance.mss.dal.model;

import java.io.Serializable;

public class PfsBatchInfoKey implements Serializable {
    private String batchDate;

    private String batchType;

    private String batchFileName;

    private static final long serialVersionUID = 1L;

    public String getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(String batchDate) {
        this.batchDate = batchDate;
    }

    public String getBatchType() {
        return batchType;
    }

    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }

    public String getBatchFileName() {
        return batchFileName;
    }

    public void setBatchFileName(String batchFileName) {
        this.batchFileName = batchFileName;
    }
}