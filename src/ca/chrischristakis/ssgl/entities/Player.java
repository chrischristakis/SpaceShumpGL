package ca.chrischristakis.ssgl.entities;

import org.lwjgl.glfw.GLFW;

import ca.chrischristakis.ssgl.Main;
import ca.chrischristakis.ssgl.input.KeyInput;
import ca.chrischristakis.ssgl.scene.Scene;
import ca.chrischristakis.ssgl.utils.TextureUtils;

public class Player extends Entity
{
	
	private Bullet[] bullets = new Bullet[5];
	private boolean spacePressed;
	
	public Player(int x, int y, int width, int height)
	{
		super(x,y,width,height, TextureUtils.player);	
		for(int i = 0; i < bullets.length; i++)
		{
			bullets[i] = new Bullet((int) position.x + width/2, (int) position.y + height, 6, 40);
			bullets[i].active = false;
			bullets[i].velY = 10;
		}
	}

	@Override
	public void update() 
	{
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
					break;
				}
		}
		else if(!KeyInput.isPressed(GLFW.GLFW_KEY_SPACE))
			spacePressed = false;
		
		if(position.x < 0) position.x = 0;
		if(position.x + width > Main.WIDTH) position.x = Main.WIDTH - width;
		if(position.y < 0) position.y = 0;
		if(position.y + height > Main.HEIGHT) position.y = Main.HEIGHT - height;
		
		for(int i = 0; i < bullets.length; i++)
			if(bullets[i].active)
				bullets[i].update();
		
		model.translate((int) position.x, (int) position.y, 0.0f);
	}

	@Override
	public void render()
	{
		calculateMvp(Scene.texShader);
		tex.bind();
		vao.draw();
		tex.unbind();
		
		for(int i = 0; i < bullets.length; i++)
			if(bullets[i].active)
				bullets[i].render();
	}

}
