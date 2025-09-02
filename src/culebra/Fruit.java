package culebra;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public abstract class Fruit {
    protected int x;
    protected int y;
    protected Color color;
    protected GameBoard gameBoard;

    public Fruit(Color color, GameBoard gameBoard) {
        this.color = color;
        this.gameBoard = gameBoard;
        locate();
    }

    public final void locate() {
        Random rand = new Random();
        int ancho = gameBoard.B_WIDTH / 10;
        int alto = gameBoard.B_HEIGHT / 10;
        x = rand.nextInt(ancho) * 10;
        y = rand.nextInt(alto) * 10;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, 10, 10);
        g.setColor(Color.black);
        g.drawOval(x, y, 10, 10);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void applyEffect(Snake snake, GameBoard gameBoard);
}

class Apple extends Fruit {
    public Apple(GameBoard gameBoard) {
        super(Color.red, gameBoard);
    }

    @Override
    public void applyEffect(Snake snake, GameBoard gameBoard) {
        snake.grow();
    }
}

class OrangeApple extends Fruit {
    public OrangeApple(GameBoard gameBoard) {
        super(Color.orange, gameBoard);
    }

    @Override
    public void applyEffect(Snake snake, GameBoard gameBoard) {
        snake.shrink();
    }
}

class VioletApple extends Fruit {
    public VioletApple(GameBoard gameBoard) {
        super(Color.magenta, gameBoard);
    }

    @Override
    public void applyEffect(Snake snake, GameBoard gameBoard) {
        gameBoard.increaseSpeedTemporarily(0.95, 5000);
        snake.grow();
    }
}
