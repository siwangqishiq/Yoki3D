#version 300 es

layout(location = 0) in vec4 aColor;
layout(location = 1) in vec3 aPos;
layout(location = 2) in float pointSize;

uniform mat4 uMvpMatrix;
out vec4 vColor;

void main(){
    gl_Position = uMvpMatrix * vec4(aPos.xyz , 1.0f);
    gl_PointSize = pointSize;
    vColor = aColor;
}