package ca.chrischristakis.ssgl.entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import ca.chrischristakis.ssgl.ogl.ShaderProgram;
import ca.chrischristakis.ssgl.ogl.Texture;
import ca.chrischristakis.ssgl.ogl.VAO;
import ca.chrischristakis.ssgl.scene.Scene;

abstract public class Entity 
{

	public int width, height;
	public Vector3f position;
	public Matrix4f model;
	
	protected VAO vao;
	protected Texture tex;
	
	public Entity(int x, int y, int width, int height)
	{
		this.width = width; this.height = height;
		position = new Vector3f(x, y, 0.0f);
		model = new Matrix4f();
		model.translate(position);
		
		float[] verts = {
			0.0f, 0.0f, 0.0f,
			width, 0.0f, 0.0f,
			width, height, 0.0f,
			0.0f, height, 0.0f
		};
		
		byte[] inds = {
			0,1,2,
			2,3,0
		};
 		
		vao = new VAO(verts, inds);
	}
	
	public Entity(int x, int y, int width, int height, Texture tex)
	{
		this(x,y,width,height);
		this.tex = tex;
		float[] tc = {
			0.0f, 0.0f,
			1.0f, 0.0f,
			1.0f, 1.0f,
			0.0f, 1.0f
		};
		vao.attachTexture(tc);
	}
	
	public void calculateMvp(ShaderProgram shader)
	{
		shader.bind();
		Matrix4f result = new Matrix4f();
		result.identity();
		result.mul(Scene.projection).mul(Scene.view).mul(model);
		shader.setMat4f("mvp", result);
	}
	
	public boolean collidesWith(Entity other)
	{
		return (position.x < other.position.x + other.width && other.position.x < position.x + width
				&& position.y + height > other.position.y && other.position.y + other.height > position.y);
	}
	
	abstract public void update();
	abstract public void render();
	
}
