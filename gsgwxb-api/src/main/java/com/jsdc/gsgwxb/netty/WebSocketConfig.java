package com.jsdc.gsgwxb.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Configuration
public class WebSocketConfig {

    EventLoopGroup bossGroup;
    //负责处理已经建立连接的客户端的读写请求
    EventLoopGroup workerGroup;

    @Value("${netty.isEnable}")
    private boolean isEnable;
    @PreDestroy
    public void stopServer() {
        bossGroup.shutdownGracefully().syncUninterruptibly();
        workerGroup.shutdownGracefully().syncUninterruptibly();
    }

    // 在应用启动时启动Netty服务器
    @PostConstruct
    public void startNettyServer() {
        if (!isEnable){
            return;
        }
        //负责接收客户端的连接请求
        bossGroup = new NioEventLoopGroup(3);
        //负责处理已经建立连接的客户端的读写请求
        workerGroup = new NioEventLoopGroup();
        //创建服务驱动
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        // Http编解码器
                        pipeline.addLast(new HttpServerCodec());
                        // 写文件内容
                        pipeline.addLast(new ChunkedWriteHandler());
                        // 聚合解码HttpRequest/HttpContent/LastHttpContent到FullHttpRequest
                        //http数据在传输过程中是分段的，可以多个段聚合
                        pipeline.addLast(new HttpObjectAggregator(8192));
                        // 处理WebSocket升级握手、Ping、Pong等消息
                        pipeline.addLast(new WebSocketServerProtocolHandler("/websocket"));
                        // 自定义WebSocket处理器
                        pipeline.addLast(new WebSocketHandler());
                    }
                });
                //绑定端口启动netty服务
                serverBootstrap.bind(8886)
                .addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        System.out.println("Netty Server started on port 8886" );
                    } else {
                        System.err.println("Netty Server start failed on port 8886");
                    }
                });
    }
}

