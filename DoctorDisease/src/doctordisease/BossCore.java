/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author verratti.gfv
 */
public class BossCore extends BossConcept {
    
    Animation coreIntro1;    
    Animation coreIntro2;
    Animation coreIdle;
    Animation coreAttackSet;
    Animation coreAttackCharge;
    Animation coreAttackShoot;
    Animation coreAttackCd;
    Animation coreAttackReset;
    Animation[] animAtual;
    final int intro1 = 0, intro2 = 1, idle = 2, attackSet = 3, attackCharge = 4, attackShoot = 5, attackCd = 6, attackReset = 7;
    int time;
    
    int status, x, y;
    boolean onAttack;
    boolean onCD;

    public BossCore() {
    }
    
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        coreIntro1 = new Animation(new SpriteSheet("data/image/Fase01/core-1-1-intro-pt1.png", 128, 128), 10);
        coreIntro1.stopAt(61);

        coreIntro2 = new Animation(new SpriteSheet("data/image/Fase01/core-1-1-intro-pt2.png", 128, 128), 10);
        coreIntro2.stopAt(7);
        
        coreIdle = new Animation(new SpriteSheet("data/image/Fase01/core-1-1-idle.png", 128, 128), 100);
        coreIdle.stopAt(29);
        
        coreAttackReset = new Animation(new SpriteSheet("data/image/Fase01/core-1-1-atk-reset.png", 128, 128), 100);
        coreAttackReset.stopAt(14);
        
        coreAttackCharge = new Animation(new SpriteSheet("data/image/Fase01/core-1-1-atk-charge.png", 128, 128), 100);
        
        coreAttackSet = new Animation(new SpriteSheet("data/image/Fase01/core-1-1-atk-set.png", 128, 128), 100);
        coreAttackSet.stopAt(16);
        
        coreAttackCd = new Animation(new SpriteSheet("data/image/Fase01/core-1-1-atk-cd.png", 128, 128), 100);
        coreAttackCd.stopAt(4);
        
        coreAttackShoot = new Animation(new SpriteSheet("data/image/Fase01/core-1-1-atk-shoot.png", 128, 128), 200);
        coreAttackShoot.stopAt(2);
        
        animAtual = new Animation[] {coreIntro1, coreIntro2, coreIdle, coreAttackSet, coreAttackCharge, coreAttackShoot, coreAttackCd, coreAttackReset};
    
        x = 484; y = 64;
    }

    @Override
    public void render(Graphics g) throws SlickException {
        g.drawAnimation(animAtual[status], x, y);
    }
    
    public void update(int delta) throws SlickException {
        time += delta;
        
        if (time >= 1500){
            onAttack = true;
            status = attackSet;
            time = 0;
        }
        
        switch (status) {
            case intro1:
                break;
            case intro2:
                break;
            case idle:
                if (animAtual[idle].isStopped()) animAtual[idle].restart();
                break;
            case attackSet:
                if (animAtual[attackSet].isStopped()) {
                    status = attackCharge;
                    animAtual[attackSet].restart();
                }
                time = 0;
                break;
            case attackCharge:
                if (time >= 500) {
                    status = attackShoot;
                    animAtual[attackCharge].restart();
                    time = 0;
                }
                break;
            case attackShoot:
                if (animAtual[attackShoot].isStopped() && time >= 300) {
                    status = attackCd; // adicionar o tiro
                    
                    animAtual[attackShoot].restart();
                }
                break;
            case attackCd:
                if (animAtual[attackCd].isStopped()) {
                    status = attackReset;
                    animAtual[attackCd].restart();
                }
                break;
            case attackReset:
                if (animAtual[attackReset].isStopped()) {
                    status = idle;
                    animAtual[attackReset].restart();
                }
                time = 0;
                break;
        }
    }
    
    
}
