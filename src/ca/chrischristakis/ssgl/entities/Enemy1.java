package ca.chrischristakis.ssgl.entities;

import ca.chrischristakis.ssgl.scene.Scene;
import ca.chrischristakis.ssgl.utils.TextureUtils;

public class Enemy1 extends Entity
{

	float velY = -1f;
	
	public Enemy1(int x, int y, int width, int height) {
		super(x, y, width, height, TextureUtils.enemy1);
	}
	
	public void update()
	{
		position.y += velY;
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
