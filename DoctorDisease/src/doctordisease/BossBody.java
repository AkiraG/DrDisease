/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author saita
 */
public class BossBody extends BossConcept {
    SpriteSheet sBase;
    Animation aBase;

    public BossBody(Point location) {
        super(location);
        
        try {
            
            sBase = new SpriteSheet("/data/image/Fase01/body-1-1-intro.png",616,208);
            aBase= new Animation(sBase,60);
            
            aBase.setAutoUpdate(false);
            aBase.stopAt(41);
            
        } catch (SlickException ex) {
            Logger.getLogger(BossBody.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        super.setHitbox(new Rectangle(location.getX(),location.getY(),aBase.getWidth(),aBase.getHeight()/2));
        
    }

    @Override
    public void draw(Graphics g) {
        aBase.draw(location.getX(),location.getY());
        g.draw(hitbox);
    }
     @Override
    public void draw(Graphics g, Color c) {
        aBase.draw(location.getX(),location.getY(),c);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        
    }
    
    public void runIntro(){
        aBase.setAutoUpdate(true);
    }
    
    public Shape getHitbox(){
        return hitbox;
    }
    
    public Animation checkAnimation(){
        return aBase;
    }
    
}
