package ui;

import java.io.IOException;

// executable file that starts the game

public class ZoneChaserGame {
    public static void main(String[] args) throws Exception {
        // create and start the game
        GameState gameHandler = new GameState();

        gameHandler.start();
    }
}
