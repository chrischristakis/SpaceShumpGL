#version 330 core

layout(location = 0) in vec2 position;
layout(location = 1) in vec2 tc_in;

uniform mat4 mvp;

out vec2 texCoords;

void main()
{
	gl_Position = mvp * vec4(position, 0.0, 1.0);
	texCoords = tc_in;
}
