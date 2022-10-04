import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class PacKeyEvent extends KeyAdapter {

    PacKeyEvent(boolean inGame, Pacman player, Timer timer) {
        this.inGame = inGame;
        this.player = player;
        this.timer = timer;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (inGame) {
            if (key == KeyEvent.VK_LEFT) {
                player.set_req_dx(-1);
                player.set_req_dy(0);
            } else if (key == KeyEvent.VK_RIGHT) {
                player.set_req_dx(1);
                player.set_req_dy(0);
            } else if (key == KeyEvent.VK_UP) {
                player.set_req_dx(0);
                player.set_req_dy(-1);
            } else if (key == KeyEvent.VK_DOWN) {
                player.set_req_dx(0);
                player.set_req_dy(1);
            } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                inGame = false;
            } else if (key == KeyEvent.VK_SPACE) {
                if (timer.isRunning()) {
                    timer.stop();
                } else {
                    timer.start();
                }
            }
        } else {
            if (key == KeyEvent.VK_ENTER) {
                setInGame(true);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT
                || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            // TO DO:
        }
    }

    public static void setInGame(boolean in_inGame) {
        inGame =  in_inGame;
    }
    public static boolean isInGame() {
        return inGame;
    }

    private int req_dx, req_dy;
    private static boolean inGame;
    private Timer timer;
    Pacman player;
}