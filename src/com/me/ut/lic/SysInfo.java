package com.me.ut.lic;


import java.io.IOException;
import java.util.Properties;

import org.sf.feeling.swt.win32.extension.io.FileSystem;
import org.sf.feeling.swt.win32.extension.system.SystemInfo;




public class SysInfo
{
    private static final SystemInfo sys = SystemInfo.getInstance();

    //需要把 swt-extension-win32.dll，swt-win32.dll拷贝到jdk/bin下
    public static void main(String[] args) throws IOException
    {
        getSysInfo();
        System.out.println("=========================");
        System.out.println("license.propertie完成");
        System.out.println("=========================");
    }
    

    private static Properties getSysInfo()
    {
        Properties properties = new Properties();
        System.out.println(getCPUID());
        System.out.println(getMACID());
        System.out.println(getHDID());
        return properties;
    }


    public static String getCPUID()
    {
        String cpuid = sys.getCPUID();
        return cpuid;
    }


    public static String getMACID()
    {
        String[] arrs = sys.getMACAddresses(); 
        String MacID = "";
        for (int i = 0; i < arrs.length; i++)
        {
        }
        if (arrs != null && arrs.length > 0)
        {
            MacID = arrs[0];
        }
        return MacID;
    }


    @SuppressWarnings("static-access")
    public static String getHDID()
    {
        FileSystem fileSystem = new FileSystem();
        String[] dris = fileSystem.getLogicalDrives();
        String serialNum = "";
        for (int i = 0; i < dris.length; i++)
        {
            if (fileSystem.getDriveType(dris[i]) == FileSystem.DRIVE_TYPE_FIXED)
            {
                serialNum = serialNum
                        + fileSystem.getVolumeSerialNumber(dris[i]) + "-"; 
            }
        }
        if (serialNum.endsWith("-"))
        {
            serialNum = serialNum.substring(0, serialNum.length() - 1);
        }
        return serialNum;
    }
}
