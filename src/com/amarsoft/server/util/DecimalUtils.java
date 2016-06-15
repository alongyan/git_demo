package com.amarsoft.server.util;

import java.math.BigDecimal;

public class DecimalUtils {
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 对数Param进行小数点Type前移1～4位，保留Dot位小数
     *
     * @param sParam
     * @param iDot
     * @param iType，1：千，2：万，3：亿
     * @return
     */
    public static String getFormatDecimal(String sParam, int iDot, int iType) {
        double dTemp = 0.0, dParam = 0.0;
        String sReturnValue = sParam;
        if (sParam == null || sParam.trim().length() == 0) return "";
        dParam = Double.parseDouble(sParam);
        switch (iType) {
            case 3:
                dTemp = 100000000;//亿
                break;
            case 2:
                dTemp = 10000;//万
                break;
            case 1:
                dTemp = 1000;//千
                break;
            default:
                dTemp = 1;//元
                break;
        }
        sReturnValue = String.valueOf(divide(dParam, dTemp, iDot));
        return sReturnValue;
    }

    public static String getFormatDecimal(double sParam, int iDot, int iType) {
        return getFormatDecimal(String.valueOf(sParam), iDot, iType);
    }

    public static String getFormatDecimal(String sParam, String iDot, String iType) {
        int iTemp1 = 0, iTemp2 = 0;
        iTemp1 = Integer.parseInt(iDot);
        iTemp2 = Integer.parseInt(iType);
        return getFormatDecimal(sParam, iTemp1, iTemp2);
    }

    /**
     * 提供精确的加法运算。
     *
     * @param dValue1 被加数
     * @param dValue2 加数
     * @return 两个参数的和
     */
    public static double add(double dValue1, double dValue2) {
        BigDecimal bg1 = new BigDecimal(Double.toString(dValue1));
        BigDecimal bg2 = new BigDecimal(Double.toString(dValue2));
        return bg1.add(bg2).doubleValue();
    }

    public static double add(String dValue1, String dValue2) {
        double dTemp1 = 0.0;
        double dTemp2 = 0.0;
        if (dValue1 != null && dValue1.length() != 0) {
            dTemp1 = Double.parseDouble(dValue1);
        }
        if (dValue2 != null && dValue2.length() != 0) {
            dTemp2 = Double.parseDouble(dValue2);
        }
        return add(dTemp1, dTemp2);
    }

    /**
     * 提供精确的减法运算。
     *
     * @param dValue1 被减数
     * @param dValue2 减数
     * @return 两个参数的差
     */
    public static double subtract(double dValue1, double dValue2) {
        BigDecimal bg1 = new BigDecimal(Double.toString(dValue1));
        BigDecimal bg2 = new BigDecimal(Double.toString(dValue2));
        return bg1.subtract(bg2).doubleValue();
    }

    public static double subtract(String dValue1, String dValue2) {
        double dTemp1 = 0.0;
        double dTemp2 = 0.0;
        if (dValue1 != null && dValue1.length() != 0) {
            dTemp1 = Double.parseDouble(dValue1);
        }
        if (dValue2 != null && dValue2.length() != 0) {
            dTemp2 = Double.parseDouble(dValue2);
        }
        return subtract(dTemp1, dTemp2);
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param dValue1 被乘数
     * @param dValue2 乘数
     * @return 两个参数的积
     */
    public static double multiply(double dValue1, double dValue2) {
        BigDecimal bg1 = new BigDecimal(Double.toString(dValue1));
        BigDecimal bg2 = new BigDecimal(Double.toString(dValue2));
        return bg1.multiply(bg2).doubleValue();
    }

    public static double multiply(String dValue1, String dValue2) {
        double dTemp1 = 0.0;
        double dTemp2 = 0.0;
        if (dValue1 != null && dValue1.length() != 0) {
            dTemp1 = Double.parseDouble(dValue1);
        }
        if (dValue2 != null && dValue2.length() != 0) {
            dTemp2 = Double.parseDouble(dValue2);
        }
        return multiply(dTemp1, dTemp2);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param dValue1 被除数
     * @param dValue2 除数
     * @return 两个参数的商
     */
    public static double divide(double dValue1, double dValue2) {
        return divide(dValue1, dValue2, DEF_DIV_SCALE);
    }

    public static double divide(String dValue1, String dValue2, int scale) {
        double dTemp1 = 0.0;
        double dTemp2 = 0.0;
        if (dValue1 != null && dValue1.length() != 0) {
            dTemp1 = Double.parseDouble(dValue1);
        }
        if (dValue2 != null && dValue2.length() != 0) {
            dTemp2 = Double.parseDouble(dValue2);
        } else {
            dTemp2 = 1;
        }
        return divide(dTemp1, dTemp2, scale);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，默认四舍五入保留四位小数
     *
     * @param dValue1 被除数
     * @param dValue2 除数
     * @param scale   表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double divide(String dValue1, String dValue2) {
        return divide(dValue1, dValue2, 4);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param dValue1 被除数
     * @param dValue2 除数
     * @param scale   表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double divide(double dValue1, double dValue2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal bg1 = new BigDecimal(Double.toString(dValue1));
        BigDecimal bg2 = new BigDecimal(Double.toString(dValue2));
        return bg1.divide(bg2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
