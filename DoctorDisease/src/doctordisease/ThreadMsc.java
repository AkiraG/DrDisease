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
    private Music intro;
    private Music introDrop = intro;
    private Music bgMenuMusic;
    private Music bgPlayMusic;
    //
    final int INTRO = 0, MENU = 1, PLAY = 2;
    
    ThreadMsc (String name) {
        threadName = name;
        System.out.println("Thread criado");
    }
    
    @Override
    public void run() {
        while (DoctorDisease.gameRunning){ // Thread sincronizada até o jogo ser fechado
            switch (DoctorDisease.gameState){ // verifica o estado do jogo/menu atual
                case INTRO: // a Thread é responsável por todas as questões de SoundTrack tomando as decições a partir do caso atual
                    if(!intro.playing()) intro.play();
                    break;
                case MENU:
                    // verifica se a opção de Sound do jogo é false ou true, desativando ou não o som
                    intro.stop();
                    if (!bgMenuMusic.playing() && Button.estados[2] == 0) {
                        bgMenuMusic.play();
                        DoctorDisease.app.setSoundOn(true);
                    }
                    else if (Button.estados[2] != 0) {
                        bgMenuMusic.pause();
                        DoctorDisease.app.setSoundOn(false);
                    }      
                    break;
                case PLAY:
                    bgMenuMusic.stop();
                    if (!bgPlayMusic.playing() && Button.estados[2] == 0) {
                        bgPlayMusic.play();
                        DoctorDisease.app.setSoundOn(true);
                    }
                    else if (Button.estados[2] != 0) {
                        bgPlayMusic.pause();
                        DoctorDisease.app.setSoundOn(false);
                    }      
                    break;
            }
        }
    }
    
    public void begin() throws SlickException {
        if (t == null){ // verifica se a thread já foi criada
            intro = new Music("/data/sound/Intro/Intro_Pre_Drop.ogg"); // instanciação das músicas
            bgMenuMusic= new Music("/data/sound/Intro/Intro_Drop.ogg");
            bgPlayMusic = new Music("data/sound/Fase01/fase01_Completa.ogg");
            t = new Thread (this, threadName); // instanciação do obj Thread
            t.start(); // chamada do método .run() da Thread
        }
    }
}
