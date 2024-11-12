package model;

public enum Move {
    
    TACKLE("Tackle", Type.NORMAL, 40),
    FLAMETHROWER("Flamethrower", Type.FIRE, 90);

    private final String NAME;
    private final Type TYPE;
    private final int POWER;

    Move(String name, Type type, int power) {
        this.NAME = name;
        this.TYPE = type;
        this.POWER = power;
    }

    public String getName() {
        return NAME;
    }

    public Type getType() {
        return TYPE;
    }

    public int getPower() {
        return POWER;
    }
}
