/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class ThreadMsc implements Runnable {

    private Thread t; // obj Thread
    private final String threadName; // nome para futura referencia
    // criação das var de músicas
    private Music introDrop;
    private Music bgMusic;
    
    ThreadMsc (String name) {
        threadName = name;
        System.out.println("Thread criado");
    }
    
    @Override
    public void run() {
        while (DoctorDisease.gameRunning){ // Thread sincronizada até o jogo ser fechado
            switch (DoctorDisease.gameState){ // verifica o estado do jogo/menu atual
                case 0: // a Thread é responsável por todas as questões de SoundTrack tomando as decições a partir do caso atual
                    if(!introDrop.playing())introDrop.play();
                    break;
                case 1:
                    // verifica se a opção de Sound do jogo é false ou true, desativando ou não o som
                    introDrop.stop();
                    if (!bgMusic.playing() && Button.estados[2] == 0) {
                        bgMusic.play();
                        DoctorDisease.app.setSoundOn(true);
                    }
                    else if (Button.estados[2] != 0) {
                        bgMusic.pause();
                        DoctorDisease.app.setSoundOn(false);
                    }      
                    break;
                case 2:
                    bgMusic.stop();
                    break;
            }
        }
    }
    
    public void start() throws SlickException {
        if (t == null){ // verifica se a thread já foi criada
            introDrop = new Music("/data/sound/Intro_Pre_Drop.ogg"); // instanciação das músicas
            bgMusic= new Music("/data/sound/Intro_Drop.ogg");
            t = new Thread (this, threadName); // instanciação do obj Thread
            t.start(); // chamada do método .run() da Thread
        }
    }
}
