package org.mdev.revolution.communication.packets;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketEvent {
    short number();
    boolean enabled() default true;
}