#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texCoords

uniform mat4 mvp = mat4(1.0);
uniform vec3 col_in = vec3(1.0, 1.0, 1.0);

out vec4 col;
out vec2 tc;

void main()
{
	gl_Position = mvp * vec4(position, 1.0);
	col = vec4(col_in, 1.0);
	tc = texCoords;
}
