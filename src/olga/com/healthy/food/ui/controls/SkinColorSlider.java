package olga.com.healthy.food.ui.controls;

import olga.com.healthy.food.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SkinColorSlider extends View {

	private Float x = null;
	private float percent = 0;
	private Bitmap bitmap;
	public SkinColorSlider(Context context) {
		super(context);
	}
	
	public SkinColorSlider(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	public SkinColorSlider(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}
	
	public float getPersent(){
		if(x != null && bitmap != null){
			percent = ((x + bitmap.getWidth()) *100)/getWidth();
		}
		return percent;
	}
	
	public void setPercent(float value){
		if(value < 0){
			value = 0;
		}else if(value > 100){
			value = 100;
		}
		percent = value;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
			float xPos = event.getX();
			x = xPos;
			invalidate();
			return true;
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		Paint paint = new Paint();
		int allWidth = getWidth();
		float xPos = 0;
		if(bitmap == null){
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slider);
		}
		if(x == null){
			xPos = Float.valueOf(percent*allWidth/100);
		}else{
			xPos = x - bitmap.getWidth()/2;
		}
		if(xPos >= allWidth){
			xPos = Float.valueOf(allWidth - bitmap.getWidth());
		}else if(xPos <= 0){
			xPos = Float.valueOf(0);
		}
		canvas.drawBitmap(bitmap, xPos, (getHeight() - bitmap.getHeight())/2, paint);
	}
	
}
