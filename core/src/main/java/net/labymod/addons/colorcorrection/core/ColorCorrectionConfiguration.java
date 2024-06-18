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

package net.labymod.addons.colorcorrection.core;

import javax.inject.Singleton;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@Singleton
@SpriteTexture("settings/settings")
@ConfigName("settings")
public class ColorCorrectionConfiguration extends AddonConfig {

  @SpriteSlot(x = 6)
  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(false);

  @SpriteSlot(x = 0)
  @SliderSetting(min = 0.0F, max = 1.0F, steps = 0.05F)
  private final ConfigProperty<Float> red = new ConfigProperty<>(1.0F);

  @SpriteSlot(x = 1)
  @SliderSetting(min = 0.0F, max = 1.0F, steps = 0.05F)
  private final ConfigProperty<Float> green = new ConfigProperty<>(1.0F);

  @SpriteSlot(x = 2)
  @SliderSetting(min = 0.0F, max = 1.0F, steps = 0.05F)
  private final ConfigProperty<Float> blue = new ConfigProperty<>(1.0F);

  @SpriteSlot(x = 3)
  @SliderSetting(min = 0.0F, max = 1.0F, steps = 0.05F)
  private final ConfigProperty<Float> hue = new ConfigProperty<>(1.0F);

  @SpriteSlot(x = 4)
  @SliderSetting(min = 0.0F, max = 1.5F, steps = 0.05F)
  private final ConfigProperty<Float> saturation = new ConfigProperty<>(1.0F);

  @SpriteSlot(x = 5)
  @SliderSetting(min = 0.5F, max = 1.25F, steps = 0.05F)
  private final ConfigProperty<Float> lightness = new ConfigProperty<>(1.0F);

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ConfigProperty<Float> red() {
    return this.red;
  }

  public ConfigProperty<Float> green() {
    return this.green;
  }

  public ConfigProperty<Float> blue() {
    return this.blue;
  }

  public ConfigProperty<Float> hue() {
    return this.hue;
  }

  public ConfigProperty<Float> saturation() {
    return this.saturation;
  }

  public ConfigProperty<Float> lightness() {
    return this.lightness;
  }

}
