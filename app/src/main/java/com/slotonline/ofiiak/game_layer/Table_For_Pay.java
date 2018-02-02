package com.slotonline.ofiiak.game_layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;


import com.slotonline.ofiiak.game_mania.My_R;
import com.slotonline.ofiiak.view_details.Button_Grow;

public class Table_For_Pay extends CCLayer
{
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new Table_For_Pay());
		return scene;
	}
/***************************************************************************************************************************************************************************************************************/	
	public Table_For_Pay()
	{
		super();
		
		CCSprite im_back = CCSprite.sprite(My_R._getImg(String.format("backImages/pay_table%d-hd", My_R.curLevel)));
		My_R.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);	
		
		Button_Grow retu = Button_Grow.button(My_R._getImg("Buttons/return"), My_R._getImg("Buttons/return"),this,"returnPayTable",0);
	
		retu.setPosition(My_R._getX(889), My_R._getY(540));
		addChild(retu);
		
	}
/***************************************************************************************************************************************************************************************************************/	
	public void returnPayTable(Object sender){
		My_R.playEffect(My_R.click);
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));		
	}
}