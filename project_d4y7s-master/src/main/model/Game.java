package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import model.CaptureZone;

// Game is the collection of all the other classes that constitute the elements of the game and controls the game

public class Game {


    private boolean ended = false;
    public final int ticksPerSecond = 45;
    public final int gradientCycleEveryXTicks = ticksPerSecond / 15;
    private Player player = new Player();
    // private final ArrayList<Projectile> listOfProj = new ArrayList<>();
    private final int maxX;
    private final int maxY;
    private int tickNum;
    private int secondsSinceStart;
    private int gradientCounter;
    private ArrayList<CaptureZone> zones = new ArrayList<>();
    private boolean zonedThisSecond;
    private final int secondsPerZone = CaptureZone.secondsPerZone;
    private final int zoneCap = 7;

    private int level = 1;
    private int scoreMultiplier = 25;
    private int levelScore = 0;
    private int scoreBarVal = 0;

    //REQUIRES: maximum x and y value for screen region
    //MODIFIES: this
    //EFFECTS: constructs a Game object and initialises the values of the fields
    public Game(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        tickNum = 0;
        secondsSinceStart = 0;
        gradientCounter = 0;
        zonedThisSecond = false;
    }

    //MODIFIES: this
    //EFFECTS: Performs 1 tick of the game by checking collisions, spawning objects and updating field values
    public void tick() {
        tickNum += 1;

        if (secondsSinceStart % secondsPerZone == 0 && (!zonedThisSecond)) { //if (secondsPerZone)s seconds have passed
            if (!fullZones()) {
                spawnZone();
            }
            zonedThisSecond = true;
        }


        collisions();
        //spawnProjectiles();
        //moveProjectiles();
        rainbowCheck();

        if (tickNum % gradientCycleEveryXTicks == 0) {
            gradientCounter += 1;
        }
        resetGrad();
        if (tickNum >= ticksPerSecond) {      // One Second has Passed
            resetTickNum();
            addTimeCapturing();
        }
    }

    //EFFECTS: adds time being captured for each zone in the game that is being captured
    public void addTimeCapturing() {
        for (CaptureZone zones1 : zones) {
            if (zones1.isCapturing()) {
                zones1.addSecondTimeBeingCaptured();
            } else {
                zones1.resetTimeBeingCaptured();
            }
        }
    }

    //EFFECTS: checks if a zone is being captured by the player
    public void rainbowCheck() {
        for (CaptureZone zones1 : zones) {
            if (zones1.isCapturing()) {
                rainbowPlayer();
            }
        }
    }

    //EFFECTS: changes the player's color to give a rainbow gradient effect
    public void rainbowPlayer() {
        player.setDefaultColor(player.getGradient().get(gradientCounter));
        //System.out.println(gradientCounter);
    }

    //EFFECTS: resets the value of gradient counter if it goes out of bounds for the array of colors
    public void resetGrad() {
        if (gradientCounter >= 10) {
            gradientCounter = 0;
        }
    }

    //MODIFIES: this
    //EFFECTS: checks if a player is at the same position as a zone and if so updates a boolean in the zone class
    public void collisions() {
        for (CaptureZone zone: zones) {
            if (player.getPlayerPos().collides(player, zone)) {
                zone.setCapturing(true);
            } else {
                zone.setCapturing(false);
                player.setDefaultColor(new Color(255, 0, 0));
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: generates coordinates and spawns a new zone with those coordinates and adds it to the game
    public void spawnZone() {
        int zoneX = (int) (Math.random() * (maxX - 50));
        int zoneY = (int) (Math.random() * (maxY - 50));
        CaptureZone zone = new CaptureZone(zoneX, zoneY);
        zones.add(zone);
    }

    //MODIFIES: this
    //EFFECTS: resets the tick number, updates the number of seconds passed, and resets the boolean for zone spawns
    public void resetTickNum() {
        tickNum = 0;
        secondsSinceStart += 1;
        zonedThisSecond = false;
    }

    // EFFECTS: returns true if zone capacity has been reached
    public boolean fullZones() {
        return (zones.size() >= zoneCap);
    }



   // public void moveProjectiles() {    }

   // public void spawnProjectiles() {    }

    // EFFECTS: returns things in this Game as a JSON array
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("player", savePlayer());
        json.put("tickNum", tickNum);
        json.put("secondSinceStart", secondsSinceStart);
        json.put("zonedThisSecond", zonedThisSecond);
        json.put("gradientCounter", gradientCounter);
        json.put("maxX", maxX);
        json.put("maxY", maxY);

        json.put("level", level);
        json.put("levelScore", levelScore);
        json.put("scoreMultiplier", scoreMultiplier);
        json.put("scoreBarVal", scoreBarVal);

        json.put("zones", zones);
        return json;
    }

    // EFFECTS: returns the player as a JSON array
    private JSONArray savePlayer() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(player.toJson());


        return jsonArray;
    }




    //EFFECTS: returns Player object
    public Player getPlayer() {
        return player;
    }

    //EFFECTS: returns isEnded boolean
    public boolean isEnded() {
        return ended;
    }

    //MODIFIES: this
    //EFFECTS: sets ended to true, effectively ending the game
    public void end() {
        ended = true;
    }

    //EFFECTS: returns Max X coordinate
    public int getMaxX() {
        return maxX;
    }

    //EFFECTS: returns Max Y coordinate
    public int getMaxY() {
        return maxY;
    }

    //EFFECTS: returns array of CaptureZones in the game
    public ArrayList<CaptureZone> getZones() {
        return zones;
    }

    //EFFECTS: returns tick per second
    public int getTicksPerSecond() {
        return ticksPerSecond;
    }

    //EFFECTS: returns gradientCounter integer
    public int getGradientCounter() {
        return gradientCounter;
    }

    //MODIFIES: this
    //EFFECTS: changes the value of gradientCounter
    public void setGradientCounter(int gradientCounter) {
        this.gradientCounter = gradientCounter;
    }

    //EFFECTS: returns tick number in this second
    public int getTickNum() {
        return tickNum;
    }

    //EFFECTS: returns seconds since game has started
    public int getSecondsSinceStart() {
        return secondsSinceStart;
    }

    //EFFECTS: returns boolean zonedThisSecond
    public boolean isZonedThisSecond() {
        return zonedThisSecond;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setZones(ArrayList<CaptureZone> zones) {
        this.zones = zones;
    }

    public void setZonedThisSecond(boolean zonedThisSecond) {
        this.zonedThisSecond = zonedThisSecond;
    }

    public void setTickNum(int tickNum) {
        this.tickNum = tickNum;
    }

    public void setSecondsSinceStart(int secondsSinceStart) {
        this.secondsSinceStart = secondsSinceStart;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScoreMultiplier() {
        return scoreMultiplier;
    }

    public void setScoreMultiplier(int scoreMultiplier) {
        this.scoreMultiplier = scoreMultiplier;
    }

    public int getLevelScore() {
        return levelScore;
    }

    public void setLevelScore(int levelScore) {
        this.levelScore = levelScore;
    }

    public int getScoreBarVal() {
        return scoreBarVal;
    }

    public void setScoreBarVal(int scoreBarVal) {
        this.scoreBarVal = scoreBarVal;
    }

    public int getSecondsPerZone() {
        return secondsPerZone;
    }
}
