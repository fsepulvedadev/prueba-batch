package com.fsepulveda.pruebabatch.reader;

import com.fsepulveda.pruebabatch.domain.PuntoDeVentaPrepago;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class PuntoDeVentaReader implements ItemReader<PuntoDeVentaPrepago> {

    private String pathArchivo;
    private Iterator<Row> rowIterator;
    private Workbook workbook;

    public PuntoDeVentaReader(String pathArchivo) {
        this.pathArchivo = pathArchivo;
        try {
            FileInputStream archivoExcel = new FileInputStream(new File(pathArchivo));
            workbook = WorkbookFactory.create(archivoExcel);
            Sheet sheet  = workbook.getSheetAt(0);
            rowIterator = sheet.rowIterator();
            rowIterator.next();
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo excel.", e);
        }
    }

    @Override
    public PuntoDeVentaPrepago read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if(rowIterator != null && rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getPhysicalNumberOfCells()== 0) {
                return null;
            }
            PuntoDeVentaPrepago puntoDeVentaPrepago = new PuntoDeVentaPrepago();
            puntoDeVentaPrepago.setNombre(row.getCell(0).getStringCellValue());
            puntoDeVentaPrepago.setDireccion(row.getCell(1).getStringCellValue());
            puntoDeVentaPrepago.setLocalidad(row.getCell(2).getStringCellValue());
            puntoDeVentaPrepago.setLatitud(row.getCell(3).getStringCellValue());
            puntoDeVentaPrepago.setLongitud(row.getCell(4).getStringCellValue());
            puntoDeVentaPrepago.setSinCosto(row.getCell(5).getStringCellValue());

            return puntoDeVentaPrepago;
        } else {
            if(workbook != null) {
                workbook.close();
            }
            return null;
        }
    }
}
