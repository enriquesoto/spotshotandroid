package com.aqpup.waitforit.camera;

import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListener;

class CustomProgressListener implements MediaHttpDownloaderProgressListener {
	  public void progressChanged(MediaHttpDownloader downloader) {
	    switch (downloader.getDownloadState()) {
	      case MEDIA_IN_PROGRESS:
	        System.out.println(downloader.getProgress());
	        break;
	      case MEDIA_COMPLETE:
	        System.out.println("Download is complete!");
	    }
	  }
	}