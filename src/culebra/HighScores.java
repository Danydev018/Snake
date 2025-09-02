package culebra;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class HighScores {
    private static final String FILE_PATH = "highscores.dat";
    private static List<Score> scores = new ArrayList<>();
    private static Score highScore;

    static {
        loadScores();
    }

    public static void addScore(String name, int score) {
        if (highScore == null || score > highScore.getScore()) {
            highScore = new Score(name, score);
            JOptionPane.showMessageDialog(null, "Registrando nuevo record...");
        }

        scores.add(0, new Score(name, score));
        if (scores.size() > 6) {
            scores.remove(scores.size() - 1);
        }

        saveScores();
    }

    public static List<Score> getScores() {
        return scores;
    }

    public static Score getHighScore() {
        return highScore;
    }

    private static void loadScores() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            highScore = (Score) ois.readObject();
            scores = (List<Score>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            highScore = null;
            scores = new ArrayList<>();
        }
    }

    private static void saveScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(highScore);
            oos.writeObject(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HighScores() {
    }
}

class Score implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
