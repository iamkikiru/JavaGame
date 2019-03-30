package com.game10;

import java.awt.Color;

public class Blocks0 extends Blocks {
	
	public Blocks0() {
	}
	
	public void setBlock() {
		blockRow[0] = 0; blockCol[0] = 5;
		blockRow[1] = 0; blockCol[1] = 6; 
		blockRow[2] = 1; blockCol[2] = 5; 
		blockRow[3] = 1; blockCol[3] = 6; 
		
		blockColor = new Color(65,228,82);
	}

	@Override
	public void turnBlocks(int shape) {
		// TODO Auto-generated method stub
		
	}
}