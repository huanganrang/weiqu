package rml.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import rml.controller.SystemController;
import rml.controller.UserChatController;
import rml.model.RecordClient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Server
{
    private String houseToken;
    public int port;
    private volatile boolean running = false;
    private long receiveTimeDelay = 3000L;
    private ConcurrentHashMap<Class, ObjectAction> actionMapping = new ConcurrentHashMap();
    private Thread connWatchDog;
    public Socket s;

    private SimpMessagingTemplate simpMessagingTemplate;

    public String getHouseToken()
    {
        return this.houseToken;
    }

    public void setHouseToken(String houseToken) {
        this.houseToken = houseToken;
    }

    static Logger logger = Logger.getLogger(Server.class.getName());

    public Server(int port)
    {
        this.port = port;
    }

    public void start() {
        if (this.running) return;
        this.running = true;
        this.connWatchDog = new Thread(new ConnWatchDog());
        this.connWatchDog.start();
    }

    public void stop()
    {
        if (this.running) this.running = false;
        if (this.connWatchDog != null) this.connWatchDog.stop();
    }

    public void addActionMap(Class<Object> cls, ObjectAction action)
    {
        this.actionMapping.put(cls, action);
    }

    class SocketAction
            implements Runnable
    {
        Socket s;
        boolean run = true;
        long lastReceiveTime = System.currentTimeMillis();
        private String jsonString;
        private boolean flag = false;

        public String getJsonString()
        {
            return this.jsonString;
        }

        public void setJsonString(String jsonString) {
            this.jsonString = jsonString;
        }

        public SocketAction(Socket s) {
            this.s = s;
        }

        public boolean isFlag()
        {
            return this.flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public void run() {
            while ((Server.this.running) && (this.run))
            {
                try
                {

                    if ((this.jsonString != null) && (this.jsonString != "OXAA")) {
                        OutputStream os = this.s.getOutputStream();
                        PrintWriter pw = new PrintWriter(os);
                        pw.write(this.jsonString);
                        pw.flush();
                        os.flush();

                    }

                    if (this.flag) {
                        return;
                    }
                    this.jsonString = null;
                    InputStream in = this.s.getInputStream();
                    byte[] buffer = new byte[1024];
                    if (in.available() > 0) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        int length = 0;
                        try {
                            length = in.read(buffer);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        byte[] value = new byte[length];
                        System.arraycopy(buffer, 0, value, 0, length);
                        this.jsonString = new String(value, "UTF-8");
                        if(!this.jsonString.contains("0XAA")) {
                            HttpClient httpclient = new DefaultHttpClient();
                            RecordClient client = (RecordClient)UserChatController.getBean(this.jsonString, RecordClient.class);
                             HttpGet httpgets = new HttpGet("http://127.0.0.1:8080/UserChat/Socket?userToken=" + client.getUserToken()+"&houseToken="+client.getHouseToken()+"&content="+client.getContent()+"&type="+client.getType()+"&action="+client.getAction());
                            HttpResponse response = httpclient.execute(httpgets);
                        }
                        try {
                            List<Server> servers = (List)SystemController.values.get(Server.this.getHouseToken());
                            for (Server server : servers) {
                                System.err.println(server.getHouseToken());
                                System.err.println(server.port);
                                if (server.port != Server.this.port) {
                                    SocketAction action = server.new SocketAction(server.s);
                                    if (action == this) {
                                        break;
                                    }
                                    action.setJsonString(this.jsonString);
                                    action.setFlag(true);
                                    action.run();
                                }
                            }
                            this.lastReceiveTime = System.currentTimeMillis();

                        } catch (Exception e) {
                            e.printStackTrace();
                         }
                        this.lastReceiveTime = System.currentTimeMillis();
                        System.out.println("接收：\t" + this.jsonString);
                    } else {
                        Thread.sleep(10L);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    overThis();
                }
            }
        }

        private void overThis()
        {
            if (this.run) this.run = false;
            if (this.s != null) {
                try {
                    this.s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("关闭：" + this.s.getRemoteSocketAddress());
        }
    }

    class ConnWatchDog
            implements Runnable
    {
        ConnWatchDog()
        {
        }

        public void run()
        {
            try
            {
                ServerSocket ss = new ServerSocket(Server.this.port, 5);
                while (Server.this.running) {
                    Server.this.s = ss.accept();
                    new Thread(new SocketAction(Server.this.s)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Server.this.stop();
            }
        }
    }

    public static final class DefaultObjectAction
            implements ObjectAction
    {
        public Object doAction(Object rev)
        {
            System.out.println("处理并返回：" + rev);
            return rev;
        }
    }

    public static abstract interface ObjectAction
    {
        public abstract Object doAction(Object paramObject);
    }

    public void loopServer(String houseToken,String jsonString){
        List<Server> servers = (List)SystemController.values.get(houseToken);
        try{
            if(servers!=null) {
               for (Server server : servers)
                    if (server.port != Server.this.port) {
                        SocketAction action = server.new SocketAction(server.s);
                        action.setJsonString(jsonString);
                        action.setFlag(true);
                        action.run();
                    }
            }
    } catch (Exception e) {
     e.printStackTrace();
}
    }

}