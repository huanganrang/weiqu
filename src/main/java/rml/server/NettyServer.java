package rml.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import rml.handler.*;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple to Introduction
 * author:Jianghui
 * date:2016/7/14 11:09
 */
public class NettyServer {
    private ServerBootstrap serverBootstrap;
    private static EventLoopGroup bossGroup = new NioEventLoopGroup();
    private static EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Integer port;
    private ChannelFuture future;
    private int timeout;
    private String host;
    public NettyServer() {
        init();
    }

    public NettyServer(String host, int port, int timeout) {

        this.host=host;
        this.port=port;
        this.timeout=timeout;
        init();
    }


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public NettyServer(Integer port) {

        this.port = port;
        init();
    }


    public void init(){
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(
                                new StringDecoder(Charset.forName("utf-8")));
                        ch.pipeline().addLast(new StringEncoder(Charset.forName("utf-8")));
                        ch.pipeline().addLast("readTimeOutHandler",
                                new ReadTimeoutHandler(60));//读
                        ch.pipeline().addLast("loginHandler",new LoginAuthRespHandler());
                        ch.pipeline().addLast("HeartBeatRespHandler",
                                new HeartBeatRespHandler());
                        ch.pipeline().addLast(new AudioRespHandler());
                        ch.pipeline().addLast(new VideoRespHandler());
                        ch.pipeline().addLast(new ReqAVRespHandler());
                    }
                });
    }

    public void bind() throws Exception {
        //绑定端口，同步等待成功
        future= serverBootstrap.bind(this.host,this.port).sync();
    }
    public void close(){
        future.channel().close();
    }

    public boolean isConnected(){
        return future.channel().isActive();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUrl() {
        return future.channel().localAddress().toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass()== NettyServer.class && this.getUrl().equals(((NettyServer)obj).getUrl()) ;
    }
}
