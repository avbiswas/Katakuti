package com.zombieinawaterbottle.katakuti;

import framework.Screen;

public class About implements Screen {
	Game game;
	public About(Game g){
		game=g;
		game.render.aboutLoad();
	}
	@Override
	public void update() {
		
		// TODO Auto-generated method stub
		int x=game.getX();
		int y=game.getY();
		if (game.getKey()==1){
			game.setKey();
			game.render.helpDispose();
			game.changeScreen(new MainMenu(game));
		
		}
		
		if (game.render.screenState==10){
			if (x==-1) return;
			if (y>600 && x>200){
			game.touch.reset();
			
			game.render.aboutDispose();
			game.changeScreen(new MainMenu(game));
			}
		}
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
