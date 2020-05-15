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
				e.isDead = true;
		}
		
		if(toRemove.size() > 0)
			entities.removeAll(toRemove);
	}
	
	public void render()
	{
		player.render();
		for(Entity e : entities)
			e.render();	
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