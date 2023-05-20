package model;

import jakarta.persistence.*;

@Entity
@Table(name = "partida_normal", schema = "dbo")
public class PartidaNormal {
    @Id
    @Column(name = "id_partida", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_partida", nullable = false)
    private Partida partida;

    @Column(name = "grau_dificuldade", nullable = false)
    private Integer grauDificuldade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Integer getGrauDificuldade() {
        return grauDificuldade;
    }

    public void setGrauDificuldade(Integer grauDificuldade) {
        this.grauDificuldade = grauDificuldade;
    }

}