package moe.nea.molecula.widgets;

import moe.nea.molecula.Molecule;

public class MPadding extends MLayout {
    public MPadding(int margin, Molecule molecule) {
        addChild(molecule);
        molecule.setLocation(margin, margin);
        setSize(molecule.getWidth() + margin * 2, molecule.getHeight() + margin * 2);
    }
}
