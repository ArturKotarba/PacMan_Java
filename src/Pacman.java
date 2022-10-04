import javax.swing.JFrame;

public class Pacman {

    public Pacman() {
        set_req_dx(0);
        set_req_dy(0);
    }

    public Pacman(int pacman_x, int pacman_y, int pacmanSpeed, int blockSize, short[] screenData) {
        set_req_dx(0);
        set_req_dy(0);
        set_pacman_x(pacman_x);
        set_pacman_y(pacman_y);
        pacmand_x = 0;
        pacmand_y = 0;
        view_dx = -1;
        view_dy = 0;
        this.blockSize = blockSize;
        this.screenData = screenData;
        this.pacmanSpeed = pacmanSpeed;
        pacAnimCount = Pac_Anim_Delay;
        pacmanAnimPos = 0;
        pacAnimDir = 1;
    }

    public void movePacman() {

        int pos;
        short ch;

        if (req_dx == -pacmand_x && req_dy == -pacmand_y) {
            pacmand_x = req_dx;
            pacmand_y = req_dy;
            view_dx = pacmand_x;
            view_dy = pacmand_y;
        }

        if (pacman_x % blockSize == 0 && pacman_y % blockSize == 0) {
            pos = pacman_x / blockSize + 15 * (int) (pacman_y / blockSize);
            ch = screenData[pos];

            if ((ch & 16) != 0) {
                screenData[pos] = (short) (ch & 15);

                    Score.setScore(Score.getScore() + 1);
            }

            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    pacmand_x = req_dx;
                    pacmand_y = req_dy;
                    view_dx = pacmand_x;
                    view_dy = pacmand_y;
                }
            }

            // Check for standstill
            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
                pacmand_x = 0;
                pacmand_y = 0;
            }
        }
        pacman_x = pacman_x + pacmanSpeed * pacmand_x;
        pacman_y = pacman_y + pacmanSpeed * pacmand_y;
    }


    public void doAnim() {

        pacAnimCount--;

        if (pacAnimCount <= 0) {
            pacAnimCount = Pac_Anim_Delay;
            pacmanAnimPos = pacmanAnimPos + pacAnimDir;

            if (pacmanAnimPos == (pacman_aim_count - 1) || pacmanAnimPos == 0) {
                pacAnimDir = -pacAnimDir;
            }
        }
    }

    public void set_req_dx(int req_dx) {
        this.req_dx = req_dx;
    }

    public void set_req_dy(int req_dy) {
        this.req_dy = req_dy;
    }

    public int get_req_dx() {
        return req_dx;
    }

    public int get_req_dy() {
        return  req_dy;
    }

    public void set_pacman_x(int pacman_x) {
        this.pacman_x = pacman_x;
    }

    public void set_pacman_y(int pacman_y) {
        this.pacman_y = pacman_y;
    }

    public int get_pacman_x() {
        return pacman_x;
    }

    public int get_pacman_y() {
        return  pacman_y;
    }

    public void set_view_dx(int in_view_dx)
    {
         this.view_dx = in_view_dx;
    }

    public void set_view_dy(int in_view_dy)
    {
        this.view_dy = in_view_dy;
    }

    public int get_view_dx() {
        return view_dx;
    }

    public int get_view_dy() {
        return  view_dy;
    }


    public void set_pacmanAnimPos(int pacmanAnimPos) {
        this.pacmanAnimPos = pacmanAnimPos;
    }
    public int get_pacmanAnimPos() {
        return pacmanAnimPos;
    }


    private int pacman_x, pacman_y;
    private int req_dx, req_dy;
    private int pacmand_x, pacmand_y;
    private int pacmanSpeed;
    private int view_dx, view_dy;
    private int blockSize;
    private short screenData[];
    private final int Pac_Anim_Delay = 2;
    private int pacAnimCount;
    private int pacmanAnimPos;
    private int pacAnimDir;
    private final int pacman_aim_count = 4;
}








//System.out.println("pacmanAnimPos " + pacmanAnimPos);
//System.out.println("pacAnimDir " + pacAnimDir);