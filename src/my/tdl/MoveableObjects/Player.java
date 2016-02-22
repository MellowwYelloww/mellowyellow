package my.tdl.MoveableObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import my.project.gop.main.Vector2F;
import my.tdl.gameloop.GameLoop;
import my.tdl.gamestate.GameStateButton;
import my.tdl.gamestates.DungeonLevelLoader;
import my.tdl.generator.World;
import my.tdl.inventory.Inventory;
import my.tdl.inventory.Item;
import my.tdl.main.Animator;
import my.tdl.main.Assets;
import my.tdl.main.Check;
import my.tdl.main.Main;
import my.tdl.managers.GUImanager;
import my.tdl.managers.HUDmanager;
import my.tdl.managers.MouseManager;

public class Player implements KeyListener {
	
	Vector2F pos;
	private World world;
	private int width = 32;
	private int height = 32;
	private int scale =2;
	private static boolean up, down, left, right, running,damaged;
	private static boolean debug = false;
	private float maxSpeed = 3*32F;
	
	private float speedUp = 0;
	private float speedDown = 0;
	private float speedLeft = 0;
	private float speedRight = 0;
	
	private float slowdown = 4.093F;
	
	private float fixDT = 1f/60F;
	private static long animationSpeed = 180;
	
	private static boolean moving;	
	private static boolean spawned;
	
	MouseManager playerMM = new MouseManager();
	public PlayerStats ps = new PlayerStats(this);
	private double dmTime = 1;
	
	public static Inventory inv = new Inventory(200, 200, 6);
	
	
	/*
	 * Rendering
	 */
	private int renderDistanceW = Main.width / 32 + 1;
	private int renderDistanceH = Main.height / 32 + 1;
	public static Rectangle render;
	
	
	//TODO
	private int animationState = 0;
	
	
	/*
	 * 0 = up
	 * 1 = down
	 * 2 = right
	 * 3 = left
	 */
	
	private ArrayList<BufferedImage> listUp;
	Animator ani_up;
	private ArrayList<BufferedImage> listDown;
	Animator ani_down;
	private ArrayList<BufferedImage> listRight;
	Animator ani_right;
	private ArrayList<BufferedImage> listLeft;
	Animator ani_left;
	private ArrayList<BufferedImage> listIdle;
	Animator ani_idle;
	private ArrayList<BufferedImage> listAttackUp;
	Animator ani_attackup;
	private ArrayList<BufferedImage> listAttackDown;
	Animator ani_attackdown;
	private ArrayList<BufferedImage> listAttackRight;
	Animator ani_attackright;
	private ArrayList<BufferedImage> listAttackLeft;
	Animator ani_attackleft;
	
	private HUDmanager hudm;
	private GUImanager guim;
	public PlayerActions playerActions;
	
	public Player() {
		pos = new Vector2F(Main.width / 2 - width / 2, Main.height / 2 - height / 2);
	}
	
