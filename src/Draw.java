/*************************************************************************
 > File Name: Draw.java
 > Author: Samuel
 > Mail: enminghuang21119@gmail.com
 > Created Time: 六  4/21 22:26:52 2018
 *************************************************************************/

package snake;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Point;

@SuppressWarnings("serial")
//繪圖部分
public class Draw extends JPanel
{
    static final int scale = Snake.SCALE;
    static final Color lightViolet = new Color(238, 130, 238);
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Snake snake = Snake.snake;
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, 61 * scale, 61 * scale);
        g.setColor(Color.BLACK);
        for (int i = 1; i <= 59; i++) {
            g.drawLine(0,  i * scale,  60 * scale, i * scale);
            g.drawLine(i * scale,  0,  i * scale, 60 * scale);
        }
        g.setColor(Color.RED);
        g.fillRect(snake.food[0].x * scale, snake.food[0].y * scale, scale, scale);
        g.setColor(Color.MAGENTA);
        if (snake.food1Time % (3.5 * 18) >= 47)
            g.setColor(lightViolet);
        g.fillRect(snake.food[1].x * scale, snake.food[1].y * scale, scale, scale);
        String string = "長   度  : " + snake.length + "    分   數  : " + snake.score + "    時    間  : " + snake.time / 18;

        g.setColor(Color.GREEN);
        for (Point point : snake.snakeBody)
            g.fillRect(point.x * scale, point.y * scale, scale, scale);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(snake.head.x * scale, snake.head.y * scale, scale, scale);

        g.setColor(Color.CYAN);
        g.fillRect(0, 0, 61 * scale, scale);
        g.fillRect(0, 60 * scale, 61 * scale, scale);
        g.fillRect(0, 0, scale, 61 * scale);
        g.fillRect(60 * scale, 0, scale, 61 * scale);

        g.setColor(Color.BLACK);
        g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), scale - 2);

        boolean print = false;
        g.setColor(Color.WHITE);
        if (snake.game_over) {
            string = "遊   戲   結   束   ！   請  按  下   空  白  鍵   或   P   重  新  開  始  遊  戲。             ";
            print = true;
        }
        else if (snake.paused && !snake.game_over) {
            string = "已         暫         停！";
            print = true;
        }
        if (print)
            g.drawString(string, (int)(getWidth() / 2 - string.length() * 2.5f), (int)(snake.dim.getHeight() / 2 - 80));
    }
}