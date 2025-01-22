import javax.swing.*;
import java.awt.*;

public class CheckersGame extends JFrame {

    private CheckersBoard board;

    public CheckersGame() {
        setTitle("Checkers Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setResizable(false); // Prevent resizing for simpler drawing
        setLayout(new BorderLayout());

        board = new CheckersBoard();
        add(board, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CheckersGame());
    }
}
