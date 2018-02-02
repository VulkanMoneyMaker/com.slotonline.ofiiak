package com.slotonline.ofiiak.game_layer;


import org.cocos2d.utils.javolution.MathLib;

import com.slotonline.ofiiak.game_mania.My_R;

public class Engine 
{	
	public int m_nArrSlot[][] = new int[My_R.ROW_][My_R.COL_];
	public int m_nArrTempSlot[][] = new int[My_R.CHARACTER_COUNT][My_R.COL_];
	public float m_fBetweenY;
	public float m_fMovingY[] = new float[My_R.COL_];
	public boolean m_bSloting[] = new boolean[My_R.COL_];
	public int m_nRuleLineCount;
	public int[] m_nRowIndex = new int[My_R.COL_];
	public int m_nMaxLineCount;
	//public int nRuleLineIndex;
	public int m_nBet;
	public int m_nWin;
	public int m_nGameCoin;
	public boolean m_bStartSlot;
	public boolean m_bHit;
	public boolean m_bLoad;
	
	public float[] m_fPrevStep = new float[My_R.COL_];
	public int[] m_fMovingYStep = new int[My_R.COL_];
	public int[] m_nSlotTick = new int[My_R.COL_];
	public String[] m_strState = new String[My_R.COL_];
	public int[][] nCardsScore = {
		{0,5,50,500,5000},
		{0,0,5,25,50},
		{0,2,40,200,1000},
		{0,2,30,150,500},
		{0,2,25,100,300},
		{0,0,20,75,200},
		{0,0,20,75,200},
		{0,0,15,50,100},
		{0,0,15,50,100},
		{0,0,10,25,50},
		{0,0,10,25,50}
	};
	
