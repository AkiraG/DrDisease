/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Gabriel
 */
public class Boss {
    
    final int idle = 0;
    final int randomBullets = 1;
    final int comboBullets = 2;
    int x, y, hp, rand, attack, time;
    static List<HitBoxBoss> blasters = new ArrayList <HitBoxBoss>();
    static List<TiroBoss> bullets = new ArrayList <TiroBoss>();
    SpriteSheet blasterSheet;
    BossBody body;
    BossCore core;
    List<BossConcept> boss = new ArrayList <>();
    
    boolean onPause = false;
    boolean onIntro = true;
    
    public Boss() {
        hp = 1000;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        
        core = new BossCore();
        core.init(gc, sbg);
        body = new BossBody();
        body.init(gc, sbg);
        boss.add(core);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        boss.forEach(bossPart -> {
            try {
                bossPart.render(g);
            } catch (SlickException ex) {
                Logger.getLogger(Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        blasters.forEach(blaster -> {
            try {
                blaster.render(g);
            } catch (SlickException ex) {
                Logger.getLogger(Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        bullets.forEach(bullets ->{
            g.draw(bullets.hitbox);
            g.drawAnimation(bullets.bullet, bullets.x, bullets.y);
        });
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        if (onIntro) this.introBoss();
        else {
            core.update(delta);
            
            time += delta;
            if (time >= 50) { // select attack
                attack = (int) (Math.random() * 2) +1;
                //attack = randomBullets;
                time = -250;
            } // select attack

            switch (attack) {
                case idle:
                    break;
                case randomBullets:
                    this.randomBullets();
                    break;
                case comboBullets:
                    this.comboBullets();
                    break;
            }

            blasters.forEach(blaster -> {
                try {
                    blaster.update();
                    if (blaster.blaster.getFrame() == 5 && blaster.onAttack){
                        Boss.bullets.add(new TiroBoss(blaster.x,blaster.y, blaster.ang));
                        blaster.onAttack = false;
                    }
                } catch (SlickException ex) {
                    Logger.getLogger(Boss.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            bullets.forEach(bullet ->{
                try {
                    bullet.intersect(Play.guts);
                } catch (SlickException ex) {
                    Logger.getLogger(Boss.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            bullets.removeIf(bullet -> bullet.bullet.isStopped());
        }
    }
    
    
    
    public void pause() {
        blasters.forEach(blasters -> {
            blasters.blaster.setAutoUpdate(false);
        });
    }
    
    public void dispause() {
        blasters.forEach(blasters -> {
            if (blasters.onAttack == true) blasters.blaster.setAutoUpdate(true);
        });
    }
    
    
    
    public void randomBullets() throws SlickException {
        if (time % 10 == 0){
            rand = (int) (Math.random() * 4);
            if (blasters.get(rand).onAttack == false) {
                blasters.get(rand).attack();
            }
        }
    }
    
    public void comboBullets() throws SlickException {
        if (time < -200){
            blasters.get(1).attack(); blasters.get(2).attack();
        }
        if (time > -150 && time < - 100){
            blasters.get(0).attack(); blasters.get(3).attack();
        }
        if (time > -50 && time <- 0){
            blasters.get(1).attack(); blasters.get(2).attack();
            blasters.get(0).attack(); blasters.get(3).attack();
        }
    }
    
    public void flowerBullets() throws SlickException {
        if (time % 10 == 0){
        }     
    }
    
    
    
    public void introBoss() throws SlickException {
        if (core.status == 0 && core.animAtual[core.status].isStopped() && boss.size() == 1) {
            boss.add(body);
        }
        if (body.bodyIntro.isStopped()) {
            core.status = 1;
            blasters.add(new HitBoxBoss(248, 175));
            blasters.add(new HitBoxBoss(440,183));
            blasters.add(new HitBoxBoss(636, 183));
            blasters.add(new HitBoxBoss(828, 175));
        }
        if (core.status == 1 && core.animAtual[core.status].isStopped()) {
            core.status = 2;
            onIntro = false;
        }

    }
    
}
