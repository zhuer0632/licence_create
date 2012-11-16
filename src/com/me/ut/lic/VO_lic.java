package com.me.ut.lic;

import com.me.ut.StringUT;


/**
 * 
 * 组成license的明文内容的所有字段
 * 
 * @author ZHU
 *
 */
public class VO_lic
{
    
    public static final String code="comdev.cn";
    private String endtime_str;//格式： yyyy-MM-dd
    private boolean official_version ;//是否是正式版
    
    private String HDID;//硬盘序列号
    private String CPUID;//CPU序列号
    private String MACID;//网卡地址
    private String mycode=code; //相当于一个密码，
    private String customer_code;//用于识别客户的一个字符串
    
    
    /**
     * index=1
     * @param endtimeStr
     */
    public String getEndtime_str()
    {
        if(StringUT.isEmpty(endtime_str))
        {
                    return  "2000-01-01";
        }
        return endtime_str;
    }
    /**
     * index=1
     * @param endtimeStr
     */
    public void setEndtime_str(String endtimeStr)
    {
        endtime_str = endtimeStr;
    }
    
    /**
     * index=2
     * @param endtimeStr
     */
    public boolean isOfficial_version()
    {
        return official_version;
    }
    /**
     * index=2
     * @param endtimeStr
     */
    public void setOfficial_version(boolean officialVersion)
    {
        official_version = officialVersion;
    }
    
    /**
     * index=3
     * @param endtimeStr
     */
    public String getHDID()
    {
        if(StringUT.isEmpty(HDID))
        {
                return "硬盘序列号";
        }
        
        return HDID;
    }
    /**
     * index=3
     * @param endtimeStr
     */
    public void setHDID(String hDID)
    {
        HDID = hDID;
    }
    
    /**
     * index=4
     * @param endtimeStr
     */
    public String getCPUID()
    {
        if(StringUT.isEmpty(CPUID))
        {
                return "CPUID序列号";
        }
        
        return CPUID;
    }
    
    /**
     * index=4
     * @param endtimeStr
     */
    public void setCPUID(String cPUID)
    {
        CPUID = cPUID;
    }
    
    /**
     * index=5
     */
    public String getMACID()
    {
        if(StringUT.isEmpty(MACID))
        {
                return "网卡序列号";
        }
        return MACID;
    }
    /**
     * index=5
     * @param mACID
     */
    public void setMACID(String mACID)
    {
        MACID = mACID;
    }
    
    /**
     * index=6
     */
    public String getMycode()
    {
        return mycode;
    }
    /**
     * index=6
     * @param mycode
     */
    public void setMycode(String mycode)
    {
        this.mycode = mycode;
    }
    /**
     * index=7
     * @param endtimeStr
     */
    public String getCustomer_code()
    {
        if(StringUT.isEmpty(customer_code))
        {
                    return  "分店标示";
        }
        return customer_code;
    }
    /**
     * index=7
     * @param endtimeStr
     */
    public void setCustomer_code(String customerCode)
    {
        customer_code = customerCode;
    }
    @Override
    public String toString()
    {
        return "VO_lic [CPUID=" + CPUID + ", HDID=" + HDID + ", MACID=" + MACID + ", customer_code=" + customer_code + ", endtime_str=" + endtime_str
                + ", mycode=" + mycode + ", official_version=" + official_version + "]";
    }
    
    
    
    
    
}
