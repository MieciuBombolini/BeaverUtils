package dev.bebomny.beaver.beaverutils.configuration.gui.menus;

import dev.bebomny.beaver.beaverutils.client.BeaverUtilsClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public abstract class OptionsMenu extends Screen {


    protected Screen parent;
    protected final BeaverUtilsClient beaverUtilsClient;

    final int BUTTON_HEIGHT = 20;
    final int SPACING = 4;

    protected OptionsMenu(Text title) {
        super(title);
        this.parent = null;
        this.beaverUtilsClient = BeaverUtilsClient.getInstance();
    }

    public void setParent(Screen parent) {
        this.parent = parent;
    }

    @Override
    public void close() {
        if (client != null)
            client.setScreen(parent);
        else
            super.close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        //Render the dimmed background
        this.renderBackground(context);

        //Maybe add some flowers for this menu here, you know autoPLANT so maybe some fun looking crops?

        //Add tittle
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 16777215);

        super.render(context, mouseX, mouseY, delta);
    }
}
