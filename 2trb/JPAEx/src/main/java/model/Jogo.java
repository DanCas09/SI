package model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "jogo", schema = "dbo")
public class Jogo {
    @Id
    @Column(name = "id", nullable = false, length = 10)
    private String id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "url", nullable = false)
    private String url;

    @OneToMany(mappedBy = "idJogo")
    private Set<Compra> compras = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idJogo")
    private Set<Cracha> crachas = new LinkedHashSet<>();

    @OneToOne(mappedBy = "jogo")
    private EstatisticasJogo estatisticasJogo;

    @OneToMany(mappedBy = "idJogo")
    private Set<Partida> partidas = new LinkedHashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Compra> getCompras() {
        return compras;
    }

    public void setCompras(Set<Compra> compras) {
        this.compras = compras;
    }

    public Set<Cracha> getCrachas() {
        return crachas;
    }

    public void setCrachas(Set<Cracha> crachas) {
        this.crachas = crachas;
    }

    public EstatisticasJogo getEstatisticasJogo() {
        return estatisticasJogo;
    }

    public void setEstatisticasJogo(EstatisticasJogo estatisticasJogo) {
        this.estatisticasJogo = estatisticasJogo;
    }

    public Set<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(Set<Partida> partidas) {
        this.partidas = partidas;
    }

    public String toString(){
        return "id: " + id + "\n" +
                "nome: " + nome + "\n" +
                "url: " + url + "\n";
    }

}