package ca.chrischristakis.ssgl.utils;

import ca.chrischristakis.ssgl.ogl.Texture;

public class TextureUtils 
{
	public static Texture player, enemy1;
	
	private TextureUtils() {}
	
	public static void init()
	{
		player = new Texture("player.png");
		enemy1 = new Texture("enemy1.png");
	}

}
