package katakuti;

import java.util.ArrayList;

import com.zombieinawaterbottle.katakuti.Game;


//User player
public class User implements Player {
	public String name="USER";
	int score;
	int playerId;
	int size;
	int move;
	Game game;
	ArrayList<Integer> moveList;
	ArrayList<Integer> opponentMoveList;
	ArrayList<Integer> availableMoves;
	public User(int size, int id, Game g){
		game=g;
		this.size=(int) Math.sqrt(size);
		move=0;
		this.setId(id);
		if (id==2){
			this.setScore(1);
		}
		else{
			this.setScore(0);
		}
		moveList=new ArrayList<Integer>();
		opponentMoveList=new ArrayList<Integer>();
		availableMoves=new ArrayList<Integer>();
		for (int i=0;i<size;i++){
			availableMoves.add(i);
		}
	}
	
	@Override
	public void setScore(int score) {
		this.score=score;
	}

	@Override
	public void setId(int id) {
		this.playerId=id;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public int getId() {
		return playerId;
	}

	
	public int move(){
		int x=game.getX();
		int y=game.getY();
		
		int m=-1;
		if (x!=-1 && y!=-1){
		
		m=convertToGrid(x,y);
		}
		return m;
		
		
	}
	@Override
	public int sizeofAvailMoves() {
		return availableMoves.size();
	}
	
	@Override
	public ArrayList<ArrayList<Integer>> Combo() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Integer> getopponentMoveList() {
		// TODO Auto-generated method stub
		return opponentMoveList;
	}
	@Override
	public ArrayList<Integer> getMoveList() {
		// TODO Auto-generated method stub
		return moveList;
	}
	@Override
	public void setAvailMoves(int tempIndex) {
		// TODO Auto-generated method stub
		int i;
		for (i=0;i<availableMoves.size();i++){
			if (availableMoves.get(i)==tempIndex){
				availableMoves.remove(i);
				break;
			}
		}
	}
	@Override
	public void updateMoveList(int tempIndex) {
		moveList.add(tempIndex);
	}
	@Override
	public void setopponentMoveList(int tempIndex) {
		// TODO Auto-generated method stub
		opponentMoveList.add(tempIndex);
	}
	@Override
	public String getName(){
		return name;
	}
	public void setMove(int i) {
		// TODO Auto-generated method stub
		move=i;
	}
	
	
	
	
	private int convertToGrid(int x, int y) {
		if (x>475)x=475;
		int col=(int) Math.floor(x/(480/size));
		int row=y/(700/size);
		return (row*size+col);
		// TODO Auto-generated method stub
		
	}
	
}
