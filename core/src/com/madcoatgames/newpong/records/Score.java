package com.madcoatgames.newpong.records;

public class Score implements Comparable<Score>{
	private int points;
	private String name;
	
	public Score(){
		points = 0; name = "default";
	}
	public Score(int points, String name){
		this.points = points;
		this.name = name;
	}
	public int getPoints(){
		return points;
	}
	public String getName(){
		return name;
	}
	@Override
	public int compareTo(Score other) {
		int otherPoints = other.getPoints();  
	    return this.points - otherPoints;
	}

}
