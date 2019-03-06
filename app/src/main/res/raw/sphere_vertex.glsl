#version 300 es

uniform mat4 uMvpMatrix;
uniform mat4 uModelMatrix;
uniform vec3 uLightPos;
uniform vec3 uCameraPos;

uniform float uAmbientLight;
uniform float uDiffuseLight;
uniform float uSepcularLight;

layout(location = 0) in vec3 aPos;
layout(location = 1) in vec3 aNormal;

out vec4 vAmbient;
out vec4 vDiffuse;
out vec4 vSpecular;

const float shininess = 0.0;//粗糙度

void pointLight(in vec3 normal,
                in vec4 ambient ,
                in vec4 diffuse ,
                in vec4 specular,
                inout vec4 outAmbient ,
                inout vec4 outDiffuse ,
                inout vec4 outSepcular){
    outAmbient = ambient;

    vec3 normalTarget = aPos + normal;
    vec3 newNormal = (uModelMatrix * vec4(normalTarget.xyz , 1.0f) - uModelMatrix * vec4(aPos.xyz , 1)).xyz;
    newNormal = normalize(newNormal);
    vec3 eye = normalize(uCameraPos - (uModelMatrix * vec4(aPos , 1.0f)).xyz);
    vec3 vp = normalize(uLightPos - (uModelMatrix * vec4(aPos , 1.0f)).xyz);
    vec3 halfVec = normalize(eye + vp);
    float nDotViewPos = max(0.0f , dot(newNormal , vp));
    outDiffuse = diffuse * nDotViewPos;

    float nDotViewHalfVec = dot(newNormal , halfVec);
    float powerFactor = max(0.0f , pow(nDotViewHalfVec , shininess));
    outSepcular = specular * powerFactor;
}

void main(){
    gl_Position = uMvpMatrix * vec4(aPos.xyz , 1.0f);

    vec4 ambientTmp;
    vec4 diffuseTmp;
    vec4 specularTmp;

    pointLight(aNormal,
                vec4(uAmbientLight,uAmbientLight,uAmbientLight,1.0f),
                vec4(uDiffuseLight,uDiffuseLight,uDiffuseLight,1.0f),
                vec4(uSepcularLight,uSepcularLight,uSepcularLight,1.0f),
                ambientTmp,
                diffuseTmp,
                specularTmp
    );
    vAmbient = ambientTmp;
    vDiffuse = diffuseTmp;
    vSpecular = specularTmp;
}