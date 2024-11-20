package model;

public enum Move {
    
    // Charmander's moves
    TACKLE("Tackle", TypePokemon.NORMAL, 40, MoveType.PHYSICAL),                   	// Normal attack
    FLAMETHROWER("Flamethrower", TypePokemon.FIRE, 90, MoveType.SPECIAL),         	// Fire attack
    GROWL("Growl", TypePokemon.NORMAL, 0, MoveType.STATUS),							// Lowers target's Attack
    SWORDS_DANCE("Swords Dance", TypePokemon.NORMAL, 0, MoveType.STATUS),			
    //SMOKESCREEN("Smokescreen", Type.NORMAL, 0),          	// Lowers target's Accuracy

    // Squirtle's moves
    WATER_GUN("Water Gun", TypePokemon.WATER, 40, MoveType.SPECIAL),              // Water attack
    BUBBLE("Bubble", TypePokemon.WATER, 40, MoveType.SPECIAL),                    // Water attack
    WITHDRAW("Withdraw", TypePokemon.WATER, 0, MoveType.STATUS),                 // Raises user's Defense
    TAIL_WHIP("Tail Whip", TypePokemon.NORMAL, 0, MoveType.STATUS),              // Lowers target's S.Defense

    // Bulbasaur's moves
    VINE_WHIP("Vine Whip", TypePokemon.GRASS, 45, MoveType.PHYSICAL),              // Grass attack
    RAZOR_LEAF("Razor Leaf", TypePokemon.GRASS, 55, MoveType.SPECIAL),            // Grass attack
    GROWTH("Growth", TypePokemon.NORMAL, 0, MoveType.STATUS),                    // Raises user's Attack and Sp. Atk
    LEER("Leer", TypePokemon.NORMAL, 0, MoveType.STATUS),                        // Lowers target's Defense

    // Additional moves for variety
    SCRATCH("Scratch", TypePokemon.NORMAL, 40, MoveType.PHYSICAL),                 // Normal attack
    BITE("Bite", TypePokemon.NORMAL, 60, MoveType.PHYSICAL),                       // Normal attack
    //DRAGON_RAGE("Dragon Rage", Type.FIRE, 0),          // Deals fixed damage
    TAIL_SLAP("Tail Slap", TypePokemon.NORMAL, 75, MoveType.PHYSICAL);             // Normal attack

    private final String NAME;
    private final TypePokemon TYPE;
    private final int POWER;
    private final MoveType MOVE;

    Move(String name, TypePokemon type, int power, MoveType move) {
        this.NAME = name;
        this.TYPE = type;
        this.POWER = power;
        this.MOVE = move;
    }

	public String getNAME() {
		return NAME;
	}

	public TypePokemon getTYPE() {
		return TYPE;
	}

	public int getPOWER() {
		return POWER;
	}

	public MoveType getMOVE() {
		return MOVE;
	}


	
    
    
}
