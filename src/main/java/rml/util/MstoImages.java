package rml.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rml.servlet.FileUploadServlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;


public class MstoImages {

    public static final String CONVERT_PDF_SHELL = "/opt/java/MStoPdf.sh";
    public static final String SPACE=" ";

    private static final Logger logger = LoggerFactory.getLogger(MstoImages.class);

    public  String MStoPdf(String msfilename,String outputfolder)
    {
        String result = null;
        String line = null;
        Process process =null;
        try {
            StringBuffer cmd = new StringBuffer();
            cmd.append(CONVERT_PDF_SHELL);
            cmd.append(SPACE);
            cmd.append(msfilename);
            cmd.append(SPACE);
            cmd.append(outputfolder);
            logger.info("脚本输出命令为:"+cmd.toString());
             process = Runtime.getRuntime().exec(cmd.toString());
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            
            while((line = input.readLine()) != null)
            {
                if(line != null)
                {
                    result = line;
                }
            }
            input.close();
            ir.close();
            return result;
        } catch (IOException e) {
            return result;
        }finally{
            process.destroy();
        }
    }
    private MstoImages(){
        
    }
    private static MstoImages ms;
    public static MstoImages getInstance()
    {
        if(null ==  ms)
        {
            ms = new MstoImages();
        }
        return ms;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        String msfilename = "/home/sdduser/Desktop/openmeetings/CameraOS_Camera_Feature_List_V2.xlsx";
        String outputfolder = "/home/sdduser/Desktop/openmeetings/images";
        String number = MstoImages.getInstance().MStoPdf(msfilename,outputfolder);
        System.out.println(number);
    }

}
