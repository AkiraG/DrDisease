/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author saita
 */
public class LaserShot {
    
    int time,cdAtk=1000;
    
    boolean collide;
    
    Shape hitbox;
    
    Point location;
    Vector2f direction;
    
    SpriteSheet sLaser;
    Animation aBase,aLaserShoot,aLaserIdle,aLaserOff;
    
    public LaserShot(Point location,Vector2f direction,int cd){
        
        this.cdAtk=cd;
        this.location=location;
        this.direction=direction;
            
        try {
            
            sLaser = new SpriteSheet("data/image/laser-1-1-shoot.png",700,32);
            
            aLaserShoot = new Animation(sLaser,0,0,0,1,false,60,true);
            aLaserShoot.stopAt(aLaserShoot.getFrameCount()-1);
            
            aLaserIdle = new Animation(sLaser,0,2,0,4,false,60,true);
            aLaserIdle.stopAt(aLaserIdle.getFrameCount()-1);

            aLaserOff = new Animation(sLaser,0,4,0,8,false,60,true);
            aLaserOff.stopAt(aLaserOff.getFrameCount()-1);
            
            
            
            hitbox = new Rectangle(location.getX(),location.getY(),700,32);
            hitbox = hitbox.transform(Transform.createRotateTransform((float)Math.toRadians(direction.getTheta()),location.getX(),location.getY()+16));
            
            
            for(int x=0;x<aLaserShoot.getFrameCount();x++){
                aLaserShoot.getImage(x).setCenterOfRotation(0,16);
            }
            
            for(int x=0;x<aLaserIdle.getFrameCount();x++){
                aLaserIdle.getImage(x).setCenterOfRotation(0,16);
            }
            
            for(int x=0;x<aLaserOff.getFrameCount();x++){
                aLaserOff.getImage(x).setCenterOfRotation(0,16);
            }
            
            for(int x=0;x<aLaserShoot.getFrameCount();x++){
                aLaserShoot.getImage(x).setRotation((float)direction.getTheta());
            }
            
            for(int x=0;x<aLaserIdle.getFrameCount();x++){
                aLaserIdle.getImage(x).setRotation((float)direction.getTheta());
            }
            
            for(int x=0;x<aLaserOff.getFrameCount();x++){
                aLaserOff.getImage(x).setRotation((float)direction.getTheta());
            }
            
            aBase = aLaserShoot;
            
            
 
        } catch (SlickException ex) {
            Logger.getLogger(LaserShot.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
    public LaserShot(Point location,Vector2f direction){
        
        this.location=location;
        this.direction=direction;
            
        try {
            
            sLaser = new SpriteSheet("data/image/laser-1-1-shoot.png",700,32);
            
            aLaserShoot = new Animation(sLaser,0,0,0,1,false,60,true);
            aLaserShoot.stopAt(aLaserShoot.getFrameCount()-1);
            
            aLaserIdle = new Animation(sLaser,0,2,0,4,false,60,true);
            aLaserIdle.stopAt(aLaserIdle.getFrameCount()-1);

            aLaserOff = new Animation(sLaser,0,4,0,8,false,60,true);
            aLaserOff.stopAt(aLaserOff.getFrameCount()-1);
            
            
            
            hitbox = new Rectangle(location.getX(),location.getY(),700,32);
            hitbox = hitbox.transform(Transform.createRotateTransform((float)Math.toRadians(direction.getTheta()),location.getX(),location.getY()+16));
            
            
            for(int x=0;x<aLaserShoot.getFrameCount();x++){
                aLaserShoot.getImage(x).setCenterOfRotation(0,16);
            }
            
            for(int x=0;x<aLaserIdle.getFrameCount();x++){
                aLaserIdle.getImage(x).setCenterOfRotation(0,16);
            }
            
            for(int x=0;x<aLaserOff.getFrameCount();x++){
                aLaserOff.getImage(x).setCenterOfRotation(0,16);
            }
            
            for(int x=0;x<aLaserShoot.getFrameCount();x++){
                aLaserShoot.getImage(x).setRotation((float)direction.getTheta());
            }
            
            for(int x=0;x<aLaserIdle.getFrameCount();x++){
                aLaserIdle.getImage(x).setRotation((float)direction.getTheta());
            }
            
            for(int x=0;x<aLaserOff.getFrameCount();x++){
                aLaserOff.getImage(x).setRotation((float)direction.getTheta());
            }
            
            aBase = aLaserShoot;
            
            
 
        } catch (SlickException ex) {
            Logger.getLogger(LaserShot.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
    public void draw(Graphics g){
        
        aBase.draw(location.getX(), location.getY());  
       // g.draw(hitbox);
        
    }
    
    public void update(int delta){
        
        if(aBase.equals(aLaserIdle))time+=delta;
        
        if(aLaserShoot.isStopped()){
            aBase.restart();
            aBase=aLaserIdle;
        }else if(aLaserIdle.isStopped()){
            if(time>=cdAtk){
                aBase.restart();
                aBase=aLaserOff;
            }else aLaserIdle.restart();
        }
        
    }
    
    public boolean checkAnimation(){
        return aLaserOff.isStopped();
    }
    
    public boolean checkCollision(Shape c){
       return(hitbox.intersects(c));
    }
    
    
    
}
