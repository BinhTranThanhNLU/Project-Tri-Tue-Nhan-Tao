package view;

import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;

public class StartScreen extends JFrame {
    private GameController controller;
    
    public StartScreen(GameController controller) {
        this.controller = controller;
        setTitle("Pokemon Selection");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Use a layered pane to handle the background and components separately
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 800, 600);

        // Set up background and UI components
        setupBackground(layeredPane);
        setupUI(layeredPane);

        // Add the layered pane to the frame
        add(layeredPane);
    }

    private void setupBackground(JLayeredPane layeredPane) {
        // Set up the background image using relative path
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/image/back3.png"));
        Image bgImage = bgIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setBounds(0, 0, 800, 600);

        // Add background to the lowest layer
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
    }

    private void setupUI(JLayeredPane layeredPane) {
        // Pokémon selection buttons with images and labels
        addPokemonSelection(layeredPane, "Charmander", new Status(39, 52, 43, 60, 50, 65), TypePokemon.FIRE,
                Move.TACKLE, Move.FLAMETHROWER, Move.GROWL, Move.SWORDS_DANCE, 1, "/image/chamander1.png", 150, 250);
        
        addPokemonSelection(layeredPane, "Squirtle", new Status(44, 48, 65, 50, 64, 43), TypePokemon.WATER,
                Move.TACKLE, Move.WATER_GUN, Move.WITHDRAW, Move.TAIL_WHIP, 1,  "/image/squirtle1.png", 350, 250);
        
        addPokemonSelection(layeredPane, "Bulbasaur", new Status(45, 49, 49, 65, 65, 45), TypePokemon.GRASS,
                Move.TACKLE, Move.VINE_WHIP, Move.GROWTH, Move.LEER, 1, "/image/bulbasaur1.png", 550, 250);
    }

	private void addPokemonSelection(JLayeredPane layeredPane, String name, Status status, TypePokemon type, Move move1,
			Move move2, Move move3, Move move4, int level, String imagePath, int x, int y) {
		// Create button for choosing Pokémon
		JButton button = new JButton("Choose " + name);
		button.setBounds(x, y + 240, 180, 30); // Đẩy nút xuống để tránh che chỉ số
		button.setBackground(Color.LIGHT_GRAY);
		button.setFocusPainted(false);

		// Add action listener to handle selection
		button.addActionListener(e -> {
			Pokemon pokemon = new Pokemon(name, type, imagePath, status, level);
			pokemon.addMoves(move1, move2, move3, move4);
			controller.setPlayerPokemon(pokemon);
			dispose(); // Close StartScreen to avoid multiple frames
		});

		// Pokémon icon display
		java.net.URL imageURL = getClass().getResource(imagePath);
		if (imageURL != null) {
			ImageIcon icon = new ImageIcon(imageURL);
			Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Tăng kích thước biểu tượng
			JLabel iconLabel = new JLabel(new ImageIcon(img));
			iconLabel.setBounds(x + 20, y, 100, 100); // Căn chỉnh lại biểu tượng
			layeredPane.add(iconLabel, JLayeredPane.PALETTE_LAYER); // Add image label above background
		} else {
			System.out.println("Could not find image: " + imagePath);
		}

		// Pokémon stats display with more space
		JLabel statsLabel = new JLabel("<html>" + "<table style='font-size:10px;'>" + // Giảm kích thước chữ nếu cần
				"<tr><td><b>Level:</b></td><td>" + level + "</td></tr>" + "<tr><td><b>HP:</b></td><td>" + status.getHp()
				+ "</td></tr>" + "<tr><td><b>ATK:</b></td><td>" + status.getAtk() + "</td></tr>"
				+ "<tr><td><b>DEF:</b></td><td>" + status.getDefense() + "</td></tr>"
				+ "<tr><td><b>SP ATK:</b></td><td>" + status.getSp_atk() + "</td></tr>"
				+ "<tr><td><b>SP DEF:</b></td><td>" + status.getSp_defense() + "</td></tr>"
				+ "<tr><td><b>SPD:</b></td><td>" + status.getSpeed() + "</td></tr>" + "</table></html>");
		statsLabel.setForeground(Color.WHITE);
		statsLabel.setBounds(x, y + 110, 180, 130); // Điều chỉnh để hiển thị đầy đủ
		layeredPane.add(statsLabel, JLayeredPane.PALETTE_LAYER); // Add stats label above background

		// Add button to the layeredPane
		layeredPane.add(button, JLayeredPane.PALETTE_LAYER);
	}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameController controller = new GameController();  // Assuming GameController handles transition
            StartScreen screen = new StartScreen(controller);
            screen.setVisible(true);
        });
    }
}
