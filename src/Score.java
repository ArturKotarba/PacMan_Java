import javax.swing.*;
import java.awt.*;

public class Score extends JFrame {
    Score(Graphics2D g2d, int screenSize, int pacsLeft, Image pacman3left) {
        this.g2d = g2d;
        smallFont = new Font("Helvetica", Font.BOLD, 14);
        this.screenSize = screenSize;
        this.pacsLeft = pacsLeft;
        this.pacman3left = pacman3left;
    }

    public void drawScore() {
        String s;
        g2d.setFont(smallFont);
        g2d.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        g2d.drawString(s, screenSize / 2 + 96, screenSize + 16);

        for (int i = 0; i < pacsLeft; i++) {
            g2d.drawImage(pacman3left, i * 28 + 8, screenSize + 1, this);
        }
    }

    public static void setScore(int in_newScore) {
        score = in_newScore;
    }

    public static int getScore() {
        return score;
    }

    private static int score = 0;
    private final Font smallFont;
    private Graphics2D g2d;
    private int screenSize, pacsLeft;
    private Image pacman3left;
}
