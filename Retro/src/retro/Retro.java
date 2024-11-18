/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package retro;

import javax.swing.JFrame;

/**
 *
 * @author genkah
 */
public class Retro {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("xoxo");
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Retro");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        
        //JButton jbutton = new JButton("hola");
        //window.add(jbutton);
        
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();

    }
    
}
