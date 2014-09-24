package olga.com.healthy.food.ui.screens;

import olga.com.healthy.food.Pref;
import olga.com.healthy.food.R;
import olga.com.healthy.food.model.PersonalDetails;
import olga.com.healthy.food.model.SimpleItem;
import olga.com.healthy.food.ui.controls.SkinColorSlider;
import olga.com.healthy.food.ui.helpers.SimpleAdapter;
import olga.com.healthy.food.utils.Logger;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class Registration extends Activity {

	private EditText name;
	private EditText age;
	private EditText height;
	private Spinner sex;
	private Spinner lifeStyle;
	private Button ok;
	private PersonalDetails details;
	private SkinColorSlider skinColor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		details = new PersonalDetails();
		details.load(this);
		
		name = (EditText)findViewById(R.id.name);
		age = (EditText)findViewById(R.id.age);
		height = (EditText)findViewById(R.id.height);
		sex = (Spinner)findViewById(R.id.sex);
		lifeStyle = (Spinner)findViewById(R.id.lifestyle);
		skinColor = (SkinColorSlider)findViewById(R.id.skin_color);
		addSpinnerValues();
		sex.setOnItemSelectedListener(onSexItemSelected());
		ok = (Button)findViewById(R.id.ok);
		ok.setClickable(true);
		ok.setOnClickListener(onOk());
		
		name.setText(details.getName());
		age.setText(details.getAge() > 0 ? "" + details.getAge() : "");
		height.setText(details.getHeight() > 0 ? "" + details.getHeight() : "");
		skinColor.setPercent(details.getSkinColor());
	}
	
	private void addSpinnerValues(){
		SimpleAdapter adapter = new SimpleAdapter(this);
		adapter.add(new SimpleItem(R.string.male, PersonalDetails.MALE));
		adapter.add(new SimpleItem(R.string.female, PersonalDetails.FEMALE));
		sex.setAdapter(adapter);
		addMaleValues();
		if(details.getSex().equals(PersonalDetails.FEMALE)){
			sex.setSelection(1);
			addFemaleValues();
		}else{
			sex.setSelection(0);
		}
	}
	
	private void addMaleValues(){
		SimpleAdapter adapter = new SimpleAdapter(this);
		adapter.add(new SimpleItem(R.string.lifestyle_sedentary, PersonalDetails.LIFESTYLE_SEDENTARY));
		adapter.add(new SimpleItem(R.string.lifestyle_active, PersonalDetails.LIFESTYLE_ACTIVE));
		adapter.add(new SimpleItem(R.string.lifestyle_hard, PersonalDetails.LIFESTYLE_HARD_WORK));
		lifeStyle.setAdapter(adapter);

		if(details.getLifestyle().equals(PersonalDetails.LIFESTYLE_ACTIVE)){
			lifeStyle.setSelection(1);
		}else if(details.getLifestyle().equals(PersonalDetails.LIFESTYLE_HARD_WORK)){
			lifeStyle.setSelection(2);
		}else if(details.getLifestyle().equals(PersonalDetails.LIFESTYLE_SEDENTARY)){
			lifeStyle.setSelection(0);
		}
	}
	
	private OnItemSelectedListener onSexItemSelected(){
		return new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(((SimpleItem)arg0.getSelectedItem()).value == PersonalDetails.FEMALE){
					addFemaleValues();
				}else{
					removeFemaleValues();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		};
	}
	
	private void addFemaleValues(){
		SimpleAdapter adapter = (SimpleAdapter)lifeStyle.getAdapter();
		if(adapter.getCount() < 5){
			adapter.add(new SimpleItem(R.string.lifestyle_breast_feading, PersonalDetails.LIFESTYLE_PREGNANT));
			adapter.add(new SimpleItem(R.string.lifestyle_pregnant, PersonalDetails.LIFESTYLE_BREAST_FEEDING));
		}
		if(details.getLifestyle().equals(PersonalDetails.LIFESTYLE_BREAST_FEEDING)){
			lifeStyle.setSelection(4);
		}else if(details.getLifestyle().equals(PersonalDetails.LIFESTYLE_PREGNANT)){
			lifeStyle.setSelection(3);
		}
	}
	
	private void removeFemaleValues(){
		SimpleAdapter adapter = (SimpleAdapter)lifeStyle.getAdapter();
		if(adapter.getCount() >= 5){
			adapter.remove(4);
			adapter.remove(3);
		}
		if(lifeStyle.getSelectedItemPosition() >= 3){
			lifeStyle.setSelection(0);
		}
	}
	
	private OnClickListener onOk(){
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					if(checkInputFields()){
						PersonalDetails details = new PersonalDetails();
						details.setAge(Integer.parseInt(age.getText().toString()));
						details.setHeight(Integer.parseInt(height.getText().toString()));
						SimpleItem item = (SimpleItem)lifeStyle.getSelectedItem();
						details.setLifestyle(item.value);
						details.setName(name.getText().toString());
						item = (SimpleItem)sex.getSelectedItem();
						details.setSex(item.value);
						details.setSkinColor((int)skinColor.getPersent());
						details.save(Registration.this);
						Pref pref = new Pref(Registration.this);
						if(!pref.getBoolean(Pref.KEY_IS_NOT_FIRST_TIME)){
							pref.setBoolean(Pref.KEY_IS_NOT_FIRST_TIME, true);
						}
						Registration.this.finish();
					}else{
						AlertDialog.Builder dialog = new AlertDialog.Builder(Registration.this);
						dialog.setMessage(R.string.reg_dialog_not_fill);
						dialog.setPositiveButton(R.string.btn_ok, null);
						dialog.create().show();
					}
				}catch(Exception e){
					Logger.log(e);
				}
			}
		};
	}
	
	private boolean checkInputFields(){
		String value = age.getText().toString();
		if(TextUtils.isEmpty(value) || !TextUtils.isDigitsOnly(value)){
			return false;
		}
		value = this.height.getText().toString();
		if(TextUtils.isEmpty(value) || !TextUtils.isDigitsOnly(value)){
			return false;
		}
		value = this.name.getText().toString();
		if(TextUtils.isEmpty(value)){
			return false;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_registration, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Logger.cancel();
	}

}
