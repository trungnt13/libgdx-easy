//this variable send from your application down to GPU
//this is also a constant between shader state
// pass by call glUniform*()
uniform mat4 projection_matrix;
uniform mat4 modelview_matrix;
uniform mat3 normal_matrix;
uniform vec4 light_position;

// this is global variable
// pass on vertex shader by call glVertexAttribPointer()
attribute vec3 a_Vertex;
attribute vec3 a_Color;
attribute vec2 a_TexCoord0;

//add the lighting
attribute vec3 a_Normal;

//out attribute to fragment shader
varying vec4 color;
varying vec2 texCoord0;

void main(){
	// pass the tex coord
	texCoord0 = a_TexCoord0;
	// multi modelveiw and project matrix
	vec4 pos = modelview_matrix * vec4(a_Vertex,1.0);
	gl_Position = projection_matrix*pos;
	// pass the color
	color = vec4(a_Color,1.0);
	
	//calculate lighting
	vec3 N = normalize(normal_matrix*a_Normal);
	vec3 L = normalize(modelview_matrix*light_position).xyz;
	
	float NdotL = max(dot(N,L),0.0);
	color = color*vec4(NdotL);
}
