package katakuti;

//This class represents the CPU Player.

import java.util.ArrayList;
import java.util.Random;

public class CPUplayer implements Player {
	ArrayList<ArrayList<Integer>> combo;	//stores the different combinations of points that are possible to recieve points
	public  String name="CPU";
	private int size;
	private int score;
	private int playerId;
	private ArrayList<Integer> moveList;	//stores the cells occupied by the current player
	private ArrayList<Integer> opponentMoveList;	//stores the cells occupied by the opponent
	ArrayList<ArrayList<Integer>> availableMoves;	//stores the free cells in the grid
	public CPUplayer(int size, int id) {
		this.size=size;
		combo=new ArrayList<ArrayList<Integer>>();
		this.getCombo();
		if (id==2){
			this.setScore(1);		// Second player starts with a default score of 1
		}
		else{
			this.setScore(0);
		}
		this.setId(id);
		moveList=new ArrayList<Integer>();
		opponentMoveList=new ArrayList<Integer>();
		availableMoves=new ArrayList<ArrayList<Integer>>();
		
		this.fillAvailMoves();
	}
	public String getName(){
		return name;
	}
	public ArrayList<ArrayList<Integer>> Combo(){
		return combo;
	}
	
	//Function to fill the availableMoves list.
	private void fillAvailMoves() {
		int k= (int) Math.ceil((Math.sqrt(size))/2)-1;
		int row,col;
			for (int i=0;i<size;i++){
				availableMoves.add(new ArrayList<Integer>());
				row=(int) (i/(Math.sqrt(size)));
				col=(int) (i%(Math.sqrt(size)));
				int preference=0;
				if(row==Math.sqrt(size) && col==Math.sqrt(size)){preference++;}
				
				row=k-(Math.abs(k-row));
				col=k-(Math.abs(k-col));
				preference+=row<col?row:col;
				
				availableMoves.get(i).add(i);
				availableMoves.get(i).add(preference*4);}
			
			
			//availableMoves=sortByPreference(availableMoves);
			
	}
	
	// finds the most preferred move from the available moves array by sorting with highest pref.
	public ArrayList<ArrayList<Integer>> sortByPreference(ArrayList<ArrayList<Integer>> tempAvail){

		ArrayList<ArrayList<Integer>> sort= new ArrayList<ArrayList<Integer>>();
		int max,pos,i=0,j;
		while (tempAvail.size()!=0){
			sort.add(new ArrayList<Integer>());
			max=0;
			pos=0;
			
			for (j=0;j<tempAvail.size();j++){
				
				if (max < tempAvail.get(j).get(1)){
					max=tempAvail.get(j).get(1);
					pos=j;
				}
			}
			System.out.println();
			sort.get(i).addAll(tempAvail.get(pos));
			tempAvail.remove(pos);
			i++;
		}
		
		return sort;
		}
	
	// Populates all the combinations of cells (1-D indexes) that will win points.
	
