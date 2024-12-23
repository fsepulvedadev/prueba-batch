package com.fsepulveda.pruebabatch.writer;

import com.fsepulveda.pruebabatch.domain.PuntoDeVentaPrepago;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.Column;
import java.util.List;

public class PuntoDeVentaWriter implements ItemWriter<PuntoDeVentaPrepago> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void write(List<? extends PuntoDeVentaPrepago> puntos) throws Exception {
        for (PuntoDeVentaPrepago punto : puntos) {
            String sql = "INSERT INTO datos (id, direccion,latitud,longitud, localidad, nombre, sin_costo) VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, punto.getId(), punto.getDireccion(), punto.getLatitud(), punto.getLongitud(), punto.getLocalidad(), punto.getNombre(), punto.getSinCosto());
        }
    }
}
