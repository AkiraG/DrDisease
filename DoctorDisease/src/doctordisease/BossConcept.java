/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author saita
 */
public abstract class  BossConcept{
    
    protected Point location;
    protected Vector2f direction;
    protected Shape hitbox;

    public BossConcept(Point location, Vector2f direction) {
        this.location = location;
        this.direction = direction;

    }
    
    public BossConcept(Point location){
        this.location = location;
    }
   
    public abstract void draw(Graphics g);
    
    public abstract void draw(Graphics g, Color c);
    
    public abstract void update(GameContainer gc, StateBasedGame sbg, int delta);
    
    public boolean checkCollision(Shape c){
        return hitbox.intersects(c);
    }
    
    public void setHitbox(Shape c){
        hitbox=c;
    }
    
    public void setDirection(Vector2f direction){
        this.direction = direction;
    }
}
