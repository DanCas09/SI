package businessLogic.register.entities;

import concrete.interfaces.IRepository;
import model.Cracha;
import model.Jogo;

public class CrachaRM {

    private Jogo jogo;
    private String nome;
    private Integer pontuacao;
    private String imgUrl;


    public CrachaRM(Jogo jogo, String nome, Integer pontuacao, String imgUrl){
        this.jogo = jogo;
        this.nome = nome;
        this.pontuacao = pontuacao;
        this.imgUrl = imgUrl;
    }

    public Cracha createCracha(){
        Cracha cracha = new Cracha();

        cracha.setIdJogo(jogo);
        cracha.setNome(nome);
        cracha.setPontuacao(pontuacao);
        cracha.setImageUrl(imgUrl);

        return cracha;
    }
}
