package com.slotonline.ofiiak.game_mania;


import java.util.Vector;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.sound.SoundEngine;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.slotonline.ofiiak.game_layer.Result_Play;
import com.slotonline.ofiiak.R;

public class My_R {
	public static Vector<Result_Play> TGameResult = new Vector<Result_Play>();
	public static Activity		g_Context; 
	
	public static final int 	seccess 	    = R.raw.increase;
	public static final int 	fire_btn 	    = R.raw.decrease;	
	public static final int 	click 		    = R.raw.click;
	public static final int     spin            = R.raw.spin;
	public static final int     ROW_            = 4;
	public static final int     COL_            = 5;
	public static final int     CHARACTER_COUNT = 11;
	public static final int     DEFAULT_RINDEX  = 6;
	public static final int     RULE_LINE_COUNT = 9;	
	public static final int[]   coinVal         = {500, 1000, 1500, 2000};
	public static final int     lineX           = 119;	
	public static final int[]   lineY           = {316,445,184,121,225,265,223,350,142};
	
	
	public static final float 	DEFAULT_W 	    = 480f; 
	public static final float 	DEFAULT_H 	    = 360f;
	
	private static final String _imgDirPath     = "images/"; 
	private static final String _fntDirPath     = "font/";
	
	public static int           MIN_VEL         = 8;
	public static int           MAX_VEL         = 70;
	public static int           PREVSTEP        = 4;
	public static int           PREVTICK        = 8;
	public static int           TICK            = 30;		
	public static int           allCoin;
	public static int           curLevel;
	public static int           curLine;
	public static int           maxline;
	public static int           bet;
	public static int [][]      arrSlot = new int[ROW_][COL_];
    public static int [][]      arrTempSlot = new int[CHARACTER_COUNT][COL_];
    public static int []        rowIndex = new int[COL_];
    public static long           currentTime;
    
	
	public static float 		_scaleX 		= 1f;
	public static float 		_scaleY 		= 1f;	
	public static float 		WIN_W           = 960f; 
	public static float 		WIN_H	        = 640f;
	public static float         CARD_STRT_X     = 118f;
	public static float         CARD_STRT_Y     = 396f;
	public static float         CARD_BETWEEN_X  = 145f;
	public static float         CARD_BETWEEN_Y  = 136f;	
	
	public static String[]      strIconName     =  {"character1","character2","character3","character4","character5","character6","character7","character8","character9","character10","character11"};
	public static String        GAME_STATE      = "";

	
	
	public static boolean       bgmState;        
	public static boolean       effectState;  	
	public static boolean       stopSound       = false;
	public static boolean       startSound      = false;
	public static boolean       payTableFlag;
	public static boolean       titleState;
	public static boolean       setTimeState;

	
	
/*****************************************************************************************************************************************************************************************************************/
	
	public static void setScale(){
		_scaleX = CCDirector.sharedDirector().winSize().width / WIN_W;
		_scaleY = CCDirector.sharedDirector().winSize().height / WIN_H;
	}
/*****************************************************************************************************************************************************************************************************************/
	public static float _getX(float x) {
		return _scaleX*x;
	}
/*****************************************************************************************************************************************************************************************************************/	
	public static float _getY(float y) {
		return _scaleY*y;
	}
/*****************************************************************************************************************************************************************************************************************/	
	public static String _getImg(String imgName) {
		return String.format("%s%s%s",_imgDirPath, imgName, ".png" );
	}
/*****************************************************************************************************************************************************************************************************************/	
	public static String _getFont(String fntName) {
		return String.format("%s%s%s",_fntDirPath, fntName, ".ttf");
	}
/*****************************************************************************************************************************************************************************************************************/
	public static void setScale(CCNode node) {
		node.setScaleX(_scaleX);
		node.setScaleY(_scaleY);
	}
/*****************************************************************************************************************************************************************************************************************/	
	public static void setScale(CCNode node, float scaleFactor) {
		node.setScaleX(_scaleX*scaleFactor);
		node.setScaleY(_scaleY*scaleFactor);
	}
/*****************************************************************************************************************************************************************************************************************/
	public static void setScale(CCNode node, boolean bSmall) {
		float scale = bSmall ?
			(_scaleX<_scaleY ? _scaleX : _scaleY) :
			(_scaleX>_scaleY ? _scaleX : _scaleY);
		node.setScale(scale);
	}
/*****************************************************************************************************************************************************************************************************************/
	//for Sound and Effect
	public static void playSound() {
		if(!bgmState) return;
		stopSound();
		SoundEngine.sharedEngine().setSoundVolume(1f);
		SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(), R.raw.begin, true);
	}
/*****************************************************************************************************************************************************************************************************************/
	public static void playEffect(int effID) {
		if(!effectState) return;
		SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), effID);
	}
/*****************************************************************************************************************************************************************************************************************/
	public static void stopSound() {
		SoundEngine.sharedEngine().realesAllSounds();
	}
/*****************************************************************************************************************************************************************************************************************/
	public static void pauseSound() {
		SoundEngine.sharedEngine().pauseSound();
	}
/*****************************************************************************************************************************************************************************************************************/
	public static void resumeSound() {
		SoundEngine.sharedEngine().resumeSound();
	}	
/*****************************************************************************************************************************************************************************************************************/
	public static void loadSetting()
	{
		SharedPreferences setting = CCDirector.sharedDirector().getActivity().getSharedPreferences("SlotMania", Context.MODE_PRIVATE);	
	    allCoin      = setting.getInt("coin", 300);	 
	    bgmState     = setting.getBoolean("bgm", true);
	    effectState  = setting.getBoolean("effect", true);
	    setTimeState = setting.getBoolean("timeState", false);
	    currentTime  = setting.getLong("time", 0);
	}
/*****************************************************************************************************************************************************************************************************************/
	public static void saveSetting()
	{
		Editor editor = 
			CCDirector.sharedDirector().getActivity().getSharedPreferences("SlotMania", Context.MODE_PRIVATE).edit();		
	    
		editor.putInt("coin", allCoin);
		editor.putBoolean("bgm", bgmState);
		editor.putBoolean("effect", effectState);		
		editor.putBoolean("timeState", setTimeState);
		editor.putLong("time", currentTime);
		editor.commit();		
	}
	
	
	
	
	

	
}