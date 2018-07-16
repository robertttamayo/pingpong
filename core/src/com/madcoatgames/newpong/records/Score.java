package com.madcoatgames.newpong.records;

public class Score implements Comparable<Score>{
	private int points;
	private String name;
	private int type = 0;
	
	public Score(){
		points = 0; 
		name = "default";
		type = 0;
	}
	public Score(int points, String name, int type){
		this.points = points;
		this.name = name;
		this.type = type;
	}
	public int getPoints(){
		return points;
	}
	public String getName(){
		return name;
	}
	public int getType() {
		return type;
	}
	@Override
	public int compareTo(Score other) {
		int otherPoints = other.getPoints();  
	    return this.points - otherPoints;
	}

}
