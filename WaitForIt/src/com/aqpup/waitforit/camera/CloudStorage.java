package com.aqpup.waitforit.camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;
import android.util.Log;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;
import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.StorageObject;
//import android.R;

/**
 * Simple wrapper around the Google Cloud Storage API
 */
public class CloudStorage {
	
	private Storage storage = null;
	GCSParams gcsParams = new GCSParams();
		
	public void uploadFile(String bucketName, String filePath) throws Exception {

		Storage storage = getStorage();

		StorageObject object = new StorageObject();
		object.setBucket(bucketName);

		File file = new File(filePath);

		InputStream stream = new FileInputStream(file);
		try {
			String contentType = URLConnection
					.guessContentTypeFromStream(stream);
			InputStreamContent content = new InputStreamContent(contentType,
					stream);

			Storage.Objects.Insert insert = storage.objects().insert(
					bucketName, null, content);
			insert.setName(file.getName());

			insert.execute();
		} finally {
			stream.close();
		}
	}

	public void downloadFile(String bucketName, String fileName,
			String destinationDirectory) throws Exception {

		File directory = new File(destinationDirectory);
		if (!directory.isDirectory()) {
			throw new Exception(
					"Provided destinationDirectory path is not a directory");
		}
		File file = new File(directory.getAbsolutePath() + "/" + fileName);

		Storage storage = getStorage();

		
		Storage.Objects.Get get = storage.objects().get(bucketName, fileName);		
		FileOutputStream stream = new FileOutputStream(file);
		
		CustomProgressListener cpl = new CustomProgressListener();
		get.getMediaHttpDownloader().setProgressListener(cpl);
		get.executeMediaAndDownloadTo(stream);		
				
	}

	/**
	 * Deletes a file within a bucket
	 * 
	 * @param bucketName
	 *            Name of bucket that contains the file
	 * @param fileName
	 *            The file to delete
	 * @throws Exception
	 */
	public void deleteFile(String bucketName, String fileName) throws Exception {

		Storage storage = getStorage();

		storage.objects().delete(bucketName, fileName).execute();
	}

	/**
	 * Creates a bucket
	 * 
	 * @param bucketName
	 *            Name of bucket to create
	 * @throws Exception
	 */
	public void createBucket(String bucketName) throws Exception {
		Storage storage = getStorage();
		Bucket bucket = new Bucket();
		bucket.setName(bucketName);
		
		
		
		storage.buckets().insert(gcsParams.PROJECT_ID , bucket).execute();
		
		
	}

	/**
	 * Deletes a bucket
	 * 
	 * @param bucketName
	 *            Name of bucket to delete
	 * @throws Exception
	 */
	public void deleteBucket(String bucketName) throws Exception {
		Storage storage = getStorage();
		storage.buckets().delete(bucketName).execute();
	}

	/**
	 * Lists the objects in a bucket
	 * 
	 * @param bucketName
	 *            bucket name to list
	 * @return Array of object names
	 * @throws Exception
	 */
	public List<String> listBucket(String bucketName) throws Exception {

		Storage storage = getStorage();

		List<String> list = new ArrayList<String>();

		// List<StorageObject> objects =
		// storage.objects().list(bucketName).execute().getItems();
		List<StorageObject> objects = storage.objects().list(bucketName)
				.execute().getItems();
		if (objects != null) {
			for (StorageObject o : objects) {
				list.add(o.getName());
			}
		}

		return list;
	}

	/**
	 * List the buckets with the project (Project is configured in properties)
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<String> listBuckets() throws Exception {

		Storage storage = getStorage();

		List<String> list = new ArrayList<String>();

		List<Bucket> buckets = storage.buckets().list(gcsParams.PROJECT_ID).execute()
				.getItems();
		if (buckets != null) {
			for (Bucket b : buckets) {
				list.add(b.getName());
			}
		}

		return list;
	}

	private Storage getStorage() throws Exception {

		Log.e("EXTERNAL ESTORAGE", Environment.getExternalStorageDirectory().toString());
		
		if (storage == null) {

			HttpTransport httpTransport = new NetHttpTransport();
			JsonFactory jsonFactory = new JacksonFactory();

			List<String> scopes = new ArrayList<String>();
			scopes.add(StorageScopes.DEVSTORAGE_FULL_CONTROL);

			Credential credential = new GoogleCredential.Builder()
					.setTransport(httpTransport)
					.setJsonFactory(jsonFactory)
					.setServiceAccountId(gcsParams.ACCOUNT_ID)
					.setServiceAccountPrivateKeyFromP12File(
							new File(Environment.getExternalStorageDirectory()
									+ gcsParams.PRIVATE_KEY_PATH))
					.setServiceAccountScopes(scopes).build();

			storage = new Storage.Builder(httpTransport, jsonFactory,
					credential).setApplicationName(gcsParams.APPLICATION_NAME).build();
		}

		return storage;
	}
}
