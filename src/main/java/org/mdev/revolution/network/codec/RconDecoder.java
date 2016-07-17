package org.mdev.revolution.network.codec;

import com.netflix.governator.guice.lazy.LazySingleton;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

@ChannelHandler.Sharable
@LazySingleton
public class RconDecoder extends MessageToMessageDecoder<ByteBuf> {
    private static final Logger logger = LogManager.getLogger(PacketDecoder.class);

    @Inject
    public RconDecoder() {}

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        logger.info("Incoming RCON server data: {0}", buffer);
    }
}
