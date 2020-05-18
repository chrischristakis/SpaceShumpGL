package ca.chrischristakis.ssgl.font;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import ca.chrischristakis.ssgl.ogl.VAO;
import ca.chrischristakis.ssgl.scene.Scene;

public class Text 
{
	
	private ArrayList<VAO> vaos = new ArrayList<VAO>();
	private Font font;
	
	public Vector3f position;
	private Matrix4f model = new Matrix4f();
	
	public float scale = 1.0f;
	
	public Text(String content, int x, int y, Font font, float scale)
	{
		this.scale = scale;
		model.scale(scale);
		this.font = font;
		position = new Vector3f(x, y, 0.0f);
		for(int i = 0; i < content.length(); i++)
		{
			int id = (int)content.charAt(i);
			createVaoFor(id);
			position.x += font.getLetter(id).xShift;
		}
	}
	
	public void createVaoFor(int id)
	{ 
		Letter letter = font.getLetter(id);
		float[] verts = {
			position.x + letter.xOffset, position.y + letter.yOffset, 0.0f, //bl 
			position.x + letter.width + letter.xOffset, position.y + letter.yOffset, 0.0f, //br
			position.x + letter.width + letter.xOffset, position.y + letter.height + letter.yOffset, 0.0f,//tr
			position.x + letter.xOffset, position.y + letter.height + letter.yOffset, 0.0f, //tl
		};
		float[] tc = {
			((float)letter.x/font.fntWidth), ((float)(font.fntHeight - letter.y - letter.height)/font.fntHeight),
			((float)(letter.x+letter.width)/font.fntWidth), ((float)(font.fntHeight - letter.y - letter.height)/font.fntHeight),
			((float)(letter.x + letter.width)/font.fntWidth), ((float)(font.fntHeight - letter.y)/font.fntHeight),	
			((float)(letter.x)/font.fntWidth), ((float)(font.fntHeight - letter.y)/font.fntHeight),
		};
		byte[] ind = {
			0, 1, 2, 2, 3, 0
		};
		vaos.add(new VAO(verts, ind, tc));
	}
	
	public Vector3f color = new Vector3f(1.0f, 1.0f, 1.0f);
	public Matrix4f result = new Matrix4f();
	public void render()
	{
		result.identity();
		result.mul(Scene.projection).mul(model);
		Scene.fontShader.bind();
		Scene.fontShader.setMat4f("mvp", result);
		Scene.fontShader.setVec3("col_in", color);
		font.texture.bind();
		for(int i = 0; i < vaos.size(); i++)
			vaos.get(i).draw();
		font.texture.unbind();
		Scene.fontShader.unbind();
	}

}
