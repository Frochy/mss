package com.allinfinance.mss.constant;

public enum PfsNfRespEnum {
    STATUS_SUCC("S", "交易成功"),
    STATUS_FAIL("F", "交易失败"),
    STATUS_PROC("P", "交易处理中"),

    //系统类S
    STATUS_SSSS("SSSS", "交易成功"),
    STATUS_PPPP("PPPP", "交易处理中"),
    STATUS_FFFF("FFFF", "交易失败"),
    STATUS_S001("S001", "系统处理异常"),
    STATUS_S002("S002", "未知的服务码"),
    STATUS_S003("S003", "非法的请求字段"),
    STATUS_S014("S014", "无效发起者"),
    STATUS_S006("S006", "报文验证错误"),
    STATUS_M011("M011","验密失败,加密机报错"),

    //业务类B
    STATUS_B015("B015", "客户信息不存在"),
    STATUS_B016("B016", "账户信息验证失败"),
    STATUS_B145("B145","手机号不正确"),
    STATUS_B227("B227","不符合填写的格式要求"),
    STATUS_B068("B068","查询不到对应账单信息"),
    STATUS_B125("B125","当天交易参考号不能重复"),
    STATUS_D004("D004","证件号码和证件类型所对应的客户不存在"),
    STATUS_L078("L078","上送金额超过当前额度，不能进行申请"),
    STATUS_B020("B020","卡号或证件无效"),
    STATUS_B137("B137","绑定关系不存在"),
    STATUS_L005("L005","找不到原消费交易");








    public static PfsNfRespEnum valueByCode(String code) {
        for (PfsNfRespEnum enu : PfsNfRespEnum.values()) {
            if (enu.getCode().equals(code)) {
                return enu;
            }
        }
        return null;
    }

    private String code;
    private String desc;

    PfsNfRespEnum(String code, String desc) {
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
