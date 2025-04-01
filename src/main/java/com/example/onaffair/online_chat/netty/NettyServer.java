package com.example.onaffair.online_chat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.cors.CorsConfig;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;
import io.netty.handler.codec.http.cors.CorsHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;


public class NettyServer {

    private final int port;

    public static final String IP = "10.239.148.35";
    public static final String WEBSOCKET_PATH = "/ws";

    private EventLoopGroup bossGroup, workerGroup;
    ChannelFuture channelFuture;

    private SubprotocolTokenHandler subprotocolTokenHandler;
    private WebSocketChatServerHandler webSocketChatServerHandler;

    public NettyServer(int port, SubprotocolTokenHandler subprotocolTokenHandler, WebSocketChatServerHandler webSocketChatServerHandler) {
        this.port = port;
        this.subprotocolTokenHandler = subprotocolTokenHandler;
        this.webSocketChatServerHandler = webSocketChatServerHandler;
    }

    public void start() throws Exception{
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup(8);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            //跨域
                            CorsConfig corsConfig = CorsConfigBuilder.forAnyOrigin().build();
                            pipeline.addLast(new CorsHandler(corsConfig));


                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new HttpObjectAggregator(65536));

                            pipeline.addLast(subprotocolTokenHandler);

                            pipeline.addLast(new WebSocketServerProtocolHandler(
                                    WEBSOCKET_PATH,
                                    "auth", // 声明支持的子协议（需与客户端一致）
                                    true,
                                    65535
                            ));

                            pipeline.addLast(webSocketChatServerHandler);
                        }
                    });
            System.out.println("WebSocket server is ready at ws://" +IP +  ":" + port + WEBSOCKET_PATH);
            channelFuture = bootstrap.bind(port).sync();
        }finally {

        }
    }

    public void stop() throws InterruptedException {
        if (channelFuture != null){
            channelFuture.channel().closeFuture();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }
}
