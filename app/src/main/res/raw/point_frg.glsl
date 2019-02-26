#version 300 es

precision mediump float;

in vec4 vColor;
out vec4 fragColor;

void main(){
    fragColor = vColor;
    //fragColor = vec4(1.0f , 1.0f , 0.0f , 1.0f);
}
