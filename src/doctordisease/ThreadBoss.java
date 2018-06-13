/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.ConcurrentModificationException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saita
 */
public class ThreadBoss extends Thread {
    
    static Boss boss;
    boolean stop;
    int temp;
    
    public ThreadBoss(Boss boss) {
        this.boss = boss;
        System.out.println("THREAD BOSS");
    }

    @Override
    public void run(){
        while(true){
            synchronized(this){
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadBoss.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
            boss.update(temp);
            boss.checkCollision(ThreadPlayer.guts.getShootList());
            Fase01.EDGE.forEach(parede -> {
                boss.checkBulletCollision(parede);
            });
            } catch (ConcurrentModificationException e ) {
                System.out.println("Segue o jogo");
            }
        }
    }

   public synchronized void putStop(boolean st) {
        stop = st;
        while (!stop){
            temp = Fase01.dlt;
            notify();
            stop = !stop;
        }
    }  
}
