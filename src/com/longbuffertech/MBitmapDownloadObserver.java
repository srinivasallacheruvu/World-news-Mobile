package com.longbuffertech;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface MBitmapDownloadObserver {
	public void BitmapDownloadCompleted(int reqId, ImageView view,BasicItem basicItem, Bitmap bitmap, Bitmap originalBitmap) ;
}
