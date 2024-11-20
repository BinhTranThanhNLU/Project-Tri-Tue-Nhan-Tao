package model;

public enum TypePokemon {

    NORMAL(new String[]{},
            new String[]{"Rock", "Steel"},
            new String[]{"Ghost"}),
    FIRE(new String[]{"Grass"},
            new String[]{"Fire", "Water"},
            new String[]{}),
    WATER(new String[]{"Fire"},
            new String[]{"Water", "Grass"},
            new String[]{}),
    GRASS(new String[]{"Water"},
            new String[]{"Fire", "Grass"},
            new String[]{});

    private final String[] SUPER_EFFECTIVE;
    private final String[] NOT_VERY_EFFECTIVE;
    private final String[] NO_EFFECT;

    TypePokemon(String[] superEffective, String[] notVeryEffective, String[] noEffect) {
        this.SUPER_EFFECTIVE = superEffective;
        this.NOT_VERY_EFFECTIVE = notVeryEffective;
        this.NO_EFFECT = noEffect;
    }

    private boolean contains(String[] types, TypePokemon type) {
        for (String t : types) {
            if (t.equalsIgnoreCase(type.name())) {
                return true;
            }
        }
        return false;
    }

    // Method to calculate effectiveness multiplier
    public double getEffectiveness(TypePokemon targetType) {
        if (contains(SUPER_EFFECTIVE, targetType)) {
            return 2.0;
        } else if (contains(NOT_VERY_EFFECTIVE, targetType)) {
            return 0.5;
        } else if (contains(NO_EFFECT, targetType)) {
            return 0.0;
        } else {
            return 1.0; // Neutral effectiveness
        }
    }

    public boolean isSuperEffectiveAgainst(TypePokemon type) {
        return contains(SUPER_EFFECTIVE, type);
    }

    public boolean isNotVeryEffectiveAgainst(TypePokemon type) {
        return contains(NOT_VERY_EFFECTIVE, type);
    }

    public boolean hasNoEffectOn(TypePokemon type) {
        return contains(NO_EFFECT, type);
    }

    public boolean isNormalAgainst(TypePokemon type) {
        return !isSuperEffectiveAgainst(type) && !isNotVeryEffectiveAgainst(type) && !hasNoEffectOn(type);
    }
}
