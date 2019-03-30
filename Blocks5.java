package com.game10;

import java.awt.Color;

public class Blocks5 extends Blocks {
	
	public Blocks5() {
	}
	
	public void setBlock() {
		blockRow[0] = 0; blockCol[0] = 6; 
		blockRow[1] = 1; blockCol[1] = 5; 
		blockRow[2] = 1; blockCol[2] = 6; 
		blockRow[3] = 2; blockCol[3] = 5; 
		
		blockColor = new Color(246,118,57);
	}
	
	public void turnBlocks(int shape) {
		switch(shape) {
		case 0:
		case 2:
			blockCol[0]=blockCol[0]-1; blockRow[0]=blockRow[0];
			blockCol[1]=blockCol[1]+1; blockRow[1]=blockRow[1]-1;
			blockCol[2]=blockCol[2]; blockRow[2]=blockRow[2];
			blockCol[3]=blockCol[3]+2; blockRow[3]=blockRow[3]-1;
			break;
		case 1:
		case 3:
			blockCol[0]=blockCol[0]+1; blockRow[0]=blockRow[0];
			blockCol[1]=blockCol[1]-1; blockRow[1]=blockRow[1]+1;
			blockCol[2]=blockCol[2]; blockRow[2]=blockRow[2];
			blockCol[3]=blockCol[3]-2; blockRow[3]=blockRow[3]+1;
			break;
		}
	}
}