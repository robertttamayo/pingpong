package com.madcoatgames.newpong.webutil;

public class DesktopActionResolver implements ActionResolver {
	public AdWorker adWorker = new AdWorker();

	@Override
	public void showOrLoadInterstital() {
		System.out.println("Loading or showing interstitial");
	}
	public void submitScores(){
		System.out.println("Doing the scores thing!");
	}
	@Override
	public boolean getSignedInGPGS() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void loginGPGS() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void submitScoreGPGS(long score, String leaderboard) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void unlockAchievementGPGS(String achievementId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getLeaderboardGPGS(String leaderboard) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getAchievementsGPGS() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void signOutGPGS() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean getSignButtonAvailable() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean getCanSignIn() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public AdWorker getAdWorker() {
		// TODO Auto-generated method stub
		return adWorker;
	}

}
