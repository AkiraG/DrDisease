/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.Random;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Gabriel
 */
public class Celulas extends BasicGameState {
    
    Image[] cellLst = new Image [4];
    int posX, posY, vel, num;
    Image cell;
    Random rand;

    public Celulas() {
    }
    
    @Override
    public int getID() {
        return 0;
    }

    public void change() {
        num = (int) (Math.random() * 4);
        cell = cellLst[num];
        
        switch (num) {
            case 0:
                vel = 7;
                break;                
            case 1:
                vel = 12;
                break;
            case 2:
                vel = 3;
                break;
            default:
                vel = 7;
                break;
        }
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        cellLst = new Image[]{new Image("data/image/bactery_purple.png"), new Image("data/image/platelets.png")
        ,new Image("data/image/red_blood_cell.png"), new Image("data/image/white_blood_cell.png")};
        posY = (int) (Math.random() * (650 - 50));
        posX = (int) (Math.random() * 1000);        
        change();
    }    
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawImage(cell, posX, posY);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        posX += (vel*100) * (i/1000f);
        cell.rotate(10 * (i/1000f));
        if (posX > DoctorDisease.WIDTH){
            posX = -200;
            posY = (int) (Math.random() * (600 - 100) + 50);
            change();
        }
    }
}