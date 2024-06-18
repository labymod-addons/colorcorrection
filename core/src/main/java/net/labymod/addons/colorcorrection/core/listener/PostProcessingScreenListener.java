/*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.labymod.addons.colorcorrection.core.listener;

import net.labymod.addons.colorcorrection.core.ColorCorrection;
import net.labymod.addons.colorcorrection.core.ColorCorrectionConfiguration;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gfx.pipeline.post.PostProcessor;
import net.labymod.api.client.gfx.pipeline.post.PostProcessorLoader;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.post.PostProcessingScreenEvent;
import net.labymod.api.util.Lazy;

public class PostProcessingScreenListener {

  public static final ResourceLocation COLOR_CORRECTION = ResourceLocation.create(
      "colorcorrection",
      "shaders/post/colorcorrection.json"
  );

  private final ColorCorrection colorCorrection;
  private final Minecraft minecraft;

  private final Lazy<PostProcessor> processor;

  public PostProcessingScreenListener(ColorCorrection colorCorrection, Minecraft minecraft) {
    this.colorCorrection = colorCorrection;
    this.minecraft = minecraft;

    this.processor = Lazy.of(() -> {
      PostProcessor processor = PostProcessorLoader.load(
          this.minecraft.mainTarget(),
          COLOR_CORRECTION
      );
      processor.setCustomPostPassProcessor((name, program, time) -> {
        ColorCorrectionConfiguration configuration = this.colorCorrection.configuration();
        float red = configuration.red().get();
        float green = configuration.green().get();
        float blue = configuration.blue().get();
        program.setVec3("RGBModifiers", red, green, blue);

        float hue = configuration.hue().get();
        float saturation = configuration.saturation().get();
        float lightness = configuration.lightness().get();
        program.setVec3("HSLModifiers", hue, saturation, lightness);
      });
      return processor;
    });
  }

  @Subscribe
  public void onPostProcessingScreen(PostProcessingScreenEvent event) {
    ColorCorrectionConfiguration configuration = this.colorCorrection.configuration();
    if (!configuration.enabled().get()) {
      return;
    }

    this.processor.get().process(event.partialTicks());
  }
}
