package net.labymod.addons.colorcorrection.core.listener;

import net.labymod.addons.colorcorrection.core.ColorCorrectionConfiguration;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget.State;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.popup.SimpleAdvancedPopup;
import net.labymod.api.client.gui.screen.widget.widgets.popup.SimpleAdvancedPopup.SimplePopupButton;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.world.WorldEnterEvent;
import org.jetbrains.annotations.NotNull;

public class BlackscreenListener {
  private final static float WARNING_THRESHOLD = 0.5F;
  private final static long HIDING_DAYS = 7;

  private final ColorCorrectionConfiguration config;

  public BlackscreenListener(@NotNull ColorCorrectionConfiguration config) {
    this.config = config;
  }

  @Subscribe
  public void onWorldEnter(WorldEnterEvent event) {
    if (!this.config.enabled().get()) {
      return;
    }

    float brightness = this.config.lightness().get();
    if (
        this.config.red().get() * brightness >= WARNING_THRESHOLD ||
            this.config.green().get() * brightness >= WARNING_THRESHOLD ||
            this.config.blue().get() * brightness >= WARNING_THRESHOLD
    ) {
      return; // No need to warn the user when at least one color is high enough.
    }

    if (this.config.hideBlackScreenWarningTill().get() > System.currentTimeMillis()) {
      return; //User disabled the warning
    }

    this.openPopup();
  }

  public void openPopup() {
    FlexibleContentWidget container = new HideMessageCheckboxContainer();
    CheckBoxWidget checkBox = new CheckBoxWidget();
    container.addContent(checkBox);
    container.addFlexibleContent(ComponentWidget.component(Component.translatable("colorcorrection.blackscreen.popup.hideNextTime",
        NamedTextColor.GRAY).argument(Component.text(HIDING_DAYS))));

    SimpleAdvancedPopup.builder()
        .title(Component.translatable("colorcorrection.blackscreen.popup.title"))
        .description(Component.translatable("colorcorrection.blackscreen.popup.description"))
        .widgets(simpleListWidget -> simpleListWidget.addChild(container))
        .addButton(SimplePopupButton.create(
            Component.translatable("colorcorrection.blackscreen.popup.button.reset"),
            simplePopupButton -> this.config.reset()
        )).addButton(SimplePopupButton.create(
            "cancel",
            Component.translatable("colorcorrection.blackscreen.popup.button.ignore"),
        simplePopupButton -> {
              if (checkBox.state() == State.CHECKED) {
                this.config.hideBlackScreenWarningTill().set(System.currentTimeMillis() + HIDING_DAYS * 24 * 60 * 60 * 1000);
              }
        })).build().displayAsActivity();
  }

  @AutoWidget
  @Link("colorcorrection.lss")
  public static class HideMessageCheckboxContainer extends FlexibleContentWidget {}
}
