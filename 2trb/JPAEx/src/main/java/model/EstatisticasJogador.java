package model;

import jakarta.persistence.*;

@Entity
@Table(name = "estatisticas_jogador", schema = "dbo")
public class EstatisticasJogador {
    @Id
    @Column(name = "id_jogador", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador jogador;

    @Column(name = "num_partidas", nullable = false)
    private Integer numPartidas;

    @Column(name = "num_jogos_dif", nullable = false)
    private Integer numJogosDif;

    @Column(name = "total_pontos", nullable = false)
    private Integer totalPontos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public Integer getNumPartidas() {
        return numPartidas;
    }

    public void setNumPartidas(Integer numPartidas) {
        this.numPartidas = numPartidas;
    }

    public Integer getNumJogosDif() {
        return numJogosDif;
    }

    public void setNumJogosDif(Integer numJogosDif) {
        this.numJogosDif = numJogosDif;
    }

    public Integer getTotalPontos() {
        return totalPontos;
    }

    public void setTotalPontos(Integer totalPontos) {
        this.totalPontos = totalPontos;
    }

}