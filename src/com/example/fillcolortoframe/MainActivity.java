package com.example.fillcolortoframe;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity {
	private int pColor;
	private Button chooseColor;
	private AmbilWarnaDialog dialog;
	private ImageView iv;
    
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final String name = getIntent().getStringExtra("deviceName");
        int drawableResourceId = MainActivity.this.getResources().getIdentifier(name, "drawable", MainActivity.this.getPackageName());
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),drawableResourceId);
       
        chooseColor = (Button) findViewById(R.id.button1);
        iv = (ImageView) findViewById(R.id.imageView1);
        iv.setImageBitmap(bitmap);
        int initialColor = 0;
    	dialog = new AmbilWarnaDialog(this, initialColor, new OnAmbilWarnaListener() {
            public void onOk(AmbilWarnaDialog dialog, int color) {
                    // color is the color selected by the user.
            	  pColor= color;
            	  setColor(bitmap);
            }

			@Override
			public void onCancel(AmbilWarnaDialog dialog) {				
			}
        });
    	chooseColor.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			dialog.show();
    		}
    	});
    }
	public void setColor(Bitmap bitmap) {
	  iv.setImageBitmap(changeBitmapColor(bitmap));
   	  iv.setBackgroundColor(Color.WHITE);
   	  TextView textView = (TextView) findViewById(R.id.textView2);
   	  textView.setBackgroundColor(pColor);
	}
    public Bitmap changeBitmapColor(Bitmap sourceBitmap)
    {   
        int width, height;
        height = sourceBitmap.getHeight();
        width = sourceBitmap.getWidth();    

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint(pColor);
        ColorFilter filter = new LightingColorFilter(pColor, 1);
        paint.setColorFilter(filter);
        c.drawBitmap(sourceBitmap, 0, 0, paint);
        return bmpGrayscale;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
//    public static Bitmap createTransparentBitmapFromBitmap(Bitmap bitmap,
//  	      int replaceThisColor) {
//  	    if (bitmap != null) {
//  	      int picw = bitmap.getWidth();
//  	      int pich = bitmap.getHeight();
//  	      int[] pix = new int[picw * pich];
//  	      bitmap.getPixels(pix, 0, picw, 0, 0, picw, pich);
//
//  	      for (int y = 0; y < pich; y++) {
//  	        // from left to right
//  	        for (int x = 0; x < picw; x++) {
//  	          int index = y * picw + x;
//  	          
//  	          if (pix[index] == replaceThisColor) {
//  	            pix[index] = Color.TRANSPARENT;
//  	          } else {
//  	            break;
//  	          }
//  	        }
//  	        // from right to left
//  	        for (int x = picw - 1; x >= 0; x--) {
//  	          int index = y * picw + x;
//  	          if (pix[index] == replaceThisColor) {
//  	            pix[index] = Color.TRANSPARENT;
//  	          } else {
//  	            break;
//  	          }
//  	        }
//  	      }
//  	      Bitmap bm = Bitmap.createBitmap(pix, picw, pich,
//  	          Bitmap.Config.ARGB_8888);
//
//  	      return bm;
//  	    }
//  	    return null;
//  }
//  public Boolean checkBitmapTransparent (Bitmap bitmap){
//  	int i = 0;
//  	int j= 0;
//  	while (i< bitmap.getWidth()){
//  		while(j < bitmap.getHeight()){
//  			int color = bitmap.getPixel(i,j);
//  			if((color & 0xff000000) == 0x0){
//  				i++;
//  				j++;
//  			}
//  		}
//  	}
//  	if(((bitmap.getWidth()/2) <= i && i <= bitmap.getWidth()) && ((bitmap.getHeight()/2) <= i && i <= bitmap.getHeight())) return true;
//  	return false;
//  }

}
