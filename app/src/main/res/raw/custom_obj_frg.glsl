#version 300 es

precision mediump float;

uniform sampler2D uTexture;

in vec2 vCoord;
in vec4 vAmbient;
in vec4 vDiffuse;
in vec4 vSpecular;

out vec4 fragColor;

void main(){
    //fragColor = vec4(1.0f , 1.0f , 0.0f , 1.0f);
    vec4 color = texture(uTexture , vCoord);
    fragColor = vAmbient * color + vDiffuse * color + vSpecular * color;
}
