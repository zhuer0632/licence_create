package com.me.biz;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;

import com.me.exception.Errors;
import com.me.exception.JustReturnException;
import com.me.ut.DatetimeUT;
import com.me.ut.ProUT;
import com.me.ut.StringUT;
import com.me.ut.WebPath;
import com.me.ut.lic.Lic;
import com.me.ut.lic.SysInfo;
import com.me.ut.lic.VO_lic;


@Controller
public class LicBiz
{

    public void create(VO_lic vo, Map out)
    {
        if (!vo.isOfficial_version())
        {
            // 检查时间
            String endtimeStr = vo.getEndtime_str();
            Date endtime = DatetimeUT.getDate(endtimeStr);
            if (endtime.before(new Date()))
            {
                Errors.addErrtoErrs(out, "选中的日期不正确");
                throw new JustReturnException();
            }
        }

        if (StringUT.isEmpty(vo) || StringUT.isEmpty(vo.getMycode())
                || !vo.getMycode().equals(VO_lic.code))
        {
            Errors.addErrtoErrs(out, "操作密码不正确");
            throw new JustReturnException();
        }

        if (vo.isOfficial_version())
        {
            // 检查硬件信息是否上传了
            String filepath = StringUT.getUploadFiles() + "sysinfo"
                    + File.separatorChar + "SysInfo.properties";
            if (!(new File(filepath)).exists())
            {
                Errors.addErrtoErrs(out, "没有上传硬件信息[SysInfo.properties]");
                throw new JustReturnException();
            }

            // 检查文件内容
            if (StringUT.isEmpty(ProUT.get("HDID"))
                    || StringUT.isEmpty(ProUT.get("MACID"))
                    || StringUT.isEmpty(ProUT.get("CPUID")))
            {
                Errors.addErrtoErrs(out, "上传的文件信息格式不正确[SysInfo.properties]");
                throw new JustReturnException();
            }

            // 创建下载文件 bea.lic
            // 生成加密字符串

            // 准备参数
            List<String> args = new ArrayList<String>();
            args.add(vo.getEndtime_str());
            args.add(String.valueOf(vo.isOfficial_version()));
            args.add(ProUT.get("HDID"));// 注意add顺序
            args.add(ProUT.get("CPUID")); // 注意add顺序
            args.add(ProUT.get("MACID"));// 注意add顺序
            args.add(vo.getMycode());
            args.add(vo.getCustomer_code());
            String encryptStr = Lic.encode(args);

            // 写入bea.lic(web下还要做好下载)
            Lic.writeToFile(encryptStr);
        }
        else
        {

            // 创建下载文件 bea.lic
            // 生成加密字符串

            // 准备参数
            List<String> args = new ArrayList<String>();
            args.add(vo.getEndtime_str());
            args.add(String.valueOf(vo.isOfficial_version()));
            args.add(vo.getHDID());// 注意add顺序
            args.add(vo.getCPUID()); // 注意add顺序
            args.add(vo.getMACID());// 注意add顺序
            args.add(vo.getMycode());
            args.add(vo.getCustomer_code());
            String encryptStr = Lic.encode(args);

            // 写入bea.lic(web下还要做好下载)
            Lic.writeToFile(encryptStr);
        }

    }


    @SuppressWarnings("unchecked")
    public void read(Map out)
    {
        String filepath = WebPath.getUploadRootPath() + "bealic_upload"
                + File.separatorChar + "bea.lic";
        if (!(new File(filepath)).exists())
        {
            Errors.addErrtoErrs(out, "还没有上传bea.lic");
            throw new JustReturnException();
        }

        // 模拟启动程序的时候，读取文件中的全部字符串
        String read_encryptStr = Lic.readAllStr(filepath);

        // 把取到的加密字符解析出成对象
        VO_lic vo = Lic.decode(read_encryptStr);
        out.put("info", vo);

    }


    /**
     * 读取系统信息
     * 
     * @param out
     */
    @SuppressWarnings("unchecked")
    public void sys_read(Map out)
    {
        out.put("HDID", SysInfo.getHDID());
        out.put("CPUID", SysInfo.getCPUID());
        out.put("MACID", SysInfo.getMACID());
    }
}