	public void init(World world) {
		inv.init();
		playerActions = new PlayerActions(world);
		hudm = new HUDmanager(world);
		guim = new GUImanager();
		this.world = world;
		
		render = new Rectangle(
				(int) (pos.xpos - pos.getWorldLocation().xpos + pos.xpos - renderDistanceW*32 / 2 + width / 2), 
				(int) (pos.ypos - pos.getWorldLocation().ypos + pos.ypos - renderDistanceH*32 / 2 + height / 2), 
				renderDistanceW*32, 
				renderDistanceH*32);
		
		listUp = new ArrayList<BufferedImage>();
		listDown = new ArrayList<BufferedImage>();
		listRight = new ArrayList<BufferedImage>();
		listLeft = new ArrayList<BufferedImage>();
		listIdle = new ArrayList<BufferedImage>();
		listAttackUp = new ArrayList<BufferedImage>();
		listAttackDown = new ArrayList<BufferedImage>();
		listAttackRight = new ArrayList<BufferedImage>();
		listAttackLeft = new ArrayList<BufferedImage>();
		
		listUp.add(Assets.player.getTile(0, 0, 16, 16));
		listUp.add(Assets.player.getTile(16, 0, 16, 16));
		
		listDown.add(Assets.player.getTile(0, 16, 16, 16));
		listDown.add(Assets.player.getTile(16, 16, 16, 16));
		
		listRight.add(Assets.player.getTile(32, 16, 16, 16));
		listRight.add(Assets.player.getTile(48, 16, 16, 16));
		listRight.add(Assets.player.getTile(64, 16, 16, 16));
		listRight.add(Assets.player.getTile(64+16, 16, 16, 16));
		
		listLeft.add(Assets.player.getTile(32, 0, 16, 16));
		listLeft.add(Assets.player.getTile(48, 0, 16, 16));
		listLeft.add(Assets.player.getTile(64, 0, 16, 16));
		listLeft.add(Assets.player.getTile(64+16, 0, 16, 16));
		
		listIdle.add(Assets.player.getTile(0, 32, 16, 16));
		listIdle.add(Assets.player.getTile(16, 32, 16, 16));
		listIdle.add(Assets.player.getTile(32, 32, 16, 16));
		listIdle.add(Assets.player.getTile(48, 32, 16, 16));
		
		listAttackUp.add(Assets.player.getTile(0, 48, 16, 16));
		listAttackUp.add(Assets.player.getTile(16, 48, 16, 16));
		
		listAttackDown.add(Assets.player.getTile(0, 64, 16, 16));
		listAttackDown.add(Assets.player.getTile(16, 64, 16, 16));
		
		listAttackRight.add(Assets.player.getTile(0, 80, 16, 16));
		listAttackRight.add(Assets.player.getTile(16, 80, 16, 16));
		
		listAttackLeft.add(Assets.player.getTile(0, 96, 16, 16));
		listAttackLeft.add(Assets.player.getTile(16, 96, 16, 16));
		
		
		//UP
		ani_up = new Animator(listUp);
		ani_up.setSpeed(animationSpeed);
		ani_up.play();
		
		//DOWN
		ani_down = new Animator(listDown);
		ani_down.setSpeed(animationSpeed);
		ani_down.play();
		
		//RIGHT
		ani_right = new Animator(listRight);
		ani_right.setSpeed(animationSpeed);
		ani_right.play();
		
		//LEFT
		ani_left = new Animator(listLeft);
		ani_left.setSpeed(animationSpeed);
		ani_left.play();
		
		//IDLE
		ani_idle = new Animator(listIdle);
		ani_idle.setSpeed(animationSpeed);
		ani_idle.play();
		
		//AttackUP
		ani_attackup = new Animator(listAttackUp);
		ani_attackup.setSpeed(animationSpeed);
		ani_attackup.play();
		
		//attackDOWN
		ani_attackdown = new Animator(listAttackDown);
		ani_attackdown.setSpeed(animationSpeed);
		ani_attackdown.play();
		
		//AttackRIGHT
		ani_attackright = new Animator(listAttackRight);
		ani_attackright.setSpeed(animationSpeed);
		ani_attackright.play();
		
		//ATTACKLEFT
		ani_attackleft = new Animator(listAttackLeft);
		ani_attackleft.setSpeed(animationSpeed);
		ani_attackleft.play();
		
		spawned = true;

	}
		
