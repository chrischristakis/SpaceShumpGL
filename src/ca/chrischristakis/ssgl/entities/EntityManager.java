package ca.chrischristakis.ssgl.entities;

import java.util.ArrayList;

public class EntityManager 
{
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Entity> toRemove = new ArrayList<Entity>();
	private Player player;
	
	public EntityManager(Player player)
	{
		this.player = player;
	}
	
	public void update()
	{
		player.update();
		for(Entity e : entities)
		{
			if(!e.isDead)
				e.update();
			else
				toRemove.add(e);
			if(player.checkBulletCol(e))
				e.health -= 10;
			
			if(e.position.y + e.height < 0)
			{
				e.isDead = true;
				if(e instanceof Enemy1) //not enemy2
					player.health -= 10;
			}
			
			if(e.collidesWith(player) && !player.invincible)
			{
				player.damage(10);
				e.isDead = true;
			}
		}
		
		if(toRemove.size() > 0)
			entities.removeAll(toRemove);
	}
	
	public void render()
	{
		for(Entity e : entities)
			e.render();	
		player.render();
	}
	
	public void add(Entity e)
	{
		entities.add(e);
	}
	
	public void clear()
	{
		entities.clear();
	}
}