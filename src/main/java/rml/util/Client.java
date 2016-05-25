package rml.util;

/**
 * Created by Administrator on 2015/10/14 0014.
 */

import com.alibaba.fastjson.JSONObject;
import rml.model.RecordClient;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

public class Client {

    /**
     * 处理服务端发回的对象，可实现该接口。
     */
    public static interface ObjectAction{
        void doAction(Object obj,Client client);
    }
    public static final class DefaultObjectAction implements ObjectAction{
        public void doAction(Object obj,Client client) {
            System.out.println("处理：\t"+obj.toString());
        }
    }
    public static void main(String[] args) throws UnknownHostException, IOException {
        String serverIp = "139.196.34.76";
//        String serverIp = "127.0.0.1";
        int port = 31876;
        Client client = new Client(serverIp,port);
        client.start();
        RecordClient recordClient = new RecordClient();
        recordClient.setContent("123");
        recordClient.setType(3);
        recordClient.setHouseToken("c00f6489fd9f9d83292fffd4c6a0369c");
        String jsonString = JSONObject.toJSONString(recordClient);
        System.err.print(jsonString);
        client.sendObject(jsonString);
    }

    private String serverIp;
    private int port;
    private Socket socket;
    private boolean running=false;
    private long lastSendTime;
    private ConcurrentHashMap<Class, ObjectAction> actionMapping = new ConcurrentHashMap<Class,ObjectAction>();

    public Client(String serverIp, int port) {
        this.serverIp=serverIp;this.port=port;
    }

    public void start() throws UnknownHostException, IOException {
        if(running)return;
        socket = new Socket(serverIp,port);
        System.out.println("本地端口："+socket.getLocalPort());
        lastSendTime=System.currentTimeMillis();
        running=true;
//        new Thread(new KeepAliveWatchDog()).start();
        new Thread(new ReceiveWatchDog()).start();
    }

    public void stop(){
        if(running)running=false;
    }

    /**
     * 添加接收对象的处理对象。
     * @param cls 待处理的对象，其所属的类。
     * @param action 处理过程对象。
     */
    public void addActionMap(Class<Object> cls,ObjectAction action){
        actionMapping.put(cls, action);
    }

    public void sendObject(String jsonString) throws IOException {
        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os);
        pw.write(jsonString);
        pw.flush();
        os.flush();
    }

    class KeepAliveWatchDog implements Runnable{
        long checkDelay = 10;
        long keepAliveDelay = 2000;
        public void run() {
            while(running){
                if(System.currentTimeMillis()-lastSendTime>keepAliveDelay){
                    try {
                        Client.this.sendObject("0XAA");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Client.this.stop();
                    }
                    lastSendTime = System.currentTimeMillis();
                }else{
                    try {
                        Thread.sleep(checkDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Client.this.stop();
                    }
                }
            }
        }
    }

    class ReceiveWatchDog implements Runnable{
        public void run() {
            while(running){
                try {
                    InputStream in = socket.getInputStream();
                    if(in.available()>0){
                        byte[] buffer = new byte[1024];
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        int length = 0;
                        try {
                            length = in.read(buffer);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        byte[] value = new byte[length];
                        System.arraycopy(buffer, 0, value, 0, length);
                        String jsonString = new String(value, "UTF-8");
                        System.err.println("recieved:" + jsonString);
                    }else{
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Client.this.stop();
                }
            }
        }
    }
}