	public void tick(double deltaTime) {
		inv.tick();
		playerMM.tick();
		ps.tick();
		playerActions.tick();
		
		if(isDamaged()){
			
			if(dmTime != 0){
				dmTime-= 0.2;
			}
			if(dmTime <= 0){
				setDamaged(false);
				dmTime = 1;
			}
		}
		
		render = new Rectangle(
				(int) (pos.xpos - pos.getWorldLocation().xpos + pos.xpos - renderDistanceW*32 / 2 + width / 2), 
				(int) (pos.ypos - pos.getWorldLocation().ypos + pos.ypos - renderDistanceH*32 / 2 + height / 2), 
				renderDistanceW*32, 
				renderDistanceH*32);
		
		float moveAmountu = (float) (speedUp * fixDT);
		float moveAmountd = (float) (speedDown * fixDT);
		float moveAmountl = (float) (speedLeft * fixDT);
		float moveAmountr = (float) (speedRight * fixDT);
		

		if(up){
			moveMapUp(moveAmountu);
			animationState = 0;
		}else{
			moveMapUpGlide(moveAmountu);
		}
		
		if(down){				
			moveMapDown(moveAmountd);
			animationState = 1;
		}else{
			moveMapDownGlide(moveAmountd);				
		}
		
		if(right){
			moveMapRight(moveAmountr);
			animationState = 2;
		}else{
			moveMapRightGlide(moveAmountr);
		}
		
		if(left){
			moveMapLeft(moveAmountl);
			animationState = 3;
		}else{
			moveMapLeftGlide(moveAmountl);
		}
		
		if(!up && !down && !right && !left){
			/*
			 * standing still
			 */
			animationState =4;
			if(moving){
				moving = false;
			}
		}

	
		if(running){
			if(animationSpeed != 100){
				animationSpeed = 100;
				ani_up.setSpeed(animationSpeed);
				ani_down.setSpeed(animationSpeed);
				ani_right.setSpeed(animationSpeed);
				ani_left.setSpeed(animationSpeed);
				ani_idle.setSpeed(animationSpeed);
				maxSpeed += 48;
			}
		}else{
			if(animationSpeed != 180){
				animationSpeed = 180;
				ani_up.setSpeed(animationSpeed);
				ani_down.setSpeed(animationSpeed);
				ani_right.setSpeed(animationSpeed);
				ani_left.setSpeed(animationSpeed);
				ani_idle.setSpeed(animationSpeed);
				maxSpeed -= 48;
			}
		}
	}
	/*
	public void PlayerMoveCode(){
		if(!mapMove){
			
			if(up){
				
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + world.map_pos.xpos) , 
								  (int) (pos.ypos + world.map_pos.ypos - moveAmountu)),
								
						new Point((int) (pos.xpos + world.map_pos.xpos + width) , 
								  (int) (pos.ypos + world.map_pos.ypos - moveAmountu)) )){
					
					if(speedUp < maxSpeed){
						speedUp += slowdown;
					}else{
						speedUp = maxSpeed;
					}
					
					pos.ypos-=moveAmountu;
					
				}else{
					speedUp = 0;
				}
				

			}else{
				
				
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + world.map_pos.xpos) , 
								  (int) (pos.ypos + world.map_pos.ypos - moveAmountu)),
								
						new Point((int) (pos.xpos + world.map_pos.xpos + width) , 
								  (int) (pos.ypos + world.map_pos.ypos - moveAmountu)) )){
					
					if(speedUp != 0){
						speedUp -= slowdown;
						
						if(speedUp < 0){
							speedUp = 0;
						}
					}
					
					pos.ypos-=moveAmountu;
					
				}else{
					speedUp = 0;
				}
			}
			
			if(down){
				
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + world.map_pos.xpos) , 
								  (int) (pos.ypos + world.map_pos.ypos + height + moveAmountd)),
								
						new Point((int) (pos.xpos + world.map_pos.xpos + width) , 
								  (int) (pos.ypos + world.map_pos.ypos + height + moveAmountd)) )){
					
					if(speedDown < maxSpeed){
						speedDown += slowdown;
					}else{
						speedDown = maxSpeed;
					}
					
					pos.ypos+=moveAmountd;
				}else{
					speedDown = 0;
				}
				

			}else{
				
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + world.map_pos.xpos) , 
								  (int) (pos.ypos + world.map_pos.ypos + height + moveAmountd)),
								
						new Point((int) (pos.xpos + world.map_pos.xpos + width) , 
								  (int) (pos.ypos + world.map_pos.ypos + height + moveAmountd)) )){
					
					if(speedDown != 0){
						speedDown -= slowdown;
						
						if(speedDown < 0){
							speedDown = 0;
						}
						
					}
					
					pos.ypos+=moveAmountd;
					
				}else{
					speedDown = 0;
				}
			}
			
			if(left){
				
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + world.map_pos.xpos - moveAmountl) , 
								  (int) (pos.ypos + world.map_pos.ypos + height)),
								
						new Point((int) (pos.xpos + world.map_pos.xpos - moveAmountl) , 
								  (int) (pos.ypos + world.map_pos.ypos)) )){
					
					if(speedLeft < maxSpeed){
						speedLeft += slowdown;
					}else{
						speedLeft = maxSpeed;
					}
					
					pos.xpos-=moveAmountl;
				}else{
					speedLeft = 0;
				}
				

			}else{
				
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + world.map_pos.xpos - moveAmountl) , 
								  (int) (pos.ypos + world.map_pos.ypos + height)),
								
						new Point((int) (pos.xpos + world.map_pos.xpos - moveAmountl) , 
								  (int) (pos.ypos + world.map_pos.ypos)) )){
					
					if(speedLeft != 0){
						speedLeft -= slowdown;
						
						if(speedLeft < 0){
							speedLeft = 0;
						}
						
					}
					
					pos.xpos-=moveAmountl;
				}else{
					speedLeft = 0;
				}
				

				
			}
			
			if(right){
				
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + world.map_pos.xpos + width + moveAmountr) , 
								  (int) (pos.ypos + world.map_pos.ypos)),
								
						new Point((int) (pos.xpos + world.map_pos.xpos + width + moveAmountr) , 
								  (int) (pos.ypos + world.map_pos.ypos + height)) )){
					
					if(speedRight < maxSpeed){
						speedRight += slowdown;
					}else{
						speedRight = maxSpeed;
					}
					
					pos.xpos+=moveAmountr;
				}else{
					speedRight = 0;
				}
				

			}else{
				
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + world.map_pos.xpos + width + moveAmountr) , 
								  (int) (pos.ypos + world.map_pos.ypos)),
								
						new Point((int) (pos.xpos + world.map_pos.xpos + width + moveAmountr) , 
								  (int) (pos.ypos + world.map_pos.ypos + height)) )){
					
					if(speedRight != 0){
						speedRight -= slowdown;
						
						if(speedRight < 0){
							speedRight = 0;
						}
						
					}
					
					pos.xpos+=moveAmountr;
				}else{
					speedRight = 0;
				}
				

			}
			
			
			
			//PLAYER MOVE
			
		}else{
			//MAP MOVE

		}
	}
	*/
	public void moveMapUp(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + world.map_pos.xpos) ,
						  (int) (pos.ypos + world.map_pos.ypos - speed)),
						  
				new Point((int) (pos.xpos + world.map_pos.xpos + width) , 
						  (int) (pos.ypos + world.map_pos.ypos - speed))  )){
			
			if(speedUp < maxSpeed){
				speedUp += slowdown;
			}else{
				speedUp = maxSpeed;
			}
			
			world.map_pos.ypos-=speed;
			
		}else{
			speedUp = 0;
		}	
	}
	
	public void moveMapUpGlide(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + world.map_pos.xpos) ,
						  (int) (pos.ypos + world.map_pos.ypos - speed)),
						  
				new Point((int) (pos.xpos + world.map_pos.xpos + width) , 
						  (int) (pos.ypos + world.map_pos.ypos - speed))  )){
		
			if(speedUp != 0){
				speedUp -= slowdown;
				
				if(speedUp < 0){
					speedUp = 0;
				}
				
			}
			
			world.map_pos.ypos-=speed;
			
		}else{
			speedUp = 0;
		}
	}

	public void moveMapDown(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + world.map_pos.xpos) ,
						  (int) (pos.ypos + world.map_pos.ypos + height + speed)),
						  
				new Point((int) (pos.xpos + world.map_pos.xpos + width) , 
						  (int) (pos.ypos + world.map_pos.ypos + height + speed))  )){
	
			if(speedDown < maxSpeed){
				speedDown += slowdown;
			}else{
				speedDown = maxSpeed;
			}
			
			world.map_pos.ypos+=speed;
			
		}else{
			speedDown = 0;
		}
	}

	public void moveMapDownGlide(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + world.map_pos.xpos) ,
						  (int) (pos.ypos + world.map_pos.ypos + height + speed)),
						  
				new Point((int) (pos.xpos + world.map_pos.xpos + width) , 
						  (int) (pos.ypos + world.map_pos.ypos + height + speed))  )){
			
			if(speedDown != 0){
				speedDown -= slowdown;
				
				if(speedDown < 0){
					speedDown = 0;
				}
				
			}
			
			world.map_pos.ypos+=speed;
			
		}else{
			speedDown = 0;
		}
	}
	
	public void moveMapRight(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + world.map_pos.xpos + width + speed) , 
						  (int) (pos.ypos + world.map_pos.ypos)),
						
				new Point((int) (pos.xpos + world.map_pos.xpos + width + speed) , 
						  (int) (pos.ypos + world.map_pos.ypos + height)) )){
			
			if(speedRight < maxSpeed){
				speedRight += slowdown;
			}else{
				speedRight = maxSpeed;
			}
			
			world.map_pos.xpos+=speed;
		}else{
			speedRight = 0;
		}
	}
	
	public void moveMapRightGlide(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + world.map_pos.xpos + width + speed) , 
						  (int) (pos.ypos + world.map_pos.ypos)),
						
				new Point((int) (pos.xpos + world.map_pos.xpos + width + speed) , 
						  (int) (pos.ypos + world.map_pos.ypos + height)) )){
			
			if(speedRight != 0){
				speedRight -= slowdown;
				
				if(speedRight < 0){
					speedRight = 0;
				}
				
			}
			
			world.map_pos.xpos+=speed;
		}else{
			speedRight = 0;
		}
	}
	
	public void moveMapLeft(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + world.map_pos.xpos - speed) , 
						  (int) (pos.ypos + world.map_pos.ypos + height)),
						
				new Point((int) (pos.xpos + world.map_pos.xpos - speed) , 
						  (int) (pos.ypos + world.map_pos.ypos)) )){
			
			if(speedLeft < maxSpeed){
				speedLeft += slowdown;
			}else{
				speedLeft = maxSpeed;
			}
			
			world.map_pos.xpos-=speed;
		}else{
			speedLeft = 0;
		}
	}
	
	//GameStateButton button1 = new GameStateButton(200, 200);
	
	public void moveMapLeftGlide(float speed){
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + world.map_pos.xpos - speed) , 
						  (int) (pos.ypos + world.map_pos.ypos + height)),
						
				new Point((int) (pos.xpos + world.map_pos.xpos - speed) , 
						  (int) (pos.ypos + world.map_pos.ypos)) )){
			
			if(speedLeft != 0){
				speedLeft -= slowdown;
				
				if(speedLeft < 0){
					speedLeft = 0;
				}
				
			}
			
			world.map_pos.xpos-=speed;
		}else{
			speedLeft = 0;
		}
	}
	
	
	public void render(Graphics2D g) {
		

		
		//g.fillRect((int)pos.xpos, (int)pos.ypos, width, height);

		if(playerActions.attack_state != null){
			if(!playerActions.hasCompleted()){
				if(playerActions.attack){
					
					if(playerActions.attack_state == Assets.getUp_attack()){
						g.drawImage(playerActions.attack_state,
								(int) pos.xpos - width / 2, 
								(int) pos.ypos - height - 16,
								width * scale,
								height* scale,null);
					}
					
					if(playerActions.attack_state == Assets.getDown_attack()){
						g.drawImage(playerActions.attack_state,
								(int) pos.xpos - width / 2, 
								(int) pos.ypos - height + 16*3,
								width * scale,
								height* scale,null);
					}
					
                    if(playerActions.getAttack_state() == Assets.getRight_attack()){
                        g.drawImage(playerActions.attack_state,
                                        (int)pos.xpos - width / 2 + 16*3+8,
                                        (int)pos.ypos - height + 16,
                                        width * scale ,
                                        height * scale,null);
                    }
                    
                    if(playerActions.getAttack_state() == Assets.getLeft_attack()){
                        g.drawImage(playerActions.attack_state,
                                        (int)pos.xpos - width / 2 - 16*3-8,
                                        (int)pos.ypos - height + 16,
                                        width * scale ,
                                        height * scale,null);
                    }
				}
			}
		}

		//UP
		if(animationState == 0){
			g.drawImage(ani_up.sprite, (int) pos.xpos - width / 2, (int) pos.ypos - height, width * scale,height* scale,null);
			if(up){
				ani_up.update(System.currentTimeMillis());
			}

		}
		//DOWN
		if(animationState == 1){
			g.drawImage(ani_down.sprite, (int) pos.xpos - width / 2, (int) pos.ypos - height, width * scale,height* scale,null);
			if(down){
				ani_down.update(System.currentTimeMillis());
			}
		}
		//RIGHT
		if(animationState == 2){
			g.drawImage(ani_right.sprite, (int) pos.xpos - width / 2, (int) pos.ypos - height, width * scale,height* scale,null);
			if(right){
				ani_right.update(System.currentTimeMillis());
			}
		}
		//LEFE
		if(animationState == 3){
			g.drawImage(ani_left.sprite, (int) pos.xpos - width / 2, (int) pos.ypos - height, width * scale,height* scale,null);
			if(left){
				ani_left.update(System.currentTimeMillis());
			}
		}
		//IDLE
		if(animationState == 4){
			g.drawImage(ani_idle.sprite, (int) pos.xpos - width / 2, (int) pos.ypos - height, width * scale,height* scale,null);
				ani_idle.update(System.currentTimeMillis());
			}
		
		if(isDamaged()){
			g.drawImage(Assets.Player_damage,
					(int) pos.xpos - width / 2, 
					(int) pos.ypos - height,
					width * scale,
					height* scale,
					null);
		}
		
		ps.render(g);
		
		g.drawString(playerActions.getAttackTime()+"", 200, 500);
		g.drawString(playerActions.hasCompleted()+"", 200, 550);
		g.drawString(playerActions.attacked()+"", 200, 600);
		
			g.drawRect((int)pos.xpos - renderDistanceW*32 / 2 + width / 2, (int)pos.ypos - renderDistanceH*32 / 2 + height / 2, renderDistanceW*32, renderDistanceH*32);
			
			hudm.render(g);
			guim.render(g);

			
			
			playerMM.render(g);
			
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_E){
			inv.toggle();
		}
		
		if(key == KeyEvent.VK_W){
			if(!moving){
				moving = true;
			}
			up = true;
		}
		if(key == KeyEvent.VK_S){
			if(!moving){
				moving = true;
			}
			down = true;
		}
		if(key == KeyEvent.VK_A){
			if(!moving){
				moving = true;
			}
			left = true;
		}
		if(key == KeyEvent.VK_D){
			if(!moving){
				moving = true;
			}
			right = true;
		}
		if(key == KeyEvent.VK_SHIFT){
			if(!moving){
				moving = true;
			}
			running = true;
		}
		if(key == KeyEvent.VK_ESCAPE){
			System.exit(1);
		}
		if(key == KeyEvent.VK_F3){
			if(!debug){
				debug = true;
			}else{
				debug = false;
			}
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W){
			up = false;
		}
		if(key == KeyEvent.VK_S){
			down = false;
		}
		if(key == KeyEvent.VK_A){
			left = false;
		}
		if(key == KeyEvent.VK_D){
			right = false;
		}	
		
