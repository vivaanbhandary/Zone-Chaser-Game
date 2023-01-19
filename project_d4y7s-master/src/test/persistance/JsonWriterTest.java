package persistance;

import model.Game;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Game game = new Game(14, 15);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Game game = new Game(12, 15);
            JsonWriter writer = new JsonWriter("./data/PersistenceTest.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/PersistenceTest.json");
            Game test = reader.read();

            assertEquals(game.getZones().toString(), test.getZones().toString());
            assertEquals(game.getPlayer().getPlayerPos().getX(), test.getPlayer().getPlayerPos().getX());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
