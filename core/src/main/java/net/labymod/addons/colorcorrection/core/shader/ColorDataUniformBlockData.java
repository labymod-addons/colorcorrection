package net.labymod.addons.colorcorrection.core.shader;

import net.labymod.laby3d.api.shaders.block.UniformBlockData;
import org.joml.Vector3f;

public class ColorDataUniformBlockData implements UniformBlockData<ColorDataUniformBlock> {

  private final Vector3f rgb;
  private final Vector3f hsl;

  public ColorDataUniformBlockData() {
    this.rgb = new Vector3f();
    this.hsl = new Vector3f();
  }

  @Override
  public void update(ColorDataUniformBlock block) {
    block.rgb().set(this.rgb);
    block.hsl().set(this.hsl);
  }

  public Vector3f rgb() {
    return this.rgb;
  }

  public Vector3f hsl() {
    return this.hsl;
  }
}
