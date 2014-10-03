package olga.com.healthy.food.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import olga.com.healthy.food.model.Details;
import olga.com.healthy.food.utils.Logger;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class Utils {
	public static boolean setIconInView(ImageView imageView, Details details){
		if(details.imagePath != null && details.imagePath.trim().length() > 0){
			InputStream is = null;
			if(details.imagePath.contains("\\")){
				File file = new File(details.imagePath);
				if(file.exists()){
					try {
						is = new FileInputStream(file);
					} catch (FileNotFoundException e) {
						Logger.log(e);
						return false;
					}
				}
			}else{
				try {
					is = imageView.getContext().getAssets().open(details.imagePath);
				} catch (IOException e) {
					Logger.log(e);
					return false;
				}
			}
			imageView.setImageBitmap(BitmapFactory.decodeStream(is));
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					Logger.log(e);
				}
			}
			return true;
		}else if(details.imageId >= 0){
			imageView.setImageBitmap(BitmapFactory.decodeResource(imageView.getContext().getResources(), details.imageId));
			return true;
		}
		return false;
	}
}
