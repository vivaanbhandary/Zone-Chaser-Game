package ui;

import model.Event;
import model.EventLog;
import model.Game;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;


// Some parts of the code were used from the spaceInvadersBase project

//GameFrame creates a frame for the game where components can be added
public class GameFrame extends JFrame {

    private GamePanel panel;
    private Game game;
    private static final String JSON_STORE = "./data/saveData.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //REQUIRES: width, height of screen, and Game
    //MODIFIES: this
    //EFFECTS: constructs a GameFrame object and initialises the values of the fields
    public GameFrame(int width, int height, Game g) {
        super("ZoneChaser");

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);

        game = g;


        panel = new GamePanel(width, height, game);

        add(panel);

        addKeyListener(new KeyHandler());
        pack();
        setVisible(true);
        addTimer();
    }

    // EFFECTS:  Set up timer that updates game each
    //           (1000 / game.ticksPerSecond) milliseconds
    private void addTimer() {
        Timer t = new Timer((1000 / game.ticksPerSecond), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!game.isEnded()) {
                    game.tick();
                }
                panel.repaint();


                }
        });

        t.start();
    }

    // KeyHandler handles inputs from the user
    private class KeyHandler extends KeyAdapter {

        // EFFECTS: Converts a keyEvent to a keycode
        @Override
        public void keyPressed(KeyEvent e) {
            keyPressed(e.getKeyCode());
        }

        // REQUIRES: int that is a valid converted keyEvent
        // EFFECTS: Does action based on which key is pressed by the user
        public void keyPressed(int keyCode) {
            if (keyCode == KeyEvent.VK_S) {
                saveGame();
            }
            if (keyCode == KeyEvent.VK_L) {
                loadGame();
            }
            if (keyCode == KeyEvent.VK_ENTER && game.isEnded()) {
                printLog(EventLog.getInstance());

                System.exit(0);
            }

            if (!game.isEnded()) {
                movePlayer(keyCode);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the game to file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the game to file
    private void loadGame() {
        try {
            game = jsonReader.read();
            panel.setGame(game);
            System.out.println("Loaded from Save State");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: moves player
    private void movePlayer(int key) {
        int speed = game.getPlayer().getSpeed();
        switch (key) {
            case KeyEvent.VK_UP:
                game.getPlayer().move(0, -speed, game);
                break;
            case KeyEvent.VK_DOWN:
                game.getPlayer().move(0, speed, game);
                break;
            case KeyEvent.VK_RIGHT:
                game.getPlayer().move(speed, 0, game);
                break;
            case KeyEvent.VK_LEFT:
                game.getPlayer().move(-speed, 0, game);
                break;
            case KeyEvent.VK_ESCAPE:
                game.end();
                break;
            default:
                break;
        }
    }

    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n");
        }
    }

}
