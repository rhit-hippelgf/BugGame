package controllerStuff;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;

import Creatures.Player;

public class Controller {

    public Controller(JComponent listenOn, creatureStuff.Player player) {

        final int scope = JComponent.WHEN_IN_FOCUSED_WINDOW;     // fires even if sub-components steal focus
        InputMap  im = listenOn.getInputMap(scope);
        ActionMap am = listenOn.getActionMap();

        // ── W
        bind(im, am, "W",         true,  player::setUp);
        bind(im, am, "released W",false,  player::setUp);

        // ── A
        bind(im, am, "A",         true,  player::setLeft);
        bind(im, am, "released A",false,  player::setLeft);

        // ── S
        bind(im, am, "S",         true,  player::setDown);
        bind(im, am, "released S",false,  player::setDown);

        // ── D
        bind(im, am, "D",         true,  player::setRight);
        bind(im, am, "released D",false,  player::setRight);
    }

    /** Helper: wires one keystroke to an Action that flips a boolean flag. */
    private void bind(InputMap im,
                      ActionMap am,
                      String keystroke,
                      boolean value,
                      BooleanConsumer setter) {

        im.put(KeyStroke.getKeyStroke(keystroke), keystroke);
        am.put(keystroke, new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                setter.accept(value);
            }
        });
    }
}
