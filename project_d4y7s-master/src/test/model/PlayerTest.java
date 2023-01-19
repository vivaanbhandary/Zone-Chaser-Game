package model;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    Player player;
    Game game;

    @BeforeEach
    void setUp() {
        player = new Player();
        game = new Game(60, 60);
    }

    @Test
    void playerTest() {
        assertEquals(100, player.getPlayerPos().getX());
        assertEquals(100, player.getPlayerPos().getY());
        assertEquals(new Color(208, 222, 33), player.getGradient().get(2));
        assertEquals(20, player.getPlayerLength());
    }

    @Test
    void moveTest() {
        player.move(10,10,game);
        assertEquals(100, player.getPlayerPos().getX());
        assertEquals(100, player.getPlayerPos().getY());
    }

    @Test
    void addScoretest() {
        assertEquals(0, player.getScore());
        player.addScore(15);
        assertEquals(15, player.getScore());
    }

    @Test
    void toJsonTest() {
        player.setScore(15);
        Position pos2 = new Position(15, 15);
        player.setPlayerPos(pos2);

        JSONObject obj =  player.toJson();
        JSONObject pos = obj.getJSONObject("playerPos");

        assertEquals(player.getScore(), obj.getInt("score"));
        assertEquals(player.getPlayerPos().getX(), pos.getInt("x"));
        assertEquals(player.getPlayerPos().getY(), pos.getInt("y"));
        assertEquals(player.getSpeed(), 15);
    }
}
