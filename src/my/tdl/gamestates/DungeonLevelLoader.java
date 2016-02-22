package my.tdl.gamestates;

import java.awt.Graphics2D;

import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;
import my.tdl.MoveableObjects.Player;
import my.tdl.gamestate.GameState;
import my.tdl.gamestate.GameStateManager;
import my.tdl.generator.World;
import my.tdl.main.Main;

public class DungeonLevelLoader extends GameState{
	
	public static World world;
	private String worldName;
	private String map_name;

	public DungeonLevelLoader(GameStateManager gsm) {
		super(gsm);
	}
	
	public DungeonLevelLoader(GameStateManager gsm, String worldName, String map_name) {
		super(gsm);
		this.worldName = worldName;
		this.map_name = map_name;
	}

	@Override
	public void init() {
		
		if(worldName == null){
			worldName = "NULL";
			map_name = "map";
		}
		
		world = new World(worldName,gsm);
		world.setSize(100, 100);
		world.setWorldSpawn(19, 10);
		world.addPlayer(new Player());
		world.init();
		world.generate(map_name);

	}

	@Override
	public void tick(double deltaTime) {
		if(world.HasGenerated()){
			world.tick(deltaTime);
		}
	}

	@Override
	public void render(Graphics2D g) {
		if(world.HasGenerated()){
			world.render(g);
		}
	}
}
