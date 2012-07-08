package org.ege.utils;

import org.ege.widget.SwipeView;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.esotericsoftware.tablelayout.Cell;

public class Property {
	
	public static final Property instance = new Property()
	.set(Align.center, 0, 0, 100, 100, 0, 0, 0, 0, 0, 0, 0, 0, false, false,false,false);
	
	public int align;
	
	public float fillX,fillY;
	
	public float width,height;
	
	public float spacet,spacel,spacer,spaceb;
	
	public float padt,padl,padr,padb;
	
	public boolean expandX,expandY;
	
	public boolean uniformX,uniformY;

	/*******************************************************
	 * 
	 *******************************************************/
	public Property padTop(float padding){
		padt =  padding;
		return this;
	}

	public Property padLeft(float padding){
		padl =  padding;
		return this;
	}

	public Property padRight(float padding){
		padr =  padding;
		return this;
	}

	public Property padBottom(float padding){
		padb =  padding;
		return this;
	}

	
	public Property pad(float padding){
		padt =padl = padr = padb = padding;
		return this;
	}

	/*******************************************************
	 * 
	 *******************************************************/

	public Property space(float spacing){
		spacet =spacel = spacer = spaceb = spacing;
		return this;
	}

	public Property spaceTop(float spacing){
		spacet  = spacing;
		return this;
	}

	public Property spaceBottom(float spacing){
		spaceb  = spacing;
		return this;
	}
	
	public Property spaceRight(float spacing){
		spacer  = spacing;
		return this;
	}

	public Property spaceLeft(float spacing){
		spacel  = spacing;
		return this;
	}


	/*******************************************************
	 * 
	 *******************************************************/

	public Property size(float width,float height){
		this.width = width;
		this.height = height;
		return this;
	}
	
	public Property width(float width){
		this.width = width;
		return this;
	}
	
	public Property height(float height){
		this.height = height;
		return this;
	}

	/*******************************************************
	 * 
	 *******************************************************/

	public Property fill(float x,float y){
		fillX = x;
		fillY = y;
		return this;
	}
	
	public Property fill(){
		fillX = 1;
		fillY = 1;
		return this;
	}
	
	public Property fillX(){
		fillX = 1;
		return this;
	}

	public Property fillY(){
		fillY = 1;
		return this;
	}

	public Property fillX(float x){
		fillX = x;
		return this;
	}

	public Property fillY(float y){
		fillY = y;
		return this;
	}

	/*******************************************************
	 * 
	 *******************************************************/

	
	public Property align(int align){
		this.align = align;
		return this;
	}

	/*******************************************************
	 * 
	 *******************************************************/
	
	public Property expand(boolean x,boolean y){
		expandX = x;
		expandY = y;
		return this;
	}
	
	public Property expand(){
		expandX = true;
		expandY = true;
		return this;
	}
	
	public Property expandX(){
		expandX = true;
		return this;
	}

	public Property expandY(){
		expandY = true;
		return this;
	}

	public Property expandX(boolean x){
		expandX = x;
		return this;
	}

	public Property expandY(boolean y){
		expandY = y;
		return this;
	}

	/*******************************************************
	 * 
	 *******************************************************/

	public Property uniform(boolean x,boolean y){
		uniformX = x;
		uniformY = y;
		return this;
	}
	
	public Property uniform(){
		uniformX = true;
		uniformY = true;
		return this;
	}
	
	public Property uniformX(){
		uniformX = true;
		return this;
	}

	public Property uniformY(){
		uniformY = true;
		return this;
	}

	public Property uniformX(boolean x){
		uniformX = x;
		return this;
	}

	public Property uniformY(boolean y){
		uniformY = y;
		return this;
	}

	/*******************************************************
	 * 
	 *******************************************************/
	
	public Property set(int align,float fillX,float fillY,float width,float height,
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
		swipe.size(width, height);
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
