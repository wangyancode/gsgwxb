package com.jsdc.gsgwxb.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


// WebSocket处理器类
@Component
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    public static CopyOnWriteArrayList<Channel> channels = new CopyOnWriteArrayList<>();

    public static Map<Integer,Channel> userChannels = new HashMap<>();

    // 处理接收到的文本消息
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 获取接收到的消息
        String receivedMessage = msg.text();
        // 在这里处理接收到的消息，例如记录日志、处理业务逻辑等
        // 这里只简单打印出接收到的消息
//        System.out.println("Received message: " + receivedMessage);
        JSONObject jsonObject= JSON.parseObject(receivedMessage);
        userChannels.put(Integer.parseInt(jsonObject.getString("userId")),ctx.channel());
        // 假设有一个服务类（Service）来处理这个消息，比如将消息存储到数据库或者其他操作
        // service.handleMessage(receivedMessage);

        // 回复客户端收到消息的确认
        ctx.writeAndFlush(new TextWebSocketFrame("Received: " + receivedMessage));
    }

    // 处理WebSocket连接建立事件
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 在这里可以做一些连接建立的处理
        channels.add(ctx.channel());
//        System.out.println("WebSocket Client connected success " + ctx.channel());
    }

    // 处理WebSocket连接断开事件
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx){
        // 在这里可以做一些连接断开的处理
        try{
            channels.remove(ctx.channel());
        }catch (Exception e){
            e.printStackTrace();
        }
//        System.out.println("WebSocket Client disconnected: " + ctx.channel());
    }

    // 异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 发生异常时进行处理
//        cause.printStackTrace();
        ctx.close();
    }
}

