package rml.servlet;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.csource.fastdfs.*;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import rml.util.ReturnJson;
import rml.util.SerializeUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by Administrator on 2015/10/22 0022.
 */
public class FileDownloadServlet extends HttpServlet {

    static String constr = "10.174.139.99" ;

    private Jedis jedis = new Jedis(constr) ;


    @ResponseBody
    public void doPost(HttpServletRequest request,HttpServletResponse response){
        String uuid = request.getParameter("token");

        rml.model.File file = (rml.model.File)SerializeUtil.unserialize(jedis.get(SerializeUtil.serialize(uuid)));
        byte[] content = getFile(file.getGroupName(), file.getRemoteFileName());
        String fileName = UUID.randomUUID().toString().substring(0,6);
        File file1 = byte2File(content,System.getProperty("java.io.tmpdir"),file.getName());
        try {
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        }catch (Exception e){
            e.printStackTrace();
        }

        InputStream in = null ;
        OutputStream out = null ;
        try
        {
            in = new FileInputStream(file1); //获取文件的流
            int len = 0;
            byte buf[] = new byte[1024];//缓存作用
            out = response.getOutputStream();//输出流
            while( (len = in.read(buf)) > 0 ) //切忌这后面不能加 分号 ”;“
            {
                out.write(buf, 0, len);//向客户端输出，实际是把数据存放在response中，然后web服务器再去response中读取
            }

        }catch (Exception e){
            e.printStackTrace();
        } finally
        {
            if(in!=null)
            {
                try{
                    in.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }

            if(out!=null)
            {
                try{
                    out.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

    }
    @ResponseBody
    public void doGet(HttpServletRequest request,HttpServletResponse response) {
        doPost(request,response);
    }

    public static byte[] getFile(String groupName, String remoteFileName) {
        try {
            ClientGlobal.init("/fdfs_client.conf");
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);
            return client.download_file(groupName,remoteFileName);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static File byte2File(byte[] buf, String filePath, String fileName)
    {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try
        {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory())
            {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
            return file;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
