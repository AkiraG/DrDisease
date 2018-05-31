package doctordisease;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.*;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.*;

public class Player {

    static int x, y;
    Rectangle hitbox;
    boolean isOnCooldown;
    boolean isKeyDown = false;
    int cooldownTimer;
    static List<Tiro> tiros = new ArrayList <Tiro>();
    BasicCommand attack = new BasicCommand("attack");
    boolean hand = false;
    SpriteSheet gutsSheet, propulsionSheet;
    Animation guts, propulsion;
    boolean idle;
    Sound gutsShot;
    Vector2f vec;
    
    public Player() {
        x = 512;
        y = 500;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        gutsSheet = new SpriteSheet("data/image/Fase01/Guts-shoot-Sheet.png", 44, 62);
        propulsionSheet = new SpriteSheet("data/image/Fase01/Guts-propulsion-Sheet.png", 8, 16);
        guts = new Animation(gutsSheet, 100);
        guts.setAutoUpdate(false);
        propulsion = new Animation(propulsionSheet, 100);
        propulsion.setAutoUpdate(false);
        hitbox = new Rectangle(x, y, guts.getWidth(), guts.getHeight());
        gutsShot = new Sound("data/sound/Fase01/Guts_shot.ogg");
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.draw(hitbox);
        g.drawAnimation(guts, x, y);
        g.drawAnimation(propulsion, x + 18, y + 40);
        for (Tiro t: tiros){
            t.render(gc, sbg, g);
        }  
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        idle = true;
        if (gc.getInput().isKeyDown(Input.KEY_LEFT)) {
            x -= 10;
            idle = false;
        }
        if (x < 0) x += x * -1 ;
        if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
            x += 10;
            idle = false;
        }
        if (x > (1024 - hitbox.getWidth())) x = (int) (1024 - hitbox.getWidth());
        if (gc.getInput().isKeyDown(Input.KEY_UP)) {
            y -= 10;
            idle = false;
        }
        if (y < 0) y += 10 ;
        if (gc.getInput().isKeyDown(Input.KEY_DOWN)) {
            y += 10;
            idle = false;
        }
        if (y > (768 - hitbox.getHeight())) y = (int) (768 - hitbox.getHeight());
        if (idle == true) {
            propulsion.setAutoUpdate(true);
            if (propulsion.getFrame() == 6) {
                propulsion.restart();
            }
        } else propulsion.setAutoUpdate(false);
        vec = new Vector2f(x, y);
        hitbox.setLocation(vec);
        if (isOnCooldown) {
            cooldownTimer += delta;
            if (cooldownTimer > 100) {
                isOnCooldown = false;
            }
        }
        if (gc.getInput().isKeyDown(Input.KEY_SPACE)) {
            this.attack();
            guts.setAutoUpdate(true);
        } else {
            guts.setAutoUpdate(false);
            guts.restart();
            hand = false;
        }
        tiros.forEach(tiros -> {
            tiros.update();
        });
        tiros.removeIf(tiros -> tiros.bullet.isStopped());
    }
    
    public void attack() throws SlickException{
        if (isOnCooldown == false && guts.getFrame() >= 3 && guts.getFrame() < 6){
            if (hand == true)  tiros.add(new Tiro(x,y));
            if (hand == false) tiros.add(new Tiro((x + 40),y));
            gutsShot.play();
            hand = !hand;
            isOnCooldown = true;
            cooldownTimer = 0;
            if (guts.getFrame() == 5) guts.setCurrentFrame(2);
        }
    }
    
    public void pause() {
        guts.setAutoUpdate(false);
        propulsion.setAutoUpdate(false);
    }
}