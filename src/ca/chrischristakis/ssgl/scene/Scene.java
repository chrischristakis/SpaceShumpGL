package ca.chrischristakis.ssgl.scene;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import ca.chrischristakis.ssgl.Main;
import ca.chrischristakis.ssgl.input.KeyInput;
import ca.chrischristakis.ssgl.ogl.ShaderProgram;
import ca.chrischristakis.ssgl.ogl.VAO;

public class Scene 
{
	
	private VAO vao;
	private ShaderProgram shader;
	
	private Matrix4f model, view, projection;
	private Vector3f position = new Vector3f();
	
	public Scene()
	{
		float[] verts = {
				-10.0f, -10.0f, 0.0f,
				10.0f, -10.0f, 0.0f,
				10.0f, 10.f, 0.0f,
				-10.0f, 10.0f, 0.0f
		};
		
		byte[] inds = {
				0,1,2,
				2,3,0
		};
		vao = new VAO(verts, inds);
		
		model = new Matrix4f().translate(new Vector3f(100.0f, 100.0f, 0.0f));
		view = new Matrix4f();
		projection = new Matrix4f().ortho(0.0f, Main.WIDTH, 0.0f, Main.HEIGHT, -0.1f, 1.0f);
		
		shader = new ShaderProgram("shader.vert", "shader.frag");
		shader.bind();
	}
	
	public void update()
	{
		model.identity();
		if(KeyInput.isPressed(GLFW.GLFW_KEY_W))
			position.y += 5f;
		if(KeyInput.isPressed(GLFW.GLFW_KEY_S))
			position.y -= 5f;
		if(KeyInput.isPressed(GLFW.GLFW_KEY_A))
			position.x -= 5f;
		if(KeyInput.isPressed(GLFW.GLFW_KEY_D))
			position.x += 5f;
		
		model.translate(position);
	}
	
	Matrix4f result = new Matrix4f();
	public void render()
	{
		result.identity();
		result.mul(projection).mul(view).mul(model);
		shader.setMat4f("mvp", result);
		vao.draw();
	}

}
