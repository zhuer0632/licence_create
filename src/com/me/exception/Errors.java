package com.me.exception;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.me.ut.StringUT;



/**
 * 
 * 将指定的类添加到数据库中
 * 
 * @author zsl 2012-7-5
 */
public class Errors
{

    /**
     * 添加错误信息到map中，key是固定的[diverr]
     * 
     * @param map
     * @param err
     */
    public static void addErrtoErrs(Map map, String err)
    {
        if (StringUT.isEmpty(map.get(ExceptionConst.key)))
        {
            map.put(ExceptionConst.key, new ArrayList<String>());
        }
        
        List<String> errs = (List<String>) map.get(ExceptionConst.key);
        for (int i = 0; i < errs.size(); i++)
        {
            if(errs.get(i).equals(err))
            {
                errs.remove(i);
                break;
            }
        }
        errs.add(err);
        map.put(ExceptionConst.key, errs);
    }
    
    /**
     * 
     * 添加错误信息到map中，key是固定的[diverr]
     * 
     * @param map
     * @param err
     */
    public static void addErrtoErrs(Map map, List<String> errs)
    {
        if (errs != null && errs.size() > 0)
        {
            for (int i = 0; i < errs.size(); i++)
            {
                String err = errs.get(i);
                addErrtoErrs(map, err);
            }
        }
    }


    /**
     * 检查ajax返回的map中是否包含了错误信息
     * 
     * @param map
     * @return
     */
    public static boolean hasDivErr(Map map)
    {
        if (map.get(ExceptionConst.key) != null)
        {
            return true;
        }
        return false;
    }
}
