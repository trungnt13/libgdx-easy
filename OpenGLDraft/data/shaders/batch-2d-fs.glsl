#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_Texture;

varying vec4 color0;
varying vec2 texcoord0;
void main(){
	vec4 texture_color = texture2D(u_Texture,texcoord0);
	gl_FragColor = color0*texture_color;
}
