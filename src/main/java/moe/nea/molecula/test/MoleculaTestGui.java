package moe.nea.molecula.test;

import moe.nea.molecula.Molecula;
import moe.nea.molecula.MoleculaGui;
import moe.nea.molecula.ScreenDefinition;
import moe.nea.molecula.layouts.ColumnLayout;
import moe.nea.molecula.layouts.ScreenLayout;
import moe.nea.molecula.widgets.Button;

public class MoleculaTestGui extends MoleculaGui {
    @Override
    protected ScreenDefinition createScreenDefinition() {
        return Molecula.screenDefinition(def -> {
            Molecula.column(def, layout -> {
                Molecula.button(layout, "test.button.text");
                Molecula.button(layout, "test.button.text2");
            });
        });
    }
}
