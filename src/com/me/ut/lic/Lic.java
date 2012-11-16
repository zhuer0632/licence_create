package com.me.ut.lic;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.me.ut.WebPath;


/**
 * 负责对license中的明文进行加密和解密
 * 
 * @author ZHU
 * 
 */
public class Lic
{

    // 1 有效期的截止时间 [格式：yyyy-MM-dd]
    // 2 是否是正式版 [true or false]
    // 3 目标机器上的硬件信息 [格式如下]
    // HDID=2083998894-8787206-2282422314-3760124042
    // CPUID=BFEBFBFF0001067A-0000000000000000
    // MACID=00-E0-4C-08-6B-66
    // 4 公司公钥 写死在代码中的一个字符串
    // 5 客户的标示符 miaofang_xizhimen_001

    /**
     * 生成加密字符出，注入输入参数的string的顺序 <br>
     * 1 有效期的截止时间 [格式：yyyy-MM-dd] <br>
     * 2 是否是正式版 [true or false] <br>
     * 3 HDID=2083998894-8787206-2282422314-3760124042 <br>
     * 4 CPUID=BFEBFBFF0001067A-0000000000000000 <br>
     * 5 MACID=00-E0-4C-08-6B-66 <br>
     * 6 公司公钥 写死在代码中的一个字符串 "comdev.cn" <br>
     * 6 客户的标示符比如： miaofang_xizhimen_001
     * 
     */
    public static String encode(List<String> args)
    {
        String out = "";
        if (args == null || (args != null && args.size() != 7))
        {
            throw new RuntimeException("参数格式不正确");
        }

        // 把明文base64之后，把得到的值按照自己的算法处理[所有的E变成A]，所有的[B]变成[C]
        // 解析的时候当然要先逆处理自己的算法，再逆处理base64。

        String in = "";
        for (int i = 0; i < args.size(); i++)
        {
            if (i == args.size() - 1)
            {
                in = in + args.get(i); // [x]作为分隔符
            }
            else
            {
                in = in + args.get(i) + "#"; // [x]作为分隔符
            }

        }
        out = Base64.encode(in);
        out = out.replace("A", "@");
        out = out.replace("B", "^");
        return out;
    }


    /**
     * 把加密后的字符串写入文件
     * 
     * @param encrypt_str
     */
    public static void writeToFile(String encrypt_str)
    {
        String foldpath = WebPath.getUploadRootPath() + "bealic";

        if (!(new File(foldpath)).exists())
        {
            (new File(foldpath)).mkdirs();
        }

        File file = new File(foldpath + File.separator + "bea.lic");
        FileWriter w = null;
        try
        {
            w = new FileWriter(file);
            w.write(encrypt_str);
            w.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {

            if (w != null)
            {
                try
                {
                    w.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 读取加密文件中的全部文本
     */
    public static String readAllStr(String filepath)
    {

        String out = "";
        File f = new File(filepath);
        if (!f.exists())
        {
            throw new RuntimeException("指定的文件不存在" + filepath);
        }
        FileReader read = null;
        try
        {
            read = new FileReader(f);
            BufferedReader br = new BufferedReader(read);
            out = br.readLine();// 读取一行就可以了，因为之前就是一行写入的
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (read != null)
            {
                try
                {
                    read.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return out;

    }


    /**
     * 把加密后的字符串校验并且解密，返回。
     * 
     * @param licenseContent
     * @return
     */
    public static VO_lic decode(String licenseContent)
    {
        VO_lic out = new VO_lic();
        try
        {

            if (licenseContent == null)
            {
                throw new RuntimeException("参数不正确");
            }

            // 先解析出来内容
            licenseContent = licenseContent.replace("@", "A");
            licenseContent = licenseContent.replace("^", "B");

            licenseContent = Base64.decode(licenseContent);

            if (!licenseContent.contains("#"))
            {
                throw new RuntimeException("参数格式不正确");
            }

            String[] arr = licenseContent.split("#");

            if (arr.length != 7)
            {
                throw new RuntimeException("参数格式不正确");
            }

            // 判断日期格式：
            if (notTime(arr[0]))
            {
                throw new RuntimeException("指定的时间格式不正确");
            }

            if (!arr[1].equals("true") && !arr[1].equals("false"))
            {
                throw new RuntimeException("是否是正式版变量不明确");
            }

            // 不是官方发布的
            if (null == arr[5]
                    || (arr[5] != null && !arr[5].equals(VO_lic.code)))
            {
                throw new RuntimeException("license是无效的");
            }

            // 设置到变量中
            out.setEndtime_str(arr[0]);
            out.setOfficial_version(arr[1].equals("true") ? true : false);
            out.setHDID(arr[2]);
            out.setCPUID(arr[3]);
            out.setMACID(arr[4]);
            out.setMycode(arr[5]);
            out.setCustomer_code(arr[6]);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return out;
    }


    /**
     * 
     * 检查输入参数是不是 yyyy-MM-dd
     * 
     * @param string
     * @return
     */
    private static boolean notTime(String inputdate)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date d = formatter.parse(inputdate, new ParsePosition(0));
        }
        catch (Exception e)
        {
            return false;
        }
        return false;
    }


    /**
     * 取得HD，CPU，MAC序列号
     * 
     * @return
     */
    public List<String> getSysCfg()
    {
        List<String> out = new ArrayList<String>();
        String hd = SysInfo.getHDID();
        out.add(hd);

        String cpu = SysInfo.getCPUID();
        out.add(cpu);

        String mac = SysInfo.getMACID();
        out.add(mac);

        return out;
    }


    public static void main(String[] args)
    {

    }

}
