package com.amarsoft.server.util;

import java.util.*;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

/**
 * Title: ���ڼ�����
 */
public class DateUtils {
	public static final String ISO_DATE_FORMAT = "dd-MMM-yyyy";
	public static final String ISO_TIME_FORMAT = "HH:mm:ss";
	public static final String ISO_TIME_WITH_MILLISECOND_FORMAT = "HH:mm:ss.SSS";
	public static final String ISO_DATETIME_FORMAT = "dd-MMM-yyyy HH:mm:ss";
	public static final String ISO_DATETIME_WITH_MILLISECOND_FORMAT = "dd-MMM-yyyy HH:mm:ss.SSS";
	public static final String ISO_SHORT_DATE_FORMAT = "dd-MMM-yy";
	
	public static final String AMR_FULL_DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS";
	public static final String AMR_NOMAL_DATE_FORMAT = "yyyy/MM/dd";
	public static final String AMR_SHORT_DATE_FORMAT = "yyyy/MM";
	public static final String AMR_DATE_WITHOUT_SLASH_FORMAT = "yyyyMMdd";
	public static final String AMR_ARS_DATE_FORMAT = "yyyyMMdd";
	static Logger logger = Logger.getLogger(DateUtils.class.getName());  
    /**
     * ��ȡ��ǰʱ�䣬��ȷ������
     *
     * @return
     */
	public static String getNowTime(String sFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return sdf.format(c.getTime());
	}

	public static String getNowTime() {
        return getNowTime(DateUtils.AMR_FULL_DATETIME_FORMAT);
    }

