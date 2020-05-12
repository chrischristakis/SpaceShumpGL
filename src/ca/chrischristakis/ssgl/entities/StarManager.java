package ca.chrischristakis.ssgl.entities;

import java.util.Random;

import ca.chrischristakis.ssgl.Main;

public class StarManager 
{
	Random rand = new Random();
	private Bullet[] stars;
	
	public StarManager(int amt)
	{
		stars = new Bullet[amt];
		for(int i = 0; i < amt; i++)
		{
			stars[i] = new Bullet(rand.nextInt(Main.WIDTH), rand.nextInt(Main.HEIGHT), 3, 20);
			stars[i].velY = -(rand.nextFloat() * 40.0f + 6.0f);
			stars[i].color.x = 0.4f; stars[i].color.y = 0.4f; stars[i].color.z = 0.4f;
		}
	}
	
	public void update()
	{
		for(int i = 0; i < stars.length; i++)
		{
			stars[i].update();
			if(stars[i].position.y - stars[i].height < 0)
			{
				stars[i].position.y = Main.HEIGHT;
				stars[i].position.x = rand.nextInt(Main.WIDTH);
			}
		}
	}
	
	public void render()
	{
		for(int i = 0; i < stars.length; i++)
			stars[i].render();
	}
	
}
