package org.mdev.revolution.network.sessions;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.mdev.revolution.Revolution;

import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private final ConcurrentHashMap<Channel, Session> sessions;

    public SessionManager() {
        sessions = new ConcurrentHashMap<>();
    }

    public Session getSessionByChannel(Channel channel) {
        return sessions.get(channel);
    }

    public void broadcast(ByteBuf buf) {
        Revolution.getInstance().getServer().getChannels().writeAndFlush(buf);
    }

    public void addSession(Channel channel) {
        Session session = new Session(channel);
        if (sessions.containsKey(channel)) {
            session.disconnect();
        }
        sessions.put(channel, session);
    }

    public void removeSession(Channel channel) {
        Session session = getSessionByChannel(channel);
        sessions.remove(channel);
    }
}
