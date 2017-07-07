package com.dels.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by l13595 on 2017/2/16.
 * 文件相关操作
 */
public class FileUtil {


    /**
     * @description: 上传文件
     * @author: l13595
     * @param request: HttpServletRequest
     * @time: 2017/2/16 14:39
     */
    public static String uploadFile(HttpServletRequest request, String fname, String filePath){
        File file = new File(filePath);
        String fileName = null;
        if(!file.exists()&&!file.isDirectory()){
            file.mkdir();
        }
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
        fileUpload.setHeaderEncoding("UTF-8");
        try {
            List<FileItem> list = fileUpload.parseRequest(request);
            if(null==list || list.size()==0){
                return "获取上传文件失败，请联系管理员";
            }
            FileItem item = list.get(0);
            if(!item.isFormField()){
                fileName = item.getName();
                if(!fname.equals(fileName)){
                    return "请确认上传文件名";
                }
                fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
                InputStream is = item.getInputStream();
                FileOutputStream fos = new FileOutputStream(filePath+File.separator+fileName);
                byte buffer[] = new byte[1024];
                int length = 0;
                while((length = is.read(buffer))>0){
                    fos.write(buffer, 0, length);
                }
                is.close();
                fos.close();
                item.delete(); //删除处理文件上传时生成的临时文件
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
            return "上传失败，请联系管理员";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "上传失败，请联系管理员";
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败，请联系管理员";
        }
        return "ok";
    }


    /**
     * @description: 获取要上传文件的名称
     * @author: l13595
     * @param request: HttpServletRequest
     * @return: 返回文件名称
     * @time: 2017/3/28 16:18
     */
    public static String getFileName(HttpServletRequest request){
        String fileName = null;
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
        fileUpload.setHeaderEncoding("UTF-8");
        if(!fileUpload.isMultipartContent(request)){
            return null;
        }
        try {
            List<FileItem> list = fileUpload.parseRequest(request);
            FileItem item = list.get(0);
            if(!item.isFormField()){
                fileName = item.getName();
                if(fileName==null||fileName.trim().equals("")){
                    return null;
                }
                item.delete();
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return  fileName;
    }

    /**
     * @description: 校验要上传的文件，包括文件的后缀名、文件大小
     * @author: l13595
     * @param request: HttpServletRequest
     * @param suffix: 合规的文件后缀名list
     * @param fileSize: 文件大小标准,单位 M
     * @return: 返回校验结果。文件合规，返回ok；不合规，返回校验提醒
     * @time: 2017/3/28 17:08
     */
    public static String checkUploadFile(HttpServletRequest request, List<String> suffix, int fileSize){
        String fileName = null;
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
        fileUpload.setHeaderEncoding("UTF-8");
        if(!fileUpload.isMultipartContent(request)){
            return "上传失败，请联系管理员";
        }
        try {
            List<FileItem> list = fileUpload.parseRequest(request);
            FileItem item = list.get(0);
            if(!item.isFormField()){
                fileName = item.getName();
                if(fileName==null||fileName.trim().equals("")){
                    return "获取文件名失败";
                }
                if(FileUtil.checkFileSuffix(suffix, fileName)){
                    if(item.getSize()>(1024*1024*fileSize)){
                        return "文件超过"+fileSize+"M，不能上传";
                    }
                }else{
                    return "该格式文件不合规";
                }
                item.delete(); //删除处理文件上传时生成的临时文件
                return "ok";
            }else{
                return "获取文件失败";
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
            return "文件校验出错，请联系管理员";
        }
    }

    /**
     * @description:文件下载
     * @author: l13595
     * @param address: 要下载的文件地址
     * @param fileName: 文件名
     * @param response:  HttpServletResponse
     * @time: 2017/3/29 9:35
     */
    public static void downloadFile(String address, String fileName,HttpServletRequest request,HttpServletResponse response) throws IOException {

        String path = address+File.separator+fileName;
        File file = new File(path);// path是根据日志路径和文件名拼接出来的
        InputStream fis = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            response.reset();
            String agent = request.getHeader("User-Agent").toUpperCase();
            if (agent.indexOf("MSIE") > 0 || agent.indexOf("TRIDENT") > 0 || agent.indexOf("EDGE")>0) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }

            // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Content-Length", "" + file.length());
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            os.write(buffer);// 输出文件
            os.flush();
            fis.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 校验文件名后缀
     * @author: l13595
     * @param fileName: 文件名
     * @return:  文件名合格-true，文件名后缀不符合要求-false
     * @time: 2017/3/2 16:40
     */
    public static Boolean filterFileName(String fileName){
        String[] nameList = fileName.split("\\.");
        String prex = nameList[nameList.length-1];
        if(!prex.equals("doc")&&(!prex.equals("docx"))&&(!prex.equals("xls"))&&(!prex.equals("xlsx"))&&(!prex.equals("pdf"))){
            return false;
        }
        return true;
    }

    /**
     * @description: 校验文件名后缀是否合规
     * @author: l13595
     * @param suffix: 合规的文件后缀
     * @param fileName: 文件名称
     * @return:
     * @time: 2017/3/28 17:01
     */
    public static boolean checkFileSuffix(List<String> suffix, String fileName){
        String[] nameList = fileName.split("\\.");
        String prex = nameList[nameList.length-1];
        for(String suffixName: suffix ){
            if(prex.equals(suffixName)){
                return true;
            }
        }
        return false;
    }

    /**
     * @description: 校验文件大小，不能超过100M
     * @author: l13595
     * @param file: 需要校验大小的文件
     * @return: 返回校验结果   不超过100M-返回true，超过100M-返回false
     * @time: 2017/3/2 16:59
     */
    public static Boolean fileSize(File file){
        long fs = file.length();
        if(fs>(1024*1024*100)){
            return false;
        }
        return true;
    }

}
