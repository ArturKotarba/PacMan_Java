import javax.swing.*;
import java.awt.*;

public class ShowIntroScreen extends JFrame {


    ShowIntroScreen(int screenSize) {
        this.screenSize = screenSize;
    }

    public void show(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, screenSize / 2 - 30, screenSize - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, screenSize / 2 - 30, screenSize - 100, 50);

        String s = "Press enter to start.";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (screenSize - metr.stringWidth(s)) / 2, screenSize / 2);
    }

    private static int screenSize;

}
