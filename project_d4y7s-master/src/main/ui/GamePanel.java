package ui;

import model.CaptureZone;
import model.*;
import model.Event;
import model.Game;
import model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Some parts of the code were used from the spaceInvadersBase project

//GamePanel controls all the components of the game and draws them onto the frame
public class GamePanel extends JPanel {

    private Game game;
    private final int scorePerZone = 1;
    String captureLbl = "CAPTURE";
    String cappingLbl = "CAPTURING";
    String cappedLbl = "CAPTURED!";
    private int bgColor = 0;
    private int wid;
    private int hei;
    private JProgressBar scoreBar;
    int bgShift = 0;
    int bgShiftCount = 0;
    int bgShiftCountCount = 0;
    int bgShiftDX = 1;
    int bgShiftDY = 1;
    int bgSpeed = 2;

    // REQUIRES: width, height of screen, game
    // MODIFIES: this
    // EFFECTS: constructs a GamePanel object
    public GamePanel(int width, int height, Game g) {
        wid = width;
        hei = height;
        game = g;
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(bgColor, bgColor, bgColor));

        scoreBar = new JProgressBar();
        scoreBar.setString("Level Progress");
        scoreBar.setPreferredSize(new Dimension(300, 20));
        scoreBar.setBackground(Color.BLACK);
        scoreBar.setForeground(Color.green);
        scoreBar.setValue(game.getScoreBarVal());
        this.add(scoreBar);
        scoreBar.setStringPainted(true);

    }

    // MODIFIES: g
    // EFFECTS: paints all the components of the panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBG(g);
        drawGame(g);

        if (game.isEnded()) {
            gameOver(g);
        }

    }

    // MODIFIES: g
    // EFFECTS: paints the background
    private void drawBG(Graphics g) {

        try {
            Image img = img = ImageIO.read(new File("./data/GameBG2.png"));
            //img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            g.drawImage(img,-60 + bgShift * bgShiftDX,-60 + bgShift * bgShiftDY, this);
            //g.drawImage(img,-60 + bgShift * bgShiftDX,-60 + bgShift * -bgShiftDY, this);
            //g.drawImage(img,-60 + bgShift * -bgShiftDX,-60 + bgShift * bgShiftDY, this);
            //g.drawImage(img,-60 + bgShift * -bgShiftDX,-60 + bgShift * -bgShiftDY, this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        bgShift += bgSpeed;

        if (bgShift >= 60) {
            bgShift = 0;
            bgShiftCount++;
        }

        if (bgShiftCount >= 10) {
            bgShiftCount = 0;
            bgShiftCountCount++;

            if (bgShiftCountCount % 2 == 0) {
                bgShiftDX *= -1;
            } else {
                bgShiftDY *= -1;
            }
        }


    }

    // MODIFIES: g
    // EFFECTS:  draws the game onto g
    private void drawGame(Graphics g) {
        drawInfo(g);
        drawZone(g);
        drawPlayer(g);
    }

    // MODIFIES: g
    // EFFECTS:  draws the game info onto g
    private void drawInfo(Graphics g) {
        Color savedCol = g.getColor();


        g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        g.setColor(Color.WHITE);
        g.drawString("Score: ", wid / 2 + 400, 20);
        g.setColor(Color.GREEN);
        g.drawString("" + game.getPlayer().getScore(), wid / 2 + 450, 20);

        levelTextColor(g);

        g.drawString("Level: " + game.getLevel(), wid / 2 - 30, 45);
        scoreBar.setValue((game.getPlayer().getScore() - game.getLevelScore()) * game.getScoreMultiplier());
        game.setScoreBarVal(scoreBar.getValue());
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
        g.drawString("Press \"S\" to save", 10, 20);
        g.drawString("Press \"L\" to load", 10, 35);
        g.drawString("Press \"Esc\" to quit", 10, 50);
        if (scoreBar.getValue() >= 100) {
            levelUp();
        }
        g.setColor(savedCol);
    }

    // MODIFIES: g
    // EFFECTS: changes text and progress bar colors based on the level
    private void levelTextColor(Graphics g) {
        switch (game.getLevel()) {
            case 1: g.setColor(Color.GREEN);
                scoreBar.setBackground(Color.BLACK);
                scoreBar.setForeground(Color.green);
                break;
            case 2: g.setColor(Color.YELLOW);
                scoreBar.setBackground(Color.green);
                scoreBar.setForeground(Color.yellow);
                break;
            default: g.setColor(Color.RED);
                scoreBar.setBackground(Color.yellow);
                scoreBar.setForeground(Color.red);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: increases level of player
    private void levelUp() {
        game.setLevel(game.getLevel() + 1);
        switch (game.getLevel()) {
            case 2: scoreBar.setBackground(Color.green);
                scoreBar.setForeground(Color.yellow);
                scoreBar.setValue(0);
                game.setScoreMultiplier(20);
                game.setLevelScore(game.getPlayer().getScore());
                game.getPlayer().setSpeed(20);
                bgSpeed = 4;

                break;
            case 3: scoreBar.setBackground(Color.yellow);
                scoreBar.setForeground(Color.red);
                scoreBar.setValue(0);
                game.setScoreMultiplier(10);
                game.setLevelScore(game.getPlayer().getScore());
                game.getPlayer().setSpeed(25);
                bgSpeed = 6;
                break;
            case 4: scoreBar.setValue(0);
                break;
            case 5:
                game.setLevel(4);
        }
    }

    // MODIFIES: g
    // EFFECTS:  draws the zone z onto g
    private void drawZone(Graphics g) {
        for (int i = 0; i < game.getZones().size(); i++) {
            CaptureZone z = game.getZones().get(i);
            Color savedCol = g.getColor();
            g.setColor(z.getColorZone());
            g.drawRect(z.getTopLeftZonePos().getX(), z.getTopLeftZonePos().getY(),
                    z.getZoneLength(), z.getZoneLength());
            if (z.isCaptured()) {
                drawZoneCaptured(g, z);
            } else {
                if (z.isCapturing()) {
                    g.setFont(new Font("TimesRoman", Font.BOLD, 12));
                    g.drawString(cappingLbl, z.getTopLeftZonePos().getX() - 13, z.getTopLeftZonePos().getY() + 55);
                } else {
                    g.drawString(captureLbl, z.getTopLeftZonePos().getX() - 10, z.getTopLeftZonePos().getY() + 55);
                }
            }
            g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
            g.setColor(savedCol);
            //setBackground(new Color(bgColor, bgColor, bgColor));
        }
    }

    // MODIFIES: g
    // EFFECTS:  draws the captured zone z's text onto g and removes captured zones
    private void drawZoneCaptured(Graphics g, CaptureZone z) {
        g.setFont(new Font("TimesRoman", Font.BOLD, 12));
        g.drawString(cappedLbl, z.getTopLeftZonePos().getX() - 13, z.getTopLeftZonePos().getY() + 55);
        z.addTickSinceCaptured();
        if (z.getTicksSinceCaptured() >= (game.getTicksPerSecond()) * 0.5) {
            z.logEventCaptured();
            game.getZones().remove(z);
            game.getPlayer().addScore(scorePerZone);
            bgColor += 5;
        }
    }

    // MODIFIES: g
    // EFFECTS:  draws the player onto g
    private void drawPlayer(Graphics g) {
        Player player = game.getPlayer();
        Color savedCol = g.getColor();
        g.setColor(player.getDefaultColor());
        g.fillRect(player.getPlayerPos().getX(), player.getPlayerPos().getY(),
                player.getPlayerLength(), player.getPlayerLength());

        /*try {
            Image img = img = ImageIO.read(new File("./data/Jerrefy.jpg"));
            img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            g.drawImage(img, player.getPlayerPos().getX(), player.getPlayerPos().getY(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        g.setColor(savedCol);
    }

    // MODIFIES: g
    // EFFECTS:  draws "game over" and save instructions onto g
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();

        g.setColor(Color.GREEN);
        g.drawString("" + game.getPlayer().getScore(), wid / 2 + 52, game.getMaxY() / 2 + 29);
        g.setColor(Color.WHITE);
        centreString("Final Score: ", g, fm, game.getMaxY() / 2 + 30);
        centreString("Game Over!", g, fm, game.getMaxY() / 2 + 60);
        g.drawString("Press \"S\" to save progress", wid / 2 - 350, game.getMaxY() / 2 - 50);
        g.drawString("Press \"Enter\" to exit", wid / 2 + 150, game.getMaxY() / 2 - 50);
        //centreString(REPLAY, g, fm, game.getMaxY() / 2 + 50);
        g.setColor(saved);


    }

    // MODIFIES: g
    // EFFECTS:  centres the string str horizontally onto g at vertical position yPos
    private void centreString(String str, Graphics g, FontMetrics fm, int ypos) {
        int width = fm.stringWidth(str);
        g.drawString(str, (game.getMaxX() - width) / 2, ypos);
    }



    public void setGame(Game game) {
        this.game = game;
    }

    public int getBgSpeed() {
        return bgSpeed;
    }

    public void setBgSpeed(int bgSpeed) {
        this.bgSpeed = bgSpeed;
    }
}
