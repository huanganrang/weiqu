package rml.servlet;

import Decoder.BASE64Decoder;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			
			FileInputStream fis = new FileInputStream(new File("F:\\png.txt"));
			int len = -1;
			byte[]  buffer = new byte[1024];
			StringBuffer sb = new StringBuffer();
			while((len = fis.read(buffer)) != -1)
			{
				byte[] tmp = new byte[len];
				System.arraycopy(buffer, 0, tmp, 0, len);
				sb.append(new String(tmp));
			}
			JSONObject obj = JSONObject.fromObject(sb.toString());
			String data = obj.getString("data");
			data = data.replace("data:image/png;base64,", "");
			System.out.println(data);
//			
			byte[] debytes = decoder.decodeBuffer(data);
			File ofile = new File("F:\\out.png");
			if(!ofile.exists())
				ofile.createNewFile();
			FileOutputStream fos = new FileOutputStream(ofile);
			fos.write(debytes);
//			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
