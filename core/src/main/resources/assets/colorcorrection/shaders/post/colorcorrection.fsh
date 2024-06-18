#version 150

uniform sampler2D DiffuseSampler;
uniform vec3 RGBModifiers;
uniform vec3 HSLModifiers;

in vec2 texCoord;

out vec4 fragColor;

float hueToRGB(float p, float q, float t) {
  if (t < 0.0) t += 1.0;
  if (t > 1.0) t -= 1.0;
  if (t < 1.0 / 6.0) return p + (q - p) * 6.0 * t;
  if (t < 1.0 / 2.0) return q;
  if (t < 2.0 / 3.0) return p + (q - p) * (2.0 / 3.0 - t) * 6.0;
  return p;
}

vec3 RGBtoHSL(vec3 color) {
  float min = min(min(color.r, color.g), color.b);
  float max = max(max(color.r, color.g), color.b);
  float delta = max - min;
  float h, s, l;

  if (max == min) {
    h = 0.0;
  } else if (max == color.r) {
    h = mod((color.g - color.b) / delta, 6.0);
  } else if (max == color.g) {
    h = (color.b - color.r) / delta + 2.0;
  } else {
    h = (color.r - color.g) / delta + 4.0;
  }

  h = h / 6.0;
  l = (max + min) / 2.0;

  if (max == min) {
    s = 0.0;
  } else if (l <= 0.5) {
    s = delta / (max + min);
  } else {
    s = delta / (2.0 - max - min);
  }

  return vec3(h, s, l);
}

vec3 HSLtoRGB(vec3 color) {
  float h = color.r;
  float s = color.g;
  float l = color.b;
  float r, g, b;

  if (s == 0.0) {
    r = l;
    g = l;
    b = l;
  } else {
    float q = l < 0.5 ? l * (1.0 + s) : l + s - l * s;
    float p = 2.0 * l - q;
    r = hueToRGB(p, q, h + 1.0 / 3.0);
    g = hueToRGB(p, q, h);
    b = hueToRGB(p, q, h - 1.0 / 3.0);
  }

  return vec3(r, g, b);
}

void main() {
  vec4 color = TEXTURE(DiffuseSampler, texCoord);
  vec3 hsl = RGBtoHSL(color.rgb * RGBModifiers);
  fragColor = vec4(HSLtoRGB(hsl * HSLModifiers), color.a);
}