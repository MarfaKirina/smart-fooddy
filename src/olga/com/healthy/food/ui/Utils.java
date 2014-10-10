package olga.com.healthy.food.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import olga.com.healthy.food.model.Constituent;
import olga.com.healthy.food.model.Details;
import olga.com.healthy.food.model.Food;
import olga.com.healthy.food.utils.Logger;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class Utils {
	public static boolean setIconInView(ImageView imageView, Details details){
		if(details instanceof Food){
			
			if(((Food)details).imagePath != null && ((Food)details).imagePath.trim().length() > 0){
				InputStream is = null;
				if(((Food)details).imagePath.contains("\\")){
					File file = new File(((Food)details).imagePath);
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
						is = imageView.getContext().getAssets().open(((Food)details).imagePath);
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
			}else if(((Food)details).imageId >= 0){
				imageView.setImageBitmap(BitmapFactory.decodeResource(imageView.getContext().getResources(), ((Food)details).imageId));
				return true;
			}
		}
		return false;
	}
	
	public static Details copy(Details details){
		Details result;
		if(details instanceof Food){
			result = new Food(details);
		}else if(details instanceof Constituent){
			result = new Constituent(details);
		}else{
			result = new Details(details);
		}
		return result;
	}
}
