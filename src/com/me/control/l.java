package com.me.control;


import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.me.ut.DatetimeUT;
import com.me.ut.StringUT;


@Controller
@RequestMapping("l")
public class l
{
    @RequestMapping("login")
    public ModelAndView login()
    {
        ModelAndView out=new ModelAndView();
        out.setViewName("login");
        return out;
    }
    
    @RequestMapping("lic_create")
    public ModelAndView lic_create()
    {
        ModelAndView mod = new ModelAndView();
        mod.addObject("fileId", StringUT.getUUID());
        mod.addObject("endtime_str", DatetimeUT.getDateStr(DatetimeUT.addMonth(
                new Date(), 3)));
        mod.setViewName("lic_create");
        return mod;
    }
    
    @RequestMapping("lic_read")
    public  ModelAndView lic_read()
    {
        ModelAndView out=new ModelAndView();
        out.addObject("fileId", StringUT.getUUID());
        out.setViewName("lic_read");
        return out;
    }
    
    @RequestMapping("sys_read")
    public  ModelAndView sys_read()
    {
        ModelAndView out=new ModelAndView();
        out.setViewName("sys_read");
        return out;
    }
    
}
