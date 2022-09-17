package moe.nea.molecula;

import moe.nea.molecula.layouts.ColumnLayout;
import moe.nea.molecula.layouts.ScreenLayout;
import moe.nea.molecula.widgets.Button;

import java.util.function.Consumer;

public class Molecula {

    public static ScreenDefinition screenDefinition(Consumer<ScreenLayout> o) {
        ScreenLayout layout = new ScreenLayout();
        o.accept(layout);
        return new ScreenDefinition(layout);
    }

    public static ColumnLayout column(Layout layout, Consumer<ColumnLayout> o) {
        ColumnLayout l = new ColumnLayout();
        o.accept(l);
        layout.addChild(l);
        return l;
    }

    public static Button button(ColumnLayout layout, String label) {
        Button button = new Button(label);
        layout.addChild(button);
        return button;
    }
}
