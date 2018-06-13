/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Gabriel
 */
public class ThreadPlayer extends Thread {
    
    static Player guts;
    boolean stop;
    int time;
    Input input;

    public ThreadPlayer(Player guts) throws SlickException {
        this.guts = guts;
        System.out.println("THREAD PLAYER");
    }
    
    @Override
    public void run(){
        while(true){
            synchronized(this){
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            guts.update(time, input);
            guts.checkCollision(ThreadBoss.boss.getShootList());
            guts.checkLaserCollision(ThreadBoss.boss.getLaserList());
            Fase01.EDGE.forEach(parede -> {
                guts.checkBulletCollision(parede);
            });
        }
    }
    
    public synchronized void verStop() throws InterruptedException {
        wait();
    }

    public synchronized void putStop(boolean st) {
        stop = st;
        while (!stop){
            input = Fase01.input;
            time = Fase01.dlt;
            notify();
            stop = !stop;
        }
    }

}
