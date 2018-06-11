/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author saita
 */
public class ThreadBoss extends Thread {
    Boss boss;
    GameContainer gc;StateBasedGame sbg;int delta;

    public ThreadBoss(Boss boss, GameContainer gc, StateBasedGame sbg, int delta) {
        this.boss = boss;
        this.gc = gc;
        this.sbg = sbg;
        this.delta = delta;
        System.out.println("THREAD"+this.getName());
    }

    @Override
    public void run(){
        while(true){
        this.boss.update(gc, sbg, delta);
        }
    }
    
}
