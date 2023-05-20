package model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "conversa", schema = "dbo")
public class Conversa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @ManyToMany
    @JoinTable(name = "conversa_jogador",
            joinColumns = @JoinColumn(name = "id_conversa"),
            inverseJoinColumns = @JoinColumn(name = "id_jogador"))
    private Set<Jogador> jogadors = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idConversa")
    private Set<Mensagem> mensagems = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Set<Mensagem> getMensagems() {
        return mensagems;
    }

    public void setMensagems(Set<Mensagem> mensagems) {
        this.mensagems = mensagems;
    }

}