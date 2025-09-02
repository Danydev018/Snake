package culebra;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Snake {

    private final LinkedList<int[]> body;
    private int[] head;
    private final int size;
    private final Color color;

    private boolean leftDirection;
    private boolean rightDirection;
    private boolean upDirection;
    private boolean downDirection;
    private boolean canChangeDirection;

    public Snake(int initialSize, int startX, int startY, Color color) {
        this.size = initialSize;
        this.color = color;
        body = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            body.add(new int[]{startX - i * 10, startY});
        }
        head = body.getFirst();
        leftDirection = false;
        rightDirection = true;
        upDirection = false;
        downDirection = false;
        canChangeDirection = true;
    }

    public void move() {
        int[] newHead = new int[]{head[0], head[1]};

        if (leftDirection) {
            newHead[0] -= 10;
        }
        if (rightDirection) {
            newHead[0] += 10;
        }
        if (upDirection) {
            newHead[1] -= 10;
        }
        if (downDirection) {
            newHead[1] += 10;
        }

        body.addFirst(newHead);
        body.removeLast();
        head = newHead;
        
        canChangeDirection = true;
    }

    public void grow() {
        body.addLast(body.getLast().clone());
    }

    public void shrink() {
        if (body.size() > 3) {
            body.removeLast();
        }
    }

    public void setDirection(int key) {
        if (canChangeDirection) {
            if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A || key == KeyEvent.VK_J || key == KeyEvent.VK_NUMPAD4) && !rightDirection) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
                canChangeDirection = false;
            }
            if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D || key == KeyEvent.VK_L || key == KeyEvent.VK_NUMPAD6) && !leftDirection) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
                canChangeDirection = false;
            }
            if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W || key == KeyEvent.VK_I || key == KeyEvent.VK_NUMPAD8) && !downDirection) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
                canChangeDirection = false;
            }
            if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S || key == KeyEvent.VK_K || key == KeyEvent.VK_NUMPAD5) && !upDirection) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
                canChangeDirection = false;
            }
        }
    }

    public boolean checkCollisionWithItself() {
        for (int i = 1; i < body.size(); i++) {
            if (head[0] == body.get(i)[0] && head[1] == body.get(i)[1]) {
                return true;
            }
        }
        return false;
    }

    public int[] getHead() {
        return head;
    }

    public void draw(Graphics g) {
        for (int i = 0; i < body.size(); i++) {
            int[] part = body.get(i);
            if (i == 0) { // Head of the snake
                g.setColor(color);
                g.fillOval(part[0], part[1], 10, 10);
                g.setColor(Color.black);
                g.drawOval(part[0], part[1], 10, 10);
                g.setColor(Color.black);
                g.fillOval(part[0] + 2, part[1] + 2, 2, 2);
                g.fillOval(part[0] + 6, part[1] + 2, 2, 2);
            } else {
                g.setColor(color);
                g.fillOval(part[0], part[1], 10, 10);
                g.setColor(Color.black);
                g.drawOval(part[0], part[1], 10, 10);
            }
        }
    }
    
    public boolean checkCollisionWithOtherSnake(Snake otherSnake) {
        for (int[] part : otherSnake.body) {
            if (head[0] == part[0] && head[1] == part[1]) {
                return true;
            }
        }
        return false;
    }
}
