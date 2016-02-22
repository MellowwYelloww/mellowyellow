package my.tdl.inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import my.project.gop.main.Vector2F;
import my.tdl.managers.MouseManager;

public class Slot extends Rectangle{
	
	private Item item;
	private int slotID;
	
	private Vector2F pos = new Vector2F();
	private int size = 48;
	
	private boolean isHeldOver;
	
	private int maxStack = 64;
	private int CurrentStack = 0;
	private Font font = new Font("JandaManateeSolid",20,20);

	public Slot(float xpos, float ypos) {
		this.pos.xpos = xpos;
		this.pos.ypos = ypos;
		setBounds((int) pos.xpos, (int) pos.ypos , size, size);
	}
	
	public void tick(){
		setBounds((int) pos.xpos, (int) pos.ypos , size, size);
		
		if(CurrentStack == 0){
			if(item != null){
				clear();
			}
		}
		
		if(this.contains(MouseManager.mouse)){
			isHeldOver = true;
		}else{
			isHeldOver = false;
		}
		
	}
	
	public void render(Graphics2D g){
		
		if(item != null){
			g.drawImage(item.getItemImage(), (int) pos.xpos, (int) pos.ypos, size,size,null);
			
			g.setFont(font);
			g.setColor(Color.YELLOW);
			
			g.drawString(CurrentStack+"", (int) pos.xpos + size / 2, (int) pos.ypos + size - 4);
			
			g.setColor(Color.WHITE);
		
		}
		
		if(isHeldOver){
			g.setColor(Color.GREEN);
		}
		
		g.drawRect((int) pos.xpos, (int) pos.ypos , size - 1, size - 1);
		
		g.setColor(Color.WHITE);
	}

	public void setItem(Item item){
		this.item = item;
		slotID = item.getItemID();
		CurrentStack += 1;
	}
	
	private void clear() {
		slotID = 0;
		item = null;
		CurrentStack = 0;
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getSlotID() {
		return slotID;
	}
	
	public int getCurrentStack() {
		return CurrentStack;
	}
	
	public void setCurrentStack(int currentStack) {
		CurrentStack = currentStack;
	}
	
	public boolean isAir(){
		return (item == null && slotID == 0);
	}
	
	public boolean hasSameID(Item item){
		if(item.getItemID() == slotID){
			return true;
		}else{
			return false;
		}
	}
	
	public void addItemToStack(Item item){
		CurrentStack += 1;
	}
	
	public boolean isFull(){
		if(CurrentStack < maxStack){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean isLeftClicked(){
		
		if(isHeldOver){
			if(InventoryMouse.isLeftClicked){
				InventoryMouse.isLeftClicked = false;
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public boolean isRightClicked(){
		
		if(isHeldOver){
			if(InventoryMouse.isRightClicked){
				InventoryMouse.isRightClicked = false;
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public boolean hasItem(){
		if(item != null){
			if(slotID != 0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

}
