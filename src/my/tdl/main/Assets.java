package my.tdl.main;

import java.awt.Image;
import java.awt.image.BufferedImage;

import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;

public class Assets {
	
	SpriteSheet blocks = new SpriteSheet();
	
	public static SpriteSheet player = new SpriteSheet();
	
	
	//health
	public static BufferedImage Health_bar;
	public static BufferedImage Player_damage;
	
	//Attack
	private static BufferedImage up_attack;
	private static BufferedImage down_attack;
	private static BufferedImage right_attack;
	private static BufferedImage left_attack;
	
	
	//BUTTON
	private static BufferedImage button_heldover;
	private static BufferedImage button_notover;
	
	//Cursor
	public static BufferedImage mouse_pressed;
	public static BufferedImage mouse_unpressed;
	
	//Grounds
	private static BufferedImage stoneGround_1;
	private static BufferedImage stoneGround_2;
	private static BufferedImage grass_1;
	private static BufferedImage WoodGround_1;
	private static BufferedImage WoodGround_1uwood_grass;
	private static BufferedImage WoodGround_1dwood_grass;
	private static BufferedImage WoodGround_1lwood_grass;
	private static BufferedImage WoodGround_1rwood_grass;
	private static BufferedImage StoneGrass_path_1;
	
	//Walls
	private static BufferedImage wall_1;
	private static BufferedImage wall_2;
	private static BufferedImage CornerWall_l;
	private static BufferedImage CornerWall_r;
	private static BufferedImage CornerWall_m;
	private static BufferedImage CornerWall_lg;
	private static BufferedImage CornerWall_rg;
	private static BufferedImage WallGrass_1;
	private static BufferedImage CornerWall_lw;
	private static BufferedImage CornerWall_rw;
	private static BufferedImage CornerWall_mw;
	private static BufferedImage wall_1w;
	
	//Plants
	private static BufferedImage Flower_1;
	private static BufferedImage Flower_2;

	
	public void init(){
		blocks.setSpritesheet(loadImageFrom.LoadImageFrom(Main.class, "spritesheet.png"));
		player.setSpritesheet(loadImageFrom.LoadImageFrom(Main.class, "playersheet.png"));
		
		//Name = blocks.getTile(x, y, 16, 16);
		
		//health
		Health_bar = player.getTile(96, 0, 42, 8);
		Player_damage = player.getTile(96, 16, 16, 16);

		
		//Attack
		up_attack = player.getTile(0, 128, 16, 16);
		down_attack = player.getTile(0, 160, 16, 16);
		right_attack = player.getTile(0, 144, 16, 16);
		left_attack = player.getTile(0, 112, 16, 16);
		
		//Button
		button_heldover = player.getTile(160, 16, 48, 16);
		button_notover = player.getTile(112, 16, 48, 16);
		
		//Cursor
		mouse_pressed = player.getTile(64+32+8, 8, 8, 8);
		mouse_unpressed = player.getTile(64+32, 8, 8, 8);
		
		//Grounds
		stoneGround_1 = blocks.getTile(0, 16, 16, 16);
		stoneGround_2 = blocks.getTile(16, 0, 16, 16);
		grass_1 = blocks.getTile(32, 0, 16, 16);
		WoodGround_1 = blocks.getTile(64, 0, 16, 16);
		StoneGrass_path_1 = blocks.getTile(112, 0, 16, 16);
		
		WoodGround_1uwood_grass = blocks.getTile(128, 0, 16, 16);
		WoodGround_1dwood_grass = blocks.getTile(176, 0, 16, 16);
		WoodGround_1lwood_grass = blocks.getTile(144, 0, 16, 16);
		WoodGround_1rwood_grass = blocks.getTile(160, 0, 16, 16);
		
		
		//Walls
		wall_1 = blocks.getTile(32, 32, 16, 16);
		wall_2 = blocks.getTile(0, 32, 16, 16);
		CornerWall_l = blocks.getTile(48, 32, 16, 16);
		CornerWall_r = blocks.getTile(48, 16, 16, 16);
		CornerWall_m = blocks.getTile(32, 16, 16, 16);
		CornerWall_lg = blocks.getTile(64, 32, 16, 16);
		CornerWall_rg = blocks.getTile(64, 48, 16, 16);
		WallGrass_1 = blocks.getTile(64, 16, 16, 16);
		CornerWall_lw = blocks.getTile(80, 32, 16, 16);
		CornerWall_rw = blocks.getTile(80, 48, 16, 16);
		CornerWall_mw = blocks.getTile(80, 64, 16, 16);
		wall_1w = blocks.getTile(80, 16, 16, 16);
		
		//plants
		Flower_1 = blocks.getTile(80, 0, 16, 16);
		Flower_2 = blocks.getTile(96, 0, 16, 16);
	
	}
	
