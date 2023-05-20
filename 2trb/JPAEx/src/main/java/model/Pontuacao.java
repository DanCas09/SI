package model;

import jakarta.persistence.*;

@Entity
@Table(name = "pontuacao", schema = "dbo")
public class Pontuacao {
    @EmbeddedId
    private PontuacaoId id;

    @MapsId("idPartida")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_partida", nullable = false)
    private Partida idPartida;

    @MapsId("idJogador")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador idJogador;

    @Column(name = "pontuacao")
    private Integer pontuacao;

    public PontuacaoId getId() {
        return id;
    }

    public void setId(PontuacaoId id) {
        this.id = id;
    }

    public Partida getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Partida idPartida) {
        this.idPartida = idPartida;
    }

    public Jogador getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Jogador idJogador) {
        this.idJogador = idJogador;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

}