package com.longbuffertech;



public interface MResponseHandlerObserver {
	
	public void sendResponse(String aResponseStatus, String aResponseBody);
	public void sendResponse(String aResponseStatus, byte[] aImageBytes);
	public void sendResponseErrorCode(String aResponseStatus);


}


/*

public class ResponseHandler 	 {
		public void handleMessage(int message) {
			switch (message) {
			case HttpStatus.SC_PROCESSING: {
				String lResponseStatus = "processing";
				break;
			}
			case HttpStatus.SC_OK: {
				String lResponseStatus = "success";
			
				break;
			}
			case HttpStatus.SC_EXPECTATION_FAILED : {
			 
				String lResponseStatus = "failed";
				break;
			}
		 }
		}
	};

*/