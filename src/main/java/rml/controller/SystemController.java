package rml.controller;

import Decoder.BASE64Decoder;
import com.chinanetcenter.api.domain.HttpClientResult;
import com.chinanetcenter.api.domain.PutPolicy;
import com.chinanetcenter.api.util.*;
import com.chinanetcenter.api.wsbox.FileUploadCommand;
import net.sf.json.JSONObject;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.util.SocketUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.HouseChat;
import rml.util.ReturnJson;
import rml.util.Server;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.List;

/**
 * Created by Administrator on 2015/10/14 0014.
 */

@Controller
@RequestMapping("/System")
public class SystemController {


    @RequestMapping(value="/Port",method = RequestMethod.GET)
    @ResponseBody
    public Object getPort(){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(39000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        int port = 0;
        try{
            port = SocketUtils.findAvailableTcpPort();

        }catch (Exception e){
            returnJson.setErrorCode(39001);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(0);
        }
        returnJson.setReturnValue(new Integer(port).toString());
        return returnJson;
    }

    public static Map<String,List<Server>> values = new HashMap<String, List<Server>>();

    @RequestMapping(value="/Connect",method = RequestMethod.GET)
    @ResponseBody
    public Object connect(HouseChat houseChat){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(40000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        try{
       /*   Server server = new Server(houseChat.getPort());
          server.start();
          server.setHouseToken(houseChat.getHouseToken());*/
          //sockets.add(server);
         List<Server> list = values.get(houseChat.getHouseToken());
         if(list==null){
             list = new ArrayList<Server>();
         }
       /*  list.add(server);*/
         values.put(houseChat.getHouseToken(), list);
        }catch (Exception e){
            e.printStackTrace();
            returnJson.setErrorCode(40001);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(0);
        }
        returnJson.setReturnValue(new Integer(houseChat.getPort()).toString());
        return returnJson;
    }


    @RequestMapping(value={"/Upload/Path"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    public Object getUploadPath(String fileName)
    {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(40000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        String ak = "4e63bbe6b26650af7d7a64eaddc85eecedcc3076";
        String sk = "03ed38d00e3d2f35657f11542bb06fe5f636c083";
        Config.init(ak, sk);
        String returnBody = "url=$(url)";
        PutPolicy putPolicy = new PutPolicy();
        putPolicy.setReturnBody(returnBody);
        putPolicy.setScope("weiqu:A/" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8)+fileName);
        Long time = Long.valueOf(DateUtil.parseDate("2050-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss").getTime());
        putPolicy.setDeadline(String.valueOf(time));
        String uploadToken = TokenUtil.getUploadToken(putPolicy);
        returnJson.setReturnValue("http://upload.weiqu168.com/file/upload?token=" + uploadToken + "&returnBody=url");
        return returnJson;
    }





    @RequestMapping(value={"/Upload"}, method={RequestMethod.POST})
    @ResponseBody
    public Object uploadPng(@RequestBody String data){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(56000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(data)){
            returnJson.setErrorCode(56001);
            returnJson.setReturnMessage("data不能为空");
            returnJson.setServerStatus(0);
            return returnJson;
        }
        byte[] value = null;
        try {
            System.out.println(data);
            JSONObject obj = JSONObject.fromObject(data.toString());
            String ngData = obj.getString("data");
            ngData = ngData.replace("data:image/png;base64,", "");
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] debytes = decoder.decodeBuffer(ngData);
            File ofile = new File(System.getProperty("java.io.tmpdir") + "\\" + UUID.randomUUID().toString().substring(0, 6) + ".png");
            if(!ofile.exists())
                ofile.createNewFile();
            FileOutputStream fos = new FileOutputStream(ofile);
            fos.write(debytes);
            ClientGlobal.init("/fdfs_client.conf");
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);
            NameValuePair[] metaList = new NameValuePair[3];

            metaList[0] = new NameValuePair("fileName", ofile.getName());
            metaList[1] = new NameValuePair("fileExtName", ".png");
            metaList[2] = new NameValuePair("fileLength", String.valueOf(ofile.length()));
            String[] values = client.upload_file(debytes, ".png", metaList);
            returnJson.setReturnValue("file.weiqu168.com/"+values[0]+"/"+values[1]);
        }catch (Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(56002);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }
    static void buff2Image(byte[] b,String tagSrc) throws Exception {

        FileOutputStream fout = new FileOutputStream(tagSrc);

        fout.write(b);

        fout.close();

    }

    public static void main(String[]args){
        ReturnJson returnJson = new ReturnJson();
        String ak = "13096de52781d6939663c8d0210cc219e1930b45";
        String sk = "a59bb243527db414865bf361b3ac05ec4a885acb";
        Config.init(ak, sk);
        String bucketName = "weiqu:A/"; // 空间名称，需要改成您自己的
        String fileName = "fdfs_client.conf"; // 文件名称
        String localFilePath = "D:\\fdfs_client.conf"; // 本地文件路径
        String returnBody = "key=$(key)&fname=$(fname)&fsize=$(fsize)&url=$(url)"; //自定义返回信息
        //2.上传&自定义返回信息
        HttpClientResult httpClientResult = FileUploadCommand.uploadFileAndReturn(bucketName, fileName, localFilePath, returnBody);
        System.err.print(httpClientResult);
    }

}
