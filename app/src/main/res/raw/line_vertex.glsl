#version 300 es

layout(location = 0) in vec3 aPos;

uniform vec4 uColor;
uniform mat4 uMvpMatrix;

out vec4 vColor;

void main(){
    gl_Position = uMvpMatrix * vec4(aPos.xyz , 1.0f);
    vColor = uColor;
}