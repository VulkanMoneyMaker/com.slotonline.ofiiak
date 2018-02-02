package com.slotonline.ofiiak.utils_for_game;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Actions {
	private static final String APP_PNAME_PAID = "com.hammyliem.vegas.slotmachine";



	/**
	 * Action Review
	 */
	public static void giveUsReview(Context context) {
		context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME_PAID)));
	}
	
	/**
	 * Sharing App
	 * @param context
	 */
	public static void shareApp(Context context) {
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		String shareBody = "\nPlay Casino Vegas Slot Machine for Free! Get Chance for $5,000,000!!\n\n (https://play.google.com/store/apps/details?id=" + APP_PNAME_PAID + ")";
		sharingIntent.putExtra(Intent.EXTRA_SUBJECT,
				" I recommend 'Play Casino Slot Machine - Get Chance to have $5,000,000'");
		sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
		context.startActivity(Intent.createChooser(sharingIntent, "Share App"));		
	}
	
	public static String getFacebookLinkUrl() {
		String url = "https://play.google.com/store/apps/details?id=com.hammyliem.game.bubbleshooter&hl=en";
//		String url = "https://play.google.com/store/apps/details?id=net.xelnaga.exchanger&hl=en";
		return url;
	}
}
