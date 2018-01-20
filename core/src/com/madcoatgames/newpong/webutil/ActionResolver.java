package com.madcoatgames.newpong.webutil;

public interface ActionResolver {
	public static final long appId = 110432543899L;
	public static final String appIdString = "110432543899";
	//Leaderboard Ids
	public static final String fiestyId = "CgkIm8mrspsDEAIQAA";
	public static final String crazyId = "CgkIm8mrspsDEAIQAg";
	public static final String badId = "CgkIm8mrspsDEAIQAQ";
	
	//Achievements Ids
	public static final String nimbleKnuckles = "CgkIm8mrspsDEAIQBQ";
	public static final String madMarrow = "CgkIm8mrspsDEAIQBw";
	public static final String skullSkills = "CgkIm8mrspsDEAIQBg";
	public static final String headHunter = "CgkIm8mrspsDEAIQCQ";
	public static final String siestaSpecialist = "CgkIm8mrspsDEAIQCA";
	public static final String deadlyDays = "CgkIm8mrspsDEAIQCg";
	public static final String vertebraeVoodoo = "CgkIm8mrspsDEAIQCw";
	
	public void showOrLoadInterstital();
	public void submitScores();
	public AdWorker getAdWorker();
	
	//from tutuorial
	public boolean getSignedInGPGS();
	public void loginGPGS();
	public void submitScoreGPGS(long score, String leaderboard);
	public void unlockAchievementGPGS(String achievementId);
	public void getLeaderboardGPGS(String leaderboard);
	public void getAchievementsGPGS();
	public void signOutGPGS();
	public boolean getSignButtonAvailable();
	public boolean getCanSignIn();
}
