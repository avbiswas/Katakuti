package com.zombieinawaterbottle.katakuti;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Record {
	int[] stat=new int[]{0,0,0};
	int[] stat2=new int[]{0,0,0};
	int size=25;
	public void reset(){
		stat=new int[]{0,0,0};
		stat2=new int[]{0,0,0};
	}
	public void readFile(FileIO files){
		BufferedReader in = null;
		try {
			
			in = new BufferedReader(new InputStreamReader(
			          files.readFile("record.txt")));
			for (int i=0;i<3;i++){
				stat[i]=Integer.parseInt(in.readLine());
				}
			for (int i=0;i<3;i++){
				stat2[i]=Integer.parseInt(in.readLine());
				}
			size=Integer.parseInt(in.readLine());
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
		finally{
			try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
		}
        
	}
	public int[] getStat(){
		return stat;
	}
	public int[] getStat2(){
		return stat2;
	}
	public int getS(){
		return size;
	}
	public void setS(int s){
		size=s;
	}
	void updateStat(int result){
		if (size==25){
		stat[result]++;

		}
		else{
		stat2[result]++;
		}
	}
	
	public void updateFile(FileIO files){
		BufferedWriter out=null;
		 try {
	            out = new BufferedWriter(new OutputStreamWriter(
	                    files.writeFile("record.txt")));
	            for (int i = 0; i < 3; i++) {
	                out.write(Integer.toString(stat[i]));
	                out.write("\n");
	            }
	            for (int i = 0; i < 3; i++) {
	                out.write(Integer.toString(stat2[i]));
	                out.write("\n");
	            }
	            out.write(Integer.toString(size));
	        } catch (IOException e) {
	        	e.printStackTrace();
	        } 
		 finally {
	            try {
	                if (out != null)
	                    out.close();
	            } catch (IOException e) {
	            }
	        }

	}
}
