import javax.swing.*;
import java.awt.*;

public class Ghost extends JFrame {

    Ghost(short screenData[], int block_size, int n_ghosts, int n_blocks, int[] ghost_x,
          int[] ghost_y, int[] ghost_dx, int[] ghost_dy,int[] ghostSpeed, int[] dx, int[] dy,
          Image ghost, Pacman player) {

        this.n_ghosts = n_ghosts;
        this.n_blocks = n_blocks;
        this.block_size = block_size;
        this.ghost_x = ghost_x;
        this.ghost_y = ghost_y;
        this.ghost_dx = ghost_dx;
        this.ghost_dy = ghost_dy;
        this.ghostSpeed = ghostSpeed;
        this.dx = dx;
        this.dy = dy;
        this.ghost = ghost;
        this.screenData = screenData;
        this.player = player;
        this.dying = false;
    }

    public void moveGhosts(Graphics2D g2d) {

        short i;
        int pos;
        int count;


        for (i = 0; i < n_ghosts; i++) {
            if (ghost_x[i] % block_size == 0 && ghost_y[i] % block_size == 0) {
                pos = ghost_x[i] / block_size + n_blocks * (int) (ghost_y[i] / block_size);

                count = 0;

                if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        ghost_dx[i] = 0;
                        ghost_dy[i] = 0;
                    } else {
                        ghost_dx[i] = -ghost_dx[i];
                        ghost_dy[i] = -ghost_dy[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    ghost_dx[i] = dx[count];
                    ghost_dy[i] = dy[count];
                }

            }

            ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
            ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
            drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);

            if (player != null) {

                if (player.get_pacman_x() > (ghost_x[i] - 12) && player.get_pacman_x() < (ghost_x[i] + 12)
                        && player.get_pacman_y() > (ghost_y[i] - 12) && player.get_pacman_y() < (ghost_y[i] + 12)
                        && PacKeyEvent.isInGame()) {
                        dying = true;
                }
            }
        }
    }

    private void drawGhost(Graphics2D g2d, int x, int y) {
        g2d.drawImage(ghost, x, y, this);
    }
    public void setDying(boolean in_dying) {
        this.dying = in_dying;
    }

    public boolean getDying() {
        return dying;
    }

    private  boolean dying;
    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed, dx, dy;
    private short[] screenData;
    private int block_size, n_ghosts, n_blocks;
    private Pacman player;
    Image ghost;
}
