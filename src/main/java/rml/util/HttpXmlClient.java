package rml.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/1 0001.
 */
public class HttpXmlClient {


    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
    private static final String APPLICATION_JSON = "application/json";
    private static Logger log = Logger.getLogger(HttpXmlClient.class);


    public static void httpPostWithJSON(String url, String json) throws Exception {
        // 将JSON进行UTF-8编码,以便传输中文

        SSLClient httpClient = new SSLClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

        StringEntity se = new StringEntity(json);
        se.setContentType(CONTENT_TYPE_TEXT_JSON);
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
        httpPost.setEntity(se);
        HttpResponse result = httpClient.execute(httpPost);
    }

}
