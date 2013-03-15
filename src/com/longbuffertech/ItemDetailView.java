package com.longbuffertech;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class ItemDetailView extends Activity{
	/** Called when the activity is first created. */

	TextView titleText = null;
	ImageView itemImage = null;
	TextView descText = null;
	TextView linkText = null;
	int currentItemposition = -1;
	BasicItem currentItem = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_detail_view);
		titleText = (TextView)findViewById(R.id.TitleText);
		itemImage = (ImageView)findViewById(R.id.test_image);
		descText = (TextView)findViewById(R.id.DescText);
		linkText = (TextView)findViewById(R.id.LinkText);

		Intent localIntent = getIntent();
		int defaultValue = -1 ;
		currentItemposition = localIntent.getIntExtra("Item Position", defaultValue); 
		ArrayList list = ItemDataManager.getItemManager().getNewsList(ItemDataManager.getItemManager().getCurrentItem());
		currentItem = (BasicItem)list.get(currentItemposition);

		if(currentItem != null){
			titleText.setText(currentItem.getTitle());        	
			descText.setText(currentItem.getDescription());
			linkText.setText(currentItem.getLink());
			//jmt: pattern we want to match and turn into a clickable link
			Pattern pattern = Pattern.compile(currentItem.getLink());
			//jmt: prefix our pattern with http://
			Linkify.addLinks(linkText, pattern, "http://");
			/*
			MakeConnection connection = new MakeConnection(currentItem.getImageurl(),  new MResponseHandlerObserver()
			{

				@Override
				public void sendResponse(String aResponseStatus,
						String aResponseBody) {
					
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(),"call 1" ,
							Toast.LENGTH_SHORT).show();
					// TODO Auto-generated method stub
					BitmapFactory.Options opt = new BitmapFactory.Options();
					opt.inDither = true;
					opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
					Bitmap bitmap = BitmapFactory.decodeByteArray(aResponseBody.getBytes(), 0, aResponseBody.getBytes().length, opt);
					itemImage.setImageBitmap(bitmap);
					
					

					
					
				}

				@Override
				public void sendResponse(String aResponseStatus,
						byte[] aImageBytes) {
					Toast.makeText(getApplicationContext(),"call 2" ,
							Toast.LENGTH_SHORT).show();
					


				}

				@Override
				public void sendResponseErrorCode(
						String aResponseStatus) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(),"call 3" ,
							Toast.LENGTH_SHORT).show();

				}

			});
			connection.getResponse();
			*/
			/*
			BitmapFactory.Options bmOptions;
            bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;
            Bitmap bm = LoadImage(currentItem.getImageurl(), bmOptions);
            itemImage.setImageBitmap(bm);*/
			if(currentItem.getImageurl() != null){
				BitmapFactory.Options bmOptions;
				bmOptions = new BitmapFactory.Options();
				bmOptions.inSampleSize = 1;
				Bitmap bitmapOrg = null;
				if(currentItem.getOriginalBitmap() == null){
				LoadImage(currentItem.getImageurl(), bmOptions);
				}else{
					bitmapOrg = currentItem.getOriginalBitmap();
				}

				int width = bitmapOrg.getWidth();
				int height = bitmapOrg.getHeight();
				Display display = getWindowManager().getDefaultDisplay(); 

				int newWidth = display.getWidth();                        
				int newHeight = display.getHeight() / 2;

				float scaleWidth = ((float) newWidth) / width;
				float scaleHeight = ((float) newHeight) / height;
				Matrix matrix = new Matrix();
				matrix.postScale(scaleWidth, scaleHeight);
				matrix.postRotate(0);
				Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,width, height, matrix, true);
				BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
				itemImage.setImageDrawable(bmd);
				itemImage.setScaleType(ScaleType.CENTER);
			}
		}

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
