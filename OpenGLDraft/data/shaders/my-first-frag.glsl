#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D texture0;

varying vec4 color;
varying vec2 texCoord0;
void main() {			
	vec4 texcolor = texture2D(texture0,texCoord0);
	gl_FragColor = color*texcolor;
} 
