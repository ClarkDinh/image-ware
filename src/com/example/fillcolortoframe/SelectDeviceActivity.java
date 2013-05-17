package com.example.fillcolortoframe;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectDeviceActivity extends Activity {
	private LinearLayout  horizontalOuterLayout;
	private HorizontalScrollView horizontalScrollview;
	private TextView horizontalTextView;
	private Button selectDeviewButton;
	private TimerTask faceAnimationSchedule;
	private Button clickedButton = null;
	private Timer scrollTimer = null;
	private Timer faceTimer = null;
	private Boolean isFaceDown = true;
	private String[] imageNameArray = {"iphone5", "galaxy", "ipad3mask", "tabback", "samgs","smart_phone1","nokialumina"};
	private String[] deviceNameArray ={"Iphone 5","Galaxy 4S","Ipad 3","Android Tablet","Samsung","Iphone 4S","Nokia Lumina"};
	public SelectDeviceActivity() {
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectdeviceactivity);
        
        horizontalScrollview = (HorizontalScrollView) findViewById(R.id.horiztonal_scrollview_id);
        horizontalOuterLayout = (LinearLayout)findViewById(R.id.horiztonal_outer_layout_id);
        horizontalTextView = (TextView)findViewById(R.id.horizontal_textview_id);
        selectDeviewButton = (Button) findViewById(R.id.select_button_id);
        horizontalScrollview.setHorizontalScrollBarEnabled(false);
        addImagesToView();
        selectDevice();
    }
	public void selectDevice(){
		selectDeviewButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String deviceName = imageNameArray[(Integer) clickedButton.getTag()];
				Intent intent = new Intent(SelectDeviceActivity.this, MainActivity.class);
				intent.putExtra("deviceName",deviceName);
				startActivity(intent);
			}
		});
	}
    public void addImagesToView(){
		for (int i=0;i<imageNameArray.length;i++){
			final Button imageButton =	new Button(this);
			int imageResourceId = getResources().getIdentifier(imageNameArray[i], "drawable",getPackageName());
			System.err.print(imageResourceId);
		    Drawable image = this.getResources().getDrawable(imageResourceId);
		    imageButton.setBackground(image);
		    imageButton.setTag(i);
		    imageButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					if(isFaceDown){
						clickedButton =	(Button)arg0;
						stopAutoScrolling();
						clickedButton.startAnimation(scaleFaceUpAnimation());
						clickedButton.setSelected(true);
					}
				}
			});
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400,750);
			params.setMargins(0, 50, 0, 50);
			imageButton.setLayoutParams(params);
			horizontalOuterLayout.addView(imageButton);
		}
    }
    public void stopAutoScrolling(){
		if (scrollTimer != null) {
			scrollTimer.cancel();
			scrollTimer	= null;
		}
	}
    public Animation scaleFaceUpAnimation(){
		Animation scaleFace = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		scaleFace.setDuration(500);
		scaleFace.setFillAfter(true);
		scaleFace.setInterpolator(new AccelerateInterpolator());
		Animation.AnimationListener	scaleFaceAnimationListener = new Animation.AnimationListener() {			
			@Override
			public void onAnimationStart(Animation arg0) {
				isFaceDown = false;
				horizontalTextView.setText(deviceNameArray[(Integer) clickedButton.getTag()]);
			}			
			@Override
			public void onAnimationRepeat(Animation arg0) {}			
			@Override
			public void onAnimationEnd(Animation arg0) {
				if(faceTimer != null){
					faceTimer.cancel();
					faceTimer = null;
				}
				faceTimer = new Timer();
				if(faceAnimationSchedule != null){
					faceAnimationSchedule.cancel();
					faceAnimationSchedule = null;
				}
				faceAnimationSchedule = new TimerTask() {					
					@Override
					public void run() {
						faceScaleHandler.sendEmptyMessage(0); 										
					}
				};
				faceTimer.schedule(faceAnimationSchedule, 750);				
			}
		}; 
		scaleFace.setAnimationListener(scaleFaceAnimationListener);
		return scaleFace;
	}
    private Handler faceScaleHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	if(clickedButton.isSelected() == true)
        		clickedButton.startAnimation(scaleFaceDownAnimation(500));		
        }
	};
	public Animation scaleFaceDownAnimation(int duration){
		Animation scaleFace = new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		scaleFace.setDuration(duration);
		scaleFace.setFillAfter(true);
		scaleFace.setInterpolator(new AccelerateInterpolator());
		Animation.AnimationListener	scaleFaceAnimationListener = new Animation.AnimationListener() {			
			@Override
			public void onAnimationStart(Animation arg0) {}			
			@Override
			public void onAnimationRepeat(Animation arg0) {}			
			@Override
			public void onAnimationEnd(Animation arg0) {
				isFaceDown = true;			
				horizontalTextView.setText("");
			}
		}; 
		scaleFace.setAnimationListener(scaleFaceAnimationListener);
		return scaleFace;
	}
	public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
}
