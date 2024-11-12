package model;

import java.util.ArrayList;
import java.util.Collections;

public class Pokemon {
    private String name;
    private Type type;        
    private String image;      
    private Status status;
    private ArrayList<Move> moves;

	public Pokemon(String name, Type type, String image, Status status) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.status = status;
        this.moves = new ArrayList<Move>();
    }
	
	public void addMoves(Move... newMoves) {
        if (newMoves.length > 4) {
            throw new IllegalArgumentException("Pokemon chỉ có thể có tối đa 4 moves.");
        }
        Collections.addAll(moves, newMoves);
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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

	@Override
	public String toString() {
        return "Pokemon{name='" + name + "', type=" + type + ", image='" + image + "', status=" + status + ", moves=" + moves + "}";
    }
}
