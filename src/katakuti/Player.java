package katakuti;

import java.util.ArrayList;


//Interface from where the CPU Player and Human Player classes will be derived.
public interface Player {
		int score=0;
		int playerId=0;
		ArrayList<ArrayList<Integer>> combo= new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> moveList=new ArrayList<Integer>();
		ArrayList<Integer> opponentMoveList=new ArrayList<Integer>();
		int availableMoves = 0;
		public  String name="";
		public void setScore(int score);
		public ArrayList<ArrayList<Integer>> Combo();
		public void setId(int id);
		public int getScore();
		public int getId();
		public int move();
		public int sizeofAvailMoves();
		public ArrayList<Integer> getopponentMoveList();
		public ArrayList<Integer> getMoveList();
		public void setAvailMoves(int tempIndex);
		public void updateMoveList(int tempIndex);
		public void setopponentMoveList(int tempIndex);
		public String getName();
	}
