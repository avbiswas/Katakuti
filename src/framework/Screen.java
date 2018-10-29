package framework;

import com.zombieinawaterbottle.katakuti.Game;

public interface Screen {
	Game game=null;
	void update();
	int getP1Score();
	int getP2Score();
	
	
}
