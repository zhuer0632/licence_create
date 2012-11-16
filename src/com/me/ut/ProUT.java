package com.me.ut;


import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;


public class ProUT
{

    private static final String filepath = WebPath.getUploadRootPath()+"sysinfo"+File.separatorChar
            + "SysInfo.properties";
    
    public static String get(String key)
    {
        //config一定要new在这里，因为放在static块中的话，文件内容会有缓存的效果。
        Configuration config = null;
        try
        {
            config = new PropertiesConfiguration(filepath);
        }
        catch (ConfigurationException e)
        {
            e.printStackTrace();
        }
        
        String out = "";
        out = config.getString(key);
        return out;
    }


    public static void main(String[] args)
    {
        String s = ProUT.get("CPUID");
        System.out.println(s);
    }

}
