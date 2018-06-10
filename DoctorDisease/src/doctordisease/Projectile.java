/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author saita
 */
public class Projectile {
    
    private Animation projectile;
    private Point location;
    private Vector2f speed;
    private Circle hitbox;
    private boolean collide;

    public Projectile(Point location, Vector2f speed, SpriteSheet ref) {
        
        collide = false; 
        this.location = location;
        this.speed = speed;
        projectile = new Animation(ref,60);
        projectile.setAutoUpdate(false);
        projectile.stopAt(projectile.getFrameCount()-1);
        
        for(int x=0;x<projectile.getFrameCount();x++){
            projectile.getImage(x).setCenterOfRotation(projectile.getImage(x).getWidth()/2,projectile.getImage(x).getHeight()/2);
        }
        
        this.hitbox = new Circle(location.getX(),location.getY(),projectile.getImage(0).getWidth()/4);
    }
    
    public void update(int delta){
        projectile.getCurrentFrame().setRotation((float)speed.getTheta());
        
        if(!collide){
            this.move(delta);
        }
    }
    
    public void move(int delta){
        location.setX((float)((location.getX()+(speed.getX()*(delta/1000.0)))));
        location.setY((float)((location.getY()+(speed.getY()*(delta/1000.0)))));
        hitbox.setLocation(location.getX() - (projectile.getCurrentFrame().getWidth()/4),
                location.getY() - (projectile.getCurrentFrame().getHeight()/4));
    }
    
    public void draw(Graphics g){
        projectile.draw(location.getX() - (projectile.getCurrentFrame().getWidth()/2),
                location.getY() - (projectile.getCurrentFrame().getHeight()/2));
        //g.draw(hitbox);
    }
    
    public boolean checkCollision(Shape c){
        if(!collide){
            if(hitbox.intersects(c)){
                projectile.setAutoUpdate(true);
                collide=true;
                return true;
            }else return false;
        }return false;
    }
    
    public boolean checkAnimation(){
        return projectile.isStopped();
    }
    
       
}
