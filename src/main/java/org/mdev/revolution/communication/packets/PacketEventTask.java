package org.mdev.revolution.communication.packets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mdev.revolution.Revolution;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.network.sessions.Session;

import java.lang.reflect.InvocationTargetException;

class PacketEventTask implements Runnable {
    private static final Logger logger = LogManager.getLogger(PacketEventTask.class);

    private Session session;
    private ClientPacket packet;

    PacketEventTask(Session session, ClientPacket packet) {
        this.session = session;
        this.packet = packet;
    }

    @Override
    public void run() {
        try {
            long start = System.currentTimeMillis();
            Revolution.getInstance().getPacketManager().getPacketHandler(packet.getHeader()).invoke(null, session, packet);
            long elapsed = (System.currentTimeMillis() - start);

            if (elapsed >= 750) {
                logger.trace("[" + Revolution.getInstance().getPacketManager().getDeclaringClass(packet.getHeader()) + "] Time taken to execute packet: {0}ms", elapsed);
            }
            logger.debug("[" + Revolution.getInstance().getPacketManager().getDeclaringClass(packet.getHeader()) + "] Executed Packet: " + packet.getHeader());
            cleanup();
        } catch (NullPointerException e) {
            logger.error("Could not find method associated with packet header.", e);
            e.printStackTrace();
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("An error has occurred while attempting to parse packet.", e);
            e.printStackTrace();
        }
    }

    private void cleanup() {
        session = null;
        packet = null;
        System.gc();
    }
}
