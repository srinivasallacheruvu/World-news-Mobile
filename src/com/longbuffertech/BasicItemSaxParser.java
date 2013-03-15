package com.longbuffertech;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

public class BasicItemSaxParser extends DefaultHandler{
	private String sourceData;
	private SAXParserFactory spf = null;
	private SAXParser sp = null;
	private XMLReader xr = null;
	private int requestId = -1;
	private ArrayList<BasicItem> itemList = null;
	private BasicItem currentItem = null;
	
	private boolean itemTag = false;
	private boolean titleTag = false;
	private boolean linkTag = false;
	private boolean sourceTag = false;
	private boolean descriptionTag = false;
	private boolean pubDateTag = false;
	private boolean guidTag = false;
	private boolean imageTag = false;
	
	private boolean isParsingStarted = false;
	private boolean isUrlParsing = false;
	private boolean itemInitiated = false;
	
	private String itemTitle;
	private String itemLink;
	private String itemSource;
	private String itemDescription;
	private String itemPubTime;
	private String itemGuid;
	private String itemImageUrl;
	private Bitmap itemBitmap;
	
	
	
	private String urlToparser;
	
	private MSaxParserResponseHandler saxObserver= null;
	BasicItemSaxParser(MSaxParserResponseHandler observer){
		saxObserver = observer ;
		spf = SAXParserFactory.newInstance();
		try {
			sp = spf.newSAXParser();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			xr = sp.getXMLReader();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xr.setContentHandler(this);
		
	}
	
	public void setSource(String source, int id){
		sourceData = source ;
		requestId = id ;
		isUrlParsing = false;
	}
	public void setSourceUrl(String url, int id){
		isUrlParsing = true;
		urlToparser = url;
		requestId = id;
	}
	public void startParsing(){
		itemTitle = null;
		itemLink = null;
		itemSource = null;
		itemDescription = null;
		itemPubTime = null;
		itemGuid = null;
		itemImageUrl = null;
		itemBitmap = null;
		
		if(isUrlParsing){
			try {
				URL url = new URL(urlToparser);				
				xr.parse(new InputSource(url.openStream()));				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				//xr.parse(new InputSource(new ByteArrayInputStream(sourceData.getBytes())));				
				xr.parse(new InputSource(new FileInputStream(sourceData)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void addElement(){
		BasicItem item = new BasicItem();
		item.setDescription(itemDescription);
		item.setGuid(itemGuid);
		item.setImageUrl(itemImageUrl);
		item.setLink(itemLink);
		item.setPubtime(itemPubTime);
		item.setSource(itemSource);
		item.setTitle(itemTitle);
		itemList.add(item);
	}
	
	
	@Override
	public void startElement(String uri, String localName, String qName,
	Attributes attributes) throws SAXException {
		if(localName.equals("channel")) {
			isParsingStarted = true ;
		}else if (localName.equals("item")) {
			if(itemList == null){
				itemList = new ArrayList<BasicItem>();
			}
			//currentItem = new BasicItem();
			itemTag = true ;
			itemInitiated = true ;
//			Toast.makeText(ItemDataManager.getItemManager().getContext(),"Item Stated" ,
//	                Toast.LENGTH_SHORT).show();
		}else if (localName.equals("title")) {
			titleTag = true;
		}else if (localName.equals("link")) {
			linkTag = true;
		}else if (localName.equals("source")) {
			sourceTag = true;
		}else if (localName.equals("description")) {
			descriptionTag = true;
		}else if (localName.equals("pubDate")) {
			pubDateTag = true;
		}else if (localName.equals("guid")) {
			guidTag = true;
		}else if (localName.equals("enclosure")) {
			imageTag = true;			
			itemImageUrl = attributes.getValue(1);
			
		}else{
			
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
	throws SAXException {
		if(localName.equals("rss")) {
			isParsingStarted = false ;
			saxObserver.SaxParserResponse(requestId, itemList);
		}else if (localName.equals("item")) {
			//itemList.add(currentItem);
			addElement();
			itemTitle = null;
			itemLink = null;
			itemSource = null;
			itemDescription = null;
			itemPubTime = null;
			itemGuid = null;
			itemImageUrl = null;
			itemBitmap = null;
			itemTag = false ;
			itemInitiated = false ;
//			Toast.makeText(ItemDataManager.getItemManager().getContext(),"Item end" ,
//	                Toast.LENGTH_SHORT).show();
		}else if (localName.equals("title")) {
			titleTag = false;
		}else if (localName.equals("link")) {
			linkTag = false;
		}else if (localName.equals("source")) {
			sourceTag = false;
		}else if (localName.equals("description")) {
			descriptionTag = false;
		}else if (localName.equals("pubDate")) {
			pubDateTag = false;
		}else if (localName.equals("guid")) {
			guidTag = false;
		}else if (localName.equals("enclosure")) {
			imageTag = false;
		}else{
			
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
	throws SAXException {
		if(length == 1 || length ==2 || length ==3){
			return;
		}
		if(!itemInitiated){
			return;
		}
		if(itemTag){
			
		}
		if(titleTag) {
			String title = new String(ch,start, length);			
			itemTitle = title.replace("\t", "");
		}
		if(linkTag) {
			String link = new String(ch,start, length);
			itemLink = link.replace("\t", "");						
		}		
		if(sourceTag) {
			String source = new String(ch,start, length);
			itemSource =source.replace("\t", "");						
		}
		if(descriptionTag) {
			String desc = new String(ch,start, length);
			itemDescription = desc.replace("\t", "");						
		}
		if(pubDateTag) {
			String pubdate = new String(ch,start, length);
			itemPubTime = pubdate.replace("\t", "");
			
		}
		if(guidTag) {
			String guid = new String(ch,start, length);
			itemGuid = guid.replace("\t", "");			
		}
		if(imageTag) {
			String imageurl = new String(ch,start, length);
			itemImageUrl = imageurl.replace("\t", "");
		}
	}
	
}
