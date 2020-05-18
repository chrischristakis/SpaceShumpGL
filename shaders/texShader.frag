#version 330 core

in vec2 texCoords;

uniform sampler2D tex;
uniform bool invincible;

out vec4 fragCol;

void main()
{
	if(!invincible)
		fragCol = texture(tex, texCoords);
	else
		fragCol = texture(tex, texCoords) * vec4(1.0, 0.0, 0.0, 1.0);
	if(fragCol.w < 1.0)
		discard;
}
