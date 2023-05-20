package model;

import jakarta.persistence.*;

@Entity
@Table(name = "estatisticas_jogo", schema = "dbo")
public class EstatisticasJogo {
    @Id
    @Column(name = "id_jogo", nullable = false, length = 10)
    private String idJogo;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogo", nullable = false)
    private Jogo jogo;

    @Column(name = "num_partidas", nullable = false)
    private Integer numPartidas;

    @Column(name = "num_jogadores", nullable = false)
    private Integer numJogadores;

    @Column(name = "total_pontos", nullable = false)
    private Integer totalPontos;

    public String getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(String idJogo) {
        this.idJogo = idJogo;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Integer getNumPartidas() {
        return numPartidas;
    }

    public void setNumPartidas(Integer numPartidas) {
        this.numPartidas = numPartidas;
    }

    public Integer getNumJogadores() {
        return numJogadores;
    }

    public void setNumJogadores(Integer numJogadores) {
        this.numJogadores = numJogadores;
    }

    public Integer getTotalPontos() {
        return totalPontos;
    }

    public void setTotalPontos(Integer totalPontos) {
        this.totalPontos = totalPontos;
    }

}