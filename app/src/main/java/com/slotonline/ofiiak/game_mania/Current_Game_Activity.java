package com.slotonline.ofiiak.game_mania;


import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.transitions.CCFadeTransition;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.InterstitialAd;
import com.slotonline.ofiiak.game_layer.For_Logo;
import com.slotonline.ofiiak.game_layer.Titles_Items;
import com.slotonline.ofiiak.view_details.Manager_Score;
import com.slotonline.ofiiak.utils_for_game.Actions;
import com.slotonline.ofiiak.utils_for_game.Random;
import com.vungle.sdk.VunglePub;
import com.vungle.sdk.VunglePub.EventListener;


public class Current_Game_Activity extends Activity implements AdListener, EventListener{
	private CCGLSurfaceView mGLSurfaceView;	
	private InterstitialAd interstitialAd;
	private boolean startState ;

	//@Override 
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
                LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(LayoutParams.FLAG_KEEP_SCREEN_ON,
                LayoutParams.FLAG_KEEP_SCREEN_ON);

        mGLSurfaceView = new CCGLSurfaceView(this);        
        if(!startState){
        	CCDirector.sharedDirector().setScreenSize(CCDirector.sharedDirector().winSize().width, 
	        CCDirector.sharedDirector().winSize().height);
	        CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
		    CCDirector.sharedDirector().getActivity().setContentView(mGLSurfaceView, createLayoutParams());
		    CCDirector.sharedDirector().attachInView(mGLSurfaceView);       
	        CCDirector.sharedDirector().setAnimationInterval(1.0f / 60);
	        CCDirector.sharedDirector().setDisplayFPS(false);
	        CCTexture2D.setDefaultAlphaPixelFormat(Config.ARGB_8888);  

			InitParam();
			CCDirector.sharedDirector().runWithScene( For_Logo.scene());
			startState = true;
        }
	    
    }

    //@Override 
    public void onStart() {
        super.onStart();       

    }    
   
    @Override
	public void onBackPressed() {
    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
    	    @Override
    	    public void onClick(DialogInterface dialog, int which) {
    	        switch (which){
    	        case DialogInterface.BUTTON_POSITIVE:
    	        	if(My_R.titleState){
    					My_R.titleState = false;
    					CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.5f, Titles_Items.scene()));
    				}else{
    					My_R.stopSound();
    					CCDirector.sharedDirector().end();
    			        Manager_Score.releaseScoreManager();
    			        finish();
    				}	
    	            break;

    	        case DialogInterface.BUTTON_NEGATIVE:
    	            //No button clicked
    	            break;
    	        }
    	    }
    	};

    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Are you sure? You may lose all your coins.").setPositiveButton("Yes", dialogClickListener)
    	    .setNegativeButton("No", dialogClickListener).show();
	}
    
    
	private void InitParam() { 		
		My_R.g_Context = this;
		My_R.curLevel = 1;
		My_R.curLine = 1;
		My_R.maxline = 1;
		My_R.bet = 1;
		
	}	
	@Override public void onPause() {
	      super.onPause();
	      VunglePub.onPause();
	      CCDirector.sharedDirector().pause();
	      My_R.pauseSound();
	        
	 }

	 @Override public void onResume() {
	     super.onResume();
	     CCDirector.sharedDirector().resume();
	     VunglePub.onResume();
	     My_R.resumeSound();
	     review();
	  }

	  @Override public void onDestroy() {
	       super.onDestroy();
	       My_R.stopSound();
	       CCDirector.sharedDirector().end();
	       Manager_Score.releaseScoreManager();
	  }
   
	
    private LayoutParams createLayoutParams() {
        final DisplayMetrics pDisplayMetrics = new DisplayMetrics();
		CCDirector.sharedDirector().getActivity().getWindowManager().getDefaultDisplay().getMetrics(pDisplayMetrics);
		
		//final float mRatio = (float)My_R.DEFAULT_W / My_R.DEFAULT_H;
		final float mRatio = (float) My_R.DEFAULT_W / My_R.DEFAULT_H;
		final float realRatio = (float)pDisplayMetrics.widthPixels / pDisplayMetrics.heightPixels;

		final int width;
		final int height;
		if(realRatio < mRatio) {
			width = pDisplayMetrics.widthPixels;
			height = Math.round(width / mRatio);
		} else {
			height = pDisplayMetrics.heightPixels;
			width = Math.round(height * mRatio);
		}

		final LayoutParams layoutParams = new LayoutParams(width, height);

		layoutParams.gravity = Gravity.CENTER;
		return layoutParams;
	}

	@Override
	public void onDismissScreen(Ad arg0) {
	}

	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
	}

	@Override
	public void onLeaveApplication(Ad arg0) {

		
	}

	@Override
	public void onPresentScreen(Ad arg0) {
	}

	@Override
	public void onReceiveAd(Ad ad) {
		Log.i("slot_machine", "Received ad activity");
		if (ad == interstitialAd) {				
			interstitialAd.show();
			
		}
	}
	@Override
	public void onVungleAdEnd() {
	}
	@Override
	public void onVungleAdStart() {
	}
	@Override
	public void onVungleView(double watchedSeconds, double totalAdSeconds) {
		final double watchedPercent = watchedSeconds/totalAdSeconds;
		if(watchedPercent >=1f){
			My_R.allCoin += 250;
			My_R.saveSetting();
		}		
	}
	
	/**
	 * Review
	 */
	private void review() {
		int random = Random.random.nextInt(100);
		if (random < 15) {
			Current_Game_Activity.this.showReviewDialog();
		}
	}

    /**
     * Set Review Dialog
     */
    public void showReviewDialog() {
    	String message = "Do you Like this app? Get FREE 200 coins by giving us 5 STARS review or SHARE this app to your friends!";
		
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
            .setPositiveButton("Give Review!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	My_R.allCoin += 200;
                	My_R.saveSetting();
                    Actions.giveUsReview(Current_Game_Activity.this);
                }
            })
            .setNegativeButton("Share with Friends!", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					My_R.allCoin += 200;
					My_R.saveSetting();
					Actions.shareApp(Current_Game_Activity.this);
				}
            });
        builder.create().show();
    }	
	
}