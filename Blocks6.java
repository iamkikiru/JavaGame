package com.game10;

import java.awt.Color;

public class Blocks6 extends Blocks {
	
	public Blocks6() {
	}
	
	public void setBlock() {
		blockRow[0] = 0; blockCol[0] = 4; 
		blockRow[1] = 0; blockCol[1] = 5; 
		blockRow[2] = 0; blockCol[2] = 6; 
		blockRow[3] = 0; blockCol[3] = 7;  
		
		blockColor = new Color(224,134,4);
	}
	
	public void turnBlocks(int shape) {
		switch(shape) {
		case 0:
		case 2:
			blockCol[0]=blockCol[0]+2; blockRow[0]=blockRow[0];
			blockCol[1]=blockCol[1]+1; blockRow[1]=blockRow[1]+1;
			blockCol[2]=blockCol[2]; blockRow[2]=blockRow[2]+2;
			blockCol[3]=blockCol[3]-1; blockRow[3]=blockRow[3]+3;
			break;
		case 1:
		case 3:
			blockCol[0]=blockCol[0]-2; blockRow[0]=blockRow[0];
			blockCol[1]=blockCol[1]-1; blockRow[1]=blockRow[1]-1;
			blockCol[2]=blockCol[2]; blockRow[2]=blockRow[2]-2;
			blockCol[3]=blockCol[3]+1; blockRow[3]=blockRow[3]-3;
			break;
		}
	}
}