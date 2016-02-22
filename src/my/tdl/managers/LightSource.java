package my.tdl.managers;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import my.project.gop.main.Vector2F;
import my.tdl.generator.Block;
import my.tdl.generator.TileManager;

public class LightSource {
	
	private Vector2F lightLocation = new Vector2F();
	private double lightSize = 48;
	private double lightDistance;
	private Rectangle lightDetection;

	public LightSource(float xpos, float ypos, double lightDistance) {
		lightLocation.xpos = xpos;
		lightLocation.ypos = ypos;
		this.lightDistance = lightDistance;
	}

	public void tick(){
		lightDetection = new Rectangle(
				(int)(lightLocation.xpos - 42*lightDistance / 2 + lightSize / 2), 
				(int)(lightLocation.ypos - 42*lightDistance / 2 + lightSize / 2),
				(int) (42*lightDistance), (int) (42*lightDistance));
		
	}
	
	public void render(Graphics2D g){
//		g.fillRect((int)lightLocation.getWorldLocation().xpos, 
//				   (int)lightLocation.getWorldLocation().ypos, 
//				   (int)lightSize, 
//				   (int)lightSize);
		
//		g.drawRect((int)(lightLocation.getWorldLocation().xpos - lightDistance / 2 + lightSize / 2), 
//				   (int)(lightLocation.getWorldLocation().ypos - lightDistance / 2 + lightSize / 2),
//				   (int)lightDistance, (int)lightDistance);
		
		for(Block block : TileManager.blocks){
			if(block != null){
				if(lightDetection != null){
					if(lightDetection.intersects(block.getBounds())){
						float distance = (float) Vector2F.getDistanceOnScreen(lightLocation, block.getBlockLocation());
						
						for(int dis = 48; dis < lightDistance*42; dis++){
							if(distance < dis){
								switch(dis){
								case 42:
									if(block.getLightLevel() > 0.20)
									block.removeShadow(0.045f);
								break;
								case 42*2:
									if(block.getLightLevel() > 0.30)
									block.removeShadow(0.035f);
								break;
								case 42*3:
									if(block.getLightLevel() > 0.40)
									block.removeShadow(0.025f);
								break;
								case 42*4:
									if(block.getLightLevel() > 0.50)
									block.removeShadow(0.015f);
								break;
								case 42*5:
									if(block.getLightLevel() > 0.60)
									block.removeShadow(0.0090f);
								break;
								case 42*6:
									if(block.getLightLevel() > 0.70)
									block.removeShadow(0.0080f);
								break;
								case 42*7:
									if(block.getLightLevel() > 0.80)
									block.removeShadow(0.0070f);
								break;
								case 42*8:
									if(block.getLightLevel() > 0.90)
									block.removeShadow(0.0060f);
								break;
								}
							}
						}
						
					}
				}
			}
		}
	}
	
	public Vector2F getLightLocation() {
		return lightLocation;
	}
	
	public Rectangle getLightDetection() {
		return lightDetection;
	}
}
