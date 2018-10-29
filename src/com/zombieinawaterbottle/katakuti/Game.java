package com.zombieinawaterbottle.katakuti;

import framework.Render;
import framework.Screen;
import framework.Touch;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Game extends Activity {
	double scaleX,scaleY;
	public TextView text;
	int x, y;
	int key=0;
	public Touch touch;
	Screen screen;
	Render render;
	PowerManager.WakeLock wakeLock ;
	public FileIO fileIO;
	int state;
	public boolean vibrate;
	public boolean boardSize;
	Record record;
	public void changeScreen(Screen s){
		screen=s;
	}
	public Screen getCurrentScreen(){
		return screen;
	}
	public void setX(int x1){
		x=x1;
	}
	public void setY(int y1){
		y=y1;
	}
	public int getX()
	{
		if (x==-1)return -1;
		return (int)(x*scaleX);
	}
	public int getY(){
		if (x==-1)return -1;
		return (int)(y*scaleY);
	}
	public void setKey(){
		key=0;
	}
	public int getKey(){
		return key;
	}
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_BACK){
			key=1;
		}
		else if (keyCode==KeyEvent.KEYCODE_HOME){
			key=0;
		}
		return false;
		
	}
	public double getScaleX(){
		return scaleX;
	}
	public double getScaleY(){
		return scaleY;
	}
		@SuppressWarnings("deprecation")
		@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		state=Settings.System.getInt( this.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
		vibrate=false;
		boardSize=false;
		Settings.System.putInt( this.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			int	height = getWindowManager().getDefaultDisplay().getHeight();
			int	width = getWindowManager().getDefaultDisplay().getWidth();
			scaleX=(double)480/width;
			scaleY=(double)800/height;
			fileIO=new FileIO(getAssets());
			
			
			render=new Render(this,this,scaleX,scaleY);
		
			screen=new MainMenu(this);
		touch=new Touch(this,render,1,1);
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		 wakeLock = pm.newWakeLock( PowerManager.FULL_WAKE_LOCK, "My wakelook");
		
		 
		super.onCreate(savedInstanceState);
		setContentView(render);
		
		
	}
	
	public void vibr(){
			Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
			 v.vibrate(40);
			 try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
		wakeLock.acquire();
		render.resume();
		
	}
	@Override
	protected void onPause(){
		end();
	}
		public void end() {
		// TODO Auto-generated method stub
		if (state==1){
			Settings.System.putInt( this.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1);
			
		}
		this.finish();
		System.exit(0);
	}
}
