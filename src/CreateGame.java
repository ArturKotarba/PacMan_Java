import javax.swing.*;


public class CreateGame extends JFrame {

    CreateGame() {
        boardFroPacMan = new Board();
        add(boardFroPacMan);
        initUI();
    }

    private void initUI() {

        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 420);
        setLocationRelativeTo(null);
    }
    private Board boardFroPacMan;
}
