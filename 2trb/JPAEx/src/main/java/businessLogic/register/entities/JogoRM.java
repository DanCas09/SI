package businessLogic.register.entities;

import model.Jogo;

public class JogoRM {

    private String id;
    private String nome;
    private String url;
    public JogoRM(String id, String nome, String url){
        this.id = id;
        this.nome = nome;
        this.url = url;
    }

    public Jogo createJogo(){
        Jogo jogo = new Jogo();

        jogo.setId(id);
        jogo.setNome(nome);
        jogo.setUrl(url);

        return jogo;
    }
}
