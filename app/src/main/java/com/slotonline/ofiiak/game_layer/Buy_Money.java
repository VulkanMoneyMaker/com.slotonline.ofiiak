package com.slotonline.ofiiak.game_layer;

///import org.cocos2d.nodes.CCDirector;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;

import android.content.Intent;
import android.net.Uri;

import com.google.ads.InterstitialAd;
import com.slotonline.ofiiak.game_mania.My_R;
import com.slotonline.ofiiak.view_details.Button_Grow;

import com.slotonline.ofiiak.R;

public class Buy_Money extends CCLayer {
	public int coinCount = 0;
	
	private static long lastTime = 0;

	// Admob
	private InterstitialAd interstitial;


	public static CCScene scene() {
		CCScene scene = CCScene.node();
		scene.addChild(new Buy_Money());
		return scene;
	}

	/*****************************************************************************************************************************************************************************************************************/
	public Buy_Money() {
		super();
		schedule("getInfo", 1.0f / 10.0f);
	}

	public void getInfo(float dt) {
		unschedule("getInfo");
		CCSprite img_back = CCSprite.sprite(My_R._getImg("setting/coinSetting"));
		My_R.setScale(img_back);
		img_back.setAnchorPoint(0, 0);
		img_back.setPosition(0, 0);
		addChild(img_back);

		Button_Grow buyBtn = Button_Grow.button(My_R._getImg("setting/buyBtn"),
				My_R._getImg("setting/buyBtn"), this, "coinBuy", 0);

		buyBtn.setPosition(My_R._getX(717), My_R._getY(320));
		addChild(buyBtn);

		Button_Grow backBtn = Button_Grow.button(My_R._getImg("setting/PlusBack"),
				My_R._getImg("setting/PlusBack"), this, "backLayer", 0);
		// backBtn.setColor(new ccColor3b(0,0,0));
		backBtn.setPosition(My_R._getX(900), My_R._getY(50));
		addChild(backBtn);
	}

	/*****************************************************************************************************************************************************************************************************************/
	public void coinBuy(Object sender) {

//		if (VunglePub.isVideoAvailable(true))
//			VunglePub.displayIncentivizedAdvert(true);

//		admobInterstitial();
		
//		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(My_R.g_Context.getResources().getString(R.string.more_apps_hammyliem)));
//		My_R.g_Context.startActivity(i);
//
//		long currentTime = System.currentTimeMillis();
//		if ((currentTime - lastTime) > 15 * 60 * 1000) {
//			My_R.allCoin += 1000;
//			My_R.saveSetting();
//			lastTime = currentTime;
		

	}
	
	/*****************************************************************************************************************************************************************************************************************/
	public void backLayer(Object sender) {
		My_R.playEffect(My_R.click);
		if (My_R.GAME_STATE.equals("title")) {
			My_R.titleState = false;
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(0.7f, Titles_Items.scene()));
		} else if (My_R.GAME_STATE.equals("game")) {
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(0.7f, GameLayer.scene()));
		}
	}

}
