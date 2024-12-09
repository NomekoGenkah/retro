package main;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CheatCodeHandler extends KeyAdapter {

    private String cheatCode = "000";  // El código de trampa que se espera
    private StringBuilder inputSequence = new StringBuilder();  // Almacena las teclas ingresadas
    private boolean cheatCodeActive = false;  // Indica si el código de trampa se activó

    // Método que maneja la entrada de las teclas y verifica si coincide con el cheatCode
    @Override
    public void keyPressed(KeyEvent e) {
        inputSequence.append(e.getKeyChar());

        // Si la longitud de la secuencia es mayor que la longitud del código, recortamos
        if (inputSequence.length() > cheatCode.length()) {
            inputSequence.delete(0, inputSequence.length() - cheatCode.length());
        }

        // Comprobamos si la secuencia ingresada coincide con el cheatCode
        if (inputSequence.toString().equals(cheatCode)) {
            cheatCodeActive = true;  // El cheatcode ha sido activado
            System.out.println("¡Cheatcode activado!");
            // Aquí puedes poner lo que suceda cuando se active el cheatcode
        }
    }

    public boolean isCheatCodeActive() {
        return cheatCodeActive;
    }

    public void resetCheatCode() {
        cheatCodeActive = false;
        inputSequence.setLength(0);  // Limpiar la secuencia ingresada
    }
}
