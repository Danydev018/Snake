package culebra;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class MenuPanel extends JPanel implements KeyListener, ActionListener {

    static int numPlayers;

    private final String[] mainMenuOptions = {"Jugar", "Estadísticas", "Ayuda y Acerca"};
    private final String[] options = {"1 JUGADOR", "2 JUGADORES", "3 JUGADORES", "4 JUGADORES", "MEJORES JUGADORES"};
    private final String[] difficulties = {"Facil", "Medio", "Dificil"};
    private final String[] acercaOptions = {"1. Cómo jugar", "2. Acerca del programa"};

    private int selectedMainMenuOption = 0;
    private int selectedOption = 0;
    private int selectedDifficulty = 0;
    private int selectedAcercaOption = 0;
    private int state = 0;
    private int subState = 0;

    private final JFrame parentFrame;

    private Timer snakeTimer;
    private Timer blinkTimer;
    private int snakeX = 0;
    private int snakeY = 0;
    private int snakeDx = 2;
    private int snakeDy = 0;

    private boolean showText = true;
    private Image backgroundImage;
    private Image Controles1;
    private Image Controles2;
    private Image Controles3;
    private Image Controles4;
    private Image comoJugar1;
    private Image comoJugar2;
    private Image acercaDelPrograma;
    private Image acercaDelPrograma2;
    private Image acercaDelPrograma3;

    public MenuPanel(JFrame parentFrame) {
    System.out.println(getClass().getResource("/culebra/Pantalla Principal.png"));
    System.out.println(getClass().getResource("/culebra/control1.png"));
    System.out.println(getClass().getResource("/culebra/control2.png"));
    System.out.println(getClass().getResource("/culebra/control3.png"));
    System.out.println(getClass().getResource("/culebra/control4.png"));
    System.out.println(getClass().getResource("/culebra/Comojugar1.png"));
    System.out.println(getClass().getResource("/culebra/Comojugar2.png"));
    System.out.println(getClass().getResource("/culebra/Informacion.png"));
    System.out.println(getClass().getResource("/culebra/Informacion2.PNG"));
    System.out.println(getClass().getResource("/culebra/Informacion3.PNG"));
    System.out.println(getClass().getResource("/culebra/Pantalla Principal.png"));
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setFocusable(true);
        addKeyListener(this);

        // Cargar las imágenes
    backgroundImage = new ImageIcon(getClass().getResource("/culebra/Pantalla Principal.png")).getImage();
    Controles1 = new ImageIcon(getClass().getResource("/culebra/control1.png")).getImage();
    Controles2 = new ImageIcon(getClass().getResource("/culebra/control2.png")).getImage();
    Controles3 = new ImageIcon(getClass().getResource("/culebra/control3.png")).getImage();
    Controles4 = new ImageIcon(getClass().getResource("/culebra/control4.png")).getImage();
    comoJugar1 = new ImageIcon(getClass().getResource("/culebra/Comojugar1.png")).getImage();
    comoJugar2 = new ImageIcon(getClass().getResource("/culebra/Comojugar2.png")).getImage();
    acercaDelPrograma = new ImageIcon(getClass().getResource("/culebra/Informacion.png")).getImage();
    acercaDelPrograma2 = new ImageIcon(getClass().getResource("/culebra/Informacion2.PNG")).getImage();
    acercaDelPrograma3 = new ImageIcon(getClass().getResource("/culebra/Informacion3.PNG")).getImage();
        snakeTimer = new Timer(100, this);
        snakeTimer.start();

        blinkTimer = new Timer(1000, e -> {
            showText = !showText;
            repaint();
        });
        blinkTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar la imagen de fondo
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        g.setColor(Color.white);
        g.setFont(new Font("Sans serif", Font.BOLD, 20));

        switch (state) {
            case 0 -> {
                g.setColor(Color.black);
                g.setFont(new Font("Sans serif", Font.BOLD, 20));
                if (showText) {
                    g.drawString("Presione Enter para Continuar...", 80, 620);
                }
            }
            case 1 -> {
                g.drawString("MODULOS", 50, 50);
                for (int i = 0; i < mainMenuOptions.length; i++) {
                    if (i == selectedMainMenuOption) {
                        g.drawString("▶ " + mainMenuOptions[i], 70, 100 + i * 30);
                    } else {
                        g.drawString(mainMenuOptions[i], 90, 100 + i * 30);
                    }
                }
            }
            case 2 -> {
                g.drawString("SELECCIONE UN MODO", 50, 50);
                if (showText) {
                    g.drawString("Presione Esc para volver...", 80, 620);
                }
                for (int i = 0; i < options.length; i++) {
                    if (i == selectedOption) {
                        g.drawString("▶ " + options[i], 70, 100 + i * 30);
                    } else {
                        g.drawString(options[i], 90, 100 + i * 30);
                    }
                }
            }
            case 3 -> {
                Image controles = switch (selectedOption) {
                    case 0 -> Controles1;
                    case 1 -> Controles2;
                    case 2 -> Controles3;
                    case 3 -> Controles4;
                    default -> null;
                };

                if (controles != null) {
                    g.drawImage(controles, 0, 0, getWidth(), getHeight(), this);
                    g.setFont(new Font("Sans serif", Font.BOLD, 15));
                    if (showText) {
                        g.drawString("Presione Enter para Continuar/ Esc para volver...", 250, 15);
                    }
                }
            }
            case 4 -> {
                g.drawString("Seleccione una Dificultad", 30, 30);
                if (showText) {
                    g.drawString("Presione Esc para volver...", 80, 620);
                }
                for (int i = 0; i < difficulties.length; i++) {
                    if (i == selectedDifficulty) {
                        g.drawString("▶ " + difficulties[i], 50, 70 + i * 30);
                    } else {
                        g.drawString(difficulties[i], 70, 70 + i * 30);
                    }
                }
            }
            case 5 -> {
                g.drawString("AYUDA Y ACERCA", 50, 50);
                for (int i = 0; i < acercaOptions.length; i++) {
                    if (i == selectedAcercaOption) {
                        g.drawString("▶ " + acercaOptions[i], 70, 100 + i * 30);
                    } else {
                        g.drawString(acercaOptions[i], 90, 100 + i * 30);
                    }
                }
            }
            case 6 -> {
                switch (subState) {
                    case 0 -> {
                        g.drawImage(comoJugar1, 0, 0, getWidth(), getHeight(), this);
                        g.setFont(new Font("Sans serif", Font.BOLD, 10));
                        if (showText) {
                            g.drawString("Presione Enter para Continuar...", 200, 10);
                        }
                    }
                    case 1 -> {
                        g.drawImage(comoJugar2, 0, 0, getWidth(), getHeight(), this);
                        g.setFont(new Font("Sans serif", Font.BOLD, 10));
                        if (showText) {
                            g.drawString("Presione Esc para Volver...", 200, 10);
                        }
                    }
                }
            }
            case 7 -> {
                switch (subState) {
                    case 0 -> {
                        g.drawImage(acercaDelPrograma, 0, 0, getWidth(), getHeight(), this);
                     
                    }
                    case 1 -> {
                        g.setColor(Color.black);
                        g.drawImage(acercaDelPrograma2, 0, 0, getWidth(), getHeight(), this);
                        g.setFont(new Font("Sans serif", Font.BOLD, 10));
                        if (showText) {
                            g.drawString("Presione Enter para Continuar/Esc para Salir...", 200, 10);
                        }
                    }
                    case 2 -> {
                        g.setColor(Color.black);
                        g.drawImage(acercaDelPrograma3, 0, 0, getWidth(), getHeight(), this);
                        g.setFont(new Font("Sans serif", Font.BOLD, 10));
                        if (showText) {
                            g.drawString("Presione Enter para Continuar/Esc para Salir...", 200, 10);
                        }
                    }
                }
            }
        }

        g.setColor(Color.red);
        g.fillOval(snakeX, snakeY, 10, 10);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (state) {
            case 0 -> {
                if (key == KeyEvent.VK_ENTER) {
                    state = 1;
                    backgroundImage = new ImageIcon(getClass().getResource("/Recursos/Pantalla2.png")).getImage();
                    repaint();
                }
            }
            case 1 -> {
                if (key == KeyEvent.VK_UP) {
                    selectedMainMenuOption = (selectedMainMenuOption - 1 + mainMenuOptions.length) % mainMenuOptions.length;
                    repaint();
                }
                if (key == KeyEvent.VK_DOWN) {
                    selectedMainMenuOption = (selectedMainMenuOption + 1) % mainMenuOptions.length;
                    repaint();
                }
                if (key == KeyEvent.VK_ENTER) {
                    switch (selectedMainMenuOption) {
                        case 0 -> {
                            state = 2;
                            repaint();
                        }
                        case 1 -> {
                            parentFrame.dispose();
                            Menu.showHighScores();
                        }
                        case 2 -> {
                            state = 5;
                            repaint();
                        }
                    }
                }
                if (key == KeyEvent.VK_ESCAPE) {
                    state = 1;
                    repaint();
                }
            }
            case 2 -> {
                if (key == KeyEvent.VK_UP) {
                    selectedOption = (selectedOption - 1 + options.length) % options.length;
                    repaint();
                }
                if (key == KeyEvent.VK_DOWN) {
                    selectedOption = (selectedOption + 1) % options.length;
                    repaint();
                }
                if (key == KeyEvent.VK_ENTER) {
                    if (selectedOption == options.length - 1) {
                        parentFrame.dispose();
                        Menu.showHighScores();
                    } else {
                        state = 3;
                        repaint();
                    }
                }
                if (key == KeyEvent.VK_ESCAPE) {
                    state = 1;
                    repaint();
                }
            }
            case 3 -> {
                if (key == KeyEvent.VK_ENTER) {
                    state = 4;
                    repaint();
                }
                if (key == KeyEvent.VK_ESCAPE) {
                    state = 2;
                    repaint();
                }
            }
            case 4 -> {
                if (key == KeyEvent.VK_UP) {
                    selectedDifficulty = (selectedDifficulty - 1 + difficulties.length) % difficulties.length;
                    repaint();
                }
                if (key == KeyEvent.VK_DOWN) {
                    selectedDifficulty = (selectedDifficulty + 1) % difficulties.length;
                    repaint();
                }
                if (key == KeyEvent.VK_ENTER) {
                    MenuPanel.numPlayers = selectedOption + 1;
                    int difficulty = switch (selectedDifficulty) {
                        case 0 -> 140;
                        case 1 -> 100;
                        case 2 -> 60;
                        default -> 140;
                    };
                    parentFrame.dispose();
                    Culebra.startGame(difficulty, MenuPanel.numPlayers);
                }
                if (key == KeyEvent.VK_ESCAPE) {
                    state = 3;
                    repaint();
                }
            }
            case 5 -> {
                if (key == KeyEvent.VK_UP) {
                    selectedAcercaOption = (selectedAcercaOption - 1 + acercaOptions.length) % acercaOptions.length;
                    repaint();
                }
                if (key == KeyEvent.VK_DOWN) {
                    selectedAcercaOption = (selectedAcercaOption + 1) % acercaOptions.length;
                    repaint();
                }
                if (key == KeyEvent.VK_ENTER) {
                    switch (selectedAcercaOption) {
                        case 0 -> {
                            state = 6;
                            subState = 0;
                            repaint();
                        }
                        case 1 -> {
                            state = 7;
                            subState = 0; // Iniciar el subState a 0 cuando entras en el estado 7
                            repaint();
                        }
                    }
                }
                if (key == KeyEvent.VK_ESCAPE) {
                    state = 1;
                    repaint();
                }
            }
            case 6 -> {
                if (key == KeyEvent.VK_ENTER) {
                    subState = 1;
                    repaint();
                }
                if (key == KeyEvent.VK_ESCAPE) {
                    state = 5;
                    repaint();
                }
            }
            case 7 -> {
                if (key == KeyEvent.VK_ENTER) {
                    if (subState < 2) {
                        subState++;
                    } else {
                        subState = 0;
                        state = 5;
                    }
                    repaint();
                }
                if (key == KeyEvent.VK_ESCAPE) {
                    state = 5;
                    repaint();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        snakeX += snakeDx;
        snakeY += snakeDy;

        if (snakeX > getWidth() - 10) {
            snakeDx = 0;
            snakeDy = 2;
        }
        if (snakeY > getHeight() - 10) {
            snakeDx = -2;
            snakeDy = 0;
        }
        if (snakeX < 0) {
            snakeDx = 0;
            snakeDy = -2;
        }
        if (snakeY < 0) {
            snakeDx = 2;
            snakeDy = 0;
        }
        repaint();
    }
}
