
const mat4 combined = mat4(0.004,0.0,0.0,0.0,0.0,0.0025,0.0,0.0,0.0,0.0,-2.0,0.0,-1.0,-1.0,-1.0,1.0);

attribute vec2 a_Position;
attribute vec4 a_Color;
attribute vec2 a_Texcoord;

varying vec4 color0;
varying vec2 texcoord0;

void main(){
	gl_Position = combined*vec4(a_Position,0.0,1.0);
	color0 = a_Color;
	texcoord0 = a_Texcoord;
}
