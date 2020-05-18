package ca.chrischristakis.ssgl.scene;

import java.util.Random;

import org.joml.Matrix4f;

import ca.chrischristakis.ssgl.Main;
import ca.chrischristakis.ssgl.entities.Enemy1;
import ca.chrischristakis.ssgl.entities.Enemy2;
import ca.chrischristakis.ssgl.entities.EntityManager;
import ca.chrischristakis.ssgl.entities.Player;
import ca.chrischristakis.ssgl.entities.StarManager;
import ca.chrischristakis.ssgl.font.Font;
import ca.chrischristakis.ssgl.font.Text;
import ca.chrischristakis.ssgl.ogl.ShaderProgram;
import ca.chrischristakis.ssgl.utils.TextureUtils;

public class Scene 
{
	
	private Random rand = new Random();
	
	public static ShaderProgram texShader, shader, fontShader;
	public static Matrix4f view, projection;
	
	private float enemyInterval = 2f;
	private long lastEI = System.currentTimeMillis();
	private long lastIntervalInc;
	private float wallEnemySpawnChance = 10;
	
	private StarManager sm;
	private EntityManager em;
	private Player p;
	public static Font font;
	public static Text score;
	
	public Scene()
	{
		TextureUtils.init();
		view = new Matrix4f();
		projection = new Matrix4f().ortho(0.0f, Main.WIDTH, 0.0f, Main.HEIGHT, -0.1f, 1.0f);
		texShader = new ShaderProgram("texShader.vert", "texShader.frag");
		shader = new ShaderProgram("shader.vert", "shader.frag");
		fontShader = new ShaderProgram("fontShader.vert", "fontShader.frag");
		
		font = new Font("spaceFont");
	
		p = new Player(100, 100, 100, 100);
		sm = new StarManager(30);
		em = new EntityManager(p);
		score = new Text("Score: " + p.score, 0, Main.HEIGHT*2-500, font, 0.66f);
		
		lastIntervalInc = System.currentTimeMillis();
	}
	
	public void update()
	{
		if(System.currentTimeMillis() - lastIntervalInc > 8000)
		{
			enemyInterval = Math.max(0.45f, enemyInterval - 0.5f);
			if(wallEnemySpawnChance < 40) wallEnemySpawnChance+=5;	
			lastIntervalInc = System.currentTimeMillis();
		}
		
		if(System.currentTimeMillis() - lastEI > enemyInterval * 1000)
		{
			if(rand.nextInt(100) < 100 - wallEnemySpawnChance)
				em.add(new Enemy1(rand.nextInt(Main.WIDTH - 60), Main.HEIGHT, 60, 60));
			else
				em.add(new Enemy2(rand.nextInt(Main.WIDTH - 60), Main.HEIGHT, 60, 60));
			lastEI = System.currentTimeMillis();
		}
		
		sm.update();
		em.update();
	}
	
	public void render()
	{
		sm.render();
		em.render();
		score.render();
	}

	public static void updateProjection()
	{
		projection.identity();
		projection.ortho(0.0f, Main.WIDTH, 0.0f, Main.HEIGHT, -0.1f, 1.0f);
	}
	
}