//		if(key == KeyEvent.VK_P){
//			DungeonLevelLoader.world.changeToWorld("world", "map2");
//			world.changeToWorld("world", "map2");
//		}
		
		
		if(key == KeyEvent.VK_SHIFT){
			running = false;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	//
	//
	//
	
	public Vector2F getPos() {
		return pos;
	}
	
	public float getMaxSpeed() {
		return maxSpeed;
	}
	
	public float getSlowdown() {
		return slowdown;
	}

	public boolean hasSpawned() {
		return spawned;
	}
	
	public boolean isDebuging() {
		return debug;
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	public PlayerActions getPlayerActions() {
		return playerActions;
	}
	
	public boolean isDamaged() {
		return damaged;
	}

	public void setDamaged(boolean damaged) {
		Player.damaged = damaged;
	}

	public static class PlayerActions{

		private World world;
		private BufferedImage attack_state;
		private static boolean hasCompleted = true;
		private static boolean attack = false;
		private double attackTime = 1;
		
		
		public PlayerActions(World world) {
			this.world = world;
		}
		
		public void tick(){
			if(!hasCompleted){
				if(attack){
					if(attack_state != null){
						startAttack();
					}
				}
			}
		}
		
		private void startAttack() {
			
			if (attackTime != 0){
				attackTime-= 0.1;
			}
			if (attackTime <= 0){
				attack = false;
				hasCompleted = true;
				attack_state = null;
				attackTime = 1;
			}
		}

		public void attackUP(){
			attack_state = Assets.getUp_attack();
			attack = true;
			hasCompleted = false;
		}
		
		public void attackDOWN(){
			attack_state = Assets.getDown_attack();
			attack = true;
			hasCompleted = false;
		}
		
		public void attackRIGHT(){
			attack_state = Assets.getRight_attack();
			attack = true;
			hasCompleted = false;
		}
		
		public void attackLEFT(){
			attack_state = Assets.getLeft_attack();
			attack = true;
			hasCompleted = false;
		}
		
		public void run(){
			
		}



		public boolean hasCompleted() {
			return hasCompleted;
		}
		
		public BufferedImage getAttack_state() {
			return attack_state;
		}
		
		public boolean attacked(){
			return attack;
		}
		
		public double getAttackTime() {
			return attackTime;
		}
	}

}