	private void getCombo() {
		int i,j,row=-1;
		int len=(int) Math.sqrt((double)size);
		for (i=1;i<=len;i++){						//ROWS
			row++;
			combo.add(new ArrayList<Integer>());
			for (j=1;j<=len;j++){
				combo.get(row).add(len*(i-1)+(j-1));
				}
		}
		for (i=1;i<=len;i++){						//COLS
			combo.add(new ArrayList<Integer>());
			row++;
			for (j=1;j<=len;j++){
				combo.get(row).add(len*(j-1)+(i-1));
				}
			
		}
		for (i=1;i<=len-2;i++){
			//UpperDIAGONAL RtoL
			combo.add(new ArrayList<Integer>());
			row++;
			for (j=1;j<=len-i+1;j++){
				combo.get(row).add(len*(j-1)+((-1+j)+i-1));
				}
		}	
		for (i=3;i<=len;i++){						//Lower Diagonal RtoL
			combo.add(new ArrayList<Integer>());
			row++;
			for (j=1;j<=i;j++){
				combo.get(row).add(len*(j-1)+(i-j));
				}
			
		}	
		for (i=1;i<len-2;i++){						//UpperDIAGONAL LtoR
			combo.add(new ArrayList<Integer>());
			row++;
			for (j=1;j<=len-i;j++){
				combo.get(row).add(len*(j+i-1)+(j-1));
				}
		
		}	
		for (i=2;i<=len-2;i++){						//Lower Diagonal LtoR
			row++;
			combo.add(new ArrayList<Integer>());
			for (j=1;j<=len-i+1;j++){
				combo.get(row).add(len*(j+i-1)-j);
				}
				}	
		}
	@Override
	public void setScore(int score) {
		// TODO Auto-generated method stub
		this.score=score;
	}
	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub
		this.playerId=id;
	}
	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return score;
	}
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return playerId;
	}
	public void updateMoveList(int move) {
		moveList.add(move);	
	}
	public void setopponentMoveList(int index)
	{
		opponentMoveList.add(index);
		//System.out.println("USER MATRIX INSIDE CPU="+opponentMoveList);
			
	}
	public void setAvailMoves(int index){
		int i;
		for (i=0;i<availableMoves.size();i++){
			if (availableMoves.get(i).get(0)==index){
				availableMoves.remove(i);
				break;
			}
		}
	}
	public int sizeofAvailMoves(){
		return availableMoves.size();
	}
	
	//Decides the best move
	public int move(){
		int i=0;
		ArrayList<ArrayList<Integer>> tempAvail=new ArrayList<ArrayList<Integer>>();
		
		for (i=0;i<availableMoves.size();i++){
			tempAvail.add(new ArrayList<Integer>());
			int index=availableMoves.get(i).get(0);
			int pref=availableMoves.get(i).get(1);
			tempAvail.get(i).add(index);

			tempAvail.get(i).add(pref);
		}
		i=0;
		if (sizeofAvailMoves()==size){
			tempAvail=sortByPreference(tempAvail);
			return (tempAvail.get(0).get(0));
		}
			while (i<tempAvail.size()){
			int preference=tempAvail.get(i).get(1);	
			preference+=analyzeAttack(tempAvail.get(i).get(0));	//calculate preference for Attack wrt current cell
			preference+=analyzeDefend(tempAvail.get(i).get(0));	//calculate preference for Defence wrt current cell
			tempAvail.get(i).set(1, preference);
			i++;
			}
			//tempAvail.addAll(sortByPreference(tempAvail));
			

			tempAvail=sortByPreference(tempAvail);
			
			return moverandom(tempAvail);
	}
	
	
	//Array to identify critical cells where current player can score. Higher scoring cells will be given more priority when deciding the final move.
	public int analyzeAttack(int index){
		ArrayList<ArrayList<Integer>> temp= new ArrayList<ArrayList<Integer>>();
		int i=0,c=0,preference=0;
		do{
			if(combo.get(i).contains(index)){
				temp.add(new ArrayList<Integer>());
				temp.get(c++).addAll(combo.get(i));
				
				}
			i++;
		}
		while (i!=combo.size());
		i=0;
		do{ 
			int matches=0;
			boolean foundMove=false;
			for (int j=0;j<temp.get(i).size();j++){
				
				if (temp.get(i).get(j)==index){
					foundMove=true;
					continue;
				}
				if(moveList.contains(temp.get(i).get(j)))
					{	
						matches++; // higher the matches, higher the potential for a scoring points with the respective cell
						
					}
					else
					{
						if (matches>=1 && foundMove==true){
							switch(matches){
							 case 1: preference+=2;break;	// intuitive hardcoded values for various values of matches
							 case 2: preference+=4;
							 			break;
							 case 3: preference +=7;
							 		break;
							 case 4: preference+=12; break;
							 case 5: preference+= 15; break;
							 
							 default:
							preference+=matches*2;
							}
						}
						else{
							foundMove=false;
						}
						
						matches=0;
					}
			}
				if (matches>=1 && foundMove==true){
					switch(matches){
					 case 1: preference+=2;break;
					 case 2: preference +=4;
					 			break;
					 case 3: preference +=7;
					 		break;
					 case 4: preference+=12; break;
					 case 5: preference+= 15; break;
					 default:
					preference+=matches*2;
					}
				}
				
			i++;
		}
		while(i!=temp.size());
		return preference;
			
	}
	
	//Array to identify critical cells where opponent can score. Higher scoring cells will be given more priority when deciding the final move.
	public int analyzeDefend(int index){
		ArrayList<ArrayList<Integer>> temp= new ArrayList<ArrayList<Integer>>();
		int i=0,c=0,preference=0;
		do{
			if(combo.get(i).contains(index)){
				temp.add(new ArrayList<Integer>());
				temp.get(c++).addAll(combo.get(i));
				
				}
			i++;
		}
		while (i!=combo.size());
		
		i=0;
		do{ 
			int matches=0;
			boolean foundMove=false;
			
			for (int j=0;j<temp.get(i).size();j++){

			
					if (temp.get(i).get(j)==index){
						foundMove=true;
						continue;
					}
					if(opponentMoveList.contains(temp.get(i).get(j)))
					{	
						matches++;	//higher the value, more high-scoring it will be for the opponent. So we must priorotize it.
						}
					else
					{
						if (matches>1 && foundMove==true){
						switch(matches){
						 case 2: preference +=5;
						 			break;
						 case 3: preference +=8;
						 		break;
						 case 4: preference +=13; break;
						 default:preference +=preference*5;
						 }
						}
						else{
							foundMove=false;
						}
					matches=0;
					}
				}
			if (matches>1 && foundMove==true){
				 switch(matches){
				 case 2: preference +=5;
				 			break;
				 case 3: preference +=8;
				 		break;
				 case 4: preference +=13; break;
				 default:preference +=preference*5;
				 }
				 }
			i++;
		}
		while(i!=temp.size());
		return preference;
			
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
	private int moverandom(ArrayList<ArrayList<Integer>> tempAvail) {
		// TODO Auto-generated method stub
		int p=tempAvail.get(0).get(1);
		int c=0;
		while(true && c<tempAvail.size()){
			if (tempAvail.get(c).get(1)==p){
				c++;
				continue;
			}
			break;
		}
		
		
		long temp=(int)((new Random()).nextInt()+System.currentTimeMillis())%c;
		int index=(int)Math.abs(temp);
		return tempAvail.get(index).get(0);
	}
	
	
}
