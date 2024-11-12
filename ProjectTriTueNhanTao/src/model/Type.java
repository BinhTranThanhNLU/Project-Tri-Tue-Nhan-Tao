package model;

public enum Type {
	
	NORMAL(new String[]{},
            new String[]{"Rock", "Steel"},
            new String[]{"Ghost"}),
    FIRE(new String[] { "Grass"},
            new String[] { "Fire", "Water"},
            new String[] {}),
    WATER(new String[] { "Fire"},
            new String[] { "Water", "Grass"},
            new String[] {}),
    GRASS(new String[] { "Water"},
            new String[] { "Fire", "Grass"},
            new String[] {});

    private final String[] superEffective;
    private final String[] notVeryEffective;
    private final String[] noEffect;

    Type(String[] superEffective, String[] notVeryEffective, String[] noEffect) {
        this.superEffective = superEffective;
        this.notVeryEffective = notVeryEffective;
        this.noEffect = noEffect;
    }

    private boolean contains(String[] types, Type type) {
        for (String t : types) {
            if (t.equalsIgnoreCase(type.name())) {
                return true;
            }
        }
        return false;
    }

    private Type[] convert(String[] types) {
        Type[] typeEnumArray = new Type[types.length];
        for (int i = 0; i < types.length; i++) {
            typeEnumArray[i] = valueOf(types[i]);
        }
        return typeEnumArray;
    }

    public boolean isSuperEffectiveAgainst(Type type) {
        return contains(superEffective, type);
    }

    public boolean isNotVeryEffectiveAgainst(Type type) {
        return contains(notVeryEffective, type);
    }

    public boolean hasNoEffectOn(Type type) {
        return contains(noEffect, type);
    }

    public boolean isNormalAgainst(Type type) {
        return !isSuperEffectiveAgainst(type) && !isNotVeryEffectiveAgainst(type) && !hasNoEffectOn(type);
    }

    public Type[] getTypesSuperEffectiveAgainst() {
        return convert(superEffective);
    }

    public Type[] getTypesNotVeryEffectiveAgainst() {
        return convert(notVeryEffective);
    }

    public Type[] getTypesNoEffectAgainst() {
        return convert(noEffect);
    }
}
