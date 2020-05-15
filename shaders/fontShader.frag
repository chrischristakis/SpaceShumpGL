#version 330 core

uniform sampler2D tex;

in vec4 col;
in vec2 tc;
out vec4 fragCol;

void main()
{
	fragCol = texture(tex, tc);
	if(fragCol.w < 1.0)
		discard;
}
