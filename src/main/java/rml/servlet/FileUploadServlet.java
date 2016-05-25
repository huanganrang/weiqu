package rml.servlet;

import com.alibaba.fastjson.JSONObject;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.icepdf.core.pobjects.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import rml.model.RecordClient;
import rml.util.*;

@Component
public class FileUploadServlet extends HttpServlet
{

    private static final Logger logger = LoggerFactory.getLogger(FileUploadServlet.class);


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
                if (item.isFormField())
                {
                    String fieldName = item.getFieldName();
                    if (fieldName.equals("houseToken")) {
                        houseToken = item.getString();
                    }
                    if (fieldName.equals("userToken")) {
                        userToken = item.getString();
                    }
                    if (fieldName.equals("type")) {
                        type = item.getString();
                    }
                    str1 = item.getString();
                }
            }

            for (FileItem item : items)
                if (item.isFormField())
                {
                    String fieldName = item.getFieldName();
                    if (fieldName.equals("houseToken")) {
                        houseToken = item.getString();
                    }
                    if (fieldName.equals("userToken")) {
                        userToken = item.getString();
                    }
                    if (fieldName.equals("type")) {
                        type = item.getString();
                    }
                    str1 = item.getString();
                }
                else
                {
                    String outputfolder = System.getProperty("java.io.tmpdir") + "/" +createOrderNo(formatDate());
                    File directory = new File(outputfolder);
                    directory.mkdirs();
                    String fieldName = item.getFieldName();
                    String fileName = FilenameUtils.getName(item.getName());
                    String ext = fileName.substring(fileName.lastIndexOf("."), fileName.length());

                    InputStream fileContent = item.getInputStream();

                    java.io.File tmpDir = new java.io.File(System.getProperty("java.io.tmpdir") + "/tmp0" + ext);
                    ClientGlobal.init("/fdfs_client.conf");
                    TrackerClient tracker = new TrackerClient();
                    TrackerServer trackerServer = tracker.getConnection();
                    StorageServer storageServer = null;
                    StorageClient1 client = new StorageClient1(trackerServer, storageServer);
                    NameValuePair[] metaList = new NameValuePair[3];

                    inputstreamtofile(fileContent, tmpDir);

                    metaList[0] = new NameValuePair("fileName", tmpDir.getName());
                    metaList[1] = new NameValuePair("fileExtName", ext);
                    metaList[2] = new NameValuePair("fileLength", String.valueOf(tmpDir.length()));
                    RecordClient sendValue = new RecordClient();
                    sendValue.setAction("4");
                    String uuid = UUID.randomUUID().toString();
                    rml.model.File file = new rml.model.File();
                    if (type != null) {
                        ReturnJson value = new ReturnJson();
                        if ((StringUtils.isEmpty(houseToken)) || (StringUtils.isEmpty(userToken))) {
                            value.setErrorCode(47002);
                            value.setServerStatus(1);
                        }
                        sendValue.setType(new Integer(type).intValue());
                        sendValue.setFileName(fileName);
                        file.setUserToken(userToken);
                        file.setHouseToken(houseToken);
                    }

                    if (!StringUtils.isEmpty(type)) {
                        file.setType(new Integer(type).intValue());
                    }
                    file.setName(fileName);
                    file.setCreateDate(new Date());
                    file.setExt(ext);
                    file.setToken(uuid);
                    String[] result = client.upload_file(File2byte(tmpDir), ext, metaList);
                    Iterator localIterator2;
                    java.io.File con;
                    if ((ext.equals(".pdf")) || (ext.equals(".PDF")) || (ext.equals(".Pdf")) || (ext.equals(".PDf"))) {
                        long tmp0 = new Date().getTime();
                        List results = pdfToImg(tmpDir.getAbsolutePath());
                        tmp0 = new Date().getTime();
                        for (localIterator2 = results.iterator(); localIterator2.hasNext(); ) {
                            con = (java.io.File)localIterator2.next();
                            String imageExt = con.getName().substring(con.getName().lastIndexOf("."), con.getName().length());
                            String[] value = client.upload_file(File2byte(con), imageExt, metaList);
                            HashMap<String,String> map = new HashMap<String, String>();
                            String tmp = "file.weiqu168.com/" + value[0] + "/" + value[1];
                            map.put("pic",tmp);
                            fileList.add(map);
                        }
                        logger.info("png upload to fastdfs total"+(new Date().getTime()-tmp0)+"mill");
                    }
                    else if (ext.equals(".doc") || ext.equals(".docx")||ext.equals(".ppt") || ext.equals(".pptx")||ext.equals(".xls") || ext.equals(".xlsx")) {
                        long tmp0 = new Date().getTime();
                        logger.info("输出目录为:"+outputfolder);
                        MstoImages.getInstance().MStoPdf(tmpDir.getAbsolutePath(),outputfolder);
                        List<File> files = FileTraveller.traverseFolder(outputfolder);
                        for(File img:files){
                            String imageExt = img.getName().substring(img.getName().lastIndexOf("."), img.getName().length());
                            String[] values = client.upload_file(File2byte(img), imageExt, metaList);
                            HashMap<String,String> map = new HashMap<String, String>();
                            String tmp = "file.weiqu168.com/" + values[0] + "/" + values[1];
                            map.put("pic",tmp);
                            fileList.add(map);
                        }
                        logger.info("png upload to fastdfs total"+(new Date().getTime()-tmp0)/1000+"秒");
                    }
                    file.setGroupName(result[0]);
                    file.setRemoteFileName(result[1]);
                    sendValue.setFileUrl(result[0] + "/" + result[1]);
                    if (type != null) {
                        try {
                            Server server = new Server(13452);
                            server.loopServer(houseToken, JSONObject.toJSONString(sendValue));
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                    System.err.print(tmpDir.length());
                    PrintWriter out = response.getWriter();
                    ReturnJson value = new ReturnJson();
                    value.setErrorCode(46000);
                    if(!StringUtils.isEmpty(type)) {
                        value.setType(new Integer(type));
                    }
                    if(fileList.size()==0){
                            HashMap<String,String> map = new HashMap<String, String>();
                            map.put("pic","file.weiqu168.com/" + result[0] + "/" + result[1]);
                            fileList.add(map);
                    }
                    value.setReturnObject(fileList);
                    value.setReturnMessage("调用成功");
                    value.setServerStatus(0);
                    value.setReturnValue("file.weiqu168.com/" + result[0] + "/" + result[1]);
                    out.append(JSONObject.toJSONString(value));
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

    public void inputstreamtofile(InputStream ins, java.io.File file) throws Exception {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }

    public static byte[] File2byte(java.io.File file)
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

    public static List<java.io.File> pdfToImg(String filePath)
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
                java.io.File file = new java.io.File(System.getProperty("java.io.tmpdir") + "/"+"imageCapture1_" + i + ".png");
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