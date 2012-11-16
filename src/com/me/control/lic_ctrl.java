package com.me.control;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.me.biz.LicBiz;
import com.me.ut.StringUT;
import com.me.ut.WebPath;
import com.me.ut.lic.VO_lic;


@Controller
@RequestMapping("lic_ctrl")
public class lic_ctrl
{
    @Autowired
    private LicBiz licBiz;


    @SuppressWarnings("unchecked")
    @RequestMapping("create")
    public @ResponseBody
    Map create(VO_lic vo)
    {
        Map out = new HashMap();
        try
        {
            licBiz.create(vo, out);
            out
                    .put(
                            "downurl",
                            WebPath.SYS_PATH
                                    + "/"
                                    + "FileUploadCtrl/downFile.do?requestid=0&fieldName=bealic&fileName="
                                    + StringUT
                                            .Base64_encode("bea.lic", "UTF-8")
                                    + "&d="
                                    + String.valueOf((new Date()).getTime()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return out;
    }


    // 替换'为空
    private String replace_(String string)
    {
        return string.replace("'", "");
    }


    @SuppressWarnings("unchecked")
    @RequestMapping("read_bealic")
    public @ResponseBody
    Map read_bealic()
    {
        Map out = new HashMap();
        try
        {
            licBiz.read(out);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return out;
    }


    @SuppressWarnings("unchecked")
    @RequestMapping("sys_read")
    public @ResponseBody
    Map sys_read()
    {
        Map out = new HashMap();
        licBiz.sys_read(out);
        return out;
    }

}
