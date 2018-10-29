package framework;

import com.zombieinawaterbottle.katakuti.Game;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Touch implements OnTouchListener {
	Game game;
	int x=-1,y=-1;
	View view;
	int scaleX;
	int scaleY;
	public Touch(Game g,View v, int scale_x, int scale_y){
		game=g;
		view=v;
		view.setOnTouchListener(this);
		scaleX=scale_x;
		scaleY=scale_y;
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction()==MotionEvent.ACTION_DOWN){
			x=(int)(event.getX()*scaleX);
			y=(int)(event.getY()*scaleY);
			//System.out.println(x+" "+y);
			//game.text.setText(x +" "+y);
		}
		return true;
	}
	public int getX(){
		
		return x;
		
	}
	public int getY(){
		return y;
	}
	public void reset() {
		// TODO Auto-generated method stub
		x=-1;y=-1;
	}
}
