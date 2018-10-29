package com.zombieinawaterbottle.katakuti;

import java.util.ArrayList;

import katakuti.CPUplayer;
import katakuti.Player;
import katakuti.User;
import android.graphics.Rect;
import framework.Screen;

public class SinglePlayer implements Screen{
	
	Game game;
	Record rec;
	Player player1;
	Player player2;
	int size;
	ArrayList<Integer> availableMoves;
	int move=0;
	ArrayList<ArrayList<Integer>> combo;
	int id;
	boolean Player1Turn;
	boolean Player2Turn;
	SinglePlayer(Game g, int choice,int b_s){
		game=g;
		rec=new Record();
		
		game.touch.reset();
		size=(int) Math.sqrt(b_s);
		game.render.gameLoad(size*size);
		id=choice;
		Player1Turn=true;
		Player2Turn=false;
		availableMoves=new ArrayList<Integer>();
		for (int i=0;i<(size*size);i++){
			availableMoves.add(i);
		}
			if (id==1){
			player2=new CPUplayer(size*size,2);
			player1= new User(size*size,1,game);
			this.combo=player2.Combo();
			}
			else if (id==2){
			player1=new CPUplayer(size*size, 1);
			player2=new User(size*size,2,game);
			
			this.combo=player1.Combo();
			}
			else if (id==3){
				player1=new User(size*size,1,game);
				player2=new User(size*size,2,game);
				this.combo=(new CPUplayer(size*size,1)).Combo();
			}
		
	}
	@Override
	public void update() {
		
		
		if (game.render.screenState==4)
		{	
			if(game.getX()!=-1){
				game.render.screenState=5;
				game.touch.reset();
			}
			return;
		}
		if (game.render.screenState==5){
			if(game.getX()!=-1){
				goBack();
			}
			if (game.getKey()==1){
				goBack();
			}
			
			return;
		
		}
		if (game.getKey()==1){
			goBack();
			return;
			}
		// TODO Auto-generated method stub
		else{
			if(Player1Turn==true && Player2Turn==false)
				player1move();
			
			if (availableMoves.size()==0){
				result();
				return;
			}
			
			if(Player2Turn==true && Player1Turn==false)
			player2move();
			
			if (availableMoves.size()==0){
				 result();
				 return;
				}
		}
	}
	
	
	private void result() {
		// TODO Auto-generated method stub
		if(game.vibrate)game.vibr();
		
		game.render.screenState=4;
		String winner=null; 
	
	if (id==1 || id==2){
			

		if (player1.getScore()>player2.getScore()){
		    	winner=player1.getName();
		    }
		else {
			winner=player2.getName();
		}
	}
	
	if (id==3){
		if (player1.getScore()>player2.getScore()){
	    	winner="P1";
	    }
	else {
		winner="P2";
		}
	}
		if (player1.getScore()==player2.getScore()){
			winner="D";
			}
		
		if (id==1||id==2){
			rec.readFile(game.fileIO);
			rec.setS(size*size);
			switch(winner){
			
			case "USER":
				rec.updateStat(0);
					break;
			case "CPU":
				rec.updateStat(1);
				break;
			case "D":
				rec.updateStat(2);
			}
		rec.updateFile(game.fileIO);
		}
		
		game.render.showResults(winner);	
	
		return;
	
	}

