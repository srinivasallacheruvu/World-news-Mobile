package com.longbuffertech;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class BitmapDownloadManager extends Thread{

	public String url;
	public int requestId;
	public int imgWidth;
	public int imgHeight;
	public MBitmapDownloadObserver localObserver;
	public ImageView localimageView;
	public BasicItem localbasicItem;
	BitmapDownloadManager(String URL,ImageView imageView, BasicItem basicItem,int reqId, int width, int height, MBitmapDownloadObserver observer){
		start();
		url = URL;
		requestId = reqId ;
		imgWidth = width;
		imgHeight = height;
		localObserver = observer;
		localimageView = imageView ;
		localbasicItem = basicItem;
	}
	
	public void run() {
		 if(url != null){
         	BitmapFactory.Options bmOptions;
         	bmOptions = new BitmapFactory.Options();
         	bmOptions.inSampleSize = 1;
         	Bitmap bitmapOrg = LoadImage(url, bmOptions);


         	int width = bitmapOrg.getWidth();
         	int height = bitmapOrg.getHeight();

         	int newWidth = imgWidth;                        
         	int newHeight = imgHeight;

         	float scaleWidth = ((float) newWidth) / width;
         	float scaleHeight = ((float) newHeight) / height;
         	Matrix matrix = new Matrix();
         	matrix.postScale(scaleWidth, scaleHeight);
         	matrix.postRotate(0);
         	Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,width, height, matrix, true);
         	localObserver.BitmapDownloadCompleted(requestId, localimageView,localbasicItem,resizedBitmap,bitmapOrg);
         
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
