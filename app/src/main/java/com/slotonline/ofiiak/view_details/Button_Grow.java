package com.slotonline.ofiiak.view_details;

import org.cocos2d.actions.ease.CCEaseBackOut;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

//import sbs.com.ccframework.Button;
//import sbs.com.object.ResourceManager;

import android.view.MotionEvent;

import com.slotonline.ofiiak.game_mania.My_R;

public class Button_Grow extends CCMenu
{
	private float scaleFactor;
	public Button_Grow(CCMenuItem...items)
	{
		super(items);
		scaleFactor = items[0].getScale();
	}
	
	/*public static Button buttonWithSpriteID(String normalImage, String selectImage, CCNode target, String sel, int index){
		CCSprite normalSprite = ResourceManager.sharedResourceManager().getSpriteWithName(normalImage);
		CCSprite selectSprite = ResourceManager.sharedResourceManager().getSpriteWithName(selectImage);

		CCMenuItem menuItem = CCMenuItemSprite.item(normalSprite, selectSprite, target ,sel);
		menuItem.setTag(index);
		Button menu = new Button(menuItem);
		menu.m_nIndex = index;
		return menu;
	}*/
	public static Button_Grow button(String normalImage, String selectImage, CCNode target, String sel, int index)
	{
		CCSprite normalSprite = CCSprite.sprite(normalImage);
		CCSprite selectSprite = CCSprite.sprite(selectImage);
		CCMenuItem menuItem = CCMenuItemSprite.item(
			normalSprite, selectSprite, target, sel);
		//CCMenuItem menuItem = CCMenuItemSprite.
		menuItem.setTag(index);
		My_R.setScale(menuItem, true);
		//Button menu = new Button(menuItem);
		return new Button_Grow(menuItem);
	}
	
	public CCMenuItem itemForTouch(MotionEvent event)
	{
		CGPoint touchLocation = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(event.getX(), event.getY()));
		CCMenuItem item;
		for(CCNode node : children_) {
			item = (CCMenuItem) node;
			if ( item.getVisible() && item.isEnabled() ) {
				CGPoint local = item.convertToNodeSpace(touchLocation);
				CGRect r = item.rect();
				r.origin.set(0, 0);
				if(CGRect.containsPoint(r, local)) return item;
			}
		}
		return null;
	}
	@Override public boolean ccTouchesBegan(MotionEvent event) {
		if(!visible_ ) return false;
		CCMenuItem selItem = itemForTouch(event);
		setSelectedItem(selItem);
		
		if( selItem!=null ) {
			animateFocusMenuItem( selItem);
			return true;
		}
		return false;
	}
	
	@Override public boolean ccTouchesEnded(MotionEvent event) {
		CCMenuItem selItem = getSelectedItem(); 
		selItem.unselected();
		selItem.activate();
		animateFocusLoseMenuItem(selItem);
		return false;
	}
	
	@Override public boolean ccTouchesCancelled(MotionEvent event) {
		CCMenuItem selItem = getSelectedItem();
		selItem.unselected();
		animateFocusLoseMenuItem(selItem);
		return false;
	}

	@Override public boolean ccTouchesMoved(MotionEvent event) {
		CCMenuItem curItem = itemForTouch(event);
		CCMenuItem selItem = getSelectedItem();
		try {
		if (!curItem.equals(selItem)) {
			animateFocusLoseMenuItem(selItem);
			animateFocusMenuItem(curItem);
			selItem.unselected();
			setSelectedItem(curItem);
			selItem.selected();
		}
		}catch(Exception e){}
		return true;
	}

	
	public void animateFocusMenuItem(CCMenuItem menuItem)
	{
		CCScaleTo movetozero = CCScaleTo.action(scaleFactor * 0.1f, scaleFactor * 1.2f);
		CCEaseBackOut ease = CCEaseBackOut.action(movetozero);
		CCScaleTo movetozero1 = CCScaleTo.action(scaleFactor * 0.1f, scaleFactor * 1.15f);
		CCEaseBackOut ease1 = CCEaseBackOut.action(movetozero1);
		CCScaleTo movetozero2 = CCScaleTo.action(scaleFactor * 0.1f, scaleFactor * 1.2f);
		CCEaseBackOut ease2 = CCEaseBackOut.action(movetozero2);
		CCSequence sequence = CCSequence.actions( ease, ease1, ease2);
		menuItem.runAction(sequence);	
	}
	
	public void animateFocusLoseMenuItem(CCMenuItem menuItem)
	{
		CCScaleTo movetozero = CCScaleTo.action(scaleFactor * 0.1f, scaleFactor);
		CCEaseBackOut ease = CCEaseBackOut.action(movetozero);
		CCScaleTo movetozero1 = CCScaleTo.action(scaleFactor * 0.1f, scaleFactor * 1.05f);
		CCEaseBackOut ease1 = CCEaseBackOut.action(movetozero1);
		CCScaleTo movetozero2 = CCScaleTo.action(scaleFactor * 0.1f, scaleFactor);
		CCEaseBackOut ease2 = CCEaseBackOut.action(movetozero2);
		CCScaleTo movetozero3 = CCScaleTo.action(scaleFactor * 0.1f, scaleFactor);
		CCEaseBackOut ease3 = CCEaseBackOut.action(movetozero3);
		CCSequence sequence = CCSequence.actions(ease,ease1, ease2, ease3);
		menuItem.runAction(sequence);
	}
}