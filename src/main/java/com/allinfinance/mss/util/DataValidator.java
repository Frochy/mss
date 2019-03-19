package com.allinfinance.mss.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class DataValidator {

    public static final Logger logger = LoggerFactory.getLogger(DataValidator.class);

    public static final boolean isDateBetween(Date aDate, String minDateStr, String maxDateStr) {
        return DateUtil.isDateBetween(aDate, minDateStr, maxDateStr);
    }

    public static final boolean isStrLenEqual(String str, int l) {
        return (StringUtils.isNotBlank(str) && (str.trim().length() == l));
    }

    public static final boolean isStrLenLess(String str, int l) {
        return (StringUtils.isNotBlank(str) && (str.trim().length() < l));
    }

    public static final boolean isStrLenLessEqual(String str, int l) {
        return (StringUtils.isNotBlank(str) && (str.trim().length() <= l));
    }

    public static final boolean isNumber(String str) {
        return NumberUtils.isNumber(str);
    }

    public static final boolean isDigits(String str) {
        return NumberUtils.isDigits(str);
    }

    public static final boolean isNumberNotNullOrZero(Number number) {
        return (number != null && number.doubleValue() != 0);
    }

    public static final boolean isShortDateStr(String dateStr) {
        if (StringUtils.isBlank(dateStr))
            return false;

        String dateEl = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]"
                + "|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1]"
                + "[0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|"
                + "[3579][26])00))-02-29)";

        Pattern pattern = Pattern.compile(dateEl);
        return pattern.matcher(dateStr).matches();
    }

    public static final boolean isLongDateStr(String aDateStr) {
        try {
            DateUtil.parseLongDateString(aDateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static final boolean isMailDateStr(String aDateStr) {
        try {
            DateUtil.parseMailDateString(aDateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static final boolean isMailDateDtPartStr(String aDateStr) {
        if (StringUtils.isBlank(aDateStr))
            return false;

        String dateEl = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]"
                + "|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1]"
                + "[0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|"
                + "[3579][26])00))0229)";

        Pattern pattern = Pattern.compile(dateEl);
        return pattern.matcher(aDateStr).matches();
    }

    public static final boolean isShortMailDateStr(String dateStr) {
        if (StringUtils.isBlank(dateStr))
            return false;

        String dateEl = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]"
                + "|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1]"
                + "[0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|"
                + "[3579][26])00))0229)";

        Pattern pattern = Pattern.compile(dateEl);
        return pattern.matcher(dateStr).matches();
    }

    public static final boolean isDateStrMatched(String aDateStr, String formatter) {
        try {
            DateUtil.parser(aDateStr, formatter);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static final boolean isArrayNotEmpty(Object[] object) {
        return !ArrayUtils.isEmpty(object);
    }

    public static final boolean isCertId(String certId) {
        return RegexHelper.isMatch(certId, "\\d{15}|\\d{17}[\\dXx]");
    }

    public static final boolean is18CertId(String certId) {
        return RegexHelper.isMatch(certId, "\\d{17}[\\dXx]");
    }

    public static final boolean isIPAddress(String ip) {
        return RegexHelper.isMatch(ip, "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
    }

    public static final boolean isPasswordFormat(String password) {
        return RegexHelper.isMatch(password, "^(.{6,12})$")
                && RegexHelper.isMatch(password, "^([\\x00-\\xff]+)");
    }

    public static final boolean isEmail(String email) {
        return RegexHelper.isMatch(email,
                "^([a-zA-Z0-9_\\.\\-]+)(@{1})([a-zA-Z0-9_\\.\\-]+)(\\.[a-zA-Z0-9]{1,3})$");
    }

    public static final boolean isPhoneNum(String phoneNum) {
        return RegexHelper
                .isMatch(phoneNum,
                        "^((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?$");
    }

    public static final boolean isCellPhoneNum(String cellPhoneNum) {
        return RegexHelper.isMatch(cellPhoneNum, "^((\\(\\d{2,3}\\))|(\\d{3}\\-))?1\\d{10}$");
    }

    public static final boolean isPostalcode(String postalcode) {
        return RegexHelper.isMatch(postalcode, "^[1-9]\\d{5}$");
    }

    public static final boolean isCardNo(String cardNo) {
        return RegexHelper.isMatch(cardNo, "(^[0-9]{8,30}$)");
    }

    public static final boolean isNumAndLetter(String message) {
        return RegexHelper.isMatch(message, "^[A-Za-z0-9]+$");
    }

    public static final boolean isNumOrLetter(String message) {
        return RegexHelper.isMatch(message, "^([A-Za-z]||[0-9])+$");
    }

    public static final boolean isLetter(String message) {
        return RegexHelper.isMatch(message, "^[A-Za-z]+$");
    }


    public static final boolean isFundCode(String fundCode) {
        return RegexHelper.isMatch(fundCode, "[0-9]{6}");
    }

    public static final boolean isAreaId(String areaId) {
        return RegexHelper.isMatch(areaId, "[0-9]{4}");
    }

    public static final boolean isBankCode(String bankCode) {
        return RegexHelper.isMatch(bankCode, "[0-9]{8}");
    }

    public static final boolean isProvinceId(String provinceId) {
        return RegexHelper.isMatch(provinceId, "[0-9]{4}");
    }

    public static final boolean isCustId(String custId) {
        if (StringUtils.isBlank(custId)) {
            return false;
        }
        if (custId.getBytes().length != 16) {
            return false;
        }
        if (!isDigits(custId)) {
            return false;
        }
        return true;
    }

    public static boolean isDoubleAmtParam(String doubleAmt, int p, int s) {
        if (p - s < 1)
            return false;
        String reg = "^[1-9][0-9]{0," + (p - s - 2) + "}+\\.[0-9]{" + s + "}$";
        String regs = "0{1}+\\.[0-9]{" + s + "}$";
        return RegexHelper.isMatch(doubleAmt, reg) || RegexHelper.isMatch(doubleAmt, regs) ? true : false;

    }

    public static boolean isDoubleAmt(String doubleAmt, int p, int s) {
        if (p - s < 1)
            return false;
        String reg = "^[1-9][0-9]{0," + (p - s - 2) + "}+\\.[0-9]{" + s + "}$";
        String regs = "0{1}+\\.[0-9]{" + s + "}$";
        if (RegexHelper.isMatch(doubleAmt, reg) || RegexHelper.isMatch(doubleAmt, regs)) {
            if (Double.valueOf(doubleAmt) > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDoubleAmtOrZero(String doubleAmt, int p, int s) {
        if (p - s < 1)
            return false;
        String reg = "^[1-9][0-9]{0," + (p - s - 2) + "}+\\.[0-9]{" + s + "}$";
        String regs = "0{1}+\\.[0-9]{" + s + "}$";
        if (RegexHelper.isMatch(doubleAmt, reg) || RegexHelper.isMatch(doubleAmt, regs)) {
            if (Double.valueOf(doubleAmt) >= 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean lessEqualsMaxLen(String str, int maxLen) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        int len = str.length();
        int bytes = len;
        for (int i = 0; i < len; i++) {
            if (str.charAt(i) < 0 || str.charAt(i) > 255) {
                bytes++;
            }
        }
        return bytes <= maxLen;
    }

    public static boolean equalsMaxLen(String str, int maxLen) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        int len = str.length();
        int bytes = len;
        for (int i = 0; i < len; i++) {
            if (str.charAt(i) < 0 || str.charAt(i) > 255) {
                bytes++;
            }
        }
        return bytes == maxLen;
    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyyMMdd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            logger.error("", e);
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    public static boolean isDoubleRatio(String doubleRatio, int p, int s) {
        if (p - s < 1)
            return false;
        String regs = "^0{1}+\\.[0-9]{" + s + "}$";
        if (RegexHelper.isMatch(doubleRatio, regs)) {
            if (Double.valueOf(doubleRatio) > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUrl(String url) {
        String regEx = "^http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?$";
        return RegexHelper.isMatch(url, regEx);
    }

    public static boolean isIp(String ip) {
        String regEx = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        return RegexHelper.isMatch(ip, regEx);
    }
}
