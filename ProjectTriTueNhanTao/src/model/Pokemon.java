package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Pokemon implements Cloneable {
    private String name;
    private TypePokemon type;        
    private String image;      
    private Status status;
    private ArrayList<Move> moves;
    private int level; // thuộc tính mới thêm vào
    private HashMap<String, Integer> buffStages; // Quản lý các stage buff/debuff

    public Pokemon(String name, TypePokemon type, String image, Status status, int level) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.status = status;
        this.moves = new ArrayList<Move>();
        this.level = level;
        this.buffStages = new HashMap<>(); // Khởi tạo buff stages
        initializeBuffStages(); // Đặt giá trị ban đầu cho buff stages
    }
    
    @Override
    public Pokemon clone() {
        try {
            Pokemon cloned = (Pokemon) super.clone();
            cloned.status = this.status.clone(); // Clone đối tượng Status
            cloned.moves = new ArrayList<>(this.moves); // Clone danh sách Move
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported for Pokemon", e);
        }
    }

	private void initializeBuffStages() {
        buffStages.put("Attack", 0);
        buffStages.put("Defense", 0);
        buffStages.put("SpecialAttack", 0);
        buffStages.put("SpecialDefense", 0);
        buffStages.put("Speed", 0);
    }

    public void addMoves(Move... newMoves) {
        if (newMoves.length > 4) {
            throw new IllegalArgumentException("Pokemon chỉ có thể có tối đa 4 moves.");
        }
        Collections.addAll(moves, newMoves);
    }

    // Getter và Setter cho các thuộc tính
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypePokemon getType() {
        return type;
    }

    public void setType(TypePokemon type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isSuperEffectiveAgainst(Pokemon other) {
        return type.isSuperEffectiveAgainst(other.getType());
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    // Quản lý buff/debuff stages
    public int getBuffStage(String stat) {
        return buffStages.getOrDefault(stat, 0);
    }

    public void setBuffStage(String stat, int stage) {
        if (stage < -6) stage = -6; // Stage tối thiểu là -6
        if (stage > 6) stage = 6;  // Stage tối đa là +6
        buffStages.put(stat, stage);
    }

    public void resetBuffStages() {
        initializeBuffStages(); // Reset tất cả các buff về 0
    }

    @Override
    public String toString() {
        return "Pokemon{name='" + name + "', type=" + type + ", image='" + image + "', status=" + status + 
               ", moves=" + moves + ", buffStages=" + buffStages + "}";
    }
}
