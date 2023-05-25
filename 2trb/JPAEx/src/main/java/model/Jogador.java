package model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "jogador", schema = "dbo")
public class Jogador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "estado", nullable = false, length = 10)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "regiao", nullable = false)
    private Regiao regiao;

    @ManyToMany(mappedBy = "idJogador")
    private Set<Jogador> jogadors = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idJogador")
    private Set<Compra> compras = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "idJogador")
    private Set<Conversa> conversas = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "idJogador")
    private Set<Cracha> crachas = new LinkedHashSet<>();

    @OneToOne(mappedBy = "idJogador")
    private EstatisticasJogador estatisticasJogador;

    @OneToMany(mappedBy = "idJogador")
    private Set<Mensagem> mensagems = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idJogador")
    private Set<Pontuacao> pontuacaos = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Regiao getRegiao() {
        return regiao;
    }

    public void setRegiao(Regiao regiao) {
        this.regiao = regiao;
    }

    public Set<Jogador> getJogadors() {
        return jogadors;
    }

    public void setJogadors(Set<Jogador> jogadors) {
        this.jogadors = jogadors;
    }

    public Set<Compra> getCompras() {
        return compras;
    }

    public void setCompras(Set<Compra> compras) {
        this.compras = compras;
    }

    public Set<Conversa> getConversas() {
        return conversas;
    }

    public void setConversas(Set<Conversa> conversas) {
        this.conversas = conversas;
    }

    public Set<Cracha> getCrachas() {
        return crachas;
    }

    public void setCrachas(Set<Cracha> crachas) {
        this.crachas = crachas;
    }

    public EstatisticasJogador getEstatisticasJogador() {
        return estatisticasJogador;
    }

    public void setEstatisticasJogador(EstatisticasJogador estatisticasJogador) {
        this.estatisticasJogador = estatisticasJogador;
    }

    public Set<Mensagem> getMensagems() {
        return mensagems;
    }

    public void setMensagems(Set<Mensagem> mensagems) {
        this.mensagems = mensagems;
    }

    public Set<Pontuacao> getPontuacaos() {
        return pontuacaos;
    }

    public void setPontuacaos(Set<Pontuacao> pontuacaos) {
        this.pontuacaos = pontuacaos;
    }

}