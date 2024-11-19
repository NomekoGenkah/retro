package gameStateManager;

import retro.KeyHandler;

public class GameStateManager {
    public String[] states = {"run", "pause", "menu"};
    private String activeState;
    KeyHandler keyH;
    

    public GameStateManager(KeyHandler keyH){
        this.keyH = keyH;
        this.activeState = "true";
    }

    public void changeActiveState(){
        if(keyH.escPressed){
            this.activeState = states[1];
        }else{
            this.activeState = states[0];
        }
        System.out.println("activeState: "+ this.activeState);
    }

    public String getActiveState(){
        return this.activeState;
    }

    public boolean isRunning(){
    //    System.out.println(this.activeState);
    //    System.out.println(this.activeState.equals(states[0]));
        return this.activeState.equals(states[0]);
    }

    public void update(){
        changeActiveState();
    }

}
