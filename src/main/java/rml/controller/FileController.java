package rml.controller;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2015/10/22 0022.
 */
public class FileController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(@RequestParam MultipartFile file) {
        try {
            if(file.isEmpty()){

                String tempFileName = file.getOriginalFilename();
                //fastDFS方式
                byte[] fileBuff = file.getBytes();
                String fileId = "";
                String fileExtName = tempFileName.substring(tempFileName.lastIndexOf("."));

                //建立连接
                TrackerClient tracker = new TrackerClient();
                TrackerServer trackerServer = tracker.getConnection();
                StorageServer storageServer = null;
                StorageClient1 client = new StorageClient1(trackerServer, storageServer);

                //设置元信息
                NameValuePair[] metaList = new NameValuePair[3];
                metaList[0] = new NameValuePair("fileName", tempFileName);
                metaList[1] = new NameValuePair("fileExtName", fileExtName);
                metaList[2] = new NameValuePair("fileLength", String.valueOf(file.getSize()));

                //上传文件
                fileId = client.upload_file1(fileBuff, fileExtName, metaList);

            }

        } catch (Exception e) {

        }
        return "123";
    }
    public static void main(String[]args)throws Exception{
        String result = "url=http://dianbo.weiqu168.com/A/:一级二级分类规划（确定版)0411.xlsx&hash=FjScnVLdBMO_8j3_XgYT2DZkiCgD";
        result = result.substring(result.indexOf("=")+1,result.length());
        result = result.substring(0,result.indexOf("&"));
        System.err.print(result);
    }

}
