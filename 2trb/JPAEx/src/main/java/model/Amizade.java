package model;

import jakarta.persistence.*;

@Entity
@Table(name = "amizade", schema = "dbo")
public class Amizade {
    @EmbeddedId
    private AmizadeId id;

    @MapsId("idJogador")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador idJogador;

    @MapsId("idAmigo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_amigo", nullable = false)
    private Jogador idAmigo;

    public AmizadeId getId() {
        return id;
    }

    public void setId(AmizadeId id) {
        this.id = id;
    }

    public Jogador getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Jogador idJogador) {
        this.idJogador = idJogador;
    }

    public Jogador getIdAmigo() {
        return idAmigo;
    }

    public void setIdAmigo(Jogador idAmigo) {
        this.idAmigo = idAmigo;
    }

}