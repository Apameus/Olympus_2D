package desktop;

import UI.UI;
import graphics.State;
import graphics.Control;
import graphics.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;
import java.util.Map;

import static engine.GameEngine.changeGameState;
import static engine.GameEngine.gameState;
import static graphics.Control.*;
import static java.awt.event.KeyEvent.*;

public class KeyHandler implements KeyListener, Input {

    public final Map<Integer, Control> keyMap;
    public final BitSet bitSet;

    public static boolean enterPressed;   // stupid
    public static byte optionNumber = 2;

    public KeyHandler() {
        bitSet = new BitSet();
        keyMap = Map.of(
                VK_W, MOVE_UP,
                VK_S, MOVE_DOWN,
                VK_A, MOVE_LEFT,
                VK_D, MOVE_RIGHT
//                VK_P, CHANGE_GAME_STATE
        );
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        set(e.getKeyCode(), true);

        // PAUSE || PLAY
        if (e.getKeyCode() == VK_P){
            changeGameState();
        }
        // OPEN DIALOGUE
        if (e.getKeyCode() == VK_ENTER) {
            enterPressed = true;
        }
        // DIALOGUES
        if (gameState == State.DIALOGUE) {
            if (e.getKeyCode() == VK_ENTER) {
                gameState = State.PLAY;
            }
        }

        // TITLE
        if (gameState == State.TITLE) {
            if (e.getKeyCode() == VK_UP) {
                optionNumber++;
            }
            if (e.getKeyCode() == VK_DOWN) {
                optionNumber--;
            }

            if (enterPressed) {
                UI.option.enterOption(optionNumber);
            }
        }
    }



    @Override
    public void keyReleased(KeyEvent e) {
       set(e.getKeyCode(), false);
    }


    private void set(int keyCode, boolean active) {
        Control control = keyMap.get(keyCode);
        if (control != null){
            bitSet.set(control.ordinal(), active);
        }
    }

    @Override
    public boolean isActive(Control control) {
        return bitSet.get(control.ordinal());
    }
}
