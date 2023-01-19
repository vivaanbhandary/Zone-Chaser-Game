package persistance;

import model.CaptureZone;
import model.Game;
import model.Position;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// jsonreader reads the json save file to load the game

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }


        return contentBuilder.toString();
    }

    // EFFECTS: parses game from JSON object and returns it
    private Game parseGame(JSONObject jsonObject) {
        int maxX = jsonObject.getInt("maxX");
        int maxY = jsonObject.getInt("maxY");
        Game game = new Game(maxX, maxY);
        addPlayer(game, jsonObject);
        addZones(game, jsonObject);
        addGameData(game, jsonObject);
        return game;
    }

    // MODIFIES: game
    // EFFECTS: reads player data and adds it to game
    private void addPlayer(Game game, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("player");


        JSONObject scoreObject = (JSONObject) jsonArray.get(0);
        int score = scoreObject.getInt("score");
        game.getPlayer().setScore(score);


        JSONObject playerPosObject = scoreObject.getJSONObject("playerPos");
        int x1 = playerPosObject.getInt("x");
        int y1 = playerPosObject.getInt("y");

        Position pos = new Position(x1, y1);
        game.getPlayer().setPlayerPos(pos);
        game.getPlayer().setSpeed(scoreObject.getInt("speed"));

    }

    // MODIFIES: game
    // EFFECTS: reads zone data and adds it to game
    private void addZones(Game game, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("zones");
        ArrayList<CaptureZone> zones = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextZone = (JSONObject) json;
            JSONObject zoneLocation = nextZone.getJSONObject("topLeftZonePos");
            int x1 = zoneLocation.getInt("x");
            int y1 = zoneLocation.getInt("y");
            CaptureZone zone = new CaptureZone(x1, y1);
            zones.add(zone);
        }
        game.setZones(zones);

    }

    // MODIFIES: game
    // EFFECTS: reads game data and adds it to game
    private void addGameData(Game game, JSONObject jsonObject) {
        int gradientCounter = jsonObject.getInt("gradientCounter");
        boolean zonedThisSecond = jsonObject.getBoolean("zonedThisSecond");

        int tickNum = jsonObject.getInt("tickNum");
        int secondSinceStart = jsonObject.getInt("secondSinceStart");

        int level = jsonObject.getInt("level");
        int levelScore = jsonObject.getInt("levelScore");
        int scoreMultiplier = jsonObject.getInt("scoreMultiplier");
        int scoreBarVal = jsonObject.getInt("scoreBarVal");

        game.setGradientCounter(gradientCounter);
        game.setZonedThisSecond(zonedThisSecond);
        game.setTickNum(tickNum);
        game.setSecondsSinceStart(secondSinceStart);
        game.setScoreBarVal(scoreBarVal);

        game.setLevel(level);
        game.setLevelScore(levelScore);
        game.setScoreMultiplier(scoreMultiplier);

    }
}