package model;

import jakarta.persistence.*;

@Entity
@Table(name = "crachas_jogador", schema = "dbo")
public class CrachasJogador {
    @EmbeddedId
    private CrachasJogadorId id;

    @MapsId("idJogador")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador idJogador;

    @MapsId("idCracha")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cracha", nullable = false)
    private Cracha idCracha;

    public CrachasJogadorId getId() {
        return id;
    }

    public void setId(CrachasJogadorId id) {
        this.id = id;
    }

    public Jogador getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Jogador idJogador) {
        this.idJogador = idJogador;
    }

    public Cracha getIdCracha() {
        return idCracha;
    }

    public void setIdCracha(Cracha idCracha) {
        this.idCracha = idCracha;
    }

}