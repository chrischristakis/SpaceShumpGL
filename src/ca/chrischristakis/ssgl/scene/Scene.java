package ca.chrischristakis.ssgl.scene;

import java.util.Random;

import org.joml.Matrix4f;

import ca.chrischristakis.ssgl.Main;
import ca.chrischristakis.ssgl.entities.Enemy1;
import ca.chrischristakis.ssgl.entities.EntityManager;
import ca.chrischristakis.ssgl.entities.Player;
import ca.chrischristakis.ssgl.entities.StarManager;
import ca.chrischristakis.ssgl.font.Font;
import ca.chrischristakis.ssgl.ogl.ShaderProgram;
import ca.chrischristakis.ssgl.utils.TextureUtils;

public class Scene 
{
	
	private Random rand = new Random();
	
	public static ShaderProgram texShader, shader;
	public static Matrix4f view, projection;
	
	private float enemyInterval = 0.6f;
	private long lastEI = System.currentTimeMillis();
	
	private StarManager sm;
	private EntityManager em;
	private Player p;
	
	public Scene()
	{
		TextureUtils.init();
		view = new Matrix4f();
		projection = new Matrix4f().ortho(0.0f, Main.WIDTH, 0.0f, Main.HEIGHT, -0.1f, 1.0f);
		texShader = new ShaderProgram("texShader.vert", "texShader.frag");
		shader = new ShaderProgram("shader.vert", "shader.frag");
		
		Font font = new Font("spaceFont.fnt");
		
		p = new Player(100, 100, 100, 100);
		sm = new StarManager(30);
		em = new EntityManager(p);
	}
	
	public void update()
	{
		if(System.currentTimeMillis() - lastEI > enemyInterval * 1000)
		{
			em.add(new Enemy1(rand.nextInt(Main.WIDTH - 60), Main.HEIGHT, 60, 60));
			lastEI = System.currentTimeMillis();
		}
		
		sm.update();
		em.update();
	}
	
	public void render()
	{
		sm.render();
		em.render();
	}

	public static void updateProjection()
	{
		projection.identity();
		projection.ortho(0.0f, Main.WIDTH, 0.0f, Main.HEIGHT, -0.1f, 1.0f);
	}
	
}