    public static String getToday(String sFormat) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        String s1 = sdf.format(gc.getTime());
        return s1;
    }

    public static String getToday() {
        return getToday(DateUtils.AMR_NOMAL_DATE_FORMAT);
    }

    /**
     * renturn the report sheet fillin date;
     *
     * @param s
     * @return
     */
    public static String getFillInDate(String s) {
        String sFillDate = "";
        switch (s.length()) {
            case 7:
                sFillDate = s.substring(0, 4) + " �� " + s.substring(5) + " ��";
                break;
            case 10:
                sFillDate = s.substring(0, 4) + " �� " + s.substring(5, 7) + " �� " + s.substring(8) + " �� ";
                break;
            default:
        }
        return sFillDate;
    }

    /**
     * renturn the report sheet fillin quarter;
     *
     * @param sMonth
     * @return
     */
    public static String getQuaterName(String sMonth) {
        String[] sDateInfo = sMonth.split("/");
        if (sDateInfo.length == 1) return sDateInfo[0] + "/03";
        int iMonth = Integer.parseInt(sDateInfo[1]);
        int iQuarter = (iMonth + 2) / 3;             //�·���������
        String sQuarter = "";
        switch (iQuarter) {
            case 1:
                sQuarter = "�� һ ����";
                break;
            case 2:
                sQuarter = "�� �� ����";
                break;
            case 3:
                sQuarter = "�� �� ����";
                break;
            case 4:
                sQuarter = "�� �� ����";
                break;
        }
        return sDateInfo[0] + " �� " + sQuarter;
    }

    /**
     * ����iScal������Ӧλ�õ�����ֵ
     *
     * @param sDate
     * @param iScal
     * @return
     */
    public static String getFillinParam(String sDate, int iScal) {
        if (iScal <= 0) return sDate;
        String[] sDateInfo = sDate.split("/");
        if (sDateInfo.length >= iScal) {
            return sDateInfo[iScal - 1];
        }
        return sDate;
    }

    /**
     * ��ȡ������Ϣ
     *
     * @param sMonth
     * @param iPointer
     * @return
     */
    public static String getQuaterInfo(String sMonth, int iPointer)
    {
        String[] sDateInfo = sMonth.split("/");
        if (sDateInfo.length == 1) return sDateInfo[0] + "/03";
        int iMonth = Integer.parseInt(sDateInfo[1]);
        String sMonthRange = "";
        String sQuaterValue = "";
        if (iMonth > 0 && iMonth <= 3) {
            sMonthRange = sDateInfo[0] + "/03";
        } else if (iMonth > 3 && iMonth <= 6) {
            sMonthRange = sDateInfo[0] + "/06";
        } else if (iMonth > 6 && iMonth <= 9) {
            sMonthRange = sDateInfo[0] + "/09";
        } else if (iMonth > 9 && iMonth <= 12) {
            sMonthRange = sDateInfo[0] + "/12";
        }
        int iStep = iPointer * 3;
        try
		{
			sQuaterValue = getRelativeMonth(sMonthRange + "/01", 0, iStep);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

        return sQuaterValue;
    }

    public static String getYearInfo(String sMonth, int iPointer)
    {
        String sMonthRange = "", sYearValue = "";
        String[] sDateInfo = sMonth.split("/");
        if (sDateInfo.length == 1) return sDateInfo[0] + "/03";
        int iMonth = Integer.parseInt(sDateInfo[1]);
        if (iMonth > 0 && iMonth <= 6) {
            sMonthRange = sDateInfo[0] + "/06";
        } else if (iMonth > 6 && iMonth <= 12) {
            sMonthRange = sDateInfo[0] + "/12";
        }
        int iStep = iPointer * 6;
        try
		{
			sYearValue = getRelativeMonth(sMonthRange + "/01", 0, iStep);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
        return sYearValue;
    }

    /**
     * ������ڶ�����������Ƽ��㣬�����졢���µ�
     *
     * @param date
     * @param iYear
     * @param iMonth
     * @param iDate
     * @param sFormat
     * @return
     */
    public static String getRelativeDate(java.util.Date date, int iYear, int iMonth, int iDate, String sFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(sFormat);            //�����ʽ
        GregorianCalendar gc = new GregorianCalendar();            //

        gc.setTime(date);           //����ʱ��

        gc.add(Calendar.YEAR, iYear);               //�������
        gc.add(Calendar.MONTH, iMonth);
        gc.add(Calendar.DATE, iDate);

        return sdf.format(gc.getTime());
    }

    public static String getRelativeDate(String sDate, int iYear, int iMonth, int iDate, String sFormat)
    {
    	//System.out.println("getRelativeDate:"+sDate);
    	if(sDate==null) return null;
        Date date = parseString2Date(sDate, DateUtils.AMR_NOMAL_DATE_FORMAT );
        return getRelativeDate(date, iYear, iMonth, iDate, sFormat);
    }

    public static String getRelativeDate(java.util.Date date, int iYear, int iMonth, int iDate) {
        return getRelativeDate(date, iYear, iMonth, iDate, DateUtils.AMR_NOMAL_DATE_FORMAT);
    }

    public static String getRelativeDate(String sDate, int iYear, int iMonth, int iDate)
    {
        return getRelativeDate(sDate, iYear, iMonth, iDate, DateUtils.AMR_NOMAL_DATE_FORMAT);
    }

    public static String getRelativeMonth(java.util.Date date, int iYear, int iMonth, String s) {
        return getRelativeDate(date, iYear, iMonth, 0, s);
    }

    public static String getRelativeMonth(String sDate, int iYear, int iMonth, String s)
    {
        return getRelativeDate(sDate, iYear, iMonth, 0, s);
    }

    public static String getRelativeMonth(java.util.Date date, int iYear, int iMonth) {
        return getRelativeDate(date, iYear, iMonth, 0, DateUtils.AMR_SHORT_DATE_FORMAT);
    }

    public static String getRelativeMonth(String sDate, int iYear, int iMonth)
    {
        return getRelativeDate(sDate, iYear, iMonth, 0, DateUtils.AMR_SHORT_DATE_FORMAT);
    }

    //�ж��Ƿ�Ϊ��ĩ
    public static boolean monthEnd(String sEndDate)
    {
    	String sTommorow = getRelativeDate(sEndDate, 0, 0, 1);
    	if(sTommorow == null){ return false;}
        if (sTommorow.substring(8, 10).equals("01"))
            return true;
        else
            return false;

    }

    //�����ڴ�YYYYMMDDת��ΪYYYY/MM/DD
    public static String formatDate(String sDate) {
        if (sDate.length() == 8)
            sDate = sDate.substring(0, 4) + '/' + sDate.substring(4, 6) + '/' + sDate.substring(6);
        return sDate;
    }

    public static Date parseString2Date(String datestring)
    {
        return parseString2Date(datestring, DateUtils.AMR_NOMAL_DATE_FORMAT);
    }

    /**
     * �������ַ���ָ����ʽת��ΪDate����
     *
     * @param datestring
     * @param format
     * @return
     */
    public static Date parseString2Date(String datestring, String format)
    {
    	if (datestring==null) return null;
        try {
            String sDate = "";
            if (datestring.length() == 7) {
                sDate = datestring + "/01";
            } else {
                sDate = datestring;
            }
            Date date = new SimpleDateFormat(format).parse(sDate);
            return date;
        } catch (Exception e) {
        	//System.out.println("����ת��'" + datestring + "'ת���쳣" + e);
        	logger.info("����ת��'" + datestring + "'ת���쳣" + e);
        	return null;
        }
    }
}
