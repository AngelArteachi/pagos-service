package com.examen.pagosservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagos")
public class PagosController {

    @PostMapping("/procesar")
    public String procesarPago() {
        return "Pago procesado con éxito";
    }

    @GetMapping("/{id}")
    public String getPago(@PathVariable String id) {
        return "Detalle de pago: " + id;
    }

    @GetMapping("/orden/{id}")
    public String getPagoByOrden(@PathVariable String id) {
        return "Pagos asociados a la orden: " + id;
    }

    @PutMapping("/{id}/reembolso")
    public String procesarReembolso(@PathVariable String id) {
        return "Reembolso procesado para el pago: " + id;
    }
}
