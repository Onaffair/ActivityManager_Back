package com.example.onaffair.online_chat.netty;

import com.example.onaffair.online_chat.util.JwtUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.AttributeKey;
import org.springframework.stereotype.Component;


@Component
@ChannelHandler.Sharable
public class SubprotocolTokenHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    public static final AttributeKey<String> KEY_TOKEN = AttributeKey.valueOf("token");
    public static final String KEY_PROTOCOL = "Sec-WebSocket-Protocol";
    public static final AttributeKey<String> KEY_USER = AttributeKey.valueOf("user");

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        HttpHeaders headers = request.headers();
        String protocol = headers.get(KEY_PROTOCOL);
        if (protocol != null && !protocol.isEmpty()){
            String[] protocols = protocol.split(",");
            String subprotocol  = protocols[0].trim(); // "auth"
            String token = protocols.length > 1 ? protocols[1].trim() : null;
            String account = JwtUtil.extractUserAccount(token);
            ctx.channel().attr(KEY_USER).set(account);

        }else{
            return;
        }
        ctx.fireChannelRead(request.retain());

    }
}
