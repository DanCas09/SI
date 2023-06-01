package concrete.entities;

import model.Cracha;
import model.CrachasJogador;
import model.Jogador;

public class CrachaJogadorRM {

    Jogador jogador;
    Cracha cracha;

    public CrachaJogadorRM(Jogador jogador, Cracha cracha){
        this.jogador = jogador;
        this.cracha = cracha;
    }

    public CrachasJogador createCrachaJogador(){
        CrachasJogador crachaJogador = new CrachasJogador();
        crachaJogador.setIdJogador(jogador);
        crachaJogador.setIdCracha(cracha);
        return crachaJogador;
    }
}
