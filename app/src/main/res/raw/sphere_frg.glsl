#version 300 es

precision mediump float;

in vec4 vAmbient;
in vec4 vDiffuse;
in vec4 vSpecular;

out vec4 fragColor;

void main(){
    vec4 color = vec4(0.0f , 0.0f , 1.0f , 1.0f);
    fragColor = vAmbient * color + vDiffuse * color + vSpecular * color;
}
