package model;

public class Status {
	
	private int hp;
	private int atk;
	private int defense;
	private int sp_atk;
	private int sp_defense;
	private int speed;
	
	public Status(int hp, int atk, int defense, int sp_atk, int sp_defense, int speed) {
		super();
		this.hp = hp;
		this.atk = atk;
		this.defense = defense;
		this.sp_atk = sp_atk;
		this.sp_defense = sp_defense;
		this.speed = speed;
	}
	
	

	@Override
	public String toString() {
		return "Status [hp=" + hp + ", atk=" + atk + ", defense=" + defense + ", sp_atk=" + sp_atk + ", sp_defense="
				+ sp_defense + ", speed=" + speed + "]";
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getSp_atk() {
		return sp_atk;
	}

	public void setSp_atk(int sp_atk) {
		this.sp_atk = sp_atk;
	}

	public int getSp_defense() {
		return sp_defense;
	}

	public void setSp_defense(int sp_defense) {
		this.sp_defense = sp_defense;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	
	

}