	//health
	public static BufferedImage getHealth_bar() {
		return Health_bar;
	}
	
	public static BufferedImage getPlayer_damage() {
		return Player_damage;
	}
	
	
	//Attack
	public static BufferedImage getUp_attack() {
		return up_attack;
	}
	
	public static BufferedImage getDown_attack() {
		return down_attack;
	}
	
	public static BufferedImage getRight_attack() {
		return right_attack;
	}
	
	public static BufferedImage getLeft_attack() {
		return left_attack;
	}
	
	//Button
	public static BufferedImage getButton_notover() {
		return button_notover;
	}
	
	public static BufferedImage getButton_heldover() {
		return button_heldover;
	}
	
	//Cursor
	public static BufferedImage getMouse_pressed() {
		return mouse_pressed;
	}
	
	public static BufferedImage getMouse_unpressed() {
		return mouse_unpressed;
	}
	
	//GROUNDS
	public static BufferedImage getStoneGround_1() {
		return stoneGround_1;
	}
	
	public static BufferedImage getStoneGround_2() {
		return stoneGround_2;
	}
	
	public static BufferedImage getGrass_1() {
		return grass_1;
	}
	
	public static BufferedImage getWoodGround_1() {
		return WoodGround_1;
	}
	
	public static BufferedImage getWoodGround_1uwood_grass() {
		return WoodGround_1uwood_grass;
	}
	
	public static BufferedImage getWoodGround_1dwood_grass() {
		return WoodGround_1dwood_grass;
	}
	
	public static BufferedImage getWoodGround_1lwood_grass() {
		return WoodGround_1lwood_grass;
	}
	
	public static BufferedImage getWoodGround_1rwood_grass() {
		return WoodGround_1rwood_grass;
	}
	
	public static BufferedImage getStoneGrass_path() {
		return StoneGrass_path_1;
	}
	
	
	//WALLS
	public static BufferedImage getWall_1() {
		return wall_1;
	}
	
	public static BufferedImage getWall_2() {
		return wall_2;
		
	}
	
	public static BufferedImage getCornerWall_l() {
		return CornerWall_l;
		
	}
	
	public static BufferedImage getCornerWall_r() {
		return CornerWall_r;
		
	}
	
	public static BufferedImage getCornerWall_m() {
		return CornerWall_m;
		
	}
	
	public static BufferedImage getCornerWall_lg() {
		return CornerWall_lg;
		
	}
	
	public static BufferedImage getCornerWall_rg() {
		return CornerWall_rg;
		
	}
	
	public static BufferedImage getWallGrass_1() {
		return WallGrass_1;
		
	}
	
	public static BufferedImage getCornerWall_lw() {
		return CornerWall_lw;
		
	}
	
	public static BufferedImage getCornerWall_rw() {
		return CornerWall_rw;
		
	}
	
	public static BufferedImage getCornerWall_mw() {
		return CornerWall_mw;
		
	}
	
	public static BufferedImage getwall_1w() {
		return wall_1w;
		
	}
	//Plants		
	public static BufferedImage getFlower_1() {
		return Flower_1;
	}
	
	public static BufferedImage getFlower_2() {
		return Flower_2;
	}
}
