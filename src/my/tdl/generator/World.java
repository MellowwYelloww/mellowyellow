package my.tdl.generator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;
import my.tdl.MoveableObjects.Player;
import my.tdl.gamestate.GameStateManager;
import my.tdl.gamestates.DungeonLevelLoader;
import my.tdl.generator.Block.BlockType;
import my.tdl.main.Main;
import my.tdl.managers.LightManager;

public class World {
	
	public Vector2F map_pos = new Vector2F();
	private String worldName;
	private BufferedImage map;
	private int world_width;
	private int world_height;
	private int blockSize = 48;
	private static Player player;
	private boolean hasGenerated;
	
	//LISTS
	private CopyOnWriteArrayList<BlockEntity> blockents;
	public TileManager tiles;
	private LightManager lm;

	//world spawn
	private Block spawn;
	
	//booleans
	private boolean hasSize = false;
	private GameStateManager gsm;
	private Graphics2D g;

	public World(String worldName, GameStateManager gsm) {
		this.worldName = worldName;
		this.gsm = gsm;
		Vector2F.setWorldVaribles(map_pos.xpos, map_pos.ypos);
	}

	public void init() {
		blockents = new CopyOnWriteArrayList<BlockEntity>();
		tiles = new TileManager(this);	
		
		lm = new LightManager(tiles.getBlocks());
		lm.init();
		
		map_pos.xpos = spawn.getBlockLocation().xpos - player.getPos().xpos;
		map_pos.ypos = spawn.getBlockLocation().ypos - player.getPos().ypos;
		
		if(player != null){
			player.init(this);
		}	
		
	}

	public void tick(double deltaTime) {
			Vector2F.setWorldVaribles(map_pos.xpos, map_pos.ypos);
		
		spawn.tick(deltaTime);
		
		if(!player.hasSpawned()){
			spawn.tick(deltaTime);
		}
		
		tiles.tick(deltaTime);
		
		lm.tick();
		
		if(!blockents.isEmpty()){
			for(BlockEntity ent : blockents){
				if(player.render.intersects(ent)){
					ent.tick(deltaTime);
					ent.setAlive(true);
				}else{
					ent.setAlive(false);
				}
			}
		}
		
		if(player != null){
			player.tick(deltaTime);
		}
	}

	public void render(Graphics2D g) {
		tiles.render(g);
		if(!player.hasSpawned()){
			spawn.render(g);
		}
		
		if(!blockents.isEmpty()){
			for(BlockEntity ent : blockents){
				if(player.render.intersects(ent)){
					ent.render(g);
				}
			}
		}
		
		if(player != null){
			player.render(g);
		}
		
		for(Block block : TileManager.blocks){
			if(player.render.intersects(block)){
				block.renderBlockLightLevel(g);
			}
		}
		
		player.inv.render(g);
		
		lm.render(g);
		

		
//		g.setColor(Color.BLACK);
//		g.fillRect(0, 0, Main.width, Main.height / 6);
//		g.fillRect(0, 900, Main.width, Main.height / 6);
//		g.setColor(Color.WHITE);
	}

	public void generate(String world_image_name) {
		
		map = null;
		
		if(hasSize){
			try{
				map = loadImageFrom.LoadImageFrom(Main.class, world_image_name+".png");
			}catch(Exception e){
				
			}
				
				for(int x = 0;x < world_width; x++){
					
					for(int y = 0;y < world_height; y++){
						
						int col = map.getRGB(x, y);
						
						switch(col & 0xFFFFFF){
						
						//GROUDS
					case 0x808080:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.STONEGROUND_1));
						break;
					case 0x9F9F9F:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.STONEGROUND_2));
						break;
					case 0x00FF00:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.GRASS_1));
						break;
					case 0x7F3300:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WOODGROUND_1));
						break;
					case 0x7F3301:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WOODGROUND_1uwood_grass));
						break;
					case 0x7F3302:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WOODGROUND_1dwood_grass));
						break;
					case 0x7F3303:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WOODGROUND_1lwood_grass));
						break;
					case 0x7F3304:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WOODGROUND_1rwood_grass));
						break;
					case 0x0000FF:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.STONEGRASS_path_1));
						break;
						
						//WALLS
					case 0x404040:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WALL_1).isSolid(true));
						break;
					case 0x202020:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WALL_2).isSolid(true));
						break;
					case 0x424242:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.CORNERWALL_l).isSolid(true));
						break;
					case 0x414141:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.CORNERWALL_r).isSolid(true));
						break;
					case 0x434343:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.CORNERWALL_m).isSolid(true));
						break;
					case 0x419941:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.CORNERWALL_rg).isSolid(true));
						break;
					case 0x439943:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WALLGRASS_1).isSolid(true));
						break;
					case 0x429942:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.CORNERWALL_lg).isSolid(true));
						break;
					case 0x40AA40:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WALL_1w).isSolid(true));
						break;
					case 0x43AA43:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.CORNERWALL_mw).isSolid(true));
						break;
					case 0x41AA41:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.CORNERWALL_rw).isSolid(true));
						break;
					case 0x42AA42:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.CORNERWALL_lw).isSolid(true));
						break;
						
						//Plants
					case 0xAA01AA:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.FLOWER_1));
						break;
					case 0xAA02AA:
						tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.FLOWER_2));
						break;
					}
				}
			}
		}
		
		hasGenerated = true;
	}

	public void setSize(int world_width, int world_height) {
		this.world_width = world_width;
		this.world_height = world_height;
		hasSize = true;
	}

	public Vector2F getWorldPos(){
		return map_pos;
	}
	
	public float getWorldXpos(){
		return map_pos.xpos;
	}
	
	public float getWorldYpos(){
		return map_pos.ypos;
	}
	
	public void addPlayer(Player player) {
		this.player = player;
	}
	
	public void dropBlockEntity(Vector2F pos, BufferedImage block_image){
		BlockEntity ent = new BlockEntity(pos, block_image);
		if(!blockents.contains(ent)){
			blockents.add(ent);
		}
	}
	
	public void setWorldSpawn(float xpos, float ypos){
		if(xpos < world_width){
			if(ypos < world_height){
				Block spawn = new Block(new Vector2F(xpos*blockSize,ypos*blockSize));
				this.spawn = spawn;
			}
		}
	}
	
	public Vector2F getWorldSpawn(){
		return spawn.pos;
	}

	public void removeDroppedBlockEntity(BlockEntity blockEntity){
			if(blockents.contains(blockEntity)){
				blockents.remove(blockEntity);
			}
	}
	
	public TileManager getWorldBlocks() {
		return tiles;
	}
	
	public static Player getPlayer() {
		return player;
	}
	
	public boolean HasGenerated() {
		return hasGenerated;
	}
	
	public void resetWorld(){
		tiles.getBlocks().clear();
		tiles.getLoadedBlocks().clear();
		blockents.clear();
		spawn = null;
	}
	
	public LightManager getLm() {
		return lm;
	}
	
	public void changeToWorld(String wn, String mn){
		if(wn != worldName){
			resetWorld();
			gsm.states.push(new DungeonLevelLoader(gsm,wn,mn));
			gsm.states.peek().init();
			System.out.println("Changed To World"+wn+"");
		}else{
			System.err.println("You Are Already In That World!!");
		}
	}
}
