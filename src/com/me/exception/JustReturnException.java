package com.me.exception;


import java.util.ArrayList;
import java.util.List;


public class JustReturnException extends RuntimeException
{
    // 当mod中已经保存上了err内容
    // 并不需要在exception中添加一场内容了
    // 但是还需要中断程序

    // 此时throw new JustReturnException(); 即可

    private List<String> errs = new ArrayList<String>();

    public List<String> getErrs()
    {
        return errs;
    }
    
    public void setErrs(List<String> errs)
    {
        this.errs = errs;
    }

    public JustReturnException(List<String> errs)
    {
        super();
        this.errs = errs;
    }

    public JustReturnException(String err)
    {
        this.errs.add(err);
    }
    public JustReturnException()
    {
    }
}
