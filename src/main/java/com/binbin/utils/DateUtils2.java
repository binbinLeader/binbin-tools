package com.binbin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Title: 日期计算类
 */
public class DateUtils2 {
	public static final String ISO_DATE_FORMAT = "dd-MMM-yyyy";
	public static final String ISO_TIME_FORMAT = "HH:mm:ss";
	public static final String ISO_TIME_WITH_MILLISECOND_FORMAT = "HH:mm:ss.SSS";
	public static final String ISO_DATETIME_FORMAT = "dd-MMM-yyyy HH:mm:ss";
	public static final String ISO_DATETIME_WITH_MILLISECOND_FORMAT = "dd-MMM-yyyy HH:mm:ss.SSS";
	public static final String ISO_SHORT_DATE_FORMAT = "dd-MMM-yy";
	
	public static final String AMR_FULL_DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
	public static final String AMR_NOMAL_DATE_FORMAT = "yyyy/MM/dd";
	public static final String AMR_SHORT_DATE_FORMAT = "yyyy/MM";
	public static final String AMR_DATE_WITHOUT_SLASH_FORMAT = "yyyyMMdd";
	public static final String AMR_ARS_DATE_FORMAT = "yyyyMMdd";
    /**
     * 获取当前时间，精确到毫秒
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
        return getNowTime(DateUtils2.AMR_FULL_DATETIME_FORMAT);
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
        return getToday(DateUtils2.AMR_NOMAL_DATE_FORMAT);
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
                sFillDate = s.substring(0, 4) + " 年 " + s.substring(5) + " 月";
                break;
            case 10:
                sFillDate = s.substring(0, 4) + " 年 " + s.substring(5, 7) + " 月 " + s.substring(8) + " 日 ";
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
        int iQuarter = (iMonth + 2) / 3;             //月份所处季度
        String sQuarter = "";
        switch (iQuarter) {
            case 1:
                sQuarter = "第 一 季度";
                break;
            case 2:
                sQuarter = "第 二 季度";
                break;
            case 3:
                sQuarter = "第 三 季度";
                break;
            case 4:
                sQuarter = "第 四 季度";
                break;
        }
        return sDateInfo[0] + " 年 " + sQuarter;
    }

    /**
     * 根据iScal返回相应位置的数组值
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
     * 获取季度信息
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
     * 针对日期对象的日期推移计算，如昨天、上月等
     *
     * @param date
     * @param iYear
     * @param iMonth
     * @param iDate
     * @param sFormat
     * @return
     */
    public static String getRelativeDate(Date date, int iYear, int iMonth, int iDate, String sFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(sFormat);            //定义格式
        GregorianCalendar gc = new GregorianCalendar();            //

        gc.setTime(date);           //设置时间

        gc.add(Calendar.YEAR, iYear);               //算术求和
        gc.add(Calendar.MONTH, iMonth);
        gc.add(Calendar.DATE, iDate);

        return sdf.format(gc.getTime());
    }

    public static String getRelativeDate(String sDate, int iYear, int iMonth, int iDate, String sFormat)
    {
    	//System.out.println("getRelativeDate:"+sDate);
    	if(sDate==null) return null;
        Date date = parseString2Date(sDate, DateUtils2.AMR_NOMAL_DATE_FORMAT );
        return getRelativeDate(date, iYear, iMonth, iDate, sFormat);
    }

    public static String getRelativeDate(Date date, int iYear, int iMonth, int iDate) {
        return getRelativeDate(date, iYear, iMonth, iDate, DateUtils2.AMR_NOMAL_DATE_FORMAT);
    }

    public static String getRelativeDate(String sDate, int iYear, int iMonth, int iDate)
    {
        return getRelativeDate(sDate, iYear, iMonth, iDate, DateUtils2.AMR_NOMAL_DATE_FORMAT);
    }

    public static String getRelativeMonth(Date date, int iYear, int iMonth, String s) {
        return getRelativeDate(date, iYear, iMonth, 0, s);
    }

    public static String getRelativeMonth(String sDate, int iYear, int iMonth, String s)
    {
        return getRelativeDate(sDate, iYear, iMonth, 0, s);
    }

    public static String getRelativeMonth(Date date, int iYear, int iMonth) {
        return getRelativeDate(date, iYear, iMonth, 0, DateUtils2.AMR_SHORT_DATE_FORMAT);
    }

    public static String getRelativeMonth(String sDate, int iYear, int iMonth)
    {
        return getRelativeDate(sDate, iYear, iMonth, 0, DateUtils2.AMR_SHORT_DATE_FORMAT);
    }

    //判断是否为月末
    public static boolean monthEnd(String sEndDate)
    {
    	String sTommorow = getRelativeDate(sEndDate, 0, 0, 1);
    	if(sTommorow == null){ return false;}
        if (sTommorow.substring(8, 10).equals("01"))
            return true;
        else
            return false;

    }
    
   //获取月末
    public static String getMonthEnd(String sDate)
    {
    	String sNextMounth = getRelativeDate(sDate, 0, 1, 0);
    	
    	return getRelativeDate(sNextMounth.substring(0, 8)+"01",0,0,-1);
    }
    
  //获取月初
    public static String getMonthStart(String sDate)
    {
    	return sDate.substring(0, 8)+"01";
    }
    
    public static String getMonthStart()
    {
    	return DateUtils2.getToday("yyyy-MM-dd").substring(0, 8)+"01";
    }

    //将日期从YYYYMMDD转换为YYYY/MM/DD
    public static String formatDate(String sDate) {
        if (sDate.length() == 8)
            sDate = sDate.substring(0, 4) + '/' + sDate.substring(4, 6) + '/' + sDate.substring(6);
        return sDate;
    }
    
    /**
     * 指定格式日期，转换成yyyy/MM/dd
     * @param sDate
     * @param format
     * @return
     */
    public static String formatDate(String sDate,String format) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    	if(!format.equals("")){
    		try {
				Date date = new SimpleDateFormat(format).parse(sDate);
				sDate = sdf.format(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}

        return sDate;
    }

    public static Date parseString2Date(String datestring)
    {
        return parseString2Date(datestring, DateUtils2.AMR_NOMAL_DATE_FORMAT);
    }

    /**
     * 将日期字符按指定格式转换为Date对象
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
        	//System.out.println("日期转化'" + datestring + "'转换异常" + e);
        	return null;
        }
    }
    
    /**
     * 数字格式日期转换成yyyy/MM/dd
     * @param num
     * @return
     * @throws Exception
     */
    public static String dayAddition(int num) throws Exception {
		SimpleDateFormat timeformat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = timeformat.parse("1900/01/01");
		Calendar a = Calendar.getInstance();
		a.setTime(date);
		a.add(Calendar.DATE, (num - 2));
		return timeformat.format(a.getTime());
	}
    
    public static Long  dateTime(String str1,String format){
  	  SimpleDateFormat sdf = new SimpleDateFormat(format);
  	  Date date1 = null;
  	  try {
  		date1 = sdf.parse(str1);
  	  } catch (ParseException e) {
  		e.printStackTrace();
  	  }
  	  Long time = date1.getTime();
        return time;
    }
   
    public static String getWorkByDateStr(String strDate) throws Exception {
		int con = getDateOfWeek(strDate);

		if (con == 1) {
			strDate = getRelativeDate(strDate, 0, 0, 1);
		} else if (con == 7) {
			strDate = getRelativeDate(strDate, 0, 0, 2);
		}
		return strDate;
	}

	public static String getYesterday() throws ParseException {
		return getRelativeDate(getToday(), 0, 0, -1);
	}

    public static String getTheDayBeforeYesterday() throws ParseException {
        return getRelativeDate(getToday(), 0, 0, -2);
    }

	public static String getRelativeDate1(String sDate, int iYear, int iMonth, int iDate, String sFormat)
			throws ParseException {
		if (sDate == null)
			return null;
		Date date = parseString2Date(sDate, sFormat);
		return getRelativeDate(date, iYear, iMonth, iDate, sFormat);
	}

	public static boolean simplyValidate(String dateString, String splash) {
		if (dateString == null)
			return false;
		String dateSplash = "";
		if (splash != null)
			if (splash.length() > 1)
				dateSplash = "\\" + splash.substring(0, 1);
			else if (splash.length() == 1)
				dateSplash = "\\" + splash;

		String ragExp = "^\\d{4}" + dateSplash + "\\d{2}" + dateSplash + "\\d{2}$";
		return dateString.matches(ragExp);
	}

	public static String getMonthEnd(String date, String format) throws ParseException {
		if (date == null)
			return date;
		String sDate = date.substring(0, 7) + "/01";
		return getRelativeDate(sDate, 0, 1, -1, format);
	}

	public static int getDateOfWeek(String dateString, String format) throws ParseException {
		Date date = parseString2Date(dateString);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		int iWeekFiled = gc.get(7);
		return iWeekFiled;
	}

	public static int getDateOfWeek(String date) throws ParseException {
		return getDateOfWeek(date, "yyyy/MM/dd");
	}

	public static int compareDate(String dateA, String dateB, String format) {
		if ((dateA == null) || (dateA.length() == 0) || (dateB == null) || (dateB.length() == 0))
			return -1;

		double diffDays = 0D;
		Date dateOne = null;
		Date dateTwo = null;
		try {
			dateOne = parseString2Date(dateA, format);
			dateTwo = parseString2Date(dateB, format);
			double diffMs = dateOne.getTime() - dateTwo.getTime();
			diffDays = diffMs / 86400000.0D;
		} catch (Exception pe) {
			diffDays = -1.0D;
		}
		return new Double(diffDays).intValue();
	}

	public static int compareDate(String dateA, String dateB) {
		return compareDate(dateA, dateB, "yyyy/MM/dd");
	}

	public static int differentDays(Date date1, Date date2) {
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
		return days;
	}

	public static int differentDays(String str1, String str2, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date1 = sdf.parse(str1);
		Date date2 = sdf.parse(str2);
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
		return days;
	}

	public static String dateConversion(String str1, String format1, String format2) {
		String str2 = "";
		SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
		SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
		Date date1;
		try {
			if (!str1.equals("")) {
				date1 = sdf1.parse(str1);
				str2 = sdf2.format(date1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str2;
	}

	public static String dateChangeStr(Date date1, String format1) {
		String str1 = "";
		SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
		str1 = sdf1.format(date1);
		return str1;
	}
	
	public static int differentDays(String date1,String date2){
    	if(date1==null||date1==null) return 0;
        Date Date1 = parseString2Date(date1, DateUtils2.AMR_NOMAL_DATE_FORMAT );
        Date Date2 = parseString2Date(date2, DateUtils2.AMR_NOMAL_DATE_FORMAT );
		return differentDays(Date1,Date2);
    }
}
