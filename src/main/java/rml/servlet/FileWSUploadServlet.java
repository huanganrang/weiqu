package rml.servlet;

import com.alibaba.fastjson.JSONObject;
import com.chinanetcenter.api.domain.HttpClientResult;
import com.chinanetcenter.api.util.Config;
import com.chinanetcenter.api.wsbox.FileUploadCommand;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.icepdf.core.pobjects.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import rml.model.RecordClient;
import rml.util.FileTraveller;
import rml.util.MstoImages;
import rml.util.ReturnJson;
import rml.util.Server;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.*;

@Component
public class FileWSUploadServlet extends HttpServlet
{

    private static final Logger logger = LoggerFactory.getLogger(FileWSUploadServlet.class);


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            List<HashMap<String,String>> fileList = new ArrayList<HashMap<String,String>>();
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            String    houseToken = null;
            String userToken = null;
            String type = null;
            String str1 = null;
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    String fieldName = item.getFieldName();
                    String fileName = FilenameUtils.getName(item.getName());
                    String ext = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                    InputStream fileContent = item.getInputStream();
                    java.io.File tmpDir = new java.io.File(System.getProperty("java.io.tmpdir") + "/tmp0" + ext);
                    inputstreamtofile(fileContent, tmpDir);
                    ReturnJson returnJson = new ReturnJson();
                    String ak = "13096de52781d6939663c8d0210cc219e1930b45";
                    String sk = "a59bb243527db414865bf361b3ac05ec4a885acb";
                    Config.init(ak, sk);
                    String bucketName = "weiqu:A/"; // 空间名称，需要改成您自己的
                    String localFilePath = tmpDir.getAbsolutePath(); // 本地文件路径
                    String returnBody = "url=$(url)"; //自定义返回信息
                    //2.上传&自定义返回信息
                    HttpClientResult httpClientResult = FileUploadCommand.uploadFileAndReturn(bucketName, fileName, localFilePath, returnBody);
                    String result = httpClientResult.getResponse();
                    result = result.substring(result.indexOf("=")+1,result.length());
                    result = result.substring(0,result.indexOf("&"));
                    returnJson.setReturnValue(result);
                    returnJson.setReturnMessage("调用成功");
                    returnJson.setServerStatus(0);
                    returnJson.setErrorCode(1000);
                    response.setHeader("Content-type", "text/html;charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    out.append(JSONObject.toJSONString(returnJson));

                }
            }
        }
        catch (Exception e)
        {
            String houseToken;
            String userToken;
            String type;
            String str1;
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doGet(request, response);
    }

    public void inputstreamtofile(InputStream ins, File file) throws Exception {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }

    public static byte[] File2byte(File file)
    {
        byte[] buffer = null;
        try
        {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }

    public static List<File> pdfToImg(String filePath)
    {
        List list = new ArrayList();
        Document document = new Document();
        try {
            document.setFile(filePath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        float scale = 2.0F;
        float rotation = 0.0F;

        for (int i = 0; i < document.getNumberOfPages(); i++)
        {
            BufferedImage image = (BufferedImage)document
                    .getPageImage(i, 1, 2, rotation, scale);

            RenderedImage rendImage = image;
            try
            {
                System.out.println("/t capturing page " + i);
                File file = new File(System.getProperty("java.io.tmpdir") + "/"+"imageCapture1_" + i + ".png");
                logger.info(file.getAbsoluteFile().toString());
                ImageIO.write(rendImage, "png", file);
                list.add(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            image.flush();
        }

        document.dispose();
        return list;
    }




    public static String formatDate(){
        java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String s = format1.format(new Date());
        return s;
    }

    public static String createOrderNo(String s){
        s = s+UUID.randomUUID().toString().substring(0,4);
        s = s+UUID.randomUUID().toString().substring(0,2);
        return  s.replaceAll("-","").replaceAll(" ","").replaceAll(":","");
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    public static void main(String[]args){
//        logger.info("before word pdf:" + new Date().getTime());
//        pdfToImg("C:\\365test.pdf");
//        logger.info("after word img:" + new Date().getTime());
        System.err.print(createOrderNo(formatDate()));
    }
}