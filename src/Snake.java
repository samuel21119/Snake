/*************************************************************************
 > File Name: Snake.java
 > Author: Samuel
 > Mail: enminghuang21119@gmail.com
 > Created Time: 六  4/21 22:26:52 2018
 *************************************************************************/
package snake;
//匯入需要的套件
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.JFrame;

import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Snake implements ActionListener, KeyListener {
    //宣告變數
    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 13; // 60 * 60
    public boolean change_dir, game_over, paused, move;
    public static Snake snake;
    public JFrame jframe;
    public Draw draw;
    public Timer timer = new Timer(25, this);
    public int dir, score , length, time, food1Time;
    public Point head;
    public Point[] food = new Point[2];
    public ArrayList<Point> snakeBody = new ArrayList<Point>();
    public Random random;
    public Dimension dim;
    //main函式
    public static void main(String[] args) {
        snake = new Snake();
    }

    //遊戲開始時初始化部分
    public void start() {
        game_over = false;
        paused = false;
        score = 0;
        change_dir = true;
        move = true;
        time = food1Time = 0;
        dir = LEFT;
        length = 4;
        random = new Random();
        food[0] = new Point(random.nextInt(57), random.nextInt(57));
        foodRandom(food, 1, 0);
        head = new Point(31, 31);
        snakeBody.clear();
        timer.start();
    }

    //遊戲畫面初始化
    public Snake() {
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        jframe = new JFrame("貪 食 蛇");
        jframe.setVisible(true);
        jframe.setSize(61 * SCALE, (61 + 1) * SCALE + 6);
        jframe.setResizable(false);
        jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
        jframe.add(draw = new Draw());
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.addKeyListener(this);

        start();
    }

    //改變蛇的方向
    public void actionPerformed(ActionEvent arg0) {
        draw.repaint();
        if (move = !move && head != null && !game_over && !paused) {
            food1Time++;
            time++;
            change_dir = true;
            snakeBody.add(new Point(head.x, head.y));
            switch (dir) {
                case UP:
                    if (head.y - 1 >= 1 && at(head.x, head.y - 1))
                        head = new Point(head.x, head.y - 1);
                    else
                        game_over = true;
                    break;
                case DOWN:
                    if (head.y + 1 < 60 && at(head.x, head.y + 1))
                        head = new Point(head.x, head.y + 1);
                    else
                        game_over = true;
                    break;
                case LEFT:
                    if (head.x - 1 >= 1 && at(head.x - 1, head.y))
                        head = new Point(head.x - 1, head.y);
                    else
                        game_over = true;
                    break;
                default:
                case RIGHT:
                    if (head.x + 1 < 60 && at(head.x + 1, head.y))
                        head = new Point(head.x + 1, head.y);
                    else
                        game_over = true;
                    break;
            }
            if (snakeBody.size() > length)
                snakeBody.remove(0);
            if (food[0] != null || food[1] != null) {
                if (head.equals(food[0])) {
                    score += 10;
                    length++;
                    foodRandom(food, 0, 1);
                }
                else if (head.equals(food[1])) {
                    score += 25;
                    length += 2;
                    foodRandom(food, 1, 0);
                    food1Time = 0;
                }
            }
            if (food1Time % ((float)3.5 * 18) == 0 && food1Time > 0)
                foodRandom(food, 1, 0);
        }
    }

    //偵測按下的按鍵
    public void keyPressed(KeyEvent a) {
        int i = a.getKeyCode();
        if (!change_dir && !paused && !game_over)
            return;
        change_dir = false;
        switch (i) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if (dir == DOWN || paused || game_over)
                    break;
                dir = UP;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (dir == UP || paused || game_over)
                    break;
                dir = DOWN;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if (dir == RIGHT || paused || game_over)
                    break;
                dir = LEFT;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if (dir == LEFT || paused || game_over)
                    break;
                dir = RIGHT;
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_P:
                change_dir = true;
                if (game_over)
                    start();
                else
                    paused = !paused;
                break;
            default:
                change_dir = true;
        }
    }

    public void keyReleased(KeyEvent a) {
    }

    public void keyTyped(KeyEvent a) {
    }

    //隨機生成食物位置(Call by reference)
    public void foodRandom(Point[] in, int a, int b) {
        in[a] = in[b];
        while (in[a] == in[b])
            in[a] = new Point(random.nextInt(57) + 1, random.nextInt(57) + 1);
    }

    //判斷使否有 尾巴 在指定(x, y)的座標上
    public boolean at(int x, int y) {
        for (Point i : snakeBody)
            if (i.equals(new Point(x, y)))
                return false;
        return true;
    }
}