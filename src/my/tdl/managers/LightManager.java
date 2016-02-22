package my.tdl.managers;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import my.project.gop.main.Vector2F;
import my.tdl.generator.Block;
import my.tdl.generator.World;

public class LightManager {

	private CopyOnWriteArrayList<LightSource> lights;
	private CopyOnWriteArrayList<Block> load_blocks;
	
	public LightManager(CopyOnWriteArrayList<Block> load_blocks) {
		this.load_blocks = load_blocks;
	}
	
	LightSource light;
		
	public void init(){
		lights = new CopyOnWriteArrayList<LightSource>();
		light = new LightSource(200, 200, 10);
		lights.add(light);
//		lights.add(new LightSource(x, y, LightDistance));
//		lights.add(new LightSource(400, 400, 10));
		
	}
	
	public void tick(){
		for(LightSource light : lights){
			light.tick();
		}
		
		light.getLightLocation().xpos
		= World.getPlayer().getPos().xpos
		- World.getPlayer().getPos().getWorldLocation().xpos
		+ World.getPlayer().getPos().xpos - 8;
		
		light.getLightLocation().ypos
		= World.getPlayer().getPos().ypos
		- World.getPlayer().getPos().getWorldLocation().ypos
		+ World.getPlayer().getPos().ypos - 8;
	}
	
	public void render(Graphics2D g){
		for(LightSource light : lights){
			light.render(g);
		}
	}
	
	public void addNewLight(int xpos, int ypos, int ld){
		lights.add(new LightSource(xpos, ypos, ld));
	}
	
	public CopyOnWriteArrayList<LightSource> getLights(){
		return lights;
	}
	


}
