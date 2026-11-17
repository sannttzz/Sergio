package com.cidadeemdia.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ocorrencias")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"confirmacoes", "historicos"})
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Size(min = 5, max = 100, message = "Título deve ter entre 5 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 10, max = 500, message = "Descrição deve ter entre 10 e 500 caracteres")
    @Column(nullable = false, length = 500)
    private String descricao;

    @NotBlank(message = "Rua é obrigatória")
    @Column(nullable = false, length = 100)
    private String rua;

    @NotBlank(message = "Número é obrigatório")
    @Column(nullable = false, length = 20)
    private String numero;

    @NotBlank(message = "Bairro é obrigatório")
    @Column(nullable = false, length = 100)
    private String bairro;

    @NotBlank(message = "Cidade é obrigatória")
    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(length = 255)
    private String referencia;

    @Column(length = 500)
    private String foto;

    @NotNull(message = "Urgência é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Urgencia urgencia;

    @NotNull(message = "Prioridade é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade;

    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.NOVA;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao = LocalDateTime.now();

    @OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Confirmacao> confirmacoes = new ArrayList<>();

    @OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoStatus> historicos = new ArrayList<>();

    @Transient
    public int getTotalConfirmacoes() {
        return confirmacoes.size();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public enum Urgencia {
        BAIXA, MEDIA, ALTA
    }

    public enum Prioridade {
        BAIXA, MEDIA, ALTA
    }

    public enum Status {
        NOVA, EM_ANALISE, EM_ANDAMENTO, RESOLVIDA
    }
}
