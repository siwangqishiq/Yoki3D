#version 300 es

layout(location = 0) in vec3 aPos;
uniform mat4 uMvpMatrix;

void main(){
    gl_Position = uMvpMatrix * vec4(aPos.xyz , 1.0f);
}