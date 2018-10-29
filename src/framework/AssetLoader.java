package framework;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class AssetLoader {
	Bitmap temp;
	public Bitmap load(AssetManager ass, String string,int type) {
		// TODO Auto-generated method stub
		
		try {
			BitmapFactory.Options options=new BitmapFactory.Options();
			if (type==1){
				options.inPreferredConfig=Bitmap.Config.ARGB_8888;
			}
			else{
				options.inPreferredConfig=Bitmap.Config.ARGB_4444;
				
			}
			InputStream is= ass.open(string);
			temp=BitmapFactory.decodeStream(is,null,options);
			is.close();
			return temp;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
