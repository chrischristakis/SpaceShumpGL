package ca.chrischristakis.ssgl.ogl;

import org.lwjgl.opengl.GL30;

import ca.chrischristakis.ssgl.utils.BufferUtils;

public class VAO 
{
	
	private int vbo, vao, ebo;
	private int vertNum;
	
	public VAO(float[] verts, byte[] ind)
	{
		vertNum = ind.length;
		vbo = GL30.glGenBuffers();
		ebo = GL30.glGenBuffers();
		vao = GL30.glGenVertexArrays();
		
		GL30.glBindVertexArray(vao);
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo);
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(verts), GL30.GL_STATIC_DRAW);
		GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0);
		GL30.glEnableVertexAttribArray(0);
		
		GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, ebo);
		GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createByteBuffer(ind), GL30.GL_STATIC_DRAW);
		
		GL30.glBindVertexArray(0);
	}
	
	public void draw()
	{
		GL30.glBindVertexArray(vao);
		GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, ebo);
		GL30.glDrawElements(GL30.GL_TRIANGLES, vertNum, GL30.GL_UNSIGNED_BYTE, 0);
		GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}

}
