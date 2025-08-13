package net.labymod.addons.colorcorrection.core.shader;

import java.util.List;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.layout.DataType;
import net.labymod.laby3d.api.buffers.layout.LayoutDefinition;
import net.labymod.laby3d.api.shaders.UniformType;
import net.labymod.laby3d.api.shaders.block.AbstractUniformBlock;
import net.labymod.laby3d.api.shaders.block.property.UniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Vector3fUniformProperty;
import org.joml.Vector3f;

public class ColorDataUniformBlock extends AbstractUniformBlock {

  public static final String NAME = "ColorData";

  private static final String RGB_NAME = "RGB";
  private static final String HSL_NAME = "HSL";

  private final UniformProperty<Vector3f> rgb;
  private final UniformProperty<Vector3f> hsl;

  public static final LayoutDefinition LAYOUT = LayoutDefinition.std140()
      .add(RGB_NAME, DataType.VEC3, UniformType.VEC3)
      .add(HSL_NAME, DataType.VEC3, UniformType.VEC3)
      .build();

  public ColorDataUniformBlock(RenderDevice device) {
    super(device);

    this.rgb = this.createProperty(RGB_NAME, Vector3fUniformProperty::new);
    this.hsl = this.createProperty(HSL_NAME, Vector3fUniformProperty::new);
  }

  @Override
  protected List<UniformProperty<?>> buildProperties() {
    return List.of(
        this.rgb,
        this.hsl
    );
  }

  @Override
  public String name() {
    return NAME;
  }

  @Override
  public LayoutDefinition layout() {
    return LAYOUT;
  }

  public UniformProperty<Vector3f> rgb() {
    return this.rgb;
  }

  public UniformProperty<Vector3f> hsl() {
    return this.hsl;
  }
}
