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

//    @JoinTable(name = "Amizade", joinColumns = {
//    @JoinColumn(name = "Jogador_Que_Adiciona", referencedColumnName = "jogador_id", nullable =   false)}, inverseJoinColumns = {
//    @JoinColumn(name = "Jogador_Adicionado", referencedColumnName = "jogador_id", nullable = false)})
//    @ManyToMany
//    private Set<Jogador> amizade;

//    @ManyToMany(mappedBy = "amizade")
//    private Set<Jogador> aceitou_amizade;

    @OneToMany(mappedBy = "idJogador")
    private Set<Compra> compras = new LinkedHashSet<>();

//    @ManyToMany(mappedBy = "conversa_jogador")
//    private Set<Conversa> conversaJogadors = new LinkedHashSet<>();

//    @ManyToMany(mappedBy = "crachas_jogador")
//    private Set<Cracha> crachas = new LinkedHashSet<>();

    @OneToOne(mappedBy = "jogador")
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

    public Set<Compra> getCompras() {
        return compras;
    }

    public void setCompras(Set<Compra> compras) {
        this.compras = compras;
    }

//    public Set<Cracha> getCrachas() {
//        return crachas;
//    }
//
//    public void setCrachas(Set<Cracha> crachas) {
//        this.crachas = crachas;
//    }

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

    // Implementar o método toString() para que o programa consiga imprimir o objeto Jogador
    @Override
    public String toString() {
        return "Jogador{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", estado='" + estado + '\'' +
                ", regiao=" + regiao +
//                ", compras=" + compras +
//                ", crachas=" + crachas +
//                ", estatisticasJogador=" + estatisticasJogador +
//                ", mensagems=" + mensagems +
//                ", pontuacaos=" + pontuacaos +
                '}';
    }

}