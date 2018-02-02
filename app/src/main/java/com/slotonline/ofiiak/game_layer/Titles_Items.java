package com.slotonline.ofiiak.game_layer;


import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.nodes.CCLabel;

import com.slotonline.ofiiak.game_mania.My_R;
import com.slotonline.ofiiak.view_details.Button_Grow;


public class Titles_Items extends CCLayer
{

	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new Titles_Items());
		return scene;
	}
/***************************************************************************************************************************************************************************************************************/
	public Titles_Items()
	{
		super();
		
		schedule("getInfo", 1.0f / 10.0f);
	}	
	public void getInfo(float dt){
		unschedule("getInfo");
		createBG();
		createButton();
		createLabel();	
		getTime();
		
	}
	public void getTime(){
		 if(My_R.allCoin == 0){
			 if(My_R.setTimeState){
				 long time = System.currentTimeMillis() / 1000;
				 if((time - My_R.currentTime) / 3600 >= 24){
					 My_R.allCoin = 250;
					 My_R.setTimeState = false;
					 My_R.saveSetting();
				 }
			 }			 
		 }		
	}
/***************************************************************************************************************************************************************************************************************/
	public void createBG(){
		CCSprite im_back = CCSprite.sprite(My_R._getImg("backImages/menu_bg-hd"));
		My_R.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);
	}
/***************************************************************************************************************************************************************************************************************/
	public void createButton(){
		String [] str = {"Buttons/dragons","Buttons/pirates","Buttons/jewels","Buttons/fruit","Buttons/cash","Buttons/dragons"};
		Button_Grow selectBtn;
		for(int i = 0 ; i < 6 ; i++){

			if (i == 5) {
				selectBtn = Button_Grow.button(My_R._getImg(str[i]), My_R._getImg(str[i]),this,"startGame",(i+1));
				float fx = 900;
				float fy = 350;
				selectBtn.setAnchorPoint(0, 0);
				selectBtn.setPosition(fx, fy);
				addChild(selectBtn);
			}

		}
		
		CCSprite img_txt = CCSprite.sprite(My_R._getImg("Buttons/text_box"));
		My_R.setScale(img_txt);
		img_txt.setAnchorPoint(0, 0);
		img_txt.setPosition(My_R._getX(52), My_R._getY(564));
		addChild(img_txt);
		
		
		CCSprite img_usd = CCSprite.sprite(My_R._getImg("Buttons/usd3"));
		My_R.setScale(img_usd);
		img_usd.setAnchorPoint(0, 0);
		img_usd.setPosition(My_R._getX(40), My_R._getY(564));
		addChild(img_usd);		
		
		Button_Grow plus = Button_Grow.button(My_R._getImg("Buttons/plus1"), My_R._getImg("Buttons/plus2"),this,"plusCoin",0);
		plus.setAnchorPoint(0, 0);
		plus.setPosition(My_R._getX(288), My_R._getY(597));
		addChild(plus);
		
		Button_Grow setting = Button_Grow.button(My_R._getImg("Buttons/setting1"), My_R._getImg("Buttons/setting1"), this, "setting",0);
		setting.setAnchorPoint(0, 0);
		setting.setPosition(My_R._getX(100), My_R._getY(38));
		addChild(setting);
		
		//Button_Grow more_game = Button_Grow.button(My_R._getImg("Buttons/more_game"), My_R._getImg("Buttons/more_game"), this, "moreGame", 0);
		//more_game.setAnchorPoint(0, 0);
		//more_game.setPosition(My_R._getX(824),My_R._getY(38));
		//addChild(more_game);
	}
/***************************************************************************************************************************************************************************************************************/
	public void createLabel(){
		ccColor3B clr = ccColor3B.ccc3(255, 255, 255);
		CCLabel coinLabel = CCLabel.makeLabel(String.format("%d", My_R.allCoin), My_R._getFont("Imagica"), 30);
		My_R.setScale(coinLabel);
		coinLabel.setAnchorPoint(0, 0);
		coinLabel.setPosition(My_R._getX(160), My_R._getY(580));
		coinLabel.setColor(clr);
		addChild(coinLabel);	
			
	}
/***************************************************************************************************************************************************************************************************************/
	public void startGame(Object sender) {
		My_R.playEffect(My_R.click);
		My_R.titleState = true;
		My_R.curLevel = ((CCMenuItem)sender).getTag();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));
		
	}
/***************************************************************************************************************************************************************************************************************/
	public void plusCoin(Object sender) {
		My_R.playEffect(My_R.click);
		My_R.GAME_STATE = "title";
		My_R.titleState = true;
		
		//	
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, Buy_Money.scene()));
		
	}
/***************************************************************************************************************************************************************************************************************/
	public void setting(Object sender){
		My_R.playEffect(My_R.click);
		My_R.titleState = true;
		My_R.GAME_STATE = "title";
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, Setting.scene()));
	}
/***************************************************************************************************************************************************************************************************************/
	public void moreGame(Object sender){
		My_R.playEffect(My_R.click);
	}
	
	
}