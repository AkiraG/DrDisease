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
 *Classe abstrata criada pelos atributos compartilhados por todos os tipos de Boss que irão ser criados no jogo
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
    /*
    Método Abstrato para desenhar em um contexto gráfico da Slick
    */
    public abstract void draw(Graphics g);
  
    public abstract void draw(Graphics g, Color c);
    /*
    Método Abstrato para atualizar os atributos
    */
    public abstract void update(GameContainer gc, StateBasedGame sbg, int delta);
    /*
    Método abstrato que checa colisão entre a hitbox dessa classe e alguma outra
    */
    public boolean checkCollision(Shape c){
        return hitbox.intersects(c);
    }
    /*
    Método que configura o hitbox dessa classe
    */
    public void setHitbox(Shape c){
        hitbox=c;
    }
    /*
    Método que configura o Vetor de direção dessa classe
    */
    public void setDirection(Vector2f direction){
        this.direction = direction;
    }
    
    /*
    Método que atualiza o status de pause do player
    */
    public void pause(){
        
    }
    
}
