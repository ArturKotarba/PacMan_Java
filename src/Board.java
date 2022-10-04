import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;




public class Board extends JPanel implements ActionListener {

    private Dimension d;

    private Image ii;


    private boolean inGame = false;
    private boolean dying = false;

    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 15;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;


    private final int MAX_GHOSTS = 12;
    private final int PACMAN_SPEED = 6;

    private int N_GHOSTS = 6;
    private int pacsLeft;
    private int[] dx, dy;
    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;

    private int pacman_x, pacman_y;
    private int req_dx, req_dy, view_dx, view_dy;

    Pacman player;
    Score Score;
    ShowIntroScreen introScreen;
    Points points;
    Ghost ghostP;

    private short levelData[] = {
            19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
            17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20,
            25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21,
            1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21,
            1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21,
            1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20,
            9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 25, 24, 24, 24, 28
    };

    private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxSpeed = 6;

    private int currentSpeed = 3;
    private short[] screenData;
    public Timer timer;


    private Image ghost;
    private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    private Image pacman3up, pacman3down, pacman3left, pacman3right;
    private Image pacman4up, pacman4down, pacman4left, pacman4right;


    public Board() {
        loadImages();
        initVariables();
        initGame();
        player = new Pacman(pacman_x, pacman_y, PACMAN_SPEED, BLOCK_SIZE, screenData);
        ghostP = new Ghost(screenData, BLOCK_SIZE, N_GHOSTS, N_BLOCKS, ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed, dx, dy, ghost, player);
        initBoard();
    }

    private void loadImages() {

        ghost = new ImageIcon("src/resources/images/ghost.png").getImage();
        pacman1 = new ImageIcon("src/resources/images/pacman.png").getImage();
        pacman2up = new ImageIcon("src/resources/images/up1.png").getImage();
        pacman3up = new ImageIcon("src/resources/images/up2.png").getImage();
        pacman4up = new ImageIcon("src/resources/images/up3.png").getImage();
        pacman2down = new ImageIcon("src/resources/images/down1.png").getImage();
        pacman3down = new ImageIcon("src/resources/images/down2.png").getImage();
        pacman4down = new ImageIcon("src/resources/images/down3.png").getImage();
        pacman2left = new ImageIcon("src/resources/images/left1.png").getImage();
        pacman3left = new ImageIcon("src/resources/images/left2.png").getImage();
        pacman4left = new ImageIcon("src/resources/images/left3.png").getImage();
        pacman2right = new ImageIcon("src/resources/images/right1.png").getImage();
        pacman3right = new ImageIcon("src/resources/images/right2.png").getImage();
        pacman4right = new ImageIcon("src/resources/images/right3.png").getImage();

    }

