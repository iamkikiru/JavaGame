package com.game10;

import java.awt.Color;

public class Blocks2 extends Blocks {
	
	public Blocks2() {
	}
	
	public void setBlock() {
		blockRow[0] = 0; blockCol[0] = 7; 
		blockRow[1] = 1; blockCol[1] = 5; 
		blockRow[2] = 1; blockCol[2] = 6; 
		blockRow[3] = 1; blockCol[3] = 7;   
		
		blockColor = new Color(128,0,64);
	}
	
	public void turnBlocks(int shape) {
		switch(shape) {
		case 0:
			blockCol[0]=blockCol[0]-2; blockRow[0]=blockRow[0];
			blockCol[1]=blockCol[1]+1; blockRow[1]=blockRow[1]-1;
			blockCol[2]=blockCol[2]; blockRow[2]=blockRow[2];
			blockCol[3]=blockCol[3]-1; blockRow[3]=blockRow[3]+1;
			break;
		case 1:
			blockCol[0]=blockCol[0]; blockRow[0]=blockRow[0];
			blockCol[1]=blockCol[1]; blockRow[1]=blockRow[1];
			blockCol[2]=blockCol[2]+1; blockRow[2]=blockRow[2]-1;
			blockCol[3]=blockCol[3]-1; blockRow[3]=blockRow[3]-1;
			break;
		case 2:
			blockCol[0]=blockCol[0]+1; blockRow[0]=blockRow[0];
			blockCol[1]=blockCol[1]; blockRow[1]=blockRow[1]+1;
			blockCol[2]=blockCol[2]-1; blockRow[2]=blockRow[2]+2;
			blockCol[3]=blockCol[3]+2; blockRow[3]=blockRow[3]+1;
			break;
		case 3:
			blockCol[0]=blockCol[0]+1; blockRow[0]=blockRow[0];
			blockCol[1]=blockCol[1]-1; blockRow[1]=blockRow[1];
			blockCol[2]=blockCol[2]; blockRow[2]=blockRow[2]-1;
			blockCol[3]=blockCol[3]; blockRow[3]=blockRow[3]-1;
			break;
		}
	}
}