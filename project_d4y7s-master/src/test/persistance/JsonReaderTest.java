package persistance;

import model.CaptureZone;
import model.Game;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Game game = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGame() {
        JsonReader reader = new JsonReader("./data/saveData.json");
        try {
            Game game = reader.read();
            assertFalse(game.isEnded());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGame() {
        JsonReader reader = new JsonReader("./data/saveData.json");
        try {
            Game game = reader.read();
            List<CaptureZone> zones = game.getZones();
            assertFalse(zones.isEmpty());
            game.getPlayer();
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
