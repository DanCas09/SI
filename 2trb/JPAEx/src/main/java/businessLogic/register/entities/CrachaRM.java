package businessLogic.register.entities;

import model.Cracha;
import model.Jogo;

public class CrachaRM {

    private String idJogo;
    private String nome;
    private Integer pontuacao;
    private String imgUrl;


    public CrachaRM(String idJogo, String nome, Integer pontuacao, String imgUrl){
        this.idJogo = idJogo;
        this.nome = nome;
        this.pontuacao = pontuacao;
        this.imgUrl = imgUrl;
    }

    public Cracha createCracha(){
        Cracha cracha = new Cracha();


        cracha.setIdJogo(idJogo);
        cracha.setNome(nome);
        cracha.setPontuacao(pontuacao);
        cracha.setImageUrl(imgUrl);

        return cracha;
    }
}
