#version 330 core

uniform vec4 iColor = vec4(1,0,0.3,1);

out vec4 color;

void main(){
	color = iColor;
}