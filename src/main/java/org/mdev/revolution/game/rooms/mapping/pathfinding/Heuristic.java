package org.mdev.revolution.game.rooms.mapping.pathfinding;

final public class Heuristic {
    public static int manhattan(int dx, int dy) {
        return (dx + dy);
    }

    public static double euclidean(int dx, int dy) {
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static double octile(int dx, int dy) {
        double f = Math.sqrt(2) - 1;
        return (dx < dy) ? f * dx + dy : f * dy + dx;
    }

    public static double chebyshev(int dx, int dy) {
        return Math.max(dx, dy);
    }
}
