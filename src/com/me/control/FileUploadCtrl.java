package com.me.control;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.me.ut.FileUT;
import com.me.ut.StringUT;
import com.me.ut.WebPath;


@Controller
@RequestMapping("/FileUploadCtrl")
public class FileUploadCtrl
{

    // 例6中使用的上传
    @RequestMapping(value = "/do_upload_file", method = RequestMethod.POST)
    // Filedata此处对应js/swfupload/swfupload.js中的this.ensureDefault("file_post_name",
    // "Filedata");
    public ModelAndView do_upload_file(
            @RequestParam("Filedata") MultipartFile file,
            @RequestParam("requestid") String requestid,
            @RequestParam("fieldName") String fieldName,
            HttpServletRequest request)
    {

        ModelAndView mod = new ModelAndView();

        StringUT.printErr("当前上传域：" + fieldName);
        OutputStream output = null;
        File outfile = null;
        String fileName = "";
        try
        {
            // MultipartFile是对当前上传的文件的封装a
            if (!file.isEmpty())
            {
                fileName = new String(file.getOriginalFilename().getBytes(
                        "ISO-8859-1"), "UTF-8");

                // 删除文件名中的单引号，因为有单引号的时候，文件名会被截断，js变量不能传递。
                fileName = StringUtils.replace(fileName, "'", "");

                System.out.println("上传的文件的文件名是：" + (fileName));
                String filepath = StringUT.getUploadPath() + File.separatorChar
                        + fieldName + File.separatorChar;
                if (!(new File(filepath).exists()))
                {
                    new File(filepath).mkdirs();
                }
                outfile = new File(filepath + fileName);
                output = new FileOutputStream(outfile);
                IOUtils.copy(file.getInputStream(), output);
                // store the bytes somewhere
                // 在这里就可以对file进行处理了，可以根据自己的需求把它存到数据库或者服务器的某个文件夹
            }
            else
            {
                StringUT.print("上传失败");
                mod.addObject("uploadstate", "上传失败");
            }
        }
        catch (Exception e)
        {
            StringUT.printErr(e);
            // 此处一定要关闭
            if (output != null)
            {
                try
                {
                    output.close();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
            // 如果上传过程中出现异常：告知前台错误
            mod.setViewName("fieldtest");
            mod.addObject("fielderr", "上传文件" + fileName + "的时候出现错误");
            return mod;
        }
        finally
        {
            // 此处一定要关闭
            if (output != null)
            {
                try
                {
                    output.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        // /v/upload/doupload.do
        mod.setViewName("lic_create");
        return mod;
    }


    // 虽然前段传过来的是encodeURIComponent过的值，但是springmvc已经帮我们解析过了
    @RequestMapping("downFile")
    public ModelAndView downFile(@RequestParam("requestid") String requestid,
            @RequestParam("fieldName") String fieldName,
            @RequestParam("fileName") String fileName,
            HttpServletRequest request, HttpServletResponse response)
    {
        
        java.io.BufferedInputStream bis = null;
        java.io.BufferedOutputStream bos = null;

        String folePath = StringUT.getUploadFiles()+fieldName+File.separatorChar;
        
        String downLoadPath = folePath
                + StringUT.Base64_decode(fileName, "UTF-8");
        System.out.println(downLoadPath);
        // 不存在返回异常
        if (!(new File(downLoadPath).exists()))
        {
            throw new RuntimeException("指定的文件不存在");
        }

        try
        {
            long fileLength = new File(downLoadPath).length();
            response.setContentType("application/x-msdownload;");

            // 下面三个if中的fileName必须直接来自输入参数，没做任何处理。
            fileName = StringUT.Base64_decode(fileName, "UTF-8");
            fileName = StringUT.UTF8_ISO(fileName);
            String head = "";
            if (StringUT.isIE(request))
            {
                fileName = URLEncoder.encode(StringUT.ISO_UTF8(fileName),
                        "UTF-8").replace("+", "%20");
                head = "attachment; filename=" + fileName;
            }
            else if (StringUT.isChrome(request))
            {
                head = "attachment; filename=" + fileName;
            }
            else if (StringUT.isFirefox(request))
            {
                head = "attachment;filename=\"" + fileName + "\"";
            }
            response.setHeader("Content-disposition", head);// 下载文件的时候
            response.setHeader("Content-Length", String.valueOf(fileLength));
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
            {
                bos.write(buff, 0, bytesRead);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bis != null)
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            if (bos != null)
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
        return null;
    }


    // 删除文件
    @SuppressWarnings("unchecked")
    @RequestMapping("/delFile")
    public @ResponseBody
    Map delFile(@RequestBody String queryIn)
    {
        Map map = StringUT.url2Map(queryIn);

        try
        {
            String requestId = String.valueOf(map.get("requestId"));
            String fieldName = String.valueOf(map.get("fieldName"));
            String fileName = String.valueOf(map.get("fileName"));

            FileUT.del(requestId, fieldName, fileName);
            StringUT.print("删除了文件：" + fileName);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return map;
    }

}