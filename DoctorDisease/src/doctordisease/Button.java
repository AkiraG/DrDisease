/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;

public class Button {

    static int state, language, sound;
    static int[] estados = {state, language, sound};
    
    String path;
    Image[] buttonImg;
    Image imgButton;
    MouseOverArea button;
    int x, y, var;
    String type;
    Sound snd;

    public Button(String path, int x, int y, int var, Sound snd, String type) {
        this.path = path;
        this.x = x;
        this.y = y;
        this.var = var;
        this.snd = snd;
        this.type = type;
    }    

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        
        buttonImg = new Image[] {new Image("data/image/"+path+"1.png"), new Image("data/image/"+path+"2.png")};
        imgButton = buttonImg[0];
        if (type == "state"){
            button = new MouseOverArea(gc, buttonImg[0], x - (buttonImg[0].getWidth()/2), y, buttonImg[0].getWidth(), buttonImg[0].getHeight(),  new ComponentListener() {            
                @Override            
                public void componentActivated(AbstractComponent arg0) {
                    estados[0] = var;
                }      
            });        
            button.setMouseOverImage(buttonImg[1]);
        }
        if (type == "control"){
            button = new MouseOverArea(gc, imgButton, x - (buttonImg[0].getWidth()/2), y, buttonImg[0].getWidth(), buttonImg[0].getHeight(),  new ComponentListener() {            
                @Override            
                public void componentActivated(AbstractComponent arg0) {
                    estados[var] += 1;
                    if (estados[var] > 1) estados[var] = 0;

                    if (estados[var] == 0) {
                        button.setNormalImage(buttonImg[0]);
                        button.setMouseOverImage(buttonImg[0]);
                    }
                    else {
                        button.setNormalImage(buttonImg[1]);
                        button.setMouseOverImage(buttonImg[1]);
                    }
                }      
            }); 
        }
        if (type == "lang"){
            button = new MouseOverArea(gc, imgButton, x - (buttonImg[0].getWidth()/2), y, buttonImg[0].getWidth(), buttonImg[0].getHeight(),  new ComponentListener() {            
                @Override            
                public void componentActivated(AbstractComponent arg0) {
                    estados[var] += 1;
                    if (estados[var] > 1) estados[var] = 0;

                    if (estados[var] == 0) {
                        button.setNormalImage(buttonImg[0]);
                        button.setMouseOverImage(buttonImg[0]);
                        Button.estados[1] = 0;
                    }
                    else {
                        button.setNormalImage(buttonImg[1]);
                        button.setMouseOverImage(buttonImg[1]);
                        Button.estados[1] = 1;
                    }
                }      
            }); 
        }
        button.setMouseDownSound(snd);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        button.render(gc, g);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
    }
}