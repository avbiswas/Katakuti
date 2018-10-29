package com.zombieinawaterbottle.katakuti;

import framework.Screen;

public class Help implements Screen{
	Game game;
	public Help(Game g){
		game=g;
		game.render.helpload();
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
		if (game.render.screenState==7){
			if (x==-1) return;
			game.touch.reset();
			
			if (y>710 && x>250){
				game.render.screenState=8;
				return;
			}
			else{
				game.render.helpDispose();
				game.changeScreen(new MainMenu(game));
			}
		}
		if (game.render.screenState==8){
			if (x==-1) return;
			game.touch.reset();
			game.render.helpDispose();
			game.changeScreen(new MainMenu(game));
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
