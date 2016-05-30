package org.mdev.revolution.communication.packets;

import gnu.trove.map.hash.THashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.network.sessions.Session;
import org.mdev.revolution.threading.ThreadPool;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Set;

public class PacketManager {
    private static final Logger logger = LogManager.getLogger(PacketManager.class);

    private final THashMap<Short, Method> packets;
    private final ThreadPool threadPool;

    public PacketManager() {
        packets = new THashMap<>();
        threadPool = new ThreadPool();
    }

    public void initialize() {
        if (!packets.isEmpty()) {
            throw new IllegalStateException("Cannot re-initialize the PacketManager class.");
        }

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("org.mdev.revolution.communication.packets.incoming"))
                .setScanners(new MethodAnnotationsScanner()));

        Set<Method> methods = reflections.getMethodsAnnotatedWith(PacketEvent.class);

        for (Method method : methods) {
            PacketEvent event = method.getAnnotation(PacketEvent.class);

            if (method.getReturnType().equals(Void.TYPE) && Modifier.isStatic(method.getModifiers()) && event != null && event.enabled()) {
                if (packets.containsKey(event.number())) {
                    logger.error("Packet ID collision: {}::{} with {}::{}",
                            getDeclaringClass(event.number()),
                            packets.get(event.number()).getName(),
                            method.getDeclaringClass().getSimpleName(),
                            method.getName()
                    );
                    System.exit(1); // Fatal error!
                }
                Parameter[] params = method.getParameters();
                if (params.length == 2 && params[0].getType() == Session.class && params[1].getType() == ClientPacket.class) {
                    logger.info("Registered {}::{} to id {}",
                            method.getDeclaringClass().getSimpleName(),
                            method.getName(),
                            event.number()
                    );
                    packets.put(event.number(), method);
                }
            }
        }
    }

    public void invoke(Session session, ClientPacket packet) {
        logger.info("Incoming packet: " + packet.getHeader());
        if (!packets.containsKey(packet.getHeader())) {
            logger.debug("Unhandled packet: (Unknown: " + packet.getHeader() + ")");
            return;
        }
        logger.debug("Executing packet: " + getDeclaringClass(packet.getHeader()) + " [ID " + packet.getHeader() + "]");
        threadPool.submit(new PacketEventTask(session, packet));
    }

    public String getDeclaringClass(short header) {
        if (!packets.containsKey(header))
            return "undefined";
        return packets.get(header).getDeclaringClass().getSimpleName();
    }

    public Method getPacketHandler(short header) {
        return packets.get(header);
    }

    public void dispose() {
        packets.clear();
        threadPool.dispose();
    }
}
