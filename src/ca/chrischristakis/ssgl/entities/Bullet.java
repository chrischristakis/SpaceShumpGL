package ca.chrischristakis.ssgl.entities;

import org.joml.Vector3f;
import ca.chrischristakis.ssgl.scene.Scene;

public class Bullet extends Entity
{

	public float velY = 0.5f;
	public boolean active = false;
	public boolean isDisplay = false;
	public Vector3f color = new Vector3f(1.0f, 1.0f, 1.0f);
	
	public Bullet(int x, int y, int width, int height) 
	{
		super(x, y, width, height);
	}

	@Override
	public void update() 
	{
		position.y += velY;
		
		model.identity();
		model.translate((int) position.x, (int) position.y, 0.0f);
	}

	@Override
	public void render() 
	{
		calculateMvp(Scene.shader);
		Scene.shader.setVec3("col_in", color);
		vao.draw();
	}

}
