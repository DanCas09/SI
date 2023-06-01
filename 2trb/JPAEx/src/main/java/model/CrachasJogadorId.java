package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CrachasJogadorId implements Serializable {
    private static final long serialVersionUID = 2077337413822474679L;
    @Column(name = "id_jogador", nullable = false)
    private Integer idJogador;

    @Column(name = "id_cracha", nullable = false)
    private Integer idCracha;

    public Integer getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Integer idJogador) {
        this.idJogador = idJogador;
    }

    public Integer getIdCracha() {
        return idCracha;
    }

    public void setIdCracha(Integer idCracha) {
        this.idCracha = idCracha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrachasJogadorId entity = (CrachasJogadorId) o;
        return Objects.equals(this.idCracha, entity.idCracha) &&
                Objects.equals(this.idJogador, entity.idJogador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCracha, idJogador);
    }

}