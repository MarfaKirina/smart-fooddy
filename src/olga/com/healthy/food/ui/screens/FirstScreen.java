package olga.com.healthy.food.ui.screens;

import java.util.Timer;
import java.util.TimerTask;

import olga.com.healthy.food.Pref;
import olga.com.healthy.food.R;
import olga.com.healthy.food.utils.Logger;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FirstScreen extends Activity {

	private static final int REGISTRATION_CODE = 23;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_screen);
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				try{
					Pref pref = new Pref(FirstScreen.this);
					boolean isFirstTime = !pref.getBoolean(Pref.KEY_IS_NOT_FIRST_TIME);
					FirstScreen.this.finish();
						startActivity(new Intent(FirstScreen.this, Options.class));
					if(isFirstTime){
						startActivity(new Intent(FirstScreen.this, Registration.class));
					}
				}catch(Exception e){
					Logger.log(e);
				}
			}
		};
		timer.schedule(task, 3000);
	}
}
