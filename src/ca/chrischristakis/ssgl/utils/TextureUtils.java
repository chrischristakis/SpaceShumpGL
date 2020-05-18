package ca.chrischristakis.ssgl.utils;

import ca.chrischristakis.ssgl.ogl.Texture;

public class TextureUtils 
{
	public static Texture player, enemy1, enemy2, enemy3;
	
	private TextureUtils() {}
	
	public static void init()
	{
		player = new Texture("player.png");
		enemy1 = new Texture("enemy1.png");
		enemy2 = new Texture("enemy2.png");
		enemy3 = new Texture("enemy3.png");
	}

}
