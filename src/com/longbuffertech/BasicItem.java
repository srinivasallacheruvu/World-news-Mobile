package com.longbuffertech;

import android.graphics.Bitmap;

class BasicItem extends Object{
	
	private String itemTitle;
	private String itemLink;
	private String itemSource;
	private String itemDescription;
	private String itemPubTime;
	private String itemGuid;
	private String itemImageUrl;
	private Bitmap itemBitmap;
	private Bitmap originalBitmap;
	
	BasicItem(){
		itemTitle = null;
		itemLink = null;
		itemSource = null;
		itemDescription = null;
		itemPubTime = null;
		itemGuid = null;
		itemImageUrl = null;
		itemBitmap = null;
		originalBitmap = null;
	}
	public void setTitle(String title){
		itemTitle = title ;
	}
	public String getTitle(){
		return itemTitle;
	}
	
	public void setLink(String link){
		itemLink = link ;
	}
	public String getLink(){
		return itemLink;
	}
	
	public void setSource(String source){
		itemSource = source ;
	}
	public String getSource(){
		return itemSource;
	}
	public String getDescription(){
		return itemDescription;
	}
	public void setDescription(String description){
		itemDescription = description ;
	}
	public String getPubtime(){
		return itemPubTime;
	}
	public void setPubtime(String pubtime){
		itemPubTime = pubtime;
	}
	public String getGuid(){
		return itemGuid;
	}
	public void setGuid(String guid){
		itemTitle = guid ;
	}
	public String getImageurl(){
		return itemImageUrl;
	}
	public void setImageUrl(String imageurl){
		itemImageUrl = imageurl ;
	}
	public Bitmap getBitmap(){
		return itemBitmap;
	}
	public void setBitmap(Bitmap image){
		itemBitmap = image ;
	}
	public Bitmap getOriginalBitmap(){
		return originalBitmap;
	}
	public void setOriginalBitmap(Bitmap image){
		originalBitmap = image ;
	}
}