	public int[][][] nArrRules = {
		{{2,0},{2,1},{2,2},{2,3},{2,4}},
		{{1,0},{1,1},{1,2},{1,3},{1,4}},
		{{3,0},{3,1},{3,2},{3,3},{3,4}},
		{{1,0},{2,1},{3,2},{2,3},{1,4}},
		{{3,0},{2,1},{1,2},{2,3},{3,4}},
		{{2,0},{1,1},{1,2},{1,3},{2,4}},
		{{2,0},{3,1},{3,2},{3,3},{2,4}},
		{{1,0},{2,1},{2,2},{2,3},{1,4}},
		{{3,0},{2,1},{2,2},{2,3},{3,4}}		
	};	
	public Engine()
	{
		initVariable();
		initSlot();
		
	}
	public void getInfo(){
		m_nGameCoin = My_R.allCoin;
		m_nRuleLineCount = My_R.curLine;
		m_nMaxLineCount = My_R.maxline;
		m_nBet = My_R.bet;
	}
	public void initCardStartPosY(int nColIndex){
		if(m_bSloting[nColIndex])
			m_fMovingY[nColIndex] -= m_fBetweenY;
		else
			m_fMovingY[nColIndex] = 0;
	}
	public void initVariable(){
		m_bStartSlot = false;
		getInfo();
		int nPrevStep = My_R.PREVSTEP;
		for(int i = 0; i < My_R.COL_ ; i++){
			initCardStartPosY(i);
			m_bSloting[i] = false;
			m_fMovingYStep[i] = My_R.MIN_VEL;
			m_fPrevStep[i] = - (MathLib.random(1, nPrevStep - 1) + 2);
		}
	}
	public void loadSlot(){
		m_bStartSlot = true;
		m_bLoad = true;
		for(int i = 0; i < My_R.CHARACTER_COUNT ; i++){
			for(int j = 0; j < My_R.COL_ ; j++){
				if(i == 0)
					m_nRowIndex[i] = My_R.rowIndex[i];
				if(i < My_R.ROW_)
					m_nArrSlot[i][j] = My_R.arrSlot[i][j];
				m_nArrTempSlot[i][j] = My_R.arrTempSlot[i][j];
			}
		}
		My_R.payTableFlag = false;
	}
	public void initSlot(){
		int changeVal= 0;
		if(My_R.payTableFlag){
			loadSlot();
			return;
		}
		for (int i = 0; i < My_R.CHARACTER_COUNT; i++) {
	        for (int j = 0; j < My_R.COL_; j++) {
	            m_nArrTempSlot[i][j] = -1;
	        }
	    }
		for(int i = 0; i < My_R.COL_ ; i++){
			for(int j = 0; j < My_R.CHARACTER_COUNT ; j++){
				boolean bFlag = true;
				while(bFlag){
					changeVal = MathLib.random(0, (My_R.CHARACTER_COUNT - 1));
					boolean equalState = true;
					for(int k = 0; k < My_R.CHARACTER_COUNT ; k++){
						if(m_nArrTempSlot[k][i] == changeVal){
							equalState = false;
							break;
						}
					}
					if(equalState){
						m_nArrTempSlot[j][i] = changeVal;
						bFlag = false;
					}
				}
				
			}
		}
	    
	    for (int i = 0; i < My_R.ROW_; i++) {
	        for (int j = 0; j < My_R.COL_; j++)
	            m_nArrSlot[i][j] = m_nArrTempSlot[i][j];
	    }
	}
	public void setCardBettwenY(float fBetweenY){
		m_fBetweenY = fBetweenY;
	}
	public void resetSlot(int nColIndex){
		if(m_fMovingY[nColIndex] >= m_fBetweenY){
			for(int j = My_R.ROW_ - 2; j >= 0 ; j--){
				m_nArrSlot[j + 1][nColIndex] = m_nArrSlot[j][nColIndex];				
			}
			m_nArrSlot[0][nColIndex] = m_nArrTempSlot[m_nRowIndex[nColIndex]][nColIndex];
			m_nRowIndex[nColIndex]--;
			if(m_nRowIndex[nColIndex] < 0)
				m_nRowIndex[nColIndex] = My_R.CHARACTER_COUNT - 1;
			My_R.playEffect(My_R.spin);
			initCardStartPosY(nColIndex);
		}
	}
	public void moveSlot(int nColIndex){
		if(m_strState[nColIndex].equals("prev")){
			m_fMovingY[nColIndex] += m_fPrevStep[nColIndex];
			if(m_fPrevStep[nColIndex] <= 0.2f)
				m_fPrevStep[nColIndex] /= 1.3f;
			else
				m_fPrevStep[nColIndex] = 0;			
		}else{
			if(m_strState[nColIndex].equals("normal") && m_fMovingYStep[nColIndex] < My_R.MAX_VEL)
				m_fMovingYStep[nColIndex] += 1;
			if(m_strState[nColIndex].equals("last")){
				if(m_fMovingYStep[nColIndex]> My_R.MIN_VEL){
					m_fMovingYStep[nColIndex] -= 1;
					if(m_fMovingYStep[nColIndex] < My_R.MIN_VEL)
						m_fMovingYStep[nColIndex] = My_R.MIN_VEL;
				}
			}		
			m_fMovingY[nColIndex] += m_fMovingYStep[nColIndex];
		}
	}
	public void setState(int tick, int nColIndex){
		if(tick < My_R.PREVTICK)
			m_strState[nColIndex] = "prev";
		else if(!m_strState[nColIndex].equals("last"))
			m_strState[nColIndex] = "normal";
		if(m_bSloting[nColIndex] && m_strState[nColIndex].equals("last")){
			if(m_nSlotTick[nColIndex] < My_R.TICK)
				m_nSlotTick[nColIndex]++;
			else{
				m_bSloting[nColIndex] = false;
				m_nSlotTick[nColIndex] = 0;
			}
		}
			
	}
	public void processSlot(int tick){
		for(int i = 0; i < My_R.COL_ ; i++){
			if(m_fMovingY[i] == 0 && !m_bSloting[i])
				continue;
			setState(tick, i);
			moveSlot(i);
			resetSlot(i);
		}
	}
	public boolean isStopAllSlots(){
		boolean bIsStop = true;
		for(int i = 0; i < My_R.COL_ ; i++)
			bIsStop &= !m_bSloting[i];
		return bIsStop;
	}
	public void compareCards(){
		int nPrevStep = My_R.PREVSTEP;
		for(int i = 0; i < My_R.COL_ ; i++){
			m_fMovingYStep[i] = My_R.MIN_VEL;
			m_fPrevStep[i] = -(MathLib.random(0, nPrevStep - 1) + 5);
		}
		m_bHit = false;
		m_bStartSlot = false;
		if(m_bLoad){
			m_bLoad = false;
			return;
		}
		int nCardType = -1;
		int nEqualCount = 1;
		for(int nRuleLineIndex = 0 ; nRuleLineIndex < m_nRuleLineCount ; nRuleLineIndex++){			
			for(int nEqualIndex = 0; nEqualIndex < My_R.COL_ - 1 ; nEqualIndex++){
				int nFirstType = m_nArrSlot[nArrRules[nRuleLineIndex][nEqualIndex][0]][nArrRules[nRuleLineIndex][nEqualIndex][1]];
				int nSecondType = m_nArrSlot[nArrRules[nRuleLineIndex][nEqualIndex + 1][0]][nArrRules[nRuleLineIndex][nEqualIndex + 1][1]];
				if(nFirstType == nSecondType){
					nCardType = nFirstType;
					nEqualCount = nEqualIndex + 2;
								
				}else
					break;
			}	
			int nCoin = 0;
			if(nCardType > -1)
				nCoin = nCardsScore[nCardType][nEqualCount - 1];
			if(nCoin > 0){
				m_bHit = true;
				Result_Play r = new Result_Play();
				r.nRuleLineIndex = nRuleLineIndex;
				r.nEqualCount = nEqualCount;
				r.nCharacterIndex = nCardType;
				My_R.TGameResult.add(r);
				m_nWin += nCoin * m_nBet;				
				My_R.playEffect(My_R.seccess);
				nEqualCount = 1;
				nCardType = 1;
			}
		}
		if(m_bHit){
			m_nGameCoin += m_nWin;
			
		}else if(!m_bHit){
			My_R.playEffect(My_R.fire_btn);
			m_nGameCoin -= m_nBet*m_nRuleLineCount;			
		}
		My_R.allCoin = m_nGameCoin;
		My_R.saveSetting();
	}
	
	
	
	
	
	
}