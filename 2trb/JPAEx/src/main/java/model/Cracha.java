package model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "cracha", schema = "dbo")
public class Cracha {

    @Version
    @Column(name = "version")
    private int version;

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

    // How do i add a cracha to a jogador?
    // i don't want a link
    // i want to add a cracha to a jogador
//    @ManyToMany
//    @JoinTable(name = "crachas_jogador",
//            joinColumns = @JoinColumn(name = "id_cracha"),
//            inverseJoinColumns = @JoinColumn(name = "id_jogador"))
//    private Set<Jogador> crachas_jogador = new LinkedHashSet<>();

    // How do i update the crachas_jogador table into the database?
    // i want to add a cracha to a jogador
//    public void addCrachas_jogador(Jogador jogador) {
//        crachas_jogador.add(jogador);
//    }

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

    public String toString(){
        return "id: " + id + "\n" +
                "idJogo: " + idJogo.getId() + "\n" +
                "nome: " + nome + "\n" +
                "pontuacao: " + pontuacao + "\n" +
                "imageUrl: " + imageUrl + "\n";
    }

    public void increasePointsByTwentyPercent() {
        this.pontuacao = (int) (this.pontuacao * 1.2);
    }
}