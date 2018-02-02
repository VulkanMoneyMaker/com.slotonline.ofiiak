package com.slotonline.ofiiak.game_layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;


import com.slotonline.ofiiak.game_mania.My_R;
import com.slotonline.ofiiak.view_details.Button_Grow;


public class Setting extends CCLayer
{
	Button_Grow on1;
	Button_Grow off1;
	Button_Grow on2;
	Button_Grow off2;
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new Setting());
		return scene;
	}
/*****************************************************************************************************************************************************************************************************************/	
	public Setting()
	{
		super();
		CCSprite im_back = CCSprite.sprite(My_R._getImg("setting/setting"));
		My_R.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);	
		
		on1 = Button_Grow.button(My_R._getImg("setting/onBtn"), My_R._getImg("setting/onBtn"),this,"setOnOff1",0);
		off1= Button_Grow.button(My_R._getImg("setting/off"), My_R._getImg("setting/off"),this,"setOnOff1",0);
		on1.setPosition(My_R._getX(768), My_R._getY(332));
		off1.setPosition(My_R._getX(768), My_R._getY(332));
		addChild(on1);
		addChild(off1);
		
		
		on2 = Button_Grow.button(My_R._getImg("setting/onBtn"), My_R._getImg("setting/onBtn"),this,"setOnOff2",0);
		off2= Button_Grow.button(My_R._getImg("setting/off"), My_R._getImg("setting/off"),this,"setOnOff2",0);
		on2.setPosition(My_R._getX(768), My_R._getY(194));
		off2.setPosition(My_R._getX(768), My_R._getY(194));
		addChild(on2);
		addChild(off2);
		
		initVisible();		
		Button_Grow back = Button_Grow.button(My_R._getImg("setting/backBtn"), My_R._getImg("setting/backBtn"),this,"back",0);
		back.setPosition(My_R._getX(877), My_R._getY(55));
		addChild(back);		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void initVisible(){
		if(My_R.bgmState){
			on2.setVisible(true);
			off2.setVisible(false);
		}else{
			on2.setVisible(false);
			off2.setVisible(true);
		}
		
		if(My_R.effectState){
			on1.setVisible(true);
			off1.setVisible(false);
		}else{
			on1.setVisible(false);
			off1.setVisible(true);
		}		
		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void getStateBgm(){
		if(My_R.bgmState){
			on2.setVisible(false);
			off2.setVisible(true);
			My_R.bgmState = false;
			My_R.pauseSound();
			My_R.stopSound = true;
		}else{
			on2.setVisible(true);
			off2.setVisible(false);
			My_R.bgmState = true;
			if(My_R.stopSound){
				My_R.resumeSound();
				My_R.stopSound = false;
			}else{
				My_R.playSound();
			}
		}
		My_R.saveSetting();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void getStateEffect(){
		if(My_R.effectState){
			My_R.effectState = false;
			on1.setVisible(false);
			off1.setVisible(true);
		}else{
			My_R.effectState = true;
			on1.setVisible(true);
			off1.setVisible(false);			
		}
		My_R.saveSetting();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void back(Object sender){	
		My_R.playEffect(My_R.click);
		My_R.titleState = false;
		if(My_R.GAME_STATE.equals("title"))
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, Titles_Items.scene()));
		else
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void setOnOff1(Object sender){
		My_R.playEffect(My_R.click);
		getStateEffect();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void setOnOff2(Object sender){
		My_R.playEffect(My_R.click);
		getStateBgm();
	}
}