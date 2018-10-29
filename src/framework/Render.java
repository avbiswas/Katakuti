package framework;

import java.util.ArrayList;
import java.util.Random;

import com.zombieinawaterbottle.katakuti.Game;
import com.zombieinawaterbottle.katakuti.Record;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Render extends SurfaceView implements Runnable{
	volatile boolean listening=false;
	double scaleX;
	double scaleY;
	String screen;
	Thread renderThread=null;
	Game game;
	SurfaceHolder holder;
	Bitmap bitmap;
	Bitmap cross;
	Bitmap gol;
	Bitmap numbers;
	Bitmap score;
	Bitmap result;
	Bitmap settings;
	Bitmap board;
	Bitmap online;
	Bitmap about;
	Bitmap help1;
	Bitmap help2;
	Bitmap vibrStateOn;
	Bitmap record;
	Bitmap stats;
	Bitmap vibrStateOff;
	Bitmap five;
	Bitmap seven;
	Rect dst;
	public int screenState=0;
	AssetLoader assets;
	AssetManager ass;
	ArrayList<Rect> CrossMoveList;
	ArrayList<Rect> GolMoveList;
	Rect random;
	Rect showScoresX;
	Rect showScoresO;
	Random rn;
	int move=0;
	ArrayList<Integer> crossType;
	ArrayList<Integer> golType;
	public boolean mode;
	public Render(Context context,Game g,double scaleX2,double scaleY2){
		super(context);
		scaleX=scaleX2;
		scaleY=scaleY2;
		game=g;
		holder=getHolder();
		ass=context.getAssets();
		
		assets=new AssetLoader();
		dst=new Rect();
		CrossMoveList=new ArrayList<Rect>();
		GolMoveList=new ArrayList<Rect>();
		random=new Rect();
		rn=new Random();
		crossType= new ArrayList<Integer>();	
		golType=new ArrayList<Integer>();
	}
	
	public void gameLoad(int size){
		
		long temp=(rn.nextInt()*System.currentTimeMillis())%4;
		int n=(int)Math.abs(temp);
		String filename=null;
		switch(n){
		case 0:if (size==25) filename="board.png";
				if (size==49) filename="board7_2.png";
				cross=assets.load(ass, "cross.png",2);
				gol=assets.load(ass,"gol.png",2);		
				
				break;
		case 1:if (size==25) filename="board2.png";
				if (size==49) filename="board7_1.png";
				
				cross=assets.load(ass, "cross2.png",2);
				gol=assets.load(ass,"gol2.png",2);		
				
				break;
		case 2:if (size==25) filename="board3.png";
				if (size==49) filename="board7_4.png";
				cross=assets.load(ass, "cross.png",2);
				gol=assets.load(ass,"gol.png",2);		
				break;
		case 3:if (size==25) filename="board4.png";
				if (size==49) filename="board7_3.png";
				cross=assets.load(ass, "cross2.png",2);
				gol=assets.load(ass,"gol2.png",2);		
				break;

		}
		
		board=assets.load(ass, filename ,1);
		numbers=assets.load(ass,"numbers.png",1);
		score=assets.load(ass, "Score.png",2);
		screenState=2;
		showScoresX=new Rect();
		showScoresO=new Rect();
	}
	public void settingsload(){
		settings=assets.load(ass,"Settings.png",2);
		
		screenState=6;
	}
	public void helpload(){
		help1=assets.load(ass, "help.png", 2);
		help2=assets.load(ass,"helpPage2.png",2);
		screenState=7;
	}
	public void helpDispose(){
		help1.recycle();
		help2.recycle();
		help1=null;
		help2=null;
		
	}
	public void aboutLoad(){
		about=assets.load(ass, "aboutPage.png", 2);
		screenState=10;
	}
	public void aboutDispose(){
		about.recycle();
		about=null;
	}
	
	public void gameDispose(){
		cross.recycle();
		cross=null;
		gol.recycle();
		gol=null;
		numbers.recycle();
		numbers=null;
		score.recycle();
		score=null;
		board.recycle();
		board=null;
		screenState=0;
	}
	public void resetScreenState(){
		screenState=0;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long lastUpdateTime=System.nanoTime();
		long currentTime;
		
		while(listening){
			currentTime=System.nanoTime();
			
			if(currentTime-lastUpdateTime> 300000000){
				update();
				lastUpdateTime=currentTime;
			}
			else{
				continue;
			}
			int i=0;
			if(!holder.getSurface().isValid()){
				continue;
			}
			Canvas canvas=holder.lockCanvas();
			canvas.getClipBounds(dst);
			if(screenState==1||screenState==6){
				
			canvas.drawBitmap(bitmap, null, dst, null);
			canvas.drawBitmap(stats,(int)(10/scaleX),(int)(620/scaleY),null);
			if (game.vibrate){
				canvas.drawBitmap(vibrStateOn,(int)(420/scaleX),(int)(620/scaleY),null);
			}
			else{
				canvas.drawBitmap(vibrStateOff,(int)(420/scaleX),(int)(620/scaleY),null);
				
			}
			
			if (game.boardSize){
				canvas.drawBitmap(five,(int)(230/scaleX),(int)(620/scaleY),null);
			}
			else{
				canvas.drawBitmap(seven,(int)(230/scaleX),(int)(620/scaleY),null);
				
			}
			
			if (screenState==6){
				Rect temp=new Rect(0,(int)(300/scaleY),(int)(480/scaleX),(int)(500/scaleY));
				canvas.drawBitmap(settings,null,temp,  null);
			
			}
			
			}
			
			if (screenState==2 || screenState==4||screenState==5){
				canvas.drawBitmap(board, null, new Rect(0,0,(int)(480/scaleX),(int)(700/scaleY)), null);
				canvas.drawBitmap(score,null,new Rect(0,(int)(700/scaleY),(int)(480/scaleX),(int)(800/scaleY)),null);	
				showScoresX=getRect(game.getCurrentScreen().getP1Score()/10);
				showScoresO=getRect(game.getCurrentScreen().getP2Score()/10);
				canvas.drawBitmap(numbers, showScoresX, new Rect((int)(130/scaleX),(int)(710/scaleY),(int)(180/scaleX),(int)(780/scaleY)),null);
				canvas.drawBitmap(numbers, showScoresO, new Rect((int)(370/scaleX),(int)(710/scaleY),(int)(420/scaleX),(int)(780/scaleY)),null);
				showScoresX=getRect(game.getCurrentScreen().getP1Score()%10);
				showScoresO=getRect(game.getCurrentScreen().getP2Score()%10);
				canvas.drawBitmap(numbers, showScoresX, new Rect((int)(180/scaleX),(int)(710/scaleY),(int)(230/scaleX),(int)(780/scaleY)),null);
				canvas.drawBitmap(numbers, showScoresO, new Rect((int)(420/scaleX),(int)(710/scaleY),(int)(470/scaleX),(int)(780/scaleY)),null);
				
				for  (i=0;i<CrossMoveList.size();i++){
					random.set(75*crossType.get(i),0,75*(crossType.get(i)+1),50);
					canvas.drawBitmap(cross,random,CrossMoveList.get(i),null);
				}
			for (i=0;i<GolMoveList.size();i++){
					random.set(75*golType.get(i),0,75*(golType.get(i)+1),50);
					canvas.drawBitmap(gol,random,GolMoveList.get(i),null);
				}
			}
			
			if (screenState==4){
				
				canvas.drawBitmap(result,null ,new Rect((int)(10/scaleX),(int)(230/scaleY),(int)(470/scaleX),(int)(530/scaleY)),null);
			}
			if (screenState==7){
				canvas.drawBitmap(help1,null,dst,null);
			}
			if (screenState==8){
				canvas.drawBitmap(help2,null,dst,null);
				
			}
			if (screenState==9){
				int w,l,d;
				canvas.drawBitmap(record,null ,new Rect(0,(int)(100/scaleY),(int)(480/scaleX),(int)(700/scaleY)),null);
				if (mode){
					canvas.drawBitmap(five,null,new Rect((int)(100/scaleX),(int)(550/scaleY),(int)(180/scaleX),(int)(630/scaleY)),null);
				}
				else{
					canvas.drawBitmap(seven,null,new Rect((int)(100/scaleX),(int)(550/scaleY),(int)(180/scaleX),(int)(630/scaleY)),null);
					
				}
				
				Record tmp = new Record();
				tmp.readFile(game.fileIO);
				if (mode){
				w=tmp.getStat()[0];
				l=tmp.getStat()[1];
				d=tmp.getStat()[2];
				}
				else{
				 w=tmp.getStat2()[0];
				 l=tmp.getStat2()[1];
				 d=tmp.getStat2()[2];
					
				}
				if (w==998 || l==998 || d==998){
					tmp.reset();
					tmp.updateFile(game.fileIO);
					
				}
				
				if (w>99) canvas.drawBitmap(numbers, getRect(w/100), new Rect((int)(240/scaleX),(int)(190/scaleY),(int)(290/scaleX),(int)(250/scaleY)), null);
				if (l>99) canvas.drawBitmap(numbers, getRect(l/100), new Rect((int)(240/scaleX),(int)(310/scaleY),(int)(290/scaleX),(int)(370/scaleY)), null);
				if (d>99) canvas.drawBitmap(numbers, getRect(d/100), new Rect((int)(240/scaleX),(int)(430/scaleY),(int)(290/scaleX),(int)(490/scaleY)), null);

				if (w>9) canvas.drawBitmap(numbers, getRect((w/10)%10), new Rect((int)(290/scaleX),(int)(190/scaleY),(int)(340/scaleX),(int)(250/scaleY)), null);
				if (l>9) canvas.drawBitmap(numbers, getRect((l/10)%10), new Rect((int)(290/scaleX),(int)(310/scaleY),(int)(340/scaleX),(int)(370/scaleY)), null);
				if (d>9) canvas.drawBitmap(numbers, getRect((d/10)%10), new Rect((int)(290/scaleX),(int)(430/scaleY),(int)(340/scaleX),(int)(490/scaleY)), null);
				
				canvas.drawBitmap(numbers, getRect(w%10), new Rect((int)(340/scaleX),(int)(190/scaleY),(int)(400/scaleX),(int)(250/scaleY)), null);
				canvas.drawBitmap(numbers, getRect(l%10), new Rect((int)(340/scaleX),(int)(310/scaleY),(int)(400/scaleX),(int)(370/scaleY)), null);
				canvas.drawBitmap(numbers, getRect(d%10), new Rect((int)(340/scaleX),(int)(430/scaleY),(int)(400/scaleX),(int)(490/scaleY)), null);
				
			}
			
			if (screenState==10){
				canvas.drawBitmap(about, null, dst,null);
			}
			holder.unlockCanvasAndPost(canvas);
			
			
			
			
		}
		
	}
	private Rect getRect(int score) {
		// TODO Auto-generated method stub
		return (new Rect(57*score,0,57*(score+1),70));
	}
	private void update() {
		// TODO Auto-generated method stub
		game.setX(game.touch.getX());
		game.setY(game.touch.getY());
		game.getCurrentScreen().update();
		
		}
	public void pause() {
		// TODO Auto-generated method stub
		listening=false;
		while (true){
			try{
				renderThread.join();
			
			}
			catch(Exception E){
				
			}
		}
	}
	public void resume() {
		// TODO Auto-generated method stub
		listening=true;
		renderThread=new Thread(this);
		renderThread.start();
	}
	
	public void userMove(Rect dst2) {
		// TODO Auto-generated method stub
		
		long temp=(rn.nextInt()+System.currentTimeMillis())%4;
		int n=(int)Math.abs(temp);
		
		move++;
		if (move%2==1){
			CrossMoveList.add(dst2);
			crossType.add(n);
			
			}
		else{
			GolMoveList.add(dst2);
			golType.add(n);
			}
		
		
	}
	public void resetUserMove(){
		move=0;
		CrossMoveList.clear();
		GolMoveList.clear();
	}
	public void menuload() {
		// TODO Auto-generated method stub
		five=assets.load(ass,"5.png",2);
		seven=assets.load(ass, "7.png", 2);
		bitmap=assets.load(ass, "menu.png",1);
		vibrStateOff=assets.load(ass, "vibrateOff.png", 2);
		vibrStateOn=assets.load(ass, "vibrateOn.png", 2);
		stats=assets.load(ass, "stats.png", 2);
		screenState=1;
	}
	public void menuDispose(){
		screenState=0;
		bitmap.recycle();
		bitmap=null;
		
		//settings.recycle();
		//settings=null;
	}
	public void showResults(String winner) {
		// TODO Auto-generated method stub
		screenState=4;
		switch(winner){
		case "USER":
			result=assets.load(ass, "you.png",2);
				break;
		case "CPU":
		
			result=assets.load(ass,"cpu.png",2);
			break;
		case "D":
			result=assets.load(ass,"draw.png",2);
			break;
		case  "P1":
			result=assets.load(ass, "player1.png",2);
			break;
		case "P2":
			result=assets.load(ass, "player2.png",2);
			break;
		}
	}

	public void recordLoader() {
		// TODO Auto-generated method stub
		numbers=assets.load(ass, "numbers2.png", 2);
		record=assets.load(ass, "record.png", 2);
		screenState=9;
		mode=game.boardSize;
	}
	public void recordDispose(){
		record.recycle();
		record=null;
		numbers.recycle();
		numbers=null;
	}
}
