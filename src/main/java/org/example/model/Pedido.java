package org.example.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.Setter;
import org.example.util.LocalDateTimeAdapter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@XmlRootElement(name = "pedido")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @XmlElement
    @Column(unique = true, nullable = false)
    private String numeroControle;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataCadastro;

    @XmlElement
    @Column(nullable = false)
    private String nomeProduto;

    @XmlElement
    @Column(nullable = false)
    private BigDecimal valorUnitario;

    @XmlElement
    private Integer quantidade;

    @XmlElement
    @Column(nullable = false)
    private Long codigoCliente;

    @XmlElement
    @Column(nullable = false)
    private BigDecimal valorTotal;

    // Getters e Setters...
}