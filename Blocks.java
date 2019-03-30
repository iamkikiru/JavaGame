package com.game10;

import java.awt.Color;

public abstract class Blocks {
	int[] blockRow = new int[4];	// 4 Block의 좌표
	int[] blockCol = new int[4];	
	int shape=0;	// 4가지 모양
	Color blockColor;
	
	boolean dropEnd=false;
	
	public Blocks() {
		setOffBlock();
	}
	
	public boolean drop() {
		dropEnd = false;
		
		if(checkDrop()) {
			//한줄내림
			for(int i=0; i<4; i++) {
				blockRow[i] = blockRow[i]+1;
			}
		}else {
			dropEnd = true;
		}
		
		return dropEnd;
	}
	
	public void move(int step) {
		if(checkMove(step)) {
			for(int i=0; i<4; i++) {
				blockCol[i] = blockCol[i]+step;
			}
		}
	}
	
	public void turn() {
		if(checkTurn()) {
			//턴함
			turnBlocks(shape);
			
			if(shape<3) {
				shape++;
			}else {
				shape = 0;
			}
		}
	}
	
	public boolean checkDrop() {
		boolean dropOk = true;
		
		//4개블럭중 하나라도 끝이면
		for(int i=0; i<4; i++) {
			if( blockRow[i] == 20) {
				dropOk = false;
				break;
			}else {
				// 아래 블럭이 있으면
				if(BlockMap.map[blockRow[i]+1][blockCol[i]] >= 0) {
					dropOk = false;
					break;
				}
			}
		}
		
		System.out.println("checkDrop() : " + dropOk);
		return dropOk;
	}
	
	public boolean checkMove(int step) {
		boolean moveOk = true;
		
		//좌로나 우로 끝이면
		for(int i=0; i<4; i++) {
			if(blockCol[i]+step < 0 || blockCol[i]+step >= 12) {
				moveOk = false;
				break;
			}else {
				//좌로나 우로 다른블럭이 있으면
				if(BlockMap.map[blockRow[i]][blockCol[i]+step] >=0 ) {
					moveOk = false;
					break;
				}
			}
		}
		
		return moveOk;
	}
	
	public boolean checkTurn() {
		boolean turnOk = true;
		
		//현재 블럭의 위치를 임시 저장함
		int[] tempRow = new int[4];
		int[] tempCol = new int[4];
		for(int i=0; i<4; i++) {
			tempRow[i] = blockRow[i];
			tempCol[i] = blockCol[i];
		}
		
		//턴을 먼저 시켜보고
		turnBlocks(shape);
		
		//좌로나 우로 끝이 아니며 아래로 끝이 아니면
		for(int i=0; i<4; i++) {
			if(  (blockCol[i]>=0) && (blockCol[i]<12) &&
				     (blockRow[i]>=0) && (blockRow[i]<21)	) {
					//턴한위치에 다른 블럭이 없으면 턴할 수 있음
					if(BlockMap.map[blockRow[i]][blockCol[i]] >= 0) {
						turnOk = false;
						break;
					}
			}else {
					turnOk = false;
					break;
			}
		}
		
		//블럭 좌표 원위치
		for(int i=0; i<4; i++) {
			blockRow[i] = tempRow[i];
			blockCol[i] = tempCol[i];
		}
		
		return turnOk;
	}
	
	public  abstract void turnBlocks(int shape);
	
	public abstract void setBlock();
	
	public void setOffBlock() {
		blockRow[0] = -1; blockCol[0] = -1;
		blockRow[1] = -1; blockCol[1] = -1; 
		blockRow[2] = -1; blockCol[2] = -1; 
		blockRow[3] = -1; blockCol[3] = -1; 
	}
	
}
