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

import net.labymod.addons.colorcorrection.core.listener.PostProcessingScreenListener;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class ColorCorrection extends LabyAddon<ColorCorrectionConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();

    this.registerListener(new PostProcessingScreenListener(this, this.labyAPI().minecraft()));
  }

  @Override
  protected Class<ColorCorrectionConfiguration> configurationClass() {
    return ColorCorrectionConfiguration.class;
  }
}
