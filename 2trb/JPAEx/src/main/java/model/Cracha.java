package model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "cracha", schema = "dbo")
public class Cracha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogo", nullable = false)
    private Jogo idJogo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "pontuacao", nullable = false)
    private Integer pontuacao;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToMany
    @JoinTable(name = "crachas_jogador",
            joinColumns = @JoinColumn(name = "id_cracha"),
            inverseJoinColumns = @JoinColumn(name = "id_jogador"))
    private Set<Jogador> jogadors = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Jogo getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(Jogo idJogo) {
        this.idJogo = idJogo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Jogador> getJogadors() {
        return jogadors;
    }

    public void setJogadors(Set<Jogador> jogadors) {
        this.jogadors = jogadors;
    }

}