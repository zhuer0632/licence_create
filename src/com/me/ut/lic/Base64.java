package com.me.ut.lic;


public class Base64
{

    /**
     * 编码
     * @param input
     * @return
     */
    public static String encode(String input)
    {
        String out = "";
        out = org.apache.commons.codec.binary.Base64.encodeBase64String(input.getBytes());
        return out;
    }


    /**
     * 
     * 反编码    
     * @param input
     * @return
     */
    public static String decode(String input)
    {
        String out = "";

        byte[] bs = org.apache.commons.codec.binary.Base64.decodeBase64(input);
        out = new String(bs);
        return out;
    }
    
    
    public static void main(String[]args)
    {
       String s= Base64.encode("哈哈哈");
       System.out.println(s);
       System.out.println(Base64.decode("emhhbmdmZWk="));
    }

}
