package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ConversaJogadorId implements Serializable {
    private static final long serialVersionUID = 879534329627300538L;
    @Column(name = "id_conversa", nullable = false)
    private Integer idConversa;

    @Column(name = "id_jogador", nullable = false)
    private Integer idJogador;

    public Integer getIdConversa() {
        return idConversa;
    }

    public void setIdConversa(Integer idConversa) {
        this.idConversa = idConversa;
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
        ConversaJogadorId entity = (ConversaJogadorId) o;
        return Objects.equals(this.idConversa, entity.idConversa) &&
                Objects.equals(this.idJogador, entity.idJogador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConversa, idJogador);
    }

}