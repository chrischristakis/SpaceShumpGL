package ca.chrischristakis.ssgl.entities;

import java.util.Random;

import ca.chrischristakis.ssgl.scene.Scene;
import ca.chrischristakis.ssgl.utils.TextureUtils;

public class Enemy1 extends Entity
{

	private static Random rand = new Random();
	float velY = -3f;
	
	public Enemy1(int x, int y, int width, int height) {
		super(x, y, width, height, (rand.nextBoolean()? TextureUtils.enemy1 : TextureUtils.enemy2));
	}
	
	public void update()
	{
		position.y += velY;
		if(position.y + height < 0) isDead = true;
		model.identity();
		model.translate((int) position.x, (int) position.y, 0.0f);
	}
	
	public void render()
	{
		calculateMvp(Scene.texShader);
		tex.bind();
		vao.draw();
		tex.unbind();
	}

}
