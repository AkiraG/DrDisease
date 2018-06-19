/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author saita
 */
public class GameOver extends BasicGameState {
    
    int time,time2;
    float t1,t2;
    float alpha;
    
    Image gameoverPT,gameoverENG,canavial;
    public GameOver(int id){
        
    }
    

    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        time=0;time2=0;
        t1=0;t2=0;
        alpha=0;
        gameoverPT = new Image("data/image/gameover-pt.png");
        gameoverENG = new Image("data/image/gameover-eng.png");
        canavial = new Image("data/image/canavial.png");
        canavial.setAlpha(alpha);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        //canavial.drawSheared(0, 0, t1, t2);
        if(Button.estados[1]==0){
            gameoverPT.drawCentered(512, 300);
        }else gameoverENG.drawCentered(512, 300);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        time+=delta;
        time2+=delta;
        if(time>=10000){
            time=0;
            game.getState(1).init(container, game);
            game.enterState(1,new FadeOutTransition(new Color (255,0,0)) ,new FadeInTransition(new Color (255,0,0)));
        }
        if(time2>=100){
            t1+=10;
            t2+=5;
            time2=0;
            alpha+=0.01f;
            canavial.setAlpha(alpha);
        }
        
    }
    
}
