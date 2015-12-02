package com.sp.game.tools;

/**
 * Created by Troy on 12/1/2015.
 */
public class GameScore {
    public static int coinsCollected = 0;
    public static int enemiesShot = 0;
    public static int enemiesStomped = 0;
    public static int totalEnemies = 0;
    public static int shotsFired = 0;
    public static int shotsHit= 0;
    public static int livesLost = 0;
    public static int livesGained = 0;
    public static int curBounceStreak = 0;
    public static int bounceStreak = 0;

    public static void reset() {
        coinsCollected = 0;
        enemiesShot = 0;
        enemiesStomped = 0;
        shotsFired = 0;
        shotsHit= 0;
        livesLost = 0;
        livesGained = 0;
        curBounceStreak = 0;
        bounceStreak = 0;
    }

    public static void handleLanding() {
        bounceStreak = (curBounceStreak > bounceStreak) ? curBounceStreak : bounceStreak;
        curBounceStreak = 0;
    }
}
