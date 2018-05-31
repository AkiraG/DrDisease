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
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Gabriel
 */
public class Boss {
    
    
    
    final int idle = 0, randomBullets = 1, comboBullets = 2;
    int x, y, hp, rand, attack;
    double time = 1, lastTime = 10;
    
    static List<BossBlaster> blasters = new ArrayList <BossBlaster>();
    static List<BossBullet> bullets = new ArrayList <BossBullet>();
    
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
        boss.forEach(body -> {
            try {
                body.render(g);
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
        bullets.forEach(bullet -> {
            try {
                bullet.render(g);
            } catch (SlickException ex) {
                Logger.getLogger(Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
        });   
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        if (onIntro) introBoss();
        else {
            time -= (delta / 1000.0f);

            blasters.forEach(b -> {
                try {
                    b.update(delta);
                } catch (SlickException ex) {
                    Logger.getLogger(Boss.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            bullets.forEach(bullet ->{
                try {
                    bullet.update(delta, Play.guts);
                } catch (SlickException ex) {
                    Logger.getLogger(Boss.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            bullets.removeIf(bullet -> bullet.bullet.isStopped());
            
            core.update(delta);

            if (time <= 0) { // select attack
                //attack = (int) (Math.random() * 2) +1;
                attack = randomBullets;
                time = 10; lastTime = 10;
            } // select attack

            switch (attack) {
                case idle:
                    break;
                case randomBullets:
                    this.randomBullets();
                    break;
                case comboBullets:
                   // this.comboBullets();
                    break;
            }
        }
    }
//    
//    public void pause() {
//        blasters.forEach(b -> {
//            b.blaster.setAutoUpdate(false);
//        });
//    }
//    
//    public void dispause() {
//        blasters.forEach(b -> {
//            if (b.onAttack == true) b.blaster.setAutoUpdate(true);
//        });
//    }
//    
    public void randomBullets() throws SlickException {
        if (lastTime - time >= 0.2){
            rand = (int) (Math.random() * 4);
            if (blasters.get(rand).onAttack == false) {
                blasters.get(rand).directAttack();
                blasters.get(rand).onAttack = true;
            }
            lastTime = time;
        }
    }
//    
//    public void comboBullets() throws SlickException {
//        if (time < -200){
//            blasters.get(1).attack(); blasters.get(2).attack();
//        }
//        if (time > -150 && time < - 100){
//            blasters.get(0).attack(); blasters.get(3).attack();
//        }
//        if (time > -50 && time <- 0){
//            blasters.get(1).attack(); blasters.get(2).attack();
//            blasters.get(0).attack(); blasters.get(3).attack();
//        }
//    }
//    
//    public void flowerBullets() throws SlickException {
//        if (time % 10 == 0){
//        }     
    
    public void introBoss() throws SlickException {
        if (core.status == 0 && core.animAtual[core.status].isStopped() && boss.size() == 1) {
            boss.add(body);
        }
        if (body.bodyIntro.isStopped()) {
            core.status = 1;
            blasters.add(new BossBlaster(234, 175));
            blasters.add(new BossBlaster(426,183));
            blasters.add(new BossBlaster(622, 183));
            blasters.add(new BossBlaster(812, 175));
        }
        if (core.status == 1 && core.animAtual[core.status].isStopped()) {
            core.status = 2;
            onIntro = false;
        }
    }
    
}
