package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PontuacaoId implements Serializable {
    private static final long serialVersionUID = -8559042293378513499L;
    @Column(name = "id_partida", nullable = false)
    private Integer idPartida;

    @Column(name = "id_jogador", nullable = false)
    private Integer idJogador;

    public Integer getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Integer idPartida) {
        this.idPartida = idPartida;
    }

    public Integer getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Integer idJogador) {
        this.idJogador = idJogador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PontuacaoId entity = (PontuacaoId) o;
        return Objects.equals(this.idPartida, entity.idPartida) &&
                Objects.equals(this.idJogador, entity.idJogador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPartida, idJogador);
    }

}