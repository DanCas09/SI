package businessLogic.register.entities;

import model.Jogador;
import model.Regiao;

public class JogadorRM {
    private String email;
    private String username;
    private String estado;
    private String regiaoNome;

    public JogadorRM(String email, String username, String estado, String regiaoNome) {
        this.email = email;
        this.username = username;
        this.estado = estado;
        this.regiaoNome = regiaoNome;
    }

    public Jogador createJogador() {
        Regiao r = new Regiao();
        r.setNome(regiaoNome);

        Jogador jogador = new Jogador();
        jogador.setEmail(email);
        jogador.setUsername(username);
        jogador.setEstado(estado);
        jogador.setRegiao(r);

        return jogador;
    }
}
