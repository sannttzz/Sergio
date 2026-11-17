package com.cidadeemdia.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historicos_status")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HistoricoStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ocorrencia_id", nullable = false)
    private Ocorrencia ocorrencia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ocorrencia.Status statusAnterior;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ocorrencia.Status statusNovo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime data = LocalDateTime.now();

    @Column(length = 255)
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Perfil perfilQueAlterou;

    public enum Perfil {
        CIDADAO, ATENDENTE, ADMIN
    }
}