	private void goBack() {
		// TODO Auto-generated method stub
		if (game.vibrate){
			game.vibr();
		}
		game.setKey();
		game.render.gameDispose();
		game.render.resetUserMove();
		game.touch.reset();
		rec.readFile(game.fileIO);
		rec.setS(size*size);
		rec.updateFile(game.fileIO);
		
		game.changeScreen(new MainMenu(game));
		
		
	}
	private void player2move() {
		// TODO Auto-generated method stub
		int originalScore=player2.getScore();
		int m= player2.move();
		if (m==-1) return;
		boolean valid=false;
		for (int i=0;i<availableMoves.size();i++){
			if(availableMoves.get(i)==m){
				valid=true;
				availableMoves.remove(i);
			}
		}
		if (valid==true){
		Rect dst=convertToRec(m);
		Player2Turn=false;
		player2.setAvailMoves(m);
			player1.setAvailMoves(m);
			player2.updateMoveList(m);
			player1.setopponentMoveList(m);
			player2.setScore(this.score(m, player2.getId())+player2.getScore());
			
			if (game.vibrate){
			if (player2.getScore()!=originalScore){
				game.vibr();
			}
			}
			game.render.userMove(dst);
			Player1Turn=true;
	}

	}
	private void player1move() {
		// TODO Auto-generated method stub
			int originalScore=player1.getScore();
			int m= player1.move();
			if (m==-1) return;
			game.touch.reset();
			
			boolean valid=false;
			for (int i=0;i<availableMoves.size();i++){
				if(availableMoves.get(i)==m){
					valid=true;
					availableMoves.remove(i);
				}
			}
			if (valid==true){
			
			Player1Turn=false;
			Rect dst=convertToRec(m);
			player1.setAvailMoves(m);
			player2.setAvailMoves(m);
			
			player1.updateMoveList(m);
			player2.setopponentMoveList(m);
			player1.setScore(this.score(m, player1.getId())+player1.getScore());
			
			if (game.vibrate){
			if (player1.getScore()!=originalScore){
				game.vibr();
			}
			}
			game.render.userMove(dst);
			Player2Turn=true;
			
			}
		}
		
	
	
	private Rect convertToRec(int m) {
		// TODO Auto-generated method stub
		Rect temp = new Rect();
		int LUx,LUy,RLx,RLy;
		int row=m/size;
		int col=m%size;
		LUx=(480/size)*col+(size==5?20:10);
		RLx=LUx+(size==5?56:45);
		LUy=(700/size)*row+(size==5?25:20);
		RLy=LUy+(size==5?75:60);
		temp.set((int)(LUx/game.getScaleX()), (int)(LUy/game.getScaleY()), (int)(RLx/game.getScaleX()), (int)(RLy/game.getScaleY()));
		return temp;
	}
		public int score(int move, int player_flag){
		
		ArrayList<ArrayList<Integer>> temp=new ArrayList<ArrayList<Integer>>();
		boolean foundMove;
		int i=0;
		int c=0;
		int matches;
		int points=0;
		do{
			if(this.combo.get(i).contains(move)){
				temp.add(new ArrayList<Integer>());
				temp.get(c++).addAll(this.combo.get(i));
		
				}
			i++;	
		}
		while (i!=this.combo.size());
		i=0;
		do{ 
			matches=0;
			foundMove=false;
			
			for (int j=0;j<temp.get(i).size();j++){
	
				if (temp.get(i).get(j)==move){
					foundMove=true;
				}
				if (player_flag==1){
					if (foundMove==true && !player2.getopponentMoveList().contains(temp.get(i).get(j))){
						break;
					}
					if(player2.getopponentMoveList().contains(temp.get(i).get(j)))
					{	
						matches++;
					}
					else
					{
						if (matches>=3 && foundMove==true){
						points+=matches-2;
						}
						matches=0;
						}
				}
				else{
					if (foundMove==true && !player2.getMoveList().contains(temp.get(i).get(j))){
						break;
					}
					if(player2.getMoveList().contains(temp.get(i).get(j)))
					{
						matches++;
					}
					else{
						if (matches>=3 && foundMove==true){
							points+=matches-2;
						}
						matches=0;
						}
				}		
			}
			if (matches>=3 && foundMove==true){
				points+=matches-2;
			}
			i++;
		}
		while(i!=temp.size());
		temp.clear();
		
		return points;
	}
		@Override
		public int getP1Score() {
			// TODO Auto-generated method stub
			return player1.getScore();
		}
		@Override
		public int getP2Score() {
			// TODO Auto-generated method stub
			return player2.getScore();
		}
}

