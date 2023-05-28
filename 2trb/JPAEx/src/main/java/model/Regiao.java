package model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "regiao", schema = "dbo")
public class Regiao {
    @Id
    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToMany(mappedBy = "regiao")
    private Set<Jogador> jogadors = new LinkedHashSet<>();

    @OneToMany(mappedBy = "regiao")
    private Set<Partida> partidas = new LinkedHashSet<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Jogador> getJogadors() {
        return jogadors;
    }

    public void setJogadors(Set<Jogador> jogadors) {
        this.jogadors = jogadors;
    }

    public Set<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(Set<Partida> partidas) {
        this.partidas = partidas;
    }

    // Implementar o método toString() para que o nome da região seja exibido
    public String toString() {
        return this.nome;
    }
}