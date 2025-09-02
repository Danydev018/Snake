package culebra;

import javax.swing.JFrame;

public class Menu {
    public static void showMenu() {
        MusiquitaFenomenal.cerrar();
        MusiquitaFenomenal.reproducir("menu.wav", true);
        JFrame frame = new JFrame("Snake Game Menu");
        MenuPanel menuPanel = new MenuPanel(frame);
        frame.add(menuPanel);
        frame.setResizable(false);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        menuPanel.requestFocusInWindow();
    }

    public static void showHighScores() {
        JFrame frame = new JFrame("High Scores");
        HighScoresPanel highScoresPanel = new HighScoresPanel(frame);
        frame.add(highScoresPanel);
        frame.setResizable(false);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
