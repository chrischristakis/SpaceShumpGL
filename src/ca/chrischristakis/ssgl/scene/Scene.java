package ca.chrischristakis.ssgl.scene;

import org.joml.Matrix4f;

import ca.chrischristakis.ssgl.Main;
import ca.chrischristakis.ssgl.entities.Enemy1;
import ca.chrischristakis.ssgl.entities.Player;
import ca.chrischristakis.ssgl.entities.StarManager;
import ca.chrischristakis.ssgl.ogl.ShaderProgram;
import ca.chrischristakis.ssgl.utils.TextureUtils;

public class Scene 
{
	
	public static ShaderProgram texShader, shader;
	public static Matrix4f view, projection;
	
	private StarManager sm;
	private Player p;
	private Enemy1 e;
	
	public Scene()
	{
		TextureUtils.init();
		view = new Matrix4f();
		projection = new Matrix4f().ortho(0.0f, Main.WIDTH, 0.0f, Main.HEIGHT, -0.1f, 1.0f);
		texShader = new ShaderProgram("texShader.vert", "texShader.frag");
		shader = new ShaderProgram("shader.vert", "shader.frag");
		
		p = new Player(100, 100, 100, 100);
		e = new Enemy1(400, Main.HEIGHT, 40, 60);
		sm = new StarManager(30);
	}
	
	public void update()
	{
		sm.update();
		e.update();
		p.update();
	}
	
	public void render()
	{
		sm.render();
		e.render();
		p.render();
	}

}
