package com.longbuffertech;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LaunchingView extends Activity {
    /** Called when the activity is first created. */
	private ListView selectionList;
	private String initialSelection[]={"Top Stories","World","Americas","Europe"
			,"Asia","Africa","Mideast","Politics"
			,"Sport","Entertainment","Music","Business","Health","Science","Environment"};
	private final int DIALOG_SHOW_PROGRESS = 100 ;
	
	private String initialSelectionUrls[] = {
			"http://rss.wn.com/English/top-stories",
			"http://rss.wn.com/English/keyword/world",
			"http://rss.wn.com/English/keyword/america",
			"http://rss.wn.com/English/keyword/europe",
			"http://rss.wn.com/English/keyword/asia",
			"http://rss.wn.com/English/keyword/africa",
			"http://rss.wn.com/English/keyword/mideast",
			"http://rss.wn.com/English/keyword/sport",
			"http://rss.wn.com/English/keyword/entertainment",
			"http://rss.wn.com/English/keyword/music",
			"http://rss.wn.com/English/keyword/business",
			"http://rss.wn.com/English/keyword/health",
			"http://rss.wn.com/English/keyword/science",
			"http://rss.wn.com/English/keyword/environment"
			};	
	
	private LaunchingView currentView = null;	
	private ProgressDialog progressDialog = null;

	private int currentId = -1 ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    
        
        setContentView(R.layout.main);
        setTitle("World News");
        ItemDataManager.getItemManager().setContext(this);
        currentView = this;
        
        selectionList = (ListView)findViewById(R.id.InitialSelection);
        selectionList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , initialSelection));
        selectionList.setTextFilterEnabled(true);
        selectionList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {            	
            	currentId = (int)id ;              
            	ArrayList<BasicItem> mItems = null;
            	mItems = ItemDataManager.getItemManager().getNewsList(currentId);
            	if(mItems == null){            		
            		BasicItemSaxParser parser = new BasicItemSaxParser(new MSaxParserResponseHandler(){
						@Override
						public void SaxParserResponse(int requestId,
								ArrayList itemArray) {							
							currentView.SaxParserResponse(requestId,itemArray);
						}            			
            		});
            		parser.setSourceUrl(initialSelectionUrls[(int)id], (int)id);
            		parser.startParsing();            		
            		currentView.showDialog(DIALOG_SHOW_PROGRESS);
            		
            	}else{
            		currentView.SaxParserResponse(currentId, mItems);
            	}
            }
          });        
    }
    protected Dialog onCreateDialog(int id) {
    	Dialog dialog;
    	switch(id) {

    	case DIALOG_SHOW_PROGRESS:
    		dialog = ProgressDialog.show(currentView, "", 
    				"Loading. Please wait...", false);
    		dialog.setCancelable(true);
    		break;
    	default:
    		dialog = null;

    	}

    	return dialog;
    }
    protected void onStart (){
    	super.onStart();
    	removeDialog(DIALOG_SHOW_PROGRESS);
    }
    
	public void SaxParserResponse(int requestId, ArrayList itemArray) {
	
		if(currentId != requestId){
			return;
		}
		//dismissDialog(DIALOG_SHOW_PROGRESS);
		removeDialog(DIALOG_SHOW_PROGRESS);
		
//		// TODO Auto-generated method stub
//		String itemCount = "Request Id:" + requestId;
//		Toast.makeText(getApplicationContext(),itemCount,
//                Toast.LENGTH_SHORT).show();		
		ItemDataManager.getItemManager().setCurrentItem(requestId);
		switch (requestId) {
		case ItemDataManager.TOP_STORIES_REQUEST_ID:
			ItemDataManager.getItemManager().setTopstories(itemArray);
			//WriteToFile(itemArray);
			startActivity(new Intent(getBaseContext(), ItemListView.class));
			break;
		case ItemDataManager.WORLD_NEWS_REQUEST_ID:
			ItemDataManager.getItemManager().setWorldnews(itemArray);
			startActivity(new Intent(getBaseContext(), ItemListView.class));
			break;
		case ItemDataManager.AMERICA_NEWS_REQUEST_ID:
			ItemDataManager.getItemManager().setAmericanews(itemArray);
			startActivity(new Intent(getBaseContext(), ItemListView.class));
			break;
		case ItemDataManager.EUROPE_NEWS_REQUEST_ID:
			ItemDataManager.getItemManager().setEuropenews(itemArray);
			startActivity(new Intent(getBaseContext(), ItemListView.class));
			break;

		case ItemDataManager.ASIA_NEWS_REQUEST_ID:
			ItemDataManager.getItemManager().setAsianews(itemArray);
			startActivity(new Intent(getBaseContext(), ItemListView.class));
			break;
		case ItemDataManager.AFRICA_NEWS_REQUEST_ID:
			ItemDataManager.getItemManager().setAfricanews(itemArray);
			startActivity(new Intent(getBaseContext(), ItemListView.class));
			break;

		case ItemDataManager.MIDEAST_NEWS_REQUEST_ID:
			ItemDataManager.getItemManager().setMideastnews(itemArray);
			startActivity(new Intent(getBaseContext(), ItemListView.class));
			break;
		case ItemDataManager.POLITICS_NEWS_REQUEST_ID:
			ItemDataManager.getItemManager().setPoliticsnews(itemArray);
			startActivity(new Intent(getBaseContext(), ItemListView.class));
			break;

		case ItemDataManager.SPORT_NEWS_REQUEST_ID:
			ItemDataManager.getItemManager().setSportnewsnews(itemArray);
			startActivity(new Intent(getBaseContext(), ItemListView.class));
			break;
		case ItemDataManager.ENTERTAINMENT_NEWS_REQUEST_ID:
			ItemDataManager.getItemManager().setEntertainmentnews(itemArray);
			startActivity(new Intent(getBaseContext(), ItemListView.class));
			break;

		case ItemDataManager.MUSIC_NEWS_REQUEST_ID:
			ItemDataManager.getItemManager().setMusicnews(itemArray);
			startActivity(new Intent(getBaseContext(), ItemListView.class));
			break;
		case ItemDataManager.SCIENCE_NEWS_REQUEST_ID:
			ItemDataManager.getItemManager().setSciencenews(itemArray);
			startActivity(new Intent(getBaseContext(), ItemListView.class));
			break;

		case ItemDataManager.ENVIRONMENT_NEWS_REQUEST_ID:
			ItemDataManager.getItemManager().setEnvironmentnews(itemArray);
			startActivity(new Intent(getBaseContext(), ItemListView.class));
			break;
		
		default:
			break;
		}				
	}    
}