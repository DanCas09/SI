package model;

import jakarta.persistence.*;

@Entity
@Table(name = "partida_multijogador", schema = "dbo")
public class PartidaMultijogador {
    @Id
    @Column(name = "id_partida", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_partida", nullable = false)
    private Partida partida;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}