package model;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class CaptureZoneTest {
    CaptureZone zone;

    @BeforeEach
    void setUp() {
        zone = new CaptureZone(5, 5);
    }

    @Test
    void captureZoneTest() {
        assertEquals(5, zone.getTopLeftZonePos().getX());
        assertEquals(5, zone.getTopLeftZonePos().getY());

        assertEquals(6, zone.getTopMidZonePos().getX());
        assertEquals(5, zone.getTopMidZonePos().getY());

        assertEquals(7, zone.getTopRightZonePos().getX());
        assertEquals(5, zone.getTopRightZonePos().getY());

        assertEquals(5, zone.getMidLeftZonePos().getX());
        assertEquals(6, zone.getMidLeftZonePos().getY());

        assertEquals(6, zone.getMidMidZonePos().getX());
        assertEquals(6, zone.getMidMidZonePos().getY());

        assertEquals(7, zone.getMidRightZonePos().getX());
        assertEquals(6, zone.getMidRightZonePos().getY());

        assertEquals(5, zone.getBotLeftZonePos().getX());
        assertEquals(7, zone.getBotLeftZonePos().getY());

        assertEquals(6, zone.getBotMidZonePos().getX());
        assertEquals(7, zone.getBotMidZonePos().getY());

        assertEquals(7, zone.getBotRightZonePos().getX());
        assertEquals(7, zone.getBotRightZonePos().getY());

        assertEquals(5, zone.getTextPos().getX());
        assertEquals(8, zone.getTextPos().getY());

        assertFalse(zone.isCapturing());

    }

    @Test
    void getColorZoneTest() {
        assertEquals(Color.blue, zone.getColorZone());
        zone.setCapturing(true);

        assertEquals(Color.YELLOW, zone.getColorZone());
        zone.addSecondTimeBeingCaptured();
        assertEquals(Color.YELLOW, zone.getColorZone());
        zone.setCapturing(false);
        assertEquals(Color.YELLOW, zone.getColorZone());
    }

    @Test
    void addSecondTimeBeingCapturedTest() {
        assertEquals(0, zone.getTimeBeingCaptured());
        zone.addSecondTimeBeingCaptured();
        assertEquals(1, zone.getTimeBeingCaptured());
        zone.resetTimeBeingCaptured();
        assertEquals(0, zone.getTimeBeingCaptured());
    }

    @Test
    void addTickSinceCapturedTest() {
        assertEquals(0, zone.getTicksSinceCaptured());
        zone.addTickSinceCaptured();
        assertEquals(1, zone.getTicksSinceCaptured());
    }

    @Test
    void isCapturedTest() {
        assertFalse(zone.isCaptured());
        zone.addSecondTimeBeingCaptured();
        zone.addSecondTimeBeingCaptured();
        zone.addSecondTimeBeingCaptured();
        assertEquals(3, zone.getTimeBeingCaptured());
        assertTrue(zone.isCaptured());
        zone.logEventCaptured();
    }
}