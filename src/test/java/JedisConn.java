/**
 * Created by Administrator on 2015/9/29.
 */
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.List;


public class JedisConn {
    static String constr = "10.174.139.99" ;
    public static Jedis getRedis(){
        Jedis jedis = new Jedis(constr) ;
        return jedis ;
    }
    public static void main(String[] args){
      /*  Jedis j = JedisConn. getRedis() ;
        System. out.println( j.lrange("16 125",0,-1)) ;*/
       /* EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler());
                            ch.pipeline().addLast(
                                    new StringDecoder(Charset.forName("utf-8")));
                            ch.pipeline().addLast("MessageEncoder",
                                    new StringEncoder(Charset.forName("utf-8")));
                            ch.pipeline().addLast("ReadTimeOutHandler",
                                    new ReadTimeoutHandler(180));//超时时间，单位秒
                        }
                    });
            ChannelFuture future = bootstrap.connect(
                    new InetSocketAddress("localhost", 5797)).sync();
           future.channel().writeAndFlush("{\"type\":\"login\",\"sessionid\":\"test\"}");
            System.in.read();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }*/
    }
}
