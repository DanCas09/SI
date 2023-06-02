package model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "partida", schema = "dbo")
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogo", nullable = false)
    private Jogo idJogo;

    @Column(name = "data_ini", nullable = false)
    private Timestamp dataIni;

    @Column(name = "data_fim")
    private Timestamp dataFim;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "regiao", nullable = false)
    private Regiao regiao;

    @OneToOne(mappedBy = "partida")
    private PartidaMultijogador partidaMultijogador;

    @OneToOne(mappedBy = "partida")
    private PartidaNormal partidaNormal;

    @OneToMany(mappedBy = "idPartida")
    private Set<Pontuacao> pontuacaos = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Jogo getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(Jogo idJogo) {
        this.idJogo = idJogo;
    }

    public Timestamp getDataIni() {
        return dataIni;
    }

    public void setDataIni(Timestamp dataIni) {
        this.dataIni = dataIni;
    }

    public Timestamp getDataFim() {
        return dataFim;
    }

    public void setDataFim(Timestamp dataFim) {
        this.dataFim = dataFim;
    }

    public Regiao getRegiao() {
        return regiao;
    }

    public void setRegiao(Regiao regiao) {
        this.regiao = regiao;
    }

    public PartidaMultijogador getPartidaMultijogador() {
        return partidaMultijogador;
    }

    public void setPartidaMultijogador(PartidaMultijogador partidaMultijogador) {
        this.partidaMultijogador = partidaMultijogador;
    }

    public PartidaNormal getPartidaNormal() {
        return partidaNormal;
    }

    public void setPartidaNormal(PartidaNormal partidaNormal) {
        this.partidaNormal = partidaNormal;
    }

    public Set<Pontuacao> getPontuacaos() {
        return pontuacaos;
    }

    public void setPontuacaos(Set<Pontuacao> pontuacaos) {
        this.pontuacaos = pontuacaos;
    }

}