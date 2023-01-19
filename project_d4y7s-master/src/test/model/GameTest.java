package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    Game game;

    @BeforeEach
    void setup() {
        game = new Game(20, 20);
        Enemy enemy = new Enemy();
        Projectile proj = new Projectile();
    }

    @Test
    void gameTest() {
        assertEquals(20, game.getMaxX());
        assertEquals(20, game.getMaxY());
    }

    @Test
    void addTimeCapturing() {
        CaptureZone zone1 = new CaptureZone(5, 5);
        CaptureZone zone2 = new CaptureZone(10, 10);
        zone1.setCapturing(false);
        zone2.setCapturing(true);

        zone1.addSecondTimeBeingCaptured();
        zone2.addSecondTimeBeingCaptured();

        assertEquals(1, zone1.getTimeBeingCaptured());
        assertEquals(1, zone2.getTimeBeingCaptured());

        game.getZones().add(zone1);
        game.getZones().add(zone2);

        game.addTimeCapturing();

        assertEquals(0, zone1.getTimeBeingCaptured());
        assertEquals(2, zone2.getTimeBeingCaptured());
    }

    @Test
    void rainbowCheckTest() {
        CaptureZone zone1 = new CaptureZone(5, 5);
        CaptureZone zone2 = new CaptureZone(10, 10);

        zone1.setCapturing(false);
        zone2.setCapturing(true);

        game.getZones().add(zone1);

        game.rainbowCheck();
        //assertEquals(TextColor.ANSI.RED, game.getPlayer().getDefaultColor());

        game.getZones().add(zone2);

        game.rainbowCheck();
        assertEquals(game.getPlayer().getGradient().get(0), game.getPlayer().getDefaultColor());

    }


    @Test
    void resetGradTest() {
       assertEquals(0, game.getGradientCounter());
       game.resetGrad();
       assertEquals(0, game.getGradientCounter());
       game.setGradientCounter(15);

        assertEquals(15, game.getGradientCounter());
        game.resetGrad();
        assertEquals(0, game.getGradientCounter());

    }

    @Test
    void collisionsTest1() {
        CaptureZone zone1 = new CaptureZone(game.getPlayer().getPlayerPos().getX(),
                game.getPlayer().getPlayerPos().getY());
        game.getZones().add(zone1);
        assertFalse(zone1.isCapturing());
        game.collisions();
        assertTrue(zone1.isCapturing());

    }

    @Test
    void collisionsTest2() {
        CaptureZone zone1 = new CaptureZone(game.getPlayer().getPlayerPos().getX() - 19,
                game.getPlayer().getPlayerPos().getY());
        game.getZones().add(zone1);
        assertFalse(zone1.isCapturing());
        game.collisions();
        assertTrue(zone1.isCapturing());

    }

    @Test
    void collisionsTest3() {
        CaptureZone zone1 = new CaptureZone(game.getPlayer().getPlayerPos().getX() - 20,
                game.getPlayer().getPlayerPos().getY());
        game.getZones().add(zone1);
        assertFalse(zone1.isCapturing());
        game.collisions();
        assertTrue(zone1.isCapturing());

    }

    @Test
    void collisionsTest4() {
        CaptureZone zone1 = new CaptureZone(game.getPlayer().getPlayerPos().getX(),
                game.getPlayer().getPlayerPos().getY() + 19);
        game.getZones().add(zone1);
        assertFalse(zone1.isCapturing());
        game.collisions();
        assertTrue(zone1.isCapturing());

    }

    @Test
    void collisionsTest5() {
        CaptureZone zone1 = new CaptureZone(game.getPlayer().getPlayerPos().getX(),
                game.getPlayer().getPlayerPos().getY() + 20);
        game.getZones().add(zone1);
        assertFalse(zone1.isCapturing());
        game.collisions();
        assertTrue(zone1.isCapturing());

    }

    @Test
    void collisionsTest6() {
        CaptureZone zone1 = new CaptureZone(game.getPlayer().getPlayerPos().getX() + 17,
                game.getPlayer().getPlayerPos().getY());
        game.getZones().add(zone1);
        assertFalse(zone1.isCapturing());
        game.collisions();
        assertTrue(zone1.isCapturing());

    }

    @Test
    void collisionsTest7() {
        CaptureZone zone1 = new CaptureZone(game.getPlayer().getPlayerPos().getX(),
                game.getPlayer().getPlayerPos().getY() + 19);
        game.getZones().add(zone1);
        assertFalse(zone1.isCapturing());
        game.collisions();
        assertTrue(zone1.isCapturing());

    }

    @Test
    void collisionsTest8() {
        CaptureZone zone1 = new CaptureZone(game.getPlayer().getPlayerPos().getX() - 19,
                game.getPlayer().getPlayerPos().getY() - 19);
        game.getZones().add(zone1);
        assertFalse(zone1.isCapturing());
        game.collisions();
        assertTrue(zone1.isCapturing());

    }

    @Test
    void collisionsTest9() {
        CaptureZone zone1 = new CaptureZone(game.getPlayer().getPlayerPos().getX(),
                game.getPlayer().getPlayerPos().getY());
        game.getZones().add(zone1);
        assertFalse(zone1.isCapturing());
        game.collisions();
        assertTrue(zone1.isCapturing());

    }

    @Test
    void collisionsTestFalse() {
        CaptureZone zone1 = new CaptureZone(18, 8);
        game.getZones().add(zone1);
        assertFalse(zone1.isCapturing());
        game.collisions();
        assertFalse(zone1.isCapturing());

    }

    @Test
    void spawnZoneTest() {
        assertEquals(0, game.getZones().size());
        game.spawnZone();
        assertEquals(1, game.getZones().size());
    }

    @Test
    void getTicksPerSecondTest() {
        assertEquals(45, game.getTicksPerSecond());
    }

    @Test
    void endTest() {
        assertFalse(game.isEnded());
        game.end();
        assertTrue(game.isEnded());
    }

    @Test
    void resetTickNumTest() {
        assertEquals(0, game.getTickNum());
        assertEquals(0, game.getSecondsSinceStart());
        assertFalse(game.isZonedThisSecond());

        game.resetTickNum();

        assertEquals(0, game.getTickNum());
        assertEquals(1, game.getSecondsSinceStart());
        assertFalse(game.isZonedThisSecond());
    }

    @Test
    void tickTest() {
        assertEquals(0, game.getTickNum());
        assertFalse(game.isZonedThisSecond());
        assertEquals(0, game.getGradientCounter());
        assertEquals(0, game.getZones().size());

        game.tick();

        assertEquals(1, game.getTickNum());
        assertTrue(game.isZonedThisSecond());
        assertEquals(0, game.getGradientCounter());
        assertEquals(1, game.getZones().size());

        game.tick();
        game.tick();
        game.tick();

        assertEquals(4, game.getTickNum());
        assertTrue(game.isZonedThisSecond());
        assertEquals(1, game.getGradientCounter());
        assertEquals(1, game.getZones().size());

        for (int i = 0; i < 56; i++) {
            game.tick();
        }

        assertEquals(15, game.getTickNum());
        assertFalse(game.isZonedThisSecond());
        assertEquals(1, game.getSecondsSinceStart());
        assertEquals(1, game.getZones().size());

        game.tick();

        assertEquals(16, game.getTickNum());
        assertFalse(game.isZonedThisSecond());
        assertEquals(1, game.getSecondsSinceStart());
        assertEquals(1, game.getZones().size());



        assertEquals(false, game.fullZones());
        for (int i = 0; i < game.getSecondsPerZone()*8*game.getTicksPerSecond(); i++) {

            game.tick();
        }
        assertEquals(true, game.fullZones());
    }

    @Test
    void toJsonTest() {
        CaptureZone zone = new CaptureZone(15, 12);
        game.getZones().add(zone);
        Player player = new Player();
        game.setPlayer(player);
        game.setTickNum(15);
        game.setZonedThisSecond(false);
        game.setSecondsSinceStart(1);


        JSONObject json = new JSONObject();
        json.put("zone", game.getZones());
        json.put("player", game.getPlayer().toJson());

        JSONObject obj = game.toJson();
        JSONArray zoneObj = obj.getJSONArray("zones");
        JSONObject playObj = (JSONObject) obj.getJSONArray("player").get(0);

        assertEquals(game.getTickNum(), obj.getInt("tickNum"));
        assertEquals(game.getSecondsSinceStart(), obj.getInt("secondSinceStart"));
        assertEquals(game.isZonedThisSecond(), obj.getBoolean("zonedThisSecond"));
        assertEquals(game.getGradientCounter(), obj.getInt("gradientCounter"));
        assertEquals(game.getMaxX(), obj.getInt("maxX"));
        assertEquals(game.getMaxY(), obj.getInt("maxY"));

        assertEquals(game.getLevel(), obj.getInt("level"));
        assertEquals(game.getLevelScore(), obj.getInt("levelScore"));
        assertEquals(game.getScoreMultiplier(), obj.getInt("scoreMultiplier"));
        assertEquals(game.getScoreBarVal(), obj.getInt("scoreBarVal"));

        assertEquals(json.getJSONArray("zone").toString(), zoneObj.toString());

        assertEquals(json.getJSONObject("player").toString(), playObj.toString());

    }


}
