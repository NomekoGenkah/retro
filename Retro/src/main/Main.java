/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;
import javax.swing.JFrame;


/**
 *
 * @author pedro
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Retro");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
                
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

       // gamePanel.setupGame();
        gamePanel.setupGame();
        gamePanel.startGameThread();

    }
    
}