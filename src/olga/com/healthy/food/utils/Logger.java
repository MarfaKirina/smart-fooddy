package olga.com.healthy.food.utils;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.Vector;

import android.os.Environment;
import android.util.Log;

public class Logger {

	public static final String TAG = "smart_fooddy";
	public static final boolean DEBUG = true;
	private static final boolean WRITE_TO_FILE = false;
	private static final String FOLDER_NAME = Environment.getExternalStorageDirectory()+ "/SmartFooddy/";
	private static final String FILE_NAME = "log";
	private static LogWriter thread;
	
	public static void log(String str)
	{						
		if (DEBUG)
		{
			if (null == str)
			{
				return;
			}
			
			int startIndex = 0;
			while (startIndex < str.length())
			{
				int endIndex = startIndex + 130;
				if (endIndex >= str.length())
				{
					endIndex = str.length();
				}
				Log.i(TAG, str.substring(startIndex, endIndex));
				
				startIndex = endIndex;
			}
			writeToFile(str);
		}		
	}
	
	public static void log(Exception e)
	{
		log(e.toString());
		StackTraceElement[] elements = e.getStackTrace();
		if (null == elements)
		{
			return;
		}
		
		for (int i = 0; i < elements.length; i++)
		{
			if (null != elements[i])
			{
				log(elements[i].toString());
			}
		}
	}
	
	private static void writeToFile(String string){
		if(!WRITE_TO_FILE){
			return;
		}
		synchronized (FOLDER_NAME) {
			if(thread == null || !thread.isAlive()){
				thread = new LogWriter();
				thread.start();
			}
		}
		thread.addString(string);
	}
	
	public static void cancel(){
		if(!WRITE_TO_FILE){
			return;
		}
		synchronized (FOLDER_NAME) {
			if(thread == null || !thread.isAlive()){
				return;
			}
			thread.cancel();
		}
	}
	
	private static class LogWriter extends Thread{
		private Vector<String> log;
		private boolean canceled;
		
		public LogWriter(){
			log = new Vector<String>();
			log.add(new Date(System.currentTimeMillis()).toString());
			canceled = false;
		}
		
		public void cancel(){
			canceled = true;
		}
		
		public void addString(String string){
			try {
				Date date = new Date(System.currentTimeMillis());
				log.add("" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "-" +
	    				+ date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900) + "    "+ string);
			} catch (Exception e) {
				log(e);
			}
		}
		
		public void run(){
			try{
				File file = null;
				synchronized (FOLDER_NAME) {
					file = new File(FOLDER_NAME);
					if(!file.exists()){
						file.createNewFile();
					}
					Date date = new Date();
					String filename = file.getPath() + FILE_NAME + date.getDate() + "-" + date.getMonth() + "-" + date.getYear() + ".txt";
					file = new File(filename);
					if(!file.exists()){
						file.createNewFile();
					}
				}
				FileWriter writer = new FileWriter(file, true);
				while(!canceled){
					while(!log.isEmpty() && !canceled){
						String string = log.get(0);
						writer.append(string + "\r\n");
						log.remove(0);
					}
					writer.flush();
					while(log.isEmpty() && !canceled){
					}
				}
				
				writer.close();
				
			}catch(Exception e){
				log(e);
			}
		}
	}
}