    private void initVariables() {

        screenData = new short[N_BLOCKS * N_BLOCKS];
        d = new Dimension(400, 400);
        ghost_x = new int[MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_y = new int[MAX_GHOSTS];
        ghost_dy = new int[MAX_GHOSTS];
        ghostSpeed = new int[MAX_GHOSTS];
        dx = new int[4];
        dy = new int[4];

        timer = new Timer(40, this);
        timer.start();
    }

    public void initGame() {
        pacsLeft = 3;
        initLevel();
        N_GHOSTS = 6;
        currentSpeed = 3;
    }

    private void initBoard() {

        if (player == null) {
            System.out.println("XDDD");
        }

        addKeyListener(new PacKeyEvent(inGame, player, timer));
        setFocusable(true);
        setBackground(Color.black);
    }


    private void drawPacman(Graphics2D g2d) {

        if (player.get_view_dx() == -1) {
            drawPacnanLeft(g2d);
        } else if (player.get_view_dx() == 1) {
            drawPacmanRight(g2d);
        } else if (player.get_view_dy() == -1) {
            drawPacmanUp(g2d);
        } else {
            drawPacmanDown(g2d);
        }
    }

    private void drawPacmanUp(Graphics2D g2d) {

        switch (player.get_pacmanAnimPos()) {
            case 1:
                g2d.drawImage(pacman2up, player.get_pacman_x() + 1, player.get_pacman_y() + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3up, player.get_pacman_x() + 1, player.get_pacman_y() + 1,this);
                break;
            case 3:
                g2d.drawImage(pacman4up, player.get_pacman_x() + 1, player.get_pacman_y() + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, player.get_pacman_x() + 1, player.get_pacman_y() + 1,this);
                break;
        }
    }

    private void drawPacmanDown(Graphics2D g2d) {

        switch (player.get_pacmanAnimPos()) {
            case 1:
                g2d.drawImage(pacman2down, player.get_pacman_x() + 1, player.get_pacman_y() + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3down, player.get_pacman_x() + 1, player.get_pacman_y() + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4down, player.get_pacman_x() + 1, player.get_pacman_y() + 1,this);
                break;
            default:
                g2d.drawImage(pacman1, player.get_pacman_x() + 1, player.get_pacman_y() + 1, this);
                break;
        }
    }

    private void drawPacnanLeft(Graphics2D g2d) {

        switch (player.get_pacmanAnimPos()) {
            case 1:
                g2d.drawImage(pacman2left, player.get_pacman_x() + 1, player.get_pacman_y() + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3left, player.get_pacman_x() + 1, player.get_pacman_y() + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4left, player.get_pacman_x() + 1 + 1, player.get_pacman_y() + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, player.get_pacman_x() + 1 + 1, player.get_pacman_y() + 1, this);
                break;
        }
    }

    private void drawPacmanRight(Graphics2D g2d) {

        switch (player.get_pacmanAnimPos()) {
            case 1:
                g2d.drawImage(pacman2right, player.get_pacman_x() + 1, player.get_pacman_y() + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3right, player.get_pacman_x() + 1, player.get_pacman_y() + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4right, player.get_pacman_x() + 1, player.get_pacman_y() + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, player.get_pacman_x() + 1, player.get_pacman_y() + 1, this);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private void playGame(Graphics2D g2d, Points points) {

        if (ghostP.getDying()) {
            death();
            ghostP.setDying(false);

        } else {
            player.movePacman();
            drawPacman(g2d);
            ghostP.moveGhosts(g2d);
            if (points.checkMaze(N_BLOCKS)) {
                initLevel();
            }
        }
    }

    private void initLevel() {

        int i;
        for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
            screenData[i] = levelData[i];
        }

        continueLevel();
    }

    private void continueLevel() {

        short i;
        int dx = 1;
        int random;

        for (i = 0; i < N_GHOSTS; i++) {

            ghost_y[i] = 4 * BLOCK_SIZE;
            ghost_x[i] = 4 * BLOCK_SIZE;
            ghost_dy[i] = 0;
            ghost_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }

            ghostSpeed[i] = validSpeeds[random];
        }

        pacman_x = 7 * BLOCK_SIZE;
        pacman_y = 11 * BLOCK_SIZE;
        req_dx = 0;
        req_dy = 0;
        view_dx = -1;
        view_dy = 0;
        dying = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);


        points = new Points(screenData, SCREEN_SIZE, BLOCK_SIZE, N_GHOSTS, MAX_GHOSTS, maxSpeed, currentSpeed);
        Score = new Score(g2d, SCREEN_SIZE, pacsLeft, pacman3left);
        introScreen = new ShowIntroScreen(SCREEN_SIZE);
        points.drawMaze(g2d);
        Score.drawScore();
        player.doAnim();

        if (PacKeyEvent.isInGame()) {
            playGame(g2d, points);
        } else {
            introScreen.show(g2d);
        }

        g2d.drawImage(ii, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    private void death() {

        pacsLeft--;
        System.out.println("pacsLeft " + pacsLeft);
        if (player != null) {

            player.set_pacman_x(pacman_x);
            player.set_pacman_y(pacman_y);

        }

        if (pacsLeft == 0) {
            Score.setScore(0);
            initGame();
            PacKeyEvent.setInGame(false);
        }
        continueLevel();
    }

}


