package com.me.ut;


import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;


public class DatetimeUT
{

    public static void main(String[] args)
    {
        Date d=getDate("1988-8-8");
        System.out.println(d);
    }


    /**
     * 返回日期的str，格式yyyy-MM-dd
     * 
     * @param date
     * @return
     */
    public static String getDateStr(Date date)
    {
        String s = DateFormatUtils.format(date, "yyyy-MM-dd", Locale
                .getDefault());
        return s;
    }


    /**
     * 指定的时间加上 月
     * 
     * @param date
     * @return
     */
    public static Date addMonth(Date date, int amount)
    {
        Date d = new Date();
        d = DateUtils.addMonths(date, amount);
        return d;
    }


    /**
     * 输入参数必须是yyyy-MM-dd
     * 
     * @param endtimeStr
     * @return
     */
    public static Date getDate(String str)
    {
        Date d = new Date();
        try
        {
            d = DateUtils.parseDate(str, "yyyy-MM-dd");
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return d;
    }
}
