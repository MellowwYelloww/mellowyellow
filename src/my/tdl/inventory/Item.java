package my.tdl.inventory;

import java.awt.image.BufferedImage;

import my.tdl.main.Assets;

public enum Item {
	
	RED_BALL("Red Ball", 1, Assets.getWall_1()),
	GREEN_BALL("Green Ball", 2, Assets.getCornerWall_mw());
	
	private String itemName;
	private int itemID;
	private BufferedImage itemImage;

	
	private Item(String itemName, int itemID, BufferedImage itemImage) {
		this.itemName = itemName;
		this.itemID = itemID;
		this.itemImage = itemImage;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public int getItemID() {
		return itemID;
	}
	
	public BufferedImage getItemImage() {
		return itemImage;
	}
	
	
}
