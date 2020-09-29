package ca.chrischristakis.ssgl;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import ca.chrischristakis.ssgl.input.KeyInput;
import ca.chrischristakis.ssgl.scene.Scene;

public class Main implements Runnable
{
	
	/*
	 * TODO:
	 * - Shaders x
	 * - VAO x 
	 * - MVP matrices x
	 * - KeyInput x
	 * - Texture x
	 * - Entity support x
	 * - Player x
	 * - Shooting x
	 * - Enemies x
	 * - Stars in the background x
	 * - Entity Handler x
	 * - Text x
	 * - Health x
	 * - Score x
	 */
	
	public static boolean running;
	public static int WIDTH = 700, HEIGHT= 900;
	
	private Thread thread;
	private long window;
	
	private Scene scene;
	
	public int frames, updates;
	
	public void start()
	{
		thread = new Thread(this);
		running = true;
		thread.start();
	}
	
	public void init()
	{
		GLFWErrorCallback.createPrint(System.err).set();
		
		if(!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		
		window = glfwCreateWindow(WIDTH, HEIGHT, "SpaceShumpGL", 0, 0);
		if(window == 0)
			throw new RuntimeException("GLFW was unable to initialize a window.");
		
		GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidMode.width() - WIDTH)/2, (vidMode.height()-HEIGHT)/2);
		glfwSetWindowSizeLimits(window, 500, 400, GLFW_DONT_CARE, GLFW_DONT_CARE);
		
		glfwSetKeyCallback(window, new KeyInput());
		
		glfwSetWindowSizeCallback(window,  new GLFWWindowSizeCallback()
	    {
	        @Override
	        public void invoke(long window, int width, int height)
	        {
	        	GL15.glViewport(0, 0, width, height);
	        	WIDTH = width;
	            HEIGHT = height;
	            update();
	            render();
	            frames = 0;
	            updates = 0;
	            Scene.updateProjection();
	        }
	    });

		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		GL30.glClearColor(0.0f, 0.0f, 0.1f, 1.0f);
		GL15.glViewport(0, 0, WIDTH, HEIGHT);
		glfwSwapInterval(0);
		
		scene = new Scene();
		
		glfwShowWindow(window);
	}
	
	@Override
	public void run()
	{
		init();
		
		float tps = 60.0f;
		double interval = 1e9/tps;
		double delta = 0;
		long last = System.nanoTime();
		long now;
		
		frames = 0;
		updates = 0;
		long timer = System.currentTimeMillis();
		
		//GL30.glPolygonMode(GL30.GL_FRONT_AND_BACK, GL30.GL_LINE);
		while(!glfwWindowShouldClose(window) && running)
		{
			now = System.nanoTime();
			delta += (now - last)/interval;
			last = now;
			
			if(delta >= 1)
			{
				delta--;
				update();
				updates++;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer >= 1000)
			{
				glfwSetWindowTitle(window, "FPS: " + frames + " | UPS: " + updates);
				timer = System.currentTimeMillis();
				frames = 0;
				updates = 0;
			}
		}
		
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	public void update()
	{
		glfwPollEvents();
		scene.update();
	}
	
	public void render()
	{
		GL30.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		scene.render();
		
		glfwSwapBuffers(window);
	}
	
	public static void main(String[] args) 
	{
		new Main().start();
	}

}
