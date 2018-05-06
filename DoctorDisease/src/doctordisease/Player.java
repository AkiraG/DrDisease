package doctordisease;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.*;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.*;

public class Player {

    int x, y;
    float aclX = (float) 0.1;
    float aclY = (float) 0.1;
    Rectangle rec;
    boolean isOnCooldown;
    boolean isKeyDown = false;
    int cooldownTimer;
    static List<Tiro> tiros = new ArrayList <Tiro>();
    BasicCommand attack = new BasicCommand("attack");
    boolean hand = true;    
    
    public Player() {
        x = 512;
        y = 500;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        rec = new Rectangle(x, y, 50, 50);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.draw(rec);
        for (Tiro t: tiros){
            t.render(gc, sbg, g);
        }  
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        if (gc.getInput().isKeyDown(Input.KEY_LEFT)) x -= 10;
        if (x < 0) x += x * -1 ;
        if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) x += 10;
        if (x > (1024 - rec.getWidth())) x = (int) (1024 - rec.getWidth());
        if (gc.getInput().isKeyDown(Input.KEY_UP)) y -= 10;
        if (y < 0) y += y * -1 ;
        if (gc.getInput().isKeyDown(Input.KEY_DOWN)) y += 10;
        if (y > (768 - rec.getHeight())) y = (int) (768 - rec.getHeight());
        if (gc.getInput().isKeyDown(Input.KEY_SPACE)) this.attack();
        rec.setX(x);
        rec.setY(y);
        for (Tiro t : tiros){
            t.update();
        }      
        if (isOnCooldown) {
            cooldownTimer += delta;
            if (cooldownTimer > 200) {
                isOnCooldown = false;
            }
        }
    }
    
    public void attack() throws SlickException{
        if (isOnCooldown == false){
            if (hand == true)   tiros.add(new Tiro(x,y));
            if (hand == false) tiros.add(new Tiro((x + 40),y));
            hand = !hand;
            isOnCooldown = true;
            cooldownTimer = 0;
        }
    }             
}