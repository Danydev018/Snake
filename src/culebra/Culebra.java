 package culebra;

import javax.swing.JFrame;

public class Culebra {
    
    public static void main(String[] args) {
        
        Menu.showMenu();
        
        
        
    }
public static void startGame(int difficulty, int numPlayers) {
        JFrame gameFrame = new JFrame("Snake");
        GameBoard gameBoard = new GameBoard(difficulty, gameFrame);

        gameFrame.add(gameBoard);
        gameFrame.setResizable(false);
        gameFrame.pack();
        gameFrame.setTitle("Snake");
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
    }
    
}
