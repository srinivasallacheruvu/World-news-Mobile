package com.longbuffertech;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

public class ItemDataManager {
	
	static ItemDataManager dataManager = null;
	
	static final int TOP_STORIES_REQUEST_ID = 0;
	static final int WORLD_NEWS_REQUEST_ID = 1;
	static final int AMERICA_NEWS_REQUEST_ID = 2;
	static final int EUROPE_NEWS_REQUEST_ID = 3;
	static final int ASIA_NEWS_REQUEST_ID = 4;
	static final int AFRICA_NEWS_REQUEST_ID = 5;
	static final int MIDEAST_NEWS_REQUEST_ID = 6;
	static final int POLITICS_NEWS_REQUEST_ID = 7;
	static final int SPORT_NEWS_REQUEST_ID = 8;
	static final int ENTERTAINMENT_NEWS_REQUEST_ID = 9;
	static final int MUSIC_NEWS_REQUEST_ID = 10;
	static final int BUSINESS_NEWS_REQUEST_ID = 11;
	static final int HEALTH_NEWS_REQUEST_ID = 12;
	static final int SCIENCE_NEWS_REQUEST_ID = 13;
	static final int ENVIRONMENT_NEWS_REQUEST_ID = 14;
		
	ArrayList topStories = null;
	ArrayList worldNews = null;
	ArrayList americaNews = null;
	ArrayList europeNews = null;
	ArrayList asiaNews = null;
	ArrayList africaNews = null;
	ArrayList mideastNews = null;
	ArrayList politicsNews = null;
	ArrayList sportNews = null;
	ArrayList entertainmentNews = null;
	ArrayList musicNews = null;
	ArrayList businessNews = null;
	ArrayList healthNews = null;
	ArrayList scienceNews = null;
	ArrayList enviromentNews = null;
	
	int currentItem = -1 ;
		
	
	Context baseContext = null;
	public static ItemDataManager getItemManager(){
		if(dataManager == null){
			dataManager = new ItemDataManager();
		}
		return dataManager;
	}
	ItemDataManager(){
		
	}
	Context getContext(){
		return baseContext;
	}
	void setContext(Context context){
		baseContext = context ;
	}
	
	void setCurrentItem(int itemno){
		currentItem = itemno;
	}
	
	int getCurrentItem(){
		return currentItem;
	}
	
	ArrayList getTopstories(){
		return topStories;
	}
	
	void setTopstories(ArrayList list){
		topStories = list ;
	}
	
	ArrayList getWorldnews(){
		return worldNews;
	}
	
	void setWorldnews(ArrayList list){
		worldNews = list ;
	}
	
	ArrayList getAmericanews(){
		return americaNews;
	}
	
	void setAmericanews(ArrayList list){
		americaNews = list ;
	}
	ArrayList getEuropenews(){
		return europeNews;
	}
	
	void setEuropenews(ArrayList list){
		europeNews = list ;
	}
	
	ArrayList getAsianews(){
		return asiaNews;
	}
	
	void setAsianews(ArrayList list){
		asiaNews = list ;
	}
	
	ArrayList getAfricanews(){
		return africaNews;
	}
	
	void setAfricanews(ArrayList list){
		africaNews = list ;
	}
	
	ArrayList getMideastnews(){
		return mideastNews;
	}
	
	void setMideastnews(ArrayList list){
		mideastNews = list ;
	}
	
	ArrayList getSoprtssnews(){
		return sportNews;
	}
	
	void setSportnewsnews(ArrayList list){
		sportNews = list ;
	}
	
	ArrayList getEntertainmentnews(){
		return entertainmentNews;
	}
	
	void setEntertainmentnews(ArrayList list){
		entertainmentNews = list ;
	}
	
	ArrayList getMusicnews(){
		return musicNews;
	}
	
	void setMusicnews(ArrayList list){
		musicNews = list ;
	}
	
	ArrayList getBusinessnews(){
		return businessNews;
	}
	
	void setBusinessnews(ArrayList list){
		businessNews = list ;
	}
	ArrayList getHealthnews(){
		return healthNews;
	}
	
	void setHealthnews(ArrayList list){
		healthNews = list ;
	}
	ArrayList getSciencenews(){
		return scienceNews;
	}
	
	void setSciencenews(ArrayList list){
		scienceNews = list ;
	}
	ArrayList getEnvironmentnews(){
		return enviromentNews;
	}
	
	void setEnvironmentnews(ArrayList list){
		enviromentNews = list ;
	}
	ArrayList getPoliticsnews(){
		return politicsNews;
	}
	
	void setPoliticsnews(ArrayList list){
		politicsNews = list ;
	}
		
	ArrayList getNewsList(int requestId){
		ArrayList retList = null;
		switch (requestId) {
		case ItemDataManager.TOP_STORIES_REQUEST_ID:
			return topStories;
			
		case ItemDataManager.WORLD_NEWS_REQUEST_ID:
			
			return worldNews;
		case ItemDataManager.AMERICA_NEWS_REQUEST_ID:
			
			return americaNews;
		case ItemDataManager.EUROPE_NEWS_REQUEST_ID:
			
			return europeNews;

		case ItemDataManager.ASIA_NEWS_REQUEST_ID:
			
			return asiaNews;
		case ItemDataManager.AFRICA_NEWS_REQUEST_ID:
			
			return africaNews;

		case ItemDataManager.MIDEAST_NEWS_REQUEST_ID:
			
			return mideastNews;
		case ItemDataManager.POLITICS_NEWS_REQUEST_ID:
			
			return politicsNews;

		case ItemDataManager.SPORT_NEWS_REQUEST_ID:
			
			return sportNews;
		case ItemDataManager.ENTERTAINMENT_NEWS_REQUEST_ID:
			
			return entertainmentNews;

		case ItemDataManager.MUSIC_NEWS_REQUEST_ID:
			
			return musicNews;
		case ItemDataManager.SCIENCE_NEWS_REQUEST_ID:
			
			return scienceNews;

		case ItemDataManager.ENVIRONMENT_NEWS_REQUEST_ID:
			
			return enviromentNews;
		
		default:
			break;		
		}
		return retList;
	}
}
