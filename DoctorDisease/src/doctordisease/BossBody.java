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
 * 
 * Classe criada para lidar com o corpo do Boss
 * @author saita
 */
public class BossBody extends BossConcept {
    boolean pause;
    
    SpriteSheet sBase,sIntro;
    Animation aBase,aIntro,aIntro02;
    
    String status;

    public BossBody(Point location) {
        super(location);
        
        status="Intro";
        
        try {
            
            sBase = new SpriteSheet("/data/image/Fase01/body-1-1-intro.png",616,208);
            
            sIntro = new SpriteSheet("/data/image/Fase01/body-1-2-introt.png",616,208);
            
            aIntro= new Animation(sBase,60);
            aIntro.setAutoUpdate(false);
            aIntro.stopAt(41);
            
            aIntro02 = new Animation(sIntro,80);
            aIntro02.setAutoUpdate(false);
            aIntro02.stopAt(aIntro02.getFrameCount()-1);
            
            aBase=aIntro;
     
            
           
            
        } catch (SlickException ex) {
            Logger.getLogger(BossBody.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        super.setHitbox(new Rectangle(location.getX(),location.getY(),aBase.getWidth(),aBase.getHeight()/2));
        
    }

    @Override
    public void draw(Graphics g) {
        aBase.draw(location.getX(),location.getY());
    //    g.draw(hitbox);
    }
     @Override
    public void draw(Graphics g, Color c) {
        aBase.draw(location.getX(),location.getY(),c);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        
        
    }
    /*
    Método que inicia a animação de intro do corpo do Boss
    */
    public void runIntro(){
        aIntro.setAutoUpdate(true);
    }
    /*
    Método que retorna a Hitbox do corpo do Boss
    */
    public Shape getHitbox(){
        return hitbox;
    }
    /*
    Método que retorna a animação atual do corpo do Boss
    */
    public Animation checkAnimation(){
        return aBase;
    }
    /*
    Método que inicia a 2º parte da Intro do corpo do Boss
    */
    public void runIntro02(){
        aBase=aIntro02;
        aBase.setAutoUpdate(true);
        //super.setHitbox(new Rectangle(location.getX()+220,location.getY(),aBase.getWidth()/4,aBase.getHeight()));
    }
    
     /*
    Método que atualiza o status de pause do corpo do Boss
    */
    @Override
    public void pause(){
        pause=!pause;
        aBase.setAutoUpdate(!pause);

    }
    
}
