package model;


import jakarta.persistence.*;

@Entity
@Table(name = "jogadorTotalInfo", schema = "dbo")
public class JogadorTotalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "num_partidas")
    private Integer num_partidas;

    @Column(name = "num_jogos")
    private Integer num_jogos;

    @Column(name = "pontuacao")
    private Integer pontuacao;

    public Integer getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Integer getNum_partidas() {
        return num_partidas;
    }

    public Integer getNum_jogos() {
        return num_jogos;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }



}