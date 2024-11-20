package view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GameOverScreen extends JFrame {
    private int score = 0;
    private JLabel scoreLabel;

    public GameOverScreen() {
        // Display settings
        setTitle("Game Over Screen");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Custom panel to set background image
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the image with a null check
                ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/image/back3.png"));
                if (backgroundIcon != null) {
                    Image backgroundImage = backgroundIcon.getImage();
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    System.err.println("Background image not found!");
                }
            }
        };
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Game over label
        ImageIcon gameOverIcon = loadImageIcon("/image/gameover.png");
        if (gameOverIcon != null) {
            gameOverIcon = (ImageIcon) resizeIcon(gameOverIcon, 200, 80);
        } else {
            System.err.println("Game over image not found!");
        }
        JLabel titleLabel = new JLabel(gameOverIcon, SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Score display
        JPanel scorePanel = new JPanel();
        scorePanel.setOpaque(false);
        scorePanel.setLayout(new BorderLayout());

        scoreLabel = new JLabel(String.format("%05d", score), SwingConstants.CENTER);
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        scoreLabel.setForeground(new Color(255, 215, 0)); // Gold color
        scorePanel.add(scoreLabel, BorderLayout.CENTER);
        mainPanel.add(scorePanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout());

        // Replay button
        ImageIcon choiLaiIcon = loadImageIcon("/image/choilai.png");
        if (choiLaiIcon != null) {
            choiLaiIcon = (ImageIcon) resizeIcon(choiLaiIcon, 70, 30);
        } else {
            System.err.println("Replay button image not found!");
        }
        JButton choilaibutton = new JButton(choiLaiIcon);
        choilaibutton.setBorderPainted(false);
        choilaibutton.setContentAreaFilled(false);

        buttonPanel.add(choilaibutton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Icon resizing method
    private Icon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    // Method to load image icon safely with null checks
    private ImageIcon loadImageIcon(String path) {
        URL imageUrl = getClass().getResource(path);
        if (imageUrl != null) {
            return new ImageIcon(imageUrl);
        } else {
            System.err.println("Image not found at path: " + path);
            return null;
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            GameOverScreen gameOverScreen = new GameOverScreen();
//            gameOverScreen.setVisible(true);
//        });
//    }
}
