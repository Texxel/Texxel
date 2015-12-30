package com.github.texxel.utils;

public class GameTimer {

    private static final long startTime = System.nanoTime();
    private static long now = startTime;
    private static float diff = 0;

    /**
     * Gets the time that the last tick took in seconds.
     * @return the tick time
     */
    public static float tickTime() {
        return diff;
    }

    /**
     * Informs that another tick has passed and the tickTime should reset.
     */
    public static void update() {
        long lastTick = now;
        now = System.nanoTime();
        long diff = now - lastTick;
        assert diff >= 0;
        GameTimer.diff = Math.min( 0.1f, diff/1000000000f );
    }

    private GameTimer() {
        // static methods only. No need to construct a new instance
    }
}
