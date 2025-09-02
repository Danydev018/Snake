package culebra;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class GameBoard extends JPanel implements ActionListener {

    public final int B_WIDTH = 800;
    public final int B_HEIGHT = 600;
    private final int EASY_SPEED = 140;
    private final int MEDIUM_SPEED = 100;
    private final int HARD_SPEED = 60;
    private JFrame parentFrame;
    private Snake snake;
    private Snake snake2;
    private Snake snake3;
    private Snake snake4;
    private Fruit apple;
    private Fruit orangeApple;
    private Fruit violetApple;
    private Timer timer;
    private boolean inGame = true;
    private double speedMultiplier = 1.0;
    private Timer speedResetTimer;
    private int[] score = {0, 0, 0, 0};
    static int indiceGanador;
    private boolean isPaused = false;
    private Image backgroundImage;
    private Image GameOver;
    private boolean primerGameOver = true;

    public GameBoard(int speed, JFrame parentFrame) {
        this.parentFrame = parentFrame;
        int numPlayers = MenuPanel.numPlayers;
        initBoard(speed, numPlayers);
    GameOver = new ImageIcon(getClass().getResource("/culebra/Pantalla4.png")).getImage();
    }

    public GameBoard(int speed, int numPlayers) {
        initBoard(speed, numPlayers);
    }

    private void initBoard(int speed, int numPlayers) {
        MusiquitaFenomenal.cerrar();
        MusiquitaFenomenal.reproducir("audio.wav", true);
        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        backgroundImage = new ImageIcon(getClass().getResource("/Recursos/Pantalla3.png")).getImage();

        snake = new Snake(3, 50, 50, Color.green);

        if (numPlayers > 1) {
            snake2 = new Snake(3, 250, 250, Color.red);
        }
        if (numPlayers > 2) {
            snake3 = new Snake(3, 150, 150, Color.blue);
        }
        if (numPlayers > 3) {
            snake4 = new Snake(3, 350, 350, Color.yellow);
        }

        apple = new Apple(this);
        orangeApple = new OrangeApple(this);
        violetApple = new VioletApple(this);

        timer = new Timer((int) (speed * speedMultiplier), this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (inGame) {
            if (!isPaused) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                apple.draw(g);
                orangeApple.draw(g);
                violetApple.draw(g);
                try {
                    if (snake != null) {
                        snake.draw(g);
                    }
                    if (snake2 != null) {
                        snake2.draw(g);
                    }
                    if (snake3 != null) {
                        snake3.draw(g);
                    }
                    if (snake4 != null) {
                        snake4.draw(g);
                    }
                    drawScore(g, 1);
                    if (snake2 != null) {
                        drawScore(g, 2);
                    }
                    if (snake3 != null) {
                        drawScore(g, 3);
                    }
                    if (snake4 != null) {
                        drawScore(g, 4);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                String pauseMsg = "Juego en pausa. Presione B para continuar";
                g.setColor(Color.white);
                g.drawString(pauseMsg, (B_WIDTH - getFontMetrics(getFont()).stringWidth(pauseMsg)) / 2, B_HEIGHT / 2);
            }
            drawPauseMessage(g);
        } else {
            if (primerGameOver) {
                gameOver();
                primerGameOver = false;
            }
        }
    }

    private void drawPauseMessage(Graphics g) {
        String pauseMsg = "Presione B para pausar/ Esc para Salir";
        g.setColor(Color.white);
        g.drawString(pauseMsg, 10, B_HEIGHT - 10);
    }

    private void drawScore(Graphics g, int numJugador) {
        String scoreString = "jugador " + numJugador + " : " + score[numJugador - 1];
        g.setColor(Color.white);
        g.drawString(scoreString, 10 + (100 * (numJugador - 1)), 10);
    }

    private void gameOver() {
        MusiquitaFenomenal.cerrar();
        MusiquitaFenomenal.reproducir("gameover.aiff", false);
        int maxPuntuacion = 0;
        for (int i = 0; i < score.length; i++) {
            if (score[i] > maxPuntuacion) {
                maxPuntuacion = score[i];
                indiceGanador = i;
            }
        }

        if (GameOver != null) {
            ImageIcon icon = new ImageIcon(GameOver);
            JLabel gameOverLabel = new JLabel(icon);
            gameOverLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            JOptionPane.showMessageDialog(this, gameOverLabel, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen de Game Over.", "Game Over", JOptionPane.ERROR_MESSAGE);
        }

        String playerName = JOptionPane.showInputDialog("Ingresa tu nombre, jugador " + (indiceGanador + 1));
        if (playerName != null) {
            boolean newRecord = HighScores.getHighScore() == null || maxPuntuacion > HighScores.getHighScore().getScore();
            HighScores.addScore(playerName, maxPuntuacion);

            if (newRecord) {
                JOptionPane.showMessageDialog(this, "¡NUEVO RÉCORD!");
            }
        }

        parentFrame.dispose();
        SwingUtilities.invokeLater(() -> Menu.showHighScores());
    }

    private void checkApple() {
        try {
            if (snake != null && snake.getHead()[0] == apple.getX() && snake.getHead()[1] == apple.getY()) {
                apple.applyEffect(snake, this);
                apple.locate();
                score[0] += 10;
            }
            if (snake2 != null && snake2.getHead()[0] == apple.getX() && snake2.getHead()[1] == apple.getY()) {
                apple.applyEffect(snake2, this);
                apple.locate();
                score[1] += 10;
            }
            if (snake3 != null && snake3.getHead()[0] == apple.getX() && snake3.getHead()[1] == apple.getY()) {
                apple.applyEffect(snake3, this);
                apple.locate();
                score[2] += 10;
            }
            if (snake4 != null && snake4.getHead()[0] == apple.getX() && snake4.getHead()[1] == apple.getY()) {
                apple.applyEffect(snake4, this);
                apple.locate();
                score[3] += 10;
            }

            if (snake != null && snake.getHead()[0] == orangeApple.getX() && snake.getHead()[1] == orangeApple.getY()) {
                orangeApple.applyEffect(snake, this);
                orangeApple.locate();
                score[0] += 5;
            }
            if (snake2 != null && snake2.getHead()[0] == orangeApple.getX() && snake2.getHead()[1] == orangeApple.getY()) {
                orangeApple.applyEffect(snake2, this);
                orangeApple.locate();
                score[1] += 5;
            }
            if (snake3 != null && snake3.getHead()[0] == orangeApple.getX() && snake3.getHead()[1] == orangeApple.getY()) {
                orangeApple.applyEffect(snake3, this);
                orangeApple.locate();
                score[2] += 5;
            }
            if (snake4 != null && snake4.getHead()[0] == orangeApple.getX() && snake4.getHead()[1] == orangeApple.getY()) {
                orangeApple.applyEffect(snake4, this);
                orangeApple.locate();
                score[3] += 5;
            }

            if (snake != null && snake.getHead()[0] == violetApple.getX() && snake.getHead()[1] == violetApple.getY()) {
                violetApple.applyEffect(snake, this);
                violetApple.locate();
                score[0] += 15;
            }
            if (snake2 != null && snake2.getHead()[0] == violetApple.getX() && snake2.getHead()[1] == violetApple.getY()) {
                violetApple.applyEffect(snake2, this);
                violetApple.locate();
                score[1] += 15;
            }
            if (snake3 != null && snake3.getHead()[0] == violetApple.getX() && snake3.getHead()[1] == violetApple.getY()) {
                violetApple.applyEffect(snake3, this);
                violetApple.locate();
                score[2] += 15;
            }
            if (snake4 != null && snake4.getHead()[0] == violetApple.getX() && snake4.getHead()[1] == violetApple.getY()) {
                violetApple.applyEffect(snake4, this);
                violetApple.locate();
                score[3] += 15;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame && !isPaused) {
            try {
                if (snake != null) {
                    snake.move();
                    checkCollision(snake, 0);
                }
                if (snake2 != null) {
                    snake2.move();
                    checkCollision(snake2, 1);
                }
                if (snake3 != null) {
                    snake3.move();
                    checkCollision(snake3, 2);
                }
                if (snake4 != null) {
                    snake4.move();
                    checkCollision(snake4, 3);
                }
                checkApple();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
            repaint();
        }
    }

    private void checkCollision(Snake s, int snakeIndex) {
        if (s.checkCollisionWithItself() || checkWallCollision(s)) {
            score[snakeIndex] -= 20;
            // Eliminar la serpiente
            if (s == snake) {
                snake = null;
            } else if (s == snake2) {
                snake2 = null;
            } else if (s == snake3) {
                snake3 = null;
            } else if (s == snake4) {
                snake4 = null;
            }

            

            if (isOnlyOneSnakeAlive()) {
                inGame = false;
            }
        }
        for (Snake otherSnake : new Snake[]{snake, snake2, snake3, snake4}) {
                if (otherSnake != null && s != otherSnake && s.checkCollisionWithOtherSnake(otherSnake)) {
                    
                    if (s == snake) {
                snake = null;
            } else if (s == snake2) {
                snake2 = null;
            } else if (s == snake3) {
                snake3 = null;
            } else if (s == snake4) {
                snake4 = null;
            }
                    break;
                }
                if (isOnlyOneSnakeAlive()) {
                inGame = false;
            }
            }
    }

    private boolean isOnlyOneSnakeAlive() {
        int aliveCount = 0;
        if (snake != null) {
            aliveCount++;
        }
        if (snake2 != null) {
            aliveCount++;
        }
        if (snake3 != null) {
            aliveCount++;
        }
        if (snake4 != null) {
            aliveCount++;
        }
        
        if (MenuPanel.numPlayers == 1){
            return aliveCount < 1;
        }else
            
            return aliveCount <= 1;
        
        
        
    }

    private boolean checkWallCollision(Snake s) {
        int[] head = s.getHead();
        return head[0] < 0 || head[0] >= B_WIDTH || head[1] < 0 || head[1] >= B_HEIGHT;
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            try {
                if (key == KeyEvent.VK_LEFT) {
                    if (snake != null) {
                        snake.setDirection(key);
                    }
                } else if (key == KeyEvent.VK_RIGHT) {
                    if (snake != null) {
                        snake.setDirection(key);
                    }
                } else if (key == KeyEvent.VK_UP) {
                    if (snake != null) {
                        snake.setDirection(key);
                    }
                } else if (key == KeyEvent.VK_DOWN) {
                    if (snake != null) {
                        snake.setDirection(key);
                    }
                }

                if (key == KeyEvent.VK_A) {
                    if (snake2 != null) {
                        snake2.setDirection(key);
                    }
                } else if (key == KeyEvent.VK_D) {
                    if (snake2 != null) {
                        snake2.setDirection(key);
                    }
                } else if (key == KeyEvent.VK_W) {
                    if (snake2 != null) {
                        snake2.setDirection(key);
                    }
                } else if (key == KeyEvent.VK_S) {
                    if (snake2 != null) {
                        snake2.setDirection(key);
                    }
                }

                if (key == KeyEvent.VK_J) {
                    if (snake3 != null) {
                        snake3.setDirection(key);
                    }
                } else if (key == KeyEvent.VK_L) {
                    if (snake3 != null) {
                        snake3.setDirection(key);
                    }
                } else if (key == KeyEvent.VK_I) {
                    if (snake3 != null) {
                        snake3.setDirection(key);
                    }
                } else if (key == KeyEvent.VK_K) {
                    if (snake3 != null) {
                        snake3.setDirection(key);
                    }
                }

                if (key == KeyEvent.VK_NUMPAD4) {
                    if (snake4 != null) {
                        snake4.setDirection(key);
                    }
                } else if (key == KeyEvent.VK_NUMPAD6) {
                    if (snake4 != null) {
                        snake4.setDirection(key);
                    }
                } else if (key == KeyEvent.VK_NUMPAD8) {
                    if (snake4 != null) {
                        snake4.setDirection(key);
                    }
                } else if (key == KeyEvent.VK_NUMPAD5) {
                    if (snake4 != null) {
                        snake4.setDirection(key);
                    }
                }

                if (key == KeyEvent.VK_B) {
                    togglePause();
                } else if (key == KeyEvent.VK_ESCAPE) {
                    timer.stop();
                    parentFrame.dispose();
                    Menu.showMenu();
                }
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void togglePause() {
        isPaused = !isPaused;
    }

    public boolean isInGame() {
        return inGame;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void increaseSpeedTemporarily(double factor, int duration) {
        speedMultiplier *= factor;
        timer.setDelay((int) (timer.getInitialDelay() * speedMultiplier));
        if (speedResetTimer != null && speedResetTimer.isRunning()) {
            speedResetTimer.stop();
        }
        speedResetTimer = new Timer(duration, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speedMultiplier /= factor;
                timer.setDelay((int) (timer.getInitialDelay() * speedMultiplier));
                speedResetTimer.stop();
            }
        });
        speedResetTimer.start();
    }

}