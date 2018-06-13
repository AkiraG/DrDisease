/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.runtime.Context;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.lwjgl.*;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

/**
 *
 * @author Gabriel
 */
public class ThreadPlayer extends Thread {
    
    static Player guts;
    boolean stop;
    int time;
    Input input;

    public ThreadPlayer(Player guts, GameContainer container) throws SlickException, LWJGLException {
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
            TesteState.EDGE.forEach(parede -> {
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
            input = TesteState.input;
            time = TesteState.teste;
            notify();
            stop = !stop;
        }
    }

}
