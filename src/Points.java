import java.awt.*;

public class Points {

    Points(short screenData[], int screenSize, int blockSize, int nGhost, int mGhost, int maxSpeed, int currentSpeed) {
        this.screenData = screenData;
        dotColor = new Color(192, 192, 0);
        mazeColor = new Color(5, 100, 5);
        this.screenSize = screenSize;
        this.blockSize = blockSize;

        this.nGhost = nGhost;
        this.mGhost = mGhost;
        this.maxSpeed =maxSpeed;
        this.currentSpeed = currentSpeed;
    }

    public void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < screenSize; y += blockSize) {
            for (x = 0; x < screenSize; x += blockSize) {

                g2d.setColor(mazeColor);
                g2d.setStroke(new BasicStroke(2));

                if ((screenData[i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + blockSize - 1);
                }

                if ((screenData[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + blockSize - 1, y);
                }

                if ((screenData[i] & 4) != 0) {
                    g2d.drawLine(x + blockSize - 1, y, x + blockSize - 1,y + blockSize - 1);
                }

                if ((screenData[i] & 8) != 0) {
                    g2d.drawLine(x, y + blockSize - 1, x + blockSize - 1, y + blockSize - 1);
                }

                if ((screenData[i] & 16) != 0) {
                    g2d.setColor(dotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }
                i++;
            }
        }
    }

    public boolean checkMaze(int n_blocks) {

        short i = 0;
        boolean finished = true;

        while (i < n_blocks * n_blocks && finished) {

            if ((screenData[i] & 48) != 0) {
                finished = false;
            }
            i++;
        }

        if (finished) {

            Score.setScore(Score.getScore() + 50);

            if (nGhost < mGhost) {
                nGhost++;
            }

            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }
        }
        return finished;
    }


    private final Color dotColor;
    private int screenSize, blockSize, nGhost, mGhost, maxSpeed, currentSpeed;
    private Color mazeColor;
    private short screenData[];
}
