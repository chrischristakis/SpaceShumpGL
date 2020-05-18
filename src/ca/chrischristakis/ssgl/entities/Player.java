package ca.chrischristakis.ssgl.entities;

import org.lwjgl.glfw.GLFW;

import ca.chrischristakis.ssgl.Main;
import ca.chrischristakis.ssgl.font.Text;
import ca.chrischristakis.ssgl.input.KeyInput;
import ca.chrischristakis.ssgl.scene.Scene;
import ca.chrischristakis.ssgl.utils.TextureUtils;

public class Player extends Entity
{
	
	private Bullet[] bullets = new Bullet[3];
	private Bullet[] bulletCount = new Bullet[bullets.length];
	private boolean spacePressed;
	public int score = 0;
	private Text healthText, gameOverText;
	public  boolean invincible = false;
	private float invDuration;
	private long invStarted;
	private boolean gameOver;
	
	private int activeShots = 0;
	
	public Player(int x, int y, int width, int height)
	{
		super(x,y,width,height, TextureUtils.player);	
		for(int i = 0; i < bullets.length; i++)
		{
			bullets[i] = new Bullet((int) position.x + width/2, (int) position.y + height, 7, 40);
			bullets[i].active = false;
			bullets[i].velY = 10;
		}
		
		for(int i = 0; i < bulletCount.length; i++)
		{
			bulletCount[i] = new Bullet((int) (width/bulletCount.length), (int) position.y - 10, 6, 6);
			bulletCount[i].active = true;
			bulletCount[i].isDisplay = true;
		}
		health = 100;
		healthText = new Text("Health: " + healthDisplay(), 0, Main.HEIGHT*2-300, Scene.font, 0.55f);
		gameOverText = new Text("GAME OVER!!!", 60, 350, Scene.font, 1.2f);
	}

	@Override
	public void update() 
	{
		if(gameOver) return;
		
		model.identity();
		if(KeyInput.isPressed(GLFW.GLFW_KEY_W))
			position.y += 7f;
		if(KeyInput.isPressed(GLFW.GLFW_KEY_S))
			position.y -= 7f;
		if(KeyInput.isPressed(GLFW.GLFW_KEY_A))
			position.x -= 7f;
		if(KeyInput.isPressed(GLFW.GLFW_KEY_D))
			position.x += 7f;
		if(KeyInput.isPressed(GLFW.GLFW_KEY_SPACE) && !spacePressed)
		{
			spacePressed = true;
			for(int i = 0; i < bullets.length; i++)
				if(!bullets[i].active)
				{
					bullets[i].position.x = position.x + width/2;
					bullets[i].position.y = position.y + height;
					bullets[i].active = true;
				
					bulletCount[bulletCount.length - activeShots - 1].active = false;
					activeShots++;
					break;
				}
		}
		else if(!KeyInput.isPressed(GLFW.GLFW_KEY_SPACE))
			spacePressed = false;
		
		//Wall bounds
		if(position.x < 0) position.x = 0;
		if(position.x + width > Main.WIDTH) position.x = Main.WIDTH - width;
		if(position.y < 0) position.y = 0;
		if(position.y + height > Main.HEIGHT) position.y = Main.HEIGHT - height;
		
		//Bullet updates
		for(int i = 0; i < bullets.length; i++)
		{
			if(bullets[i].active)
			{
				bullets[i].update();
				if((bullets[i].position.y >= Main.HEIGHT || bullets[i].position.y < 0) && !bullets[i].isDisplay)
				{
					bullets[i].active = false;
					bulletCount[bulletCount.length - activeShots].active = true;
					activeShots--;
				}
			}
			bulletCount[i].position.x = position.x + width/bulletCount.length * i + (width/bulletCount.length - bulletCount[i].width)/2;
			bulletCount[i].position.y = position.y - 30;
			bulletCount[i].update();
		}
		
		if(invincible && System.currentTimeMillis() - invStarted > invDuration * 1000)
			invincible = false;
		
		model.translate((int) position.x, (int) position.y, 0.0f);
	}

	@Override
	public void render()
	{
		calculateMvp(Scene.texShader);
		Scene.texShader.setInt("invincible", invincible?1:0);
		tex.bind();
		vao.draw();
		Scene.texShader.setInt("invincible", 0);
		tex.unbind();
		
		for(int i = 0; i < bullets.length; i++)
		{
			if(bullets[i].active)
				bullets[i].render();
			if(bulletCount[i].active)
				bulletCount[i].render();
		}
		
		healthText.render();
		if(gameOver)
			gameOverText.render();
	}
	
	public boolean checkBulletCol(Entity e)
	{
		boolean result = false;
		for(int i = 0; i < bullets.length; i++)
			if(bullets[i].collidesWith(e) && bullets[i].active) 
			{ 
				result = true;
				bullets[i].active = false;
				bulletCount[bulletCount.length - activeShots].active = true;
				activeShots--;
				score = Math.min(score + 1, 999);
				Scene.score = new Text("Score: " + score, 0, Main.HEIGHT*2-500, Scene.font, 0.66f);
			}
		return result;
	}
	
	public void damage(int amt)
	{
		if(invincible) return;
		makeInvincible(0.5f);
		health -= amt;
		healthText = new Text("Health: " + healthDisplay(), 0, Main.HEIGHT*2-300, Scene.font, 0.55f);
		if(health <= 0)
		{
			health = 0;
			gameOver = true;
		}
	}
	
	public String healthDisplay()
	{
		String result = "";
		for(int i = 0; i < health / 10; i++)
			result += "*";
		return result;
	}
	
	public void makeInvincible(float time)
	{
		invincible = true;
		invDuration = time;
		invStarted = System.currentTimeMillis();
	}

}
