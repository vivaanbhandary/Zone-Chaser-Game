package ui;

import model.CaptureZone;
import model.Game;
import model.Player;
import model.Position;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Some parts of the code such as the usage of screen and GUI were used from the snakeConsole-Lanterna and
// JsonSerializationDemo project
// Gamestate controls the gameflow and creates objects from the model package

//TODO FIX ZONE SAVING

public class GameState {

    private Game game;
    private final int scorePerZone = 1;
    private static final String JSON_STORE = "./data/saveData.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private GameFrame frame;


    private final int width = 960;
    private final int height = 540;

    // MODIFIES: this
    // EFFECTS: Begins the game and method does not leave execution until game is complete.
    public void start() throws IOException, InterruptedException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        game = new Game(width, height);


        initializeFrame();
        frame.setVisible(true);

        //beginTicks();


    }

    // MODIFIES: this
    //EFFECTS: creates the main JFrame
    private void initializeFrame() {
        frame = new GameFrame(width, height, game); // the main frame where all the components are added
    }




    // MODIFIES: this
    // EFFECTS: Begins the game cycle. Ticks once every Game.TICKS_PER_SECOND until game has ended
    // and the endGui has been exited.
    /*private void beginTicks() throws IOException, InterruptedException {
        while (!game.isEnded() || endGui.getActiveWindow() != null) {
            tick();
            Thread.sleep(1000L / game.getTicksPerSecond());
        }

        System.exit(0);
    }*/


    // MODIFIES: this
    // EFFECTS: Handles one cycle in the game by taking user input,
    // ticking the game internally, and rendering the effects
    /*private void tick() throws IOException {
        handleUserInput();

        game.tick();

        screen.setCursorPosition(new TerminalPosition(0, 0));
        screen.clear();
        render();
        screen.refresh();

        screen.setCursorPosition(new TerminalPosition(screen.getTerminalSize().getColumns() - 1, 0));
    }*/


    // EFFECTS: waits for a user command to execute
    /*private void handleUserInput() throws IOException {
        KeyStroke stroke = screen.pollInput();
        if (stroke == null) {
            return;
        }

        if (stroke.getCharacter() != null) {
            if (stroke.getCharacter() == 's') {
                saveGame();
            } else if (stroke.getCharacter() == 'l') {
                loadGame();
            } else {
                return;
            }
        }


        KeyType key = stroke.getKeyType();

        movePlayer(key);
    }*/

    // EFFECTS: saves the game to file
    /*private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads the game to file
    private void loadGame() {
        try {
            game = jsonReader.read();
            System.out.println("Loaded from Save State");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }*/

    // MODIFIES: this
    // EFFECTS: moves player
    /*private void movePlayer(KeyType key) {
        switch (key) {
            case ArrowUp:
                game.getPlayer().move(0, -1, game);
                break;
            case ArrowDown:
                game.getPlayer().move(0, 1, game);
                break;
            case ArrowRight:
                game.getPlayer().move(1, 0, game);
                break;
            case ArrowLeft:
                game.getPlayer().move(-1, 0, game);
                break;
            case Escape:
                game.end();
                break;
            default:
                break;
        }
    }*/


    //  EFFECTS: Renders the current screen.
    //  Draws the end screen if the game has ended, otherwise
    //  draws the score, game, and zones.
    /*private void render() {
        if (game.isEnded()) {
            if (endGui == null) {
                drawEndScreen();
            }

            return;
        }

        //drawInfo();
        //drawZones();
        //drawPlayer();
        //drawProj();
    }*/

    // EFFECTS: draws end screen
    /*private void drawEndScreen() {
        endGui = new MultiWindowTextGUI(screen);

        new MessageDialogBuilder()
                .setTitle("Game over!")
                .setText("You Captured " + game.getPlayer().getScore() + " Zones!")
                .addButton(MessageDialogButton.Close)
                .build()
                .showDialog(endGui);
    }*/

    // EFFECTS: draws score and controls
    /*private void drawInfo() {
        TextGraphics text = screen.newTextGraphics();
        text.enableModifiers(SGR.BOLD);
        text.setForegroundColor(TextColor.ANSI.GREEN);
        text.putString(game.getMaxX() - 2, 0, "Score: ");

        text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(game.getMaxX() + 4, 0, String.valueOf(game.getPlayer().getScore()));

        text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(game.getMaxX() + 23, 0, "Press \"S\" to save");

        text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(game.getMaxX() + 23, 1, "Press \"L\" to load");

    }*/

    // EFFECTS: draws player
    /*private void drawPlayer() {
        Player player = game.getPlayer();

        drawPosition(player.getPlayerPos(), player.getDefaultColor(), '█', true,false);

    }*/

    // EFFECTS: draws all the zones
   /* private void drawZones() {
        for (int i = 0; i < game.getZones().size(); i++) {
            CaptureZone zone = game.getZones().get(i);
            if (zone.isCaptured()) {
                drawCaptureText(zone.getTextPos().getX(), zone.getTextPos().getY(), zone.isCapturing(), true);
                zone.addTickSinceCaptured();
                if (zone.getTicksSinceCaptured() >= (game.getTicksPerSecond()) * 0.5) {
                    game.getZones().remove(zone);
                    game.getPlayer().addScore(scorePerZone);
                }
                continue;
            }
            drawPosition(zone.getTopLeftZonePos(), zone.getColorZone(), '┌', false, zone.isCapturing());
            drawPosition(zone.getTopMidZonePos(), zone.getColorZone(), '-', false, zone.isCapturing());
            drawPosition(zone.getTopRightZonePos(), zone.getColorZone(), '┐', false, zone.isCapturing());
            drawPosition(zone.getMidLeftZonePos(), zone.getColorZone(), '|', false, zone.isCapturing());
            drawPosition(zone.getMidRightZonePos(), zone.getColorZone(), '|', false, zone.isCapturing());
            drawPosition(zone.getBotLeftZonePos(), zone.getColorZone(), '└', false, zone.isCapturing());
            drawPosition(zone.getBotMidZonePos(), zone.getColorZone(), '-', false, zone.isCapturing());
            drawPosition(zone.getBotRightZonePos(), zone.getColorZone(), '┘', false, zone.isCapturing());
            drawCaptureText(zone.getTextPos().getX(), zone.getTextPos().getY(), zone.isCapturing(), false);
        }
    }*/

    // EFFECTS: draws capturing text on the screen
    /*private void drawCaptureText(int x, int y, boolean capping, boolean capped) {
        TextGraphics text = screen.newTextGraphics();
        String displayText;
        if (capped) {
            text.enableModifiers(SGR.BOLD);
            displayText = "CAPTURED";
        } else if (capping) {
            text.enableModifiers(SGR.BOLD);
            displayText = "CAPTURING";
        } else {
            text.disableModifiers(SGR.BOLD);
            displayText = "CAPTURE";
        }

        text.setForegroundColor(TextColor.ANSI.GREEN);
        text.putString(x * 2, y + 1, displayText);
    }*/


    // EFFECTS: draws a particular character c at a position in colour color
    /*private void drawPosition(Position pos, TextColor color, char c, boolean wide, boolean bold) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);

        if (bold) {
            text.enableModifiers(SGR.BOLD, SGR.BLINK);
        } else {
            text.disableModifiers(SGR.BOLD, SGR.BLINK);
        }

        text.putString(pos.getX() * 2, pos.getY() + 1, String.valueOf(c));

        if (wide) {
            text.putString(pos.getX() * 2 + 1, pos.getY() + 1, String.valueOf(c));
        }
    }*/
}
