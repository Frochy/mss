package com.allinfinance.mss.constant;

public enum PfsNfTxnEnum {
    NF_XXXXX("XXXXX", "交易名"),

    ;

    public static PfsNfTxnEnum valueByCode(String code) {
        for (PfsNfTxnEnum enu : PfsNfTxnEnum.values()) {
            if (enu.getCode().equals(code)) {
                return enu;
            }
        }
        return null;
    }

    private String code;
    private String desc;

    PfsNfTxnEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
