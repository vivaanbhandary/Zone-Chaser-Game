package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    Position pos1;
    Position pos2;
    Position pos3;
    Position pos4;
    int x1 = 5;
    int y1 = 5;
    Game game;
    Player player = new Player();
    CaptureZone zone = new CaptureZone(200, 200);
    CaptureZone zone2 = new CaptureZone(100, 100);

    @BeforeEach
    void setUp() {
        pos1 = new Position(x1, y1);
        pos2 = new Position(x1, y1+5);
        pos3 = new Position(x1+5, y1);
        pos4 = new Position(x1, y1);
        game = new Game(30, 30);
    }

    @Test
    void positionTest() {
        assertEquals(x1, pos1.getX());
        assertEquals(y1, pos1.getY());
    }

    @Test
    void moveTestXPos() {
        assertEquals(x1, pos1.getX());
        pos1.move(10, 0, game, player);
        assertEquals((10 - x1), pos1.getX());
    }

    @Test
    void moveTestXNeg() {
        assertEquals(x1, pos1.getX());
        pos1.move(-2, 0, game, player);
        assertEquals((-2 + x1), pos1.getX());
    }

    @Test
    void moveTestXMax() {
        assertEquals(5, pos1.getX());
        pos1.move(25, 0, game, player);
        assertEquals(5, pos1.getX());
    }

    @Test
    void moveTestXMin() {
        assertEquals(x1, pos1.getX());
        pos1.move(-5, 0, game, player);
        assertEquals((-5 + x1), pos1.getX());
    }

    @Test
    void moveTestXMaxExceed() {
        assertEquals(x1, pos1.getX());
        pos1.move(30, 0, game, player);
        assertEquals(x1, pos1.getX());
    }

    @Test
    void moveTestXMinExceed() {
        assertEquals(x1, pos1.getX());
        pos1.move(-20, 0, game, player);
        assertEquals(x1, pos1.getX());
    }

    @Test
    void moveTestYPos() {
        assertEquals(y1, pos1.getY());
        pos1.move(0, 10, game, player);
        assertEquals(5, pos1.getY());
    }

    @Test
    void moveTestYNeg() {
        assertEquals(y1, pos1.getY());
        pos1.move(0, -2, game, player);
        assertEquals((-2 + y1), pos1.getY());
    }

    @Test
    void moveTestYMax() {
        assertEquals(y1, pos1.getY());
        pos1.move(0, 25, game, player);
        assertEquals(5, pos1.getY());
    }

    @Test
    void moveTestYMin() {
        assertEquals(y1, pos1.getY());
        pos1.move(0, -5, game, player);
        assertEquals((-5 + y1), pos1.getY());
    }

    @Test
    void moveTestYMaxExceed() {
        assertEquals(y1, pos1.getY());
        pos1.move(0, 30, game, player);
        assertEquals(y1, pos1.getY());
    }

    @Test
    void moveTestYMinExceed() {
        assertEquals(y1, pos1.getY());
        pos1.move(0, -20, game, player);
        assertEquals(y1, pos1.getY());
    }

    @Test
    void collidesTestFalse() {
        assertFalse(pos1.collides(player, zone));
    }

    @Test
    void collidesTestTrue() {
        assertTrue(pos1.collides(player, zone2));
    }

    @Test
    void collidesTestAll() {
        CaptureZone z1 = new CaptureZone(50, 100);
        CaptureZone z2 = new CaptureZone(50, 50);
        CaptureZone z3 = new CaptureZone(100, 50);
        CaptureZone z4 = new CaptureZone(100, 100);

        assertTrue(pos1.collides(player, z4));
        assertFalse(pos1.collides(player, z1));
        assertFalse(pos1.collides(player, z2));
        assertFalse(pos1.collides(player, z3));
    }

    @Test
    void toJsonTest() {
        JSONObject obj =  pos1.toJson();
        assertEquals(pos1.getX(), obj.getInt("x"));
        assertEquals(pos1.getY(), obj.getInt("y"));
    }
}
