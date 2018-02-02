package com.slotonline.ofiiak.game_layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;

import com.slotonline.ofiiak.game_mania.My_R;

public class For_Logo extends CCLayer
{
	public static CCScene scene(){		
		My_R.setScale();
		My_R.loadSetting();
		CCScene scene = CCScene.node();
		scene.addChild(new For_Logo());
		return scene;
	}
/***************************************************************************************************************************************************************************************************************/
	public For_Logo()
	{
		super();		
		CCSprite sprite = CCSprite.sprite(My_R._getImg("backImages/splash-hd"));
		My_R.setScale(sprite);
		sprite.setAnchorPoint(0, 0);
		sprite.setPosition(0, 0);
		addChild(sprite);
		schedule("logoTimer", 1);
	}

/***************************************************************************************************************************************************************************************************************/
	public void logoTimer(float dt)
	{
		unschedule("logoTimer");
		My_R.playSound();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.5f, Titles_Items.scene()));
	}
}