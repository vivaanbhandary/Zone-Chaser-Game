package model;

import org.json.JSONObject;

import java.util.Objects;

// a position is the location of an element on the screen

public class Position {

    private int x1;
    private int y1;
    private int zoneLength = CaptureZone.zoneLength;
    private int playerLength = Player.playerLength;

    // REQUIRES: x and y within scope of the screen
    // MODIFIES: this
    // EFFECTS: creates Position with a given x and y coordinate
    public Position(int x, int y) {
        this.x1 = x;
        this.y1 = y;
    }

    // EFFECTS: returns X value of position
    public int getX() {
        return x1;
    }

    // EFFECTS: returns Y value of position
    public int getY() {
        return y1;
    }

    // MODIFIES: this
    // EFFECTS: changes Position's x and y coordinate by the given amount if within the scope of the screen
    public void move(int x, int y, Game game, Player player) {
        if (0 <= this.x1 + x && this.x1 + x <= game.getMaxX() - playerLength) {
            this.x1 += x;
        }
        if (0 <= this.y1 + y && this.y1 + y <= game.getMaxY() - playerLength) {
            this.y1 += y;
        }
    }

    // EFFECTS: Returns the position as a json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", x1);
        json.put("y", y1);
        return json;
    }

    // REQUIRES: A player and a CaptureZone
    // EFFECTS: Returns true if the player and capture zone share any 1 position
    public boolean collides(Player player, CaptureZone zone) {
        int playerXStart = player.getPlayerPos().getX();
        int playerXEnd = playerXStart + playerLength;
        int playerYStart = player.getPlayerPos().getY();
        int playerYEnd = playerYStart + playerLength;

        int zoneXStart = zone.getTopLeftZonePos().getX();
        int zoneXEnd = zoneXStart + zoneLength;
        int zoneYStart = zone.getTopLeftZonePos().getY();
        int zoneYEnd = zoneYStart + zoneLength;

        boolean correctXPos = ((zoneXStart <= playerXStart) && (playerXStart <= zoneXEnd))
                            || ((zoneXStart <= playerXEnd) && (playerXEnd <= zoneXEnd));

        boolean correctYPos = ((zoneYStart <= playerYStart) && (playerYStart <= zoneYEnd))
                || ((zoneYStart <= playerYEnd) && (playerYEnd <= zoneYEnd));
        return correctXPos && correctYPos;
    }
}
