package model;

import org.json.JSONObject;

import java.awt.*;

//Capture Zone is a location on the screen that can be captured by the player for points
public class CaptureZone {

    private Position topLeftZonePos;
    private Position topMidZonePos;
    private Position topRightZonePos;
    private Position midLeftZonePos;
    private Position midMidZonePos;
    private Position midRightZonePos;
    private Position botLeftZonePos;
    private Position botMidZonePos;
    private Position botRightZonePos;
    private Position textPos;
    private Color colorZoneUncap = new Color(0,0,255);
    private Color colorZoneCap = new Color(255, 255, 0);
    private boolean capturing;
    private int timeBeingCaptured;
    private final int timeNeededToCapture = 1;
    public static final int secondsPerZone = 2;
    private int ticksSinceCaptured;
    public static final int zoneLength = 40;

    //REQUIRES: x and y value for position of top left part of capture zone
    //MODIFIES: this
    //EFFECTS: constructs a CaptureZone with 9 parts that each have a position on the screen region
    public CaptureZone(int x, int y) {
        EventLog.getInstance().logEvent(new Event("CaptureZone added to game"));
        topLeftZonePos = new Position(x, y);
        topMidZonePos = new Position((x + 1), y);
        topRightZonePos = new Position((x + 2), y);
        midLeftZonePos = new Position(x, (y + 1));
        midMidZonePos = new Position((x + 1),(y + 1));
        midRightZonePos = new Position((x + 2), (y + 1));
        botLeftZonePos = new Position(x, (y + 2));
        botMidZonePos = new Position((x + 1), (y + 2));
        botRightZonePos = new Position((x + 2), (y + 2));
        textPos = new Position((x), (y + 3));
        capturing = false;
        timeBeingCaptured = 0;
        ticksSinceCaptured = 0;
    }

    //EFFECTS: returns position for topleft part of CaptureZone
    public Position getTopLeftZonePos() {
        return topLeftZonePos;
    }

    //EFFECTS: returns position for topmid part of CaptureZone
    public Position getTopMidZonePos() {
        return topMidZonePos;
    }

    //EFFECTS: returns position for topright part of CaptureZone
    public Position getTopRightZonePos() {
        return topRightZonePos;
    }

    //EFFECTS: returns position for midleft part of CaptureZone
    public Position getMidLeftZonePos() {
        return midLeftZonePos;
    }

    //EFFECTS: returns position for midmid part of CaptureZone
    public Position getMidMidZonePos() {
        return midMidZonePos;
    }

    //EFFECTS: returns position for midright part of CaptureZone
    public Position getMidRightZonePos() {
        return midRightZonePos;
    }

    //EFFECTS: returns position for botleft part of CaptureZone
    public Position getBotLeftZonePos() {
        return botLeftZonePos;
    }

    //EFFECTS: returns position for botmid part of CaptureZone
    public Position getBotMidZonePos() {
        return botMidZonePos;
    }

    //EFFECTS: returns position for botright part of CaptureZone
    public Position getBotRightZonePos() {
        return botRightZonePos;
    }

    //EFFECTS: returns position for text part of CaptureZone
    public Position getTextPos() {
        return textPos;
    }

    //EFFECTS: returns color of zone depending on if it is being captured or not
    public Color getColorZone() {
        if (!capturing && !isCaptured()) {
            return colorZoneUncap;
        } else {
            return  colorZoneCap;
        }
    }

    public void logEventCaptured() {
        EventLog.getInstance().logEvent(new Event("CaptureZone captured and removed from game"));
    }

    //REQUIRES: boolean value
    //EFFECTS: sets the value for capturing
    public void setCapturing(boolean capturing) {
        this.capturing = capturing;
    }

    //EFFECTS: returns boolean if zone is being captured currently
    public boolean isCapturing() {
        return capturing;
    }

    //EFFECTS: returns boolean if zone has been captured
    public boolean isCaptured() {
        return (timeBeingCaptured >= timeNeededToCapture);
    }

    //EFFECTS: returns time the player has been capturing this zone for
    public int getTimeBeingCaptured() {
        return timeBeingCaptured;
    }

    //MODIFIES: this
    //EFFECTS: adds 1 to timeBeingCaptured
    public void addSecondTimeBeingCaptured() {
        this.timeBeingCaptured += 1;
    }

    //MODIFIES: this
    //EFFECTS: adds 1 to ticksSinceCaptured
    public void addTickSinceCaptured() {
        this.ticksSinceCaptured += 1;
    }

    //EFFECTS: returns ticks since zone has been captured
    public int getTicksSinceCaptured() {
        return ticksSinceCaptured;
    }

    //MODIFIES: this
    //EFFECTS: resets timeBeingCaptured to 0
    public void resetTimeBeingCaptured() {
        timeBeingCaptured = 0;
    }

    //EFFECTS: returns zone length
    public int getZoneLength() {
        return zoneLength;
    }
}
