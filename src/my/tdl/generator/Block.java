package my.tdl.generator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import my.project.gop.main.Vector2F;
import my.tdl.main.Assets;
import my.tdl.managers.LightSource;

public class Block extends Rectangle{
	
	public Vector2F pos = new Vector2F();
	public int BlockSize = 48;
	private BlockType blocktype;
	private BufferedImage block;
	private boolean isSolid;
	private boolean isAlive;
	public boolean dropped = false;
	
	private float lightLevel = 1f;
	
	public Block(Vector2F pos) {
		setBounds((int)pos.xpos, (int)pos.ypos, BlockSize, BlockSize);
		this.pos = pos;
		isAlive = true;
	}
	
	public Block(Vector2F pos, BlockType blocktype) {
		setBounds((int)pos.xpos, (int)pos.ypos, BlockSize, BlockSize);
		this.pos = pos;
		isAlive = true;
		this.blocktype = blocktype;
		init();
	}

	public Block isSolid(boolean isSolid){
		this.isSolid = isSolid;
		return this;
	}
	
	public void init(){
		switch(blocktype){
		//GROUND
		case STONEGROUND_1:
			block = Assets.getStoneGround_1();
			break;
			
		case STONEGROUND_2:
			block = Assets.getStoneGround_2();
			break;
			
		case GRASS_1:
			block = Assets.getGrass_1();
			break;
			
		case WOODGROUND_1:
			block = Assets.getWoodGround_1();
			break;
			
		case WOODGROUND_1uwood_grass:
			block = Assets.getWoodGround_1uwood_grass();
			break;
			
		case WOODGROUND_1dwood_grass:
			block = Assets.getWoodGround_1dwood_grass();
			break;
			
		case WOODGROUND_1lwood_grass:
			block = Assets.getWoodGround_1lwood_grass();
			break;
			
		case WOODGROUND_1rwood_grass:
			block = Assets.getWoodGround_1rwood_grass();
			break;
		case STONEGRASS_path_1:
			block = Assets.getStoneGrass_path();
			break;
			
		//WALL
		case WALL_1:
			block = Assets.getWall_1();
			break;
			
		case WALL_2:
			block = Assets.getWall_2();
			break;
			
		case CORNERWALL_l:
			block = Assets.getCornerWall_l();
			break;
			
		case CORNERWALL_r:
			block = Assets.getCornerWall_r();
			break;
			
		case CORNERWALL_m:
			block = Assets.getCornerWall_m();
			break;
			
		case CORNERWALL_lg:
			block = Assets.getCornerWall_lg();
			break;
			
		case CORNERWALL_rg:
			block = Assets.getCornerWall_rg();
			break;
			
		case WALLGRASS_1:
			block = Assets.getWallGrass_1();
			break;
			
		case CORNERWALL_lw:
			block = Assets.getCornerWall_lw();
			break;			
		case CORNERWALL_rw:
			block = Assets.getCornerWall_rw();
			break;			
		case CORNERWALL_mw:
			block = Assets.getCornerWall_mw();
			break;			
		case WALL_1w:
			block = Assets.getwall_1w();
			break;
			
			//Plants
		case FLOWER_1:
			block = Assets.getFlower_1();
			break;
		case FLOWER_2:
			block = Assets.getFlower_2();
			break;
		
		}
	}

	public void tick(double deltaTime){
		if(isAlive){
			setBounds((int)pos.xpos, (int)pos.ypos, BlockSize, BlockSize);
		}
		
		if(lightLevel > 0f && lightLevel < 0.40f){
			lightLevel = 0f;
		}
		
	}
	
	public void renderBlockLightLevel(Graphics2D g){
		if(lightLevel > 0){
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, lightLevel));
			g.setColor(Color.BLACK);
			g.fillRect((int)pos.getWorldLocation().xpos, (int)pos.getWorldLocation().ypos, BlockSize, BlockSize);
			g.setColor(Color.WHITE);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		}
	}
	
	public void render(Graphics2D g){ 
		if(isAlive){
			if(block != null){
				g.drawImage(block, (int)pos.getWorldLocation().xpos, (int)pos.getWorldLocation().ypos, BlockSize, BlockSize, null);
				
			}else{
				g.fillRect((int)pos.getWorldLocation().xpos, (int)pos.getWorldLocation().ypos, BlockSize, BlockSize);
			}
				
				if(isSolid){
					g.drawRect((int)pos.getWorldLocation().xpos, (int)pos.getWorldLocation().ypos, BlockSize, BlockSize);
				}
		}else{
			if(!dropped){
				float xpos = pos.xpos + 24 - 12;
				float ypos = pos.ypos + 24 - 12;
				
				Vector2F newpos = new Vector2F(xpos, ypos);
				
				//World.dropBlockEntity(newpos, block);
				
				dropped = true;
			}

		}
		
	}
	
	public enum BlockType{
		//GROUNDS
		STONEGROUND_1,
		STONEGROUND_2,
		GRASS_1,
		WOODGROUND_1, 
		WOODGROUND_1uwood_grass, 
		WOODGROUND_1dwood_grass, 
		WOODGROUND_1lwood_grass, 
		WOODGROUND_1rwood_grass,
		STONEGRASS_path_1,
		
		//WALLS
		WALL_1,
		WALL_2,
		CORNERWALL_l,
		CORNERWALL_r,
		CORNERWALL_m,
		CORNERWALL_lg,
		CORNERWALL_rg,
		WALLGRASS_1,
		CORNERWALL_lw,
		CORNERWALL_rw,
		CORNERWALL_mw,
		WALL_1w, 
		
		//plants
		FLOWER_1,
		FLOWER_2,

	}

	public boolean isSolid() {
		return isSolid;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public Vector2F getBlockLocation() {
		return pos;
	}
	
	public BufferedImage getBlockType() {
		return block;
	}
	
	public void addShadow(LightSource source, float amount){
		if(lightLevel != 1){
			if(!this.getBounds().intersects(source.getLightDetection())){
				if(lightLevel < 0.981000){
					lightLevel += amount;
				}
			}

		}
	}
	
	public void removeShadow(float amount){
		if(lightLevel > 0.001000){
			lightLevel -= amount;
		}
	}
	
	public float getLightLevel() {
		return lightLevel;
	}
}
