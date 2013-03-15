package com.longbuffertech;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;


public class MakeConnection extends Thread {
	
	
	  public static long iTime = 0L;     
	 
	  private  String iQueryRequest ;
	    
	  private  String iQueryResponse  ;
	  private byte[]   iImageBytes  ;
	  private boolean isImageBytes;
	  String iStatus;
	  int iStatusCode;
	  int iError;
	 private MResponseHandlerObserver iMResponseHandlerObserver;
	
		final Handler mHandler = new Handler();
	    final Runnable mThreadCallback = new Runnable() {
	        public void run() {
			   if(iStatusCode == HttpStatus.SC_OK && iError == 0)          
			   {    
    			   if(isImageBytes == false)
	    			   	{
	    			    iMResponseHandlerObserver.sendResponse(iStatus, iQueryResponse); // String
	    			   	}
    			   else
    			   		{
    				    iMResponseHandlerObserver.sendResponse(iStatus, iImageBytes); // bytes
    			   		}
	    		}        
			   else          
			   {   
				   iMResponseHandlerObserver.sendResponseErrorCode(iStatus);
	           } 
	        }};
	    
	public MakeConnection(String aRequest , MResponseHandlerObserver aMResponseHandlerObserver ) // for response String
	{
		iMResponseHandlerObserver=aMResponseHandlerObserver;
		this.iQueryRequest = aRequest;
		
	}
	
	public MakeConnection(String aRequest ,byte[] aImageBytes, MResponseHandlerObserver aMResponseHandlerObserver) // for response Image bytes
	{
		isImageBytes = true;
		iMResponseHandlerObserver=aMResponseHandlerObserver;
		this.iQueryRequest = aRequest;
		
	}
	

	/*================================================================
	* Name                 : getSeriesPast()
	* Description          : To use already received data in  
	* 						 method	getResponse()
	* Arguments            : None
	* Return Type          : String
	* Assumptions          : Response has already came 
	* Limitations          : None
	=================================================================*/
	public String getiQueryResponse()
	{
		return iQueryResponse;
	}
	void PostToUIThread()
	{
		mHandler.post(mThreadCallback);
	}
	
	/*================================================================
	* Name                 : getResponse()
	* Description          : Make HTTP connection, send request query  
	*                        URL and receives response(XML/JSON) from server
	* Arguments            : RequestURI
	* Return Type          : void
	* Assumptions          : HTTP Connection is successful and server responds
	* Limitations          : None
	=================================================================*/
	public void getResponse()
	{
	
		this.start();
	
	}
	/*================================================================
	* Name                 : getResponse()
	* Description          : Make HTTP connection, send request query  
	*                        URL and receives response(XML/JSON) from server
	* Arguments            : RequestURI
	* Return Type          : String
	* Assumptions          : HTTP Connection is successful and server responds
	* Limitations          : None
	=================================================================*/
	public  void run()
	{
		try  
		{    
			String lresponseString= new String("");

			URI lURI = new URI(iQueryRequest);

			HttpClient lHttpClient = new DefaultHttpClient();   

			HttpGet lHttpGet = new HttpGet(); 


			//******setting headers********
			lHttpGet.setURI(lURI);
//			lHttpGet.setHeader("X-Client-UUID","f81d4fae-7dec-11d0-a765-00a0c91e6bf6");
//			lHttpGet.setHeader("X-Client-Info","vendor=\"Samsung\" model=\"FinanceWidget\" version=\"1.0.1");	
//			lHttpGet.setHeader("X-Device-Info","make=\"Apple\" model=\"iPhone\" os=\"iPhone\" osver=\"2.2");
//			lHttpGet.setHeader("X-Device-User-Agent","");

			HttpResponse lHttpResponse = lHttpClient.execute(lHttpGet); 
			iError = 0;

			iStatusCode = lHttpResponse.getStatusLine().getStatusCode();

			if(iStatusCode != HttpStatus.SC_REQUEST_TIMEOUT  )
			{

				if(iStatusCode == HttpStatus.SC_OK)          
				{    
					ByteArrayOutputStream out = new ByteArrayOutputStream(); 
					lHttpResponse.getEntity().writeTo(out); 
					out.close();
					if(isImageBytes == false)
					{
						lresponseString = out.toString(); 
						iQueryResponse = lresponseString.substring(7,lresponseString.length()-2);
						iStatus = lHttpResponse.getStatusLine().toString();
						PostToUIThread();
						//iMResponseHandlerObserver.sendResponse(lHttpResponse.getStatusLine().toString(), iQueryResponse); // String
					}
					else
					{
						iImageBytes = out.toByteArray();
						//iMResponseHandlerObserver.sendResponse(lHttpResponse.getStatusLine().toString(), iImageBytes); // bytes
					}
				}        
				else          
				{   
					iMResponseHandlerObserver.sendResponseErrorCode(lHttpResponse.getStatusLine().toString());
					//Closes the connection. 
					lHttpResponse.getEntity().getContent().close(); 

				} 
			} else 
			{				  
				PostToUIThread();
				lHttpResponse.getEntity().getContent().close(); 
			}			
		}
		catch (ClientProtocolException lClientProtocolException)
		{ 
			Log.e("ERROR", "Error in method getResponse: " + lClientProtocolException.toString());
			lClientProtocolException.printStackTrace();
			iError = -1;
			PostToUIThread();
		} 
		catch(IOException lIOException)
		{       
			Log.e("ERROR", "Error in method getResponse: " + lIOException.toString());
			lIOException.printStackTrace();
			iError = -1;
			PostToUIThread();

		} 
		catch (URISyntaxException lURISyntaxException)
		{
			Log.e("ERROR", "Error in method getResponse: " + lURISyntaxException.toString());
			lURISyntaxException.printStackTrace();
			iMResponseHandlerObserver.sendResponseErrorCode("");

		} 				   			
	}
}


