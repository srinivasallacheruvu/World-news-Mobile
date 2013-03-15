package com.longbuffertech;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ItemListView extends ListActivity{
	
	private ProgressDialog m_ProgressDialog = null;
    private ArrayList<BasicItem> m_items = null;
    private BasicItemAdapter m_adapter;
    private Runnable viewOrders;
    private Bitmap mIcon1 = null; 
    private Button refreshButton = null;
    public boolean isItemLoading = true;
    
    
    private String initialSelectionUrls[]={"http://rss.wn.com/English/top-stories",
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
			"http://rss.wn.com/English/keyword/environment"};
    
    
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE );
        setContentView(R.layout.item_list_view);
        

      //  getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
       // 		R.drawable.refresh);

        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlelayout);

        
        mIcon1 = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        
        refreshButton = (Button)findViewById(R.id.refreshbutton);
        refreshButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	if(!isItemLoading){
            	startProgress();
            	BasicItemSaxParser parser = new BasicItemSaxParser(new MSaxParserResponseHandler(){

					@Override
					public void SaxParserResponse(int requestId,
							ArrayList itemArray) {
						// TODO Auto-generated method stub

						
						if(ItemDataManager.getItemManager().getCurrentItem() != requestId){
							return;
						}
						
						setProgressBarIndeterminateVisibility(false);
						// TODO Auto-generated method stub
						String itemCount = "Request Id:" + requestId;
						Toast.makeText(getApplicationContext(),itemCount,
				                Toast.LENGTH_SHORT).show();		
						ItemDataManager.getItemManager().setCurrentItem(requestId);
						switch (requestId) {
						case ItemDataManager.TOP_STORIES_REQUEST_ID:
							ItemDataManager.getItemManager().setTopstories(itemArray);
							//WriteToFile(itemArray);
							
							startActivity(new Intent(getBaseContext(), ItemListView.class));
							finish();
							break;
						case ItemDataManager.WORLD_NEWS_REQUEST_ID:
							ItemDataManager.getItemManager().setWorldnews(itemArray);
							startActivity(new Intent(getBaseContext(), ItemListView.class));
							finish();
							break;
						case ItemDataManager.AMERICA_NEWS_REQUEST_ID:
							ItemDataManager.getItemManager().setAmericanews(itemArray);
							startActivity(new Intent(getBaseContext(), ItemListView.class));
							finish();
							break;
						case ItemDataManager.EUROPE_NEWS_REQUEST_ID:
							ItemDataManager.getItemManager().setEuropenews(itemArray);
							startActivity(new Intent(getBaseContext(), ItemListView.class));
							finish();
							break;

						case ItemDataManager.ASIA_NEWS_REQUEST_ID:
							ItemDataManager.getItemManager().setAsianews(itemArray);
							startActivity(new Intent(getBaseContext(), ItemListView.class));
							finish();
							break;
						case ItemDataManager.AFRICA_NEWS_REQUEST_ID:
							ItemDataManager.getItemManager().setAfricanews(itemArray);
							startActivity(new Intent(getBaseContext(), ItemListView.class));
							finish();
							break;

						case ItemDataManager.MIDEAST_NEWS_REQUEST_ID:
							ItemDataManager.getItemManager().setMideastnews(itemArray);
							startActivity(new Intent(getBaseContext(), ItemListView.class));
							finish();
							break;
						case ItemDataManager.POLITICS_NEWS_REQUEST_ID:
							ItemDataManager.getItemManager().setPoliticsnews(itemArray);
							startActivity(new Intent(getBaseContext(), ItemListView.class));
							finish();
							break;

						case ItemDataManager.SPORT_NEWS_REQUEST_ID:
							ItemDataManager.getItemManager().setSportnewsnews(itemArray);
							startActivity(new Intent(getBaseContext(), ItemListView.class));
							finish();
							break;
						case ItemDataManager.ENTERTAINMENT_NEWS_REQUEST_ID:
							ItemDataManager.getItemManager().setEntertainmentnews(itemArray);
							startActivity(new Intent(getBaseContext(), ItemListView.class));
							finish();
							break;

						case ItemDataManager.MUSIC_NEWS_REQUEST_ID:
							ItemDataManager.getItemManager().setMusicnews(itemArray);
							startActivity(new Intent(getBaseContext(), ItemListView.class));
							finish();
							break;
						case ItemDataManager.SCIENCE_NEWS_REQUEST_ID:
							ItemDataManager.getItemManager().setSciencenews(itemArray);
							startActivity(new Intent(getBaseContext(), ItemListView.class));
							finish();
							break;

						case ItemDataManager.ENVIRONMENT_NEWS_REQUEST_ID:
							ItemDataManager.getItemManager().setEnvironmentnews(itemArray);
							startActivity(new Intent(getBaseContext(), ItemListView.class));
							finish();
							break;
						
						default:
							break;
						}				
						
					}
        			
        		});
        		parser.setSourceUrl(initialSelectionUrls[ItemDataManager.getItemManager().getCurrentItem()], ItemDataManager.getItemManager().getCurrentItem());
        		parser.startParsing();
            	}
            }
        });


        m_items = new ArrayList<BasicItem>();
        this.m_adapter = new BasicItemAdapter(this, R.layout.list_item_row, m_items);
        setListAdapter(this.m_adapter);
       
        viewOrders = new Runnable(){
            @Override 
            public void run() {
                loadItems();
            }
        };
        
       getListView().setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
            Intent launchIntent = new Intent(getBaseContext(), ItemDetailView.class); 
            launchIntent.putExtra("Item Position", position);
            startActivity(launchIntent);
              
            }
          });
        
        
       startProgress();
   
    }
    
    
    public void startProgress(){
    	 Thread thread =  new Thread(null, viewOrders, "MagentoBackground");
         thread.start();
         m_ProgressDialog = ProgressDialog.show(ItemListView.this,    
               "Please wait...", "Retrieving data ...", false);
         isItemLoading = true;
    }
    
    static class ViewHolder {
        TextView text1;
        TextView text2;
        ImageView icon;
    }

    private class BasicItemAdapter extends ArrayAdapter<BasicItem> {

    	private ArrayList<BasicItem> items;

    	public BasicItemAdapter(Context context, int textViewResourceId, ArrayList<BasicItem> items) {
    		super(context, textViewResourceId, items);
    		this.items = items;
    	}

    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		//View v = null;
    		final ViewHolder holder;
    		final int localPosition = position ;

    		if (convertView == null) {
    			LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    			convertView  = vi.inflate(R.layout.list_item_row, null);
    			holder = new ViewHolder();
    			TextView tt = (TextView) convertView.findViewById(R.id.toptext);
    			TextView bt = (TextView) convertView.findViewById(R.id.bottomtext);
    			ImageView iv = (ImageView) convertView.findViewById(R.id.sideicon);
    			holder.text1 = tt;
    			holder.text2 = bt;
    			holder.icon = iv;
    			convertView.setTag(holder);
    		}else{
    			//v = convertView;
    			holder = (ViewHolder) convertView.getTag();
    		}



    		final BasicItem o = items.get(position);
    		if (o != null) {
    			if (holder.text1 != null) {
    				holder.text1.setText(o.getTitle());                            
    				}
    			if(holder.text2 != null){
    				holder.text2.setText(o.getPubtime());
    			}


    			if(o.getImageurl() != null && o.getBitmap() == null){
    				final Handler handler = new Handler() {
    		            @Override
    		            public void handleMessage(Message message) {    		                
    		                holder.icon.setImageBitmap((Bitmap) message.obj);
    		                holder.icon.setVisibility(View.VISIBLE);
    		            }
    		        };
    		        
    		        Thread thread = new Thread() {
    		            @Override
    		            public void run() {
    		            	BitmapFactory.Options bmOptions;
                        	bmOptions = new BitmapFactory.Options();
                        	bmOptions.inSampleSize = 1;
    		                Bitmap drawable = LoadImage(o.getImageurl(),bmOptions);
    		                
    		                int width = drawable.getWidth();
                        	int height = drawable.getHeight();

                        	int newWidth = 50;                        
                        	int newHeight = 50;

                        	float scaleWidth = ((float) newWidth) / width;
                        	float scaleHeight = ((float) newHeight) / height;
                        	Matrix matrix = new Matrix();
                        	matrix.postScale(scaleWidth, scaleHeight);
                        	matrix.postRotate(0);
                        	Bitmap resizedBitmap = Bitmap.createBitmap(drawable, 0, 0,width, height, matrix, true);
                        	o.setBitmap(resizedBitmap);
                        	o.setOriginalBitmap(drawable);

    		                
    		                Message message = handler.obtainMessage(1, resizedBitmap);
    		                handler.sendMessage(message);

    		            }
    		        };
    		        thread.start();
    			}
    			else{
    				if(o.getBitmap() != null && holder.icon != null){
//    					Bitmap resizedBitmap = o.getBitmap();
//    					BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
//    					holder.icon.setImageDrawable(bmd);
//    					holder.icon.setScaleType(ScaleType.CENTER);
    					holder.icon.setImageBitmap(o.getBitmap());    					
    				}else{
    					holder.icon.setImageBitmap(mIcon1);
    				}
    			}

    		}
    		return convertView ;
    	}

    }
    private Runnable returnRes = new Runnable() {

        @Override
        public void run() {
            if(m_items != null && m_items.size() > 0){
                m_adapter.notifyDataSetChanged();
                for(int i=0;i<m_items.size();i++)
                m_adapter.add(m_items.get(i));
            }
            m_ProgressDialog.dismiss();
            isItemLoading = false;
            m_adapter.notifyDataSetChanged();
        }
      };
    private void loadItems(){
    	
        try{
            //m_items = ItemDataManager.getItemManager().getTopstories();
        	m_items = ItemDataManager.getItemManager().getNewsList(ItemDataManager.getItemManager().getCurrentItem());
        	/*
        	m_items = new ArrayList<BasicItem>();
            BasicItem o1 = new BasicItem();
            o1.setTitle("Strauss-Kahn faces new legal action");
            o1.setPubtime("Mon, 04 Jul 2011 16:27 GMT");
            o1.setImageUrl("http://cdn.wn.com/ph/img/5f/f4/82c519dd34c9ef799d6ce9410e4d-grande.jpg");
            BasicItem o2 = new BasicItem();
            o2.setTitle("Tony Abbott talks with 7.30");
            o2.setPubtime("Mon, 04 Jul 2011 15:49 GMT");
            o2.setImageUrl("http://cdn.wn.com/ph/img/8e/b6/fa46216223f70b0fe5271e8d6c1d-grande.jpg");
            m_items.add(o1);
            m_items.add(o2); */
              Thread.sleep(2000);
            Log.i("ARRAY", ""+ m_items.size());
          } catch (Exception e) {
            Log.e("BACKGROUND_PROC", e.getMessage());
          }
    	  
          runOnUiThread(returnRes);
      }
    
    public InputStream fromString(String str)
    {
    byte[] bytes = str.getBytes();
    return new ByteArrayInputStream(bytes);
    }
        
    private Bitmap LoadImage(String URL, BitmapFactory.Options options)
    {       
    	Bitmap bitmap = null;
    	InputStream in = null;       
    	try {
    		in = OpenHttpConnection(URL);
    		bitmap = BitmapFactory.decodeStream(in, null, options);
    		in.close();
    	} catch (IOException e1) {
    	}
    	return bitmap;               
    }

    private InputStream OpenHttpConnection(String strURL) throws IOException{
    	InputStream inputStream = null;
    	URL url = new URL(strURL);
    	URLConnection conn = url.openConnection();

    	try{
    		HttpURLConnection httpConn = (HttpURLConnection)conn;
    		httpConn.setRequestMethod("GET");
    		httpConn.connect();

    		if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
    			inputStream = httpConn.getInputStream();
    		}
    	}
    	catch (Exception ex)
    	{
    	}
    	return inputStream;
    }
 


}
