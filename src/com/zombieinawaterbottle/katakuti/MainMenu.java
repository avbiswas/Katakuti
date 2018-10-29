package com.zombieinawaterbottle.katakuti;

import framework.Screen;

public class MainMenu implements Screen {
	Game game;
	int size;
	MainMenu(Game g){
		
		game=g;
		game.render.menuload();
		
		Record temp=new Record();
		temp.readFile(game.fileIO);
		
		setSize(temp.getS());
	}
	private void setSize(int s) {
		// TODO Auto-generated method stub
		size=s;
		game.boardSize=(size==25)?true:false;
	}
	@Override
	public void update(){
		int x=game.getX();
		int y=game.getY();
		if ((x>420 && x<470) && (y>620 && y<670)){
			game.vibrate=!(game.vibrate);
			if (game.vibrate) game.vibr();
			game.touch.reset();
			return;
		}
		if ((x>230 && x<280) && (y>620 && y<670)){
			game.boardSize=!(game.boardSize);
			if (game.boardSize) setSize(25);
			else setSize(49);
			game.touch.reset();
			return;
		}
		
		if (game.render.screenState==6){
			if (x==-1) return;
			
			game.render.screenState=1;
			game.touch.reset();
			
			if (y>300 && y<400){
				game.render.menuDispose();
				if (game.vibrate) game.vibr();
				
				game.changeScreen(new SinglePlayer(game,1,getsize()));
			}
			else if(y>400 && y<500){
				game.render.menuDispose();
				if (game.vibrate) game.vibr();
				
				game.changeScreen(new SinglePlayer(game,2,getsize()));
			}
			return;
		}
		else if(game.render.screenState==9){
			if (game.getKey()==1){
				game.setKey();
				game.render.recordDispose();
				game.render.screenState=1;
				return;
			}
			
			
			if (x==-1) return;
			game.touch.reset();
			
			
			
			if (x>290 && x<420 && y>520 && y<640){
				
				if (game.vibrate) game.vibr();
				Record temp=new Record();
				temp.reset();
				
				temp.updateFile(game.fileIO);
				return;
			}
			
			if (x>100 && x<180 && y>550 && y<630){
				game.render.mode=!game.render.mode;
				return;
			}
			
			game.render.recordDispose();
			game.render.screenState=1;
			
			return;
		}
		else
		{
		if (game.getKey()==1){
				game.end();
				return;
			}
				
		if ((x>90 && x<425) && (y>220 && y<285)){
			game.touch.reset();
			game.render.settingsload();
			return;
		}
		if ((x>90 && x<425) && (y>330 && y<390)){
			game.touch.reset();
			game.render.menuDispose();
			
			game.changeScreen(new SinglePlayer(game,3,getsize()));
			if (game.vibrate) game.vibr();
			return;
		}
		
		if ((x>90 && x<425) && (y>460 && y<550)){
			game.touch.reset();
			game.render.menuDispose();
			
			game.changeScreen(new Help(game));
			return;
		}
		if (y>700 && x<250){
			game.touch.reset();
			game.render.menuDispose();
			
			game.changeScreen(new About(game));
			return;
			
		}
		if (x>10 && x<80 && y>620 && y<660){
			game.touch.reset();
			game.render.recordLoader();
			if (game.vibrate) game.vibr();
			
			return;
		}
		}
		
		if (y>710 && x>270){
			
			game.end();
		}
		
	
	}
	private int getsize() {
		// TODO Auto-generated method stub
		return size;
	}
	
	@Override
	public int getP1Score() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getP2Score() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
