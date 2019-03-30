package com.game10;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;

public class BlockGame extends JFrame implements ActionListener, Runnable, KeyListener {

	Image offI;
	Graphics offG;
	Blocks[] blocks; 
	int index ; //블럭배열을 위한 인덱스
	Random r;
	int gameStatus;   //시작전 -1, 게임중1, 게임종료0
	Color[] colorType;
	
	Thread clock;
	int interval; 	//test 3000, real 500
	
	int score;
	
	public void setGame() {
		index = 0;
		gameStatus= -1;
		interval = 3000;  //test 3000, real 500
		score = 0;
		
		//GUI
		setLayout(new BorderLayout());
		Panel pnl = new Panel();
		Button btn = new Button("START");
		
		btn.addActionListener(this);
		pnl.add(btn);
		add("South",pnl);
		
		
		setTitle("Block Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300, 500);
		setVisible(true);
		
	
		//static map[][]초기값세팅
		for(int i=0; i<21; i++) {
			for(int j=0; j<12; j++) {
				BlockMap.map[i][j]= -1;
			}
		}
		
		//Blocks 셋팅
		blocks = new Blocks[7];
		blocks[0] = new Blocks0();
		blocks[1] = new Blocks1();
		blocks[2] = new Blocks2();
		blocks[3] = new Blocks3();
		blocks[4] = new Blocks4();
		blocks[5] = new Blocks5();
		blocks[6] = new Blocks6();
		
		//colorType 셋팅
		colorType = new Color[7];
		setBlockColor();
		
		r= new Random();
		//index = 1;	//TEST용
		index = r.nextInt(7);
		System.out.println("index : " + index);
		
		blocks[index].setBlock();
		blocks[index].shape =0;
		
		//가상이미지
		offI = createImage(181, 316);
		offG = offI.getGraphics();
		
		
		this.addKeyListener(this);
		//this.requestFocus();
		
		
		
		drawMap();
		drawTitle();
		repaint();
		
	}
	
	public void drawTitle() {
		offG.setColor(Color.white);
		offG.fillRect(29, 120, 123, 70);
		offG.setColor(Color.black);
		offG.drawRect(31,125,121,60);
		offG.setColor(Color.red);
		offG.drawString("TETRIS", 70, 150);
		offG.setColor(Color.blue);
		offG.drawString("Press START button ! ", 35, 170);
	}
	
	//블럭별 색상 지정
	public void setBlockColor() {
		colorType[0] = new Color(65,228,82);
		colorType[1] = new Color(58,98,235);
		colorType[2] = new Color(128,0,64);
		colorType[3] = new Color(255,35,31);
		colorType[4] = new Color(68,17,111);
		colorType[5] = new Color(246,118,57);
		colorType[6] = new Color(224,134,4);
	}
	
	public void drawMap(){
		for(int i=0; i<21; i++) {
			for(int j=0; j<12; j++) {
				//기존 맵에 있는 것 그리기
				if(BlockMap.map[i][j]>=0) {
					System.out.print(BlockMap.map[i][j]);
					offG.setColor(colorType[BlockMap.map[i][j]]);
					offG.fillRect(j*15, i*15, 15, 15);
					offG.setColor(Color.red);
					offG.drawRect(j*15, i*15, 15, 15);
				}else {
					System.out.print(BlockMap.map[i][j]);
					offG.setColor(Color.yellow);
					offG.fillRect(j*15, i*15, 15, 15);
					offG.setColor(Color.red);
					offG.drawRect(j*15, i*15, 15, 15);
				}
				//블럭이 있는 자리 그리기
				for(int k=0; k<4; k++) {
					if(blocks[index].blockRow[k] == i && blocks[index].blockCol[k]==j) {
						System.out.print("("+i+", "+j+")");
						offG.setColor(blocks[index].blockColor);
						offG.fillRect(j*15, i*15, 15, 15);
						offG.setColor(Color.red);
						offG.drawRect(j*15, i*15, 15, 15);
					}
				}
			}
			System.out.println();
		}
		
	}
	
	public void paint(Graphics g) {
		g.drawImage(offI, 50,70,this);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(gameStatus == 1) {
			System.out.println("키가 눌렸어요 : "+e.getKeyCode());
			int keyCode = (int)e.getKeyCode();
			System.out.println("키가 눌렸어요 :키코드는 ===>  "+keyCode);
			
			if(keyCode == KeyEvent.VK_LEFT) {
				blocks[index].move(-1);
			}
			
			if(keyCode == KeyEvent.VK_RIGHT) {
				blocks[index].move(1);
			}
			
			if(keyCode == KeyEvent.VK_DOWN) {
				blocks[index].drop();
			}
			
			//반시계로 회전
			if(keyCode == KeyEvent.VK_UP) {	
				blocks[index].turn();

			}
			
			drawMap();
			repaint();
			
		}else {
			System.out.println("게임중 아님");
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		boolean dropEnd = false;
		while(gameStatus == 1) {
			
			try {
				clock.sleep(interval);	//test 3000, real 500
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			if(!dropEnd) {
				dropEnd = blocks[index].drop();
			}else {
				System.out.println();
				System.out.println();
				System.out.println("=Block Type==================== "+index);
				System.out.println();
				System.out.println();
				
				setBlockInMap();
				blocks[index].setOffBlock();
				delLine();		//다 채운 라인있는지 체크 findDelLine()해서 지움. 있으면 deleteLine()
				
				//index = 1;	//test용 블럭
				index = r.nextInt(7);	//새 블럭
				blocks[index].setBlock();	//셋팅
				blocks[index].shape =0;		//나오기 전에 shape가 바뀌면 이상현상발생하므로 나오고나면 0으로 함
				
				checkGameOver();	//게임오버 체크
				dropEnd = false;
			}
			
			drawMap();
			repaint();
		}
		
		if(gameStatus == 2) {
			System.out.println("Score : " + score);
			drawScore();
			repaint();
		}
	}
	
	//score 올리기
	public void addScore() {
		score += 10;
		System.out.println(score + "점");
		//속도 빨라짐
		if(score <1000) {
			interval = 1000-score;
		}else {
			interval = 0;
		}
	}
	
	public void drawScore() {
		offG.setColor(Color.white);
		offG.fillRect(35, 120, 110, 70);
		offG.setColor(Color.black);
		offG.drawRect(40,125,100,60);
		offG.setColor(Color.red);
		offG.drawString("Game Over", 56, 150);
		offG.setColor(Color.blue);
		offG.drawString("Score : " + score, 56, 170);
	}
	
	public void checkGameOver() {
		for(int i=0; i<4; i++) {
			System.out.print("Check GameOver : "+ blocks[index].blockRow[i] + ", "+blocks[index].blockCol[i]);
			System.out.println(BlockMap.map[blocks[index].blockRow[i]][blocks[index].blockCol[i]]);
			if(BlockMap.map[blocks[index].blockRow[i]][blocks[index].blockCol[i]] >= 0) {
				if(gameStatus == 1) {
					gameStatus = 2; 
				}
			}
		}

		if(gameStatus==2) {
			blocks[index].setOffBlock();
		}
		
		System.out.println("Game Status"+ gameStatus);
	}
	
	public void deleteLine(int line) {
		
		System.out.println("한줄지우기 시작 -->");
		try {
			Thread.sleep(interval);		//test 3000. real 500
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int delRow=line; delRow>0; delRow--) {
			for(int delCol=0; delCol<12; delCol++) {
				BlockMap.map[delRow][delCol] = BlockMap.map[delRow-1][delCol];
			}
		}
		//내릴줄이 없으므로...맨윗줄 추가
		for(int i=0; i<12; i++) {
			BlockMap.map[0][i] = -1;
		}
		
		//다시 그리기
		drawMap();
		repaint();
		
		System.out.println("한줄지우기 끝 <--");
		
		addScore();
	}

	//지울 줄이 있는지 찾음
	public int findDelLine() {
		int line = -1;
		boolean delOk;  //지울수 있음
		for(int row=20; row>=0; row--) {
			delOk = true;
			//하나라도 map[][] == false면 ,즉 빈칸이면..줄을 못지움
			for(int col=0; col<12; col++) {
				if(BlockMap.map[row][col] == -1) {  //map[][] == false면 ,즉 빈칸이면  
					delOk = false;	//못지움
				}
			}
			
			if(delOk == false) {
				line = -1;
				continue;	//다시 for문 돌림
			}else {
				System.out.println("find DeleteLine : "+row);
				line = row;
				break;
			}
			
			
		}
		
		//System.out.println("리턴 row"+delLineNum);
		return line;
	}
		
	public void delLine() {
		int line = -1;		//지울 줄
		
		for(int row=20; row>=0; row--) {
			//지울줄 찾기
			line = findDelLine();
			
			//줄지우기(줄 내리기)
			if(line == -1) {
				//System.out.println("지울줄이 없습니다.");
			}else {
				deleteLine(line);
								
				//중력에 의한 한줄 내리기 체크
				dropResultLine(line);
			}
		}
	}	
	
	//한줄을 내릴수있는지 체크
	public boolean checkLineDrop(int row) {
		boolean dropLineOk = true;
		
		for(int i=0; i<12; i++) {
			if(row != 20) {
				if(BlockMap.map[row][i]>=0 && BlockMap.map[row+1][i]>=0) {	//나도 블럭이 있고 아래칸에도 블럭이 있으면 못내려감
					dropLineOk = false;
				}
			}else {		//끝선.증가해보니 더이상 갈곳이 없을때
				dropLineOk = false;
			}
		}
		
		return dropLineOk;
	}
		
	
	public void dropResultLine(int line) {
		if(checkLineDrop(line)) {		//true면  더 내릴수 있음
			System.out.println("더 내릴 거임 시작 ---->"+ line);
			try {
				Thread.sleep(interval);		//test 2000, real 500
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//해당하는 줄(delLineNum) 처리
			for(int col=0; col<12; col++) {
				if(BlockMap.map[line+1][col]==-1 && BlockMap.map[line][col]>=0) {
					//아랫줄에 블럭없고, 현재줄에 블럭이 있으면
					BlockMap.map[line+1][col] = BlockMap.map[line][col];
				}
			}
			//해당하는 줄 윗줄이상 처리
			for(int row=line-1; row>0; row--) {
				for(int col=0; col<12; col++) {
					BlockMap.map[row+1][col] = BlockMap.map[row][col];
				}
			}
			//내릴줄이 없으므로...맨윗줄 추가
			for(int i=0; i<12; i++) {
				BlockMap.map[0][i] = -1;
			}
			
			//다시 그리기
			drawMap();
			repaint();
			
			System.out.println("더 내릴 거임  끝 <----");
			
			addScore();
		}
		
		
	}
	
	
	public void setBlockInMap() {
		
		for(int i=0; i<4; i++) {
			//블럭의 좌표에 해당하는 map좌표에  블럭번호(index)를 넣는다
			BlockMap.map[blocks[index].blockRow[i]][blocks[index].blockCol[i]] = index;
			//System.out.print( "("+blocks[index].blockRow[i]+ ","+blocks[index].blockCol[i]+")   ");
		}
		
		System.out.println("---------------------------");
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gameStatus = 1;	//게임중
		
		if(clock == null) {
			clock = new Thread(this);
			clock.start();
		}

		this.requestFocus();
	}	

}
