package org.ege.utils;

import org.ege.widget.SwipeView;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.esotericsoftware.tablelayout.Cell;

public class Properties {
	
	public static final Properties instance = new Properties()
	.set(Align.center, 0, 0, 100, 100, 0, 0, 0, 0, 0, 0, 0, 0, false, false,false,false);
	
	public int align;
	
	public float fillX,fillY;
	
	public float width,height;
	
	public float spacet,spacel,spacer,spaceb;
	
	public float padt,padl,padr,padb;
	
	public boolean expandX,expandY;
	
	public boolean uniformX,uniformY;

	public float x,y;
	
	/*******************************************************
	 * 
	 *******************************************************/
	public Properties padTop(float padding){
		padt =  padding;
		return this;
	}

	public Properties padLeft(float padding){
		padl =  padding;
		return this;
	}

	public Properties padRight(float padding){
		padr =  padding;
		return this;
	}

	public Properties padBottom(float padding){
		padb =  padding;
		return this;
	}

	
	public Properties pad(float padding){
		padt =padl = padr = padb = padding;
		return this;
	}

	/*******************************************************
	 * 
	 *******************************************************/

	public Properties space(float spacing){
		spacet =spacel = spacer = spaceb = spacing;
		return this;
	}

	public Properties spaceTop(float spacing){
		spacet  = spacing;
		return this;
	}

	public Properties spaceBottom(float spacing){
		spaceb  = spacing;
		return this;
	}
	
	public Properties spaceRight(float spacing){
		spacer  = spacing;
		return this;
	}

	public Properties spaceLeft(float spacing){
		spacel  = spacing;
		return this;
	}
	
	/*******************************************************
	 * 
	 *******************************************************/
	
	public Properties position(float x,float y){
		this.x = x;
		this.y = y;
		return this;
	}
	
	public Properties bound(float x,float y,float width,float height){
		this.x  = x;
		this.y = y;
		this.width = width;
		this.height = height;
		return this;
	}

	/*******************************************************
	 * 
	 *******************************************************/

	public Properties size(float width,float height){
		this.width = width;
		this.height = height;
		return this;
	}
	
	public Properties width(float width){
		this.width = width;
		return this;
	}
	
	public Properties height(float height){
		this.height = height;
		return this;
	}

	/*******************************************************
	 * 
	 *******************************************************/

	public Properties fill(float x,float y){
		fillX = x;
		fillY = y;
		return this;
	}
	
	public Properties fill(){
		fillX = 1;
		fillY = 1;
		return this;
	}
	
	public Properties fillX(){
		fillX = 1;
		return this;
	}

	public Properties fillY(){
		fillY = 1;
		return this;
	}

	public Properties fillX(float x){
		fillX = x;
		return this;
	}

	public Properties fillY(float y){
		fillY = y;
		return this;
	}

	/*******************************************************
	 * 
	 *******************************************************/

	
	public Properties align(int align){
		this.align = align;
		return this;
	}

	/*******************************************************
	 * 
	 *******************************************************/
	
	public Properties expand(boolean x,boolean y){
		expandX = x;
		expandY = y;
		return this;
	}
	
	public Properties expand(){
		expandX = true;
		expandY = true;
		return this;
	}
	
	public Properties expandX(){
		expandX = true;
		return this;
	}

	public Properties expandY(){
		expandY = true;
		return this;
	}

	public Properties expandX(boolean x){
		expandX = x;
		return this;
	}

	public Properties expandY(boolean y){
		expandY = y;
		return this;
	}

	/*******************************************************
	 * 
	 *******************************************************/

	public Properties uniform(boolean x,boolean y){
		uniformX = x;
		uniformY = y;
		return this;
	}
	
	public Properties uniform(){
		uniformX = true;
		uniformY = true;
		return this;
	}
	
	public Properties uniformX(){
		uniformX = true;
		return this;
	}

	public Properties uniformY(){
		uniformY = true;
		return this;
	}

	public Properties uniformX(boolean x){
		uniformX = x;
		return this;
	}

	public Properties uniformY(boolean y){
		uniformY = y;
		return this;
	}

	/*******************************************************
	 * 
	 *******************************************************/
	
	public Properties set(int align,float fillX,float fillY,float width,float height,
						 float spacet,float spacel,float spacer,float spaceb,
						 float padt,float padl,float padr,float padb,
						 boolean expandX,boolean expandY,boolean uniformX,boolean uniformY){
		
		this.align = align;
		
		this.width = width;
		this.height = height;
		
		this.fillX = fillX;
		this.fillY =fillY;
		
		this.spaceb = spaceb;
		this.spacel = spacel;
		this.spacer = spacer;
		this.spacet = spacet;
		
		this.padt = padt;
		this.padr = padr;
		this.padl = padl;
		this.padb = padb;
		
		this.expandX =expandX;
		this.expandY = expandY;
		
		this.uniformX = uniformX;
		this.uniformY = uniformY;
		return this;
	}
	
	public Cell apply(Cell cell){
		cell.align(align)
			.fill(fillX, fillY)
			.size(width, height)
			.space(spacet, spacel, spaceb, spacer)
			.pad(padt, padl, padb, padr)
			.expand(expandX, expandY)
			.uniform(uniformX, uniformY);
		return cell;
	}
	
	public Table apply(Table table){
		table.align(align);
		table.size(width, height);
		table.pad(padt, padl, padb, padr);
		return table;
	}
	
	public SwipeView apply(SwipeView swipe){
		swipe.align(align);
		swipe.setBounds(x, y, width, height);
		swipe.pad(padt, padl, padb, padr);
		
		swipe.defaults().size(width, height)
						.fill(fillX, fillY)
						.expand(expandX, expandY)
						.uniform(uniformX, uniformY)
						.space(spacet, spacel, spaceb, spacer)
						.size(width, height);
		return swipe;
	}
}
