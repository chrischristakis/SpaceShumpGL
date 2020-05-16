package ca.chrischristakis.ssgl.font;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import ca.chrischristakis.ssgl.ogl.Texture;

public class Font 
{
	
	public Texture texture;
	private Letter[] letters = new Letter[256];
	
	private HashMap<String, String> elements = new HashMap<String, String>();
	private BufferedReader reader;
	private String line; //the BufferedReader line.
	
	public int fntWidth, fntHeight;
	
	/*
	 * Both the .fnt and .png files for the font must have the same name, for example spaceFont.fnt and spaceFont.png
	 */
	public Font(String name)
	{
		texture = new Texture(name + ".png");
		loadFont(name);
	}
	
	public void loadFont(String path)
	{
		try {
			reader = new BufferedReader(new FileReader(path + ".fnt"));

			//Skip straight into the information about characters
			
			nextLine();
			nextLine();
			
			fntWidth = getIntegerValue("scaleW");
			fntHeight = getIntegerValue("scaleH");
			
			nextLine();
			nextLine();
			
			//Character information begins.
			while(nextLine())
				if(!assignCharacter())
					break;
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private int getIntegerValue(String name)
	{
		return Integer.parseInt(elements.get(name));
	}
	
	public boolean assignCharacter()
	{
		if(!elements.containsKey("id")) return false; //since this means you are not reading a character.
		
		Letter c;
		int id = getIntegerValue("id");
		int x = getIntegerValue("x");
		int y = getIntegerValue("y");
		int width = getIntegerValue("width");
		int height = getIntegerValue("height");
		int xOffset = getIntegerValue("xoffset");
		int yOffset = getIntegerValue("yoffset");
		int xAdv = getIntegerValue("xadvance");
		
		c = new Letter(x,y,width,height,xOffset,yOffset,xAdv);
		
		letters[id] = c;
		return true;
	}
	
	private boolean nextLine() throws IOException
	{
		elements.clear();
		line = reader.readLine();
		if(line == null)
			return false;
		else
		{
			String[] splitter = line.split(" ");
			String[] values;
			for(int i = 0; i < splitter.length; i++)
				if(splitter[i].contains("="))
				{
					values = splitter[i].split("="); //a key, and a value. 2 elements tops.
					if(values.length == 2) //just to make sure :)
						elements.put(values[0], values[1]);
				}
			return true;
		}
	}
	
	public Letter getLetter(int id)
	{
		return letters[id];
	}
	
	public void bind()
	{
		texture.bind();
	}
	
	public void unbind()
	{
		texture.unbind();
	}

}
