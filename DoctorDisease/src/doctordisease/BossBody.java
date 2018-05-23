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
 * @author Gabriel
 */
public class BossBody extends BossConcept {

    SpriteSheet sheetBodyIntro;
    Animation bodyIntro;
    int x, y;

    public BossBody() {
    }
    
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        sheetBodyIntro = new SpriteSheet("data/image/Fase01/body-1-1-intro.png", 616, 208);
        bodyIntro = new Animation(sheetBodyIntro, 100);
        bodyIntro.stopAt(41);
        x = 240;
    }

    public void render(Graphics g) throws SlickException {
        g.drawAnimation(bodyIntro, x, y);
    }

    public void update() throws SlickException {
    }
    
}
