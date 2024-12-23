package com.fsepulveda.pruebabatch.domain;


import lombok.Data;
import javax.persistence.*;


@Entity
@Data
@Table(name = "SUC_PUNTO_VENTA_PREPAGO")
public class PuntoDeVentaPrepago {

    @Id
    @GeneratedValue(generator = "puntoVentaGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "puntoVentaGenerator", sequenceName = "SUC_PUNTO_VENTA_PREPAGO_SEQ", allocationSize = 50)
    private Long id;
    private String direccion;
    private String localidad;
    private String nombre;
    private String latitud;
    private String longitud;
    @Column(name = "sin_costo")
    private Boolean sinCosto;

    public void setSinCosto(String sinCosto) {
        this.sinCosto = !sinCosto.equals("NO");
    }
}