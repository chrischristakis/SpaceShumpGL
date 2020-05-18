package ca.chrischristakis.ssgl.entities;

import ca.chrischristakis.ssgl.scene.Scene;
import ca.chrischristakis.ssgl.utils.TextureUtils;

public class Enemy2 extends Entity
{
	float velY = -3f;
	
	public Enemy2(int x, int y, int width, int height) {
		super(x, y, width, height, TextureUtils.enemy3);
		velY *= 0.5f;
		health = 30;
	}
	
	public void update()
	{
		position.y += velY;
		if(health <= 0) isDead = true;
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
