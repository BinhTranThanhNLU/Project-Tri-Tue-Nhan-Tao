package controller;

import model.*;
import view.BattleScreen;
import view.GameOverScreen;
import view.StartScreen;

import java.util.ArrayList;
import java.util.Random;

public class GameController {
    private Pokemon playerPokemon;
    private Pokemon opponentPokemon;
    private StartScreen startScreen;
    private Battle battle;

    public GameController() {
        // Initialize StartScreen and show it
        startScreen = new StartScreen(this);
        startScreen.setVisible(true);
    }

    public void setPlayerPokemon(Pokemon chosenPokemon) {
        this.playerPokemon = chosenPokemon;
        
        // AI chooses the opponent's Pokémon
        this.opponentPokemon = chooseOpponentPokemon();

        // Initialize Battle model
        battle = new Battle(playerPokemon, opponentPokemon);

        // Start the battle in BattleScreen
        startBattle();
    }

    private Pokemon chooseOpponentPokemon() {
        ArrayList<Pokemon> availablePokemons = getAvailablePokemons();

        // Randomly select a Pokémon for the opponent and set its level randomly between 1 and 100
        Random random = new Random();
        Pokemon selectedPokemon = availablePokemons.get(random.nextInt(availablePokemons.size()));
        selectedPokemon.setLevel(1); // Set level 5
        return selectedPokemon;
    }



    private ArrayList<Pokemon> getAvailablePokemons() {
        ArrayList<Pokemon> availablePokemons = new ArrayList<>();

        // Define sample Pokémon with statuses and moves
		// Charmander's Status (balanced with high Attack moves)
		Status charmanderStatus = new Status(39, 52, 43, 60, 50, 65);
		Pokemon charmander = new Pokemon("Charmander", TypePokemon.FIRE, "/image/chamander1.png", charmanderStatus, 1);
		charmander.addMoves(Move.TACKLE, Move.FLAMETHROWER, Move.GROWL, Move.SWORDS_DANCE);
		availablePokemons.add(charmander);

		// Squirtle's Status (higher Defense due to Withdraw move, balanced Attack)
		Status squirtleStatus = new Status(44, 48, 65, 50, 64, 43);
		Pokemon squirtle = new Pokemon("Squirtle", TypePokemon.WATER, "/image/squirtle1.png", squirtleStatus, 1);
		squirtle.addMoves(Move.TACKLE, Move.WATER_GUN, Move.WITHDRAW, Move.TAIL_WHIP);
		availablePokemons.add(squirtle);

		// Bulbasaur's Status (balanced stats for Grass-type moves and Growth move)
		Status bulbasaurStatus = new Status(45, 49, 49, 65, 65, 45);
		Pokemon bulbasaur = new Pokemon("Bulbasaur", TypePokemon.GRASS, "/image/bulbasaur1.png", bulbasaurStatus, 1);
		bulbasaur.addMoves(Move.TACKLE, Move.VINE_WHIP, Move.GROWTH, Move.LEER);
		availablePokemons.add(bulbasaur);

        
        return availablePokemons;
    }


    private void startBattle() {
        // Hide StartScreen and show BattleScreen
        startScreen.setVisible(false);
        BattleScreen battleScreen = new BattleScreen(this, playerPokemon, opponentPokemon);
        battleScreen.setVisible(true);
    }

    public Battle getBattle() {
        return battle;
    }
    
    public void showGameOverScreen(int score) {
        GameOverScreen gameOverScreen = new GameOverScreen();
        //gameOverScreen.setScore(score); // Add a method in GameOverScreen to set the score
        gameOverScreen.setVisible(true);
    }


    public static void main(String[] args) {
        new GameController();
    }
}
