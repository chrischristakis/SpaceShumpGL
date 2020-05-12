#version 330 core

in vec2 texCoords;

uniform sampler2D tex;

out vec4 fragCol;

void main()
{
	fragCol = texture(tex, texCoords);
	if(fragCol.w < 1.0)
		discard;
}
