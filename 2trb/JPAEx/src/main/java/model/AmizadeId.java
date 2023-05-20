package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AmizadeId implements Serializable {
    private static final long serialVersionUID = -6500274106242836294L;
    @Column(name = "id_jogador", nullable = false)
    private Integer idJogador;

    @Column(name = "id_amigo", nullable = false)
    private Integer idAmigo;

    public Integer getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Integer idJogador) {
        this.idJogador = idJogador;
    }

    public Integer getIdAmigo() {
        return idAmigo;
    }

    public void setIdAmigo(Integer idAmigo) {
        this.idAmigo = idAmigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmizadeId entity = (AmizadeId) o;
        return Objects.equals(this.idAmigo, entity.idAmigo) &&
                Objects.equals(this.idJogador, entity.idJogador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAmigo, idJogador);
    }

}