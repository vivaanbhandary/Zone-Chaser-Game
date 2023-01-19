package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;

// player is the controllable character that captures zones and avoids projectiles and enemies

public class Player {

    private Position playerPos;
    private final ArrayList<Color> gradient = new ArrayList<>();
    private Color defaultColor = new Color(255, 0, 0);
    private int score;
    private int speed;
    public static final int playerLength = 20;

    // MODIFIES: this
    // EFFECTS: creates Player with the starting x and y coordinate, and sets up the gradient colors
    public Player() {
        int startingX = 100;
        int startingY = 100;
        playerPos = new Position(startingX, startingY);
        setColors();
        score = 0;
        speed = 15;
    }

    // MODIFIES: this
    // EFFECTS: changes Player's Position's x and y coordinate by the given amount if within the scope of the screen
    public void move(int x, int y, Game game) {

        playerPos.move(x, y, game, this);

    }

    // MODIFIES: this
    // EFFECTS: adds all the colors to gradient that the player cycles through when capturing a zone
    private void setColors() {
        gradient.add(new Color(255, 0, 0));
        gradient.add(new Color(255, 154, 0));
        gradient.add(new Color(208, 222, 33));
        gradient.add(new Color(79, 220, 74));
        gradient.add(new Color(63, 218, 216));
        gradient.add(new Color(47, 201, 226));
        gradient.add(new Color(28, 127, 238));
        gradient.add(new Color(95, 21, 242));
        gradient.add(new Color(186, 12, 248));
        gradient.add(new Color(251, 7, 217));
    }

    // EFFECTS: returns the player as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("playerPos", playerPos.toJson());
        json.put("score", score);
        json.put("speed", speed);
        return json;
    }

    // REQUIRES: score >= 0
    // MODIFIES: this
    // EFFECTS: adds score Player's Score
    public void addScore(int score) {
        this.score += score;
    }

    // EFFECTS: returns Player's Score
    public int getScore() {
        return score;
    }

    // EFFECTS: returns Player's Position
    public Position getPlayerPos() {
        return playerPos;
    }

    // EFFECTS: returns list of colors that the player cycles through when capturing a zone
    public ArrayList<Color> getGradient() {
        return gradient;
    }

    // EFFECTS: returns default color of player when not capturing
    public Color getDefaultColor() {
        return defaultColor;
    }

    //REQUIRES: A valid color
    // EFFECTS: changes default color of the player
    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPlayerPos(Position playerPos) {
        this.playerPos = playerPos;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPlayerLength() {
        return playerLength;
    }
}
