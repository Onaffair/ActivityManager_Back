package com.example.onaffair.online_chat.netty;

import com.example.onaffair.online_chat.dto.WebsocketMessage;
import com.example.onaffair.online_chat.entity.FriendMessage;
import com.example.onaffair.online_chat.entity.GroupMember;
import com.example.onaffair.online_chat.entity.GroupMessage;
import com.example.onaffair.online_chat.entity.User;
import com.example.onaffair.online_chat.service.GroupMemberService;
import com.example.onaffair.online_chat.service.GroupMessageService;
import com.example.onaffair.online_chat.util.ChannelManager;
import com.example.onaffair.online_chat.service.FriendMessageService;
import com.example.onaffair.online_chat.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;


@Component
@ChannelHandler.Sharable
public class WebSocketChatServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    public ObjectMapper objectMapper;


    public static final String KEY_FRIEND =  "friend";
    public static final String KEY_GROUP =  "group";

    @Autowired
    private UserService userService;

    @Autowired
    private FriendMessageService friendMessageService;

    @Autowired
    private GroupMessageService groupMessageService;

    @Autowired
    private GroupMemberService groupMemberService;


    @Autowired
    @Qualifier("myExecutor")
    private Executor taskExecutor;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String rawJson = msg.text();
        WebsocketMessage websocketMessage = objectMapper.readValue(rawJson,WebsocketMessage.class);
        /*好友私聊信息*/
        if (KEY_FRIEND.equals(websocketMessage.getType())){
            FriendMessage friendMessage = objectMapper.convertValue(websocketMessage.getData(), FriendMessage.class);
            friendMessage.setCreatedAt(LocalDateTime.now());
            //存储信息
            taskExecutor.execute(() ->{
                friendMessageService.addMessage(friendMessage);
            });

            String serializedMessage = objectMapper.writeValueAsString(friendMessage);
            //信息写回发送者客户端
            ctx.channel().writeAndFlush(new TextWebSocketFrame(serializedMessage));
            //转发信息
            sendMessage(ctx,serializedMessage,friendMessage.getReceiver());
        }
        /*群聊信息*/
        else if (KEY_GROUP.equals(websocketMessage.getType())){
            GroupMessage groupMessage = objectMapper.convertValue(websocketMessage.getData(), GroupMessage.class);
            groupMessage.setCreatedAt(LocalDateTime.now());

            taskExecutor.execute(() ->{
                groupMessageService.addGroupMessage(groupMessage);
            });

            //转发群聊信息
            String serializedMessage = objectMapper.writeValueAsString(groupMessage);
            List<GroupMember> groupMemberList = groupMemberService.getGroupMemberList(groupMessage.getGroupId());
            groupMemberList.forEach(groupMember -> {
                sendMessage(ctx,serializedMessage,groupMember.getUserAccount());
            });
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(ctx.channel().remoteAddress()+"连接到服务器");
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(ctx.channel().remoteAddress()+"上线了");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(ctx.channel().remoteAddress() + " 离开了\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        String account = getAccount(ctx);
//        ChannelManager.removeChannel(account,ctx.channel());
        /*用户下线*/
//        User user = userService.findByAccount(account);
//        user.setStatus("offline");
//        userService.updateUser(account,user);

//        System.out.println(ctx.channel().remoteAddress()+"下线了");
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"异常断开");
        cause.printStackTrace();

        ctx.channel().close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //在握手协议完成之后
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            //添加到map统一管理
            String account = getAccount(ctx);
            ChannelManager.addChannel(account, ctx.channel());

            /*用户上线*/
            User user = userService.findByAccount(account);
            user.setStatus("online");
            userService.updateUser(account,user);

        }
        super.userEventTriggered(ctx, evt);
    }

    private String getAccount(ChannelHandlerContext ctx){
        return ctx.channel().attr(SubprotocolTokenHandler.KEY_USER).get();
    }
    private void sendMessage(ChannelHandlerContext ctx,String msg,String account){

        //信息转发
        Set<Channel> channelsByAccount = ChannelManager.getChannelsByAccount(account);

        //用户不在线
        if (channelsByAccount.isEmpty()) return;

        channelsByAccount.forEach(channel -> {
            channel.writeAndFlush(new TextWebSocketFrame(msg));
        });
    }
}
