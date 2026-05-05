package com.examen.pagosservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "pagos")
public class Pago {
    @Id
    private String id;
    private String ordenId;
    private Double monto;
    private String metodo;
    private String status;
}
