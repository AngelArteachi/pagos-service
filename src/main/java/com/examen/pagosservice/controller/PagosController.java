package com.examen.pagosservice.controller;

import com.examen.pagosservice.model.Pago;
import com.examen.pagosservice.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagosController {

    @Autowired
    private PagoRepository repository;

    @Autowired
    private org.springframework.kafka.core.KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @PostMapping("/procesar")
    public org.springframework.http.ResponseEntity<?> procesarPago(@RequestBody Pago pago) {
        pago.setStatus("COMPLETADO");
        try {
            Pago saved = repository.save(pago);
            String payload = objectMapper.writeValueAsString(saved);
            kafkaTemplate.send("payment_received_events", payload);
            return org.springframework.http.ResponseEntity.ok(saved);
        } catch (Exception e) {
            return org.springframework.http.ResponseEntity.status(500)
                    .body("Error al crear pago: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Pago getPago(@PathVariable String id) {
        return repository.findById(id).orElse(null);
    }

    @GetMapping("/orden/{id}")
    public List<Pago> getPagoByOrden(@PathVariable String id) {
        return repository.findByOrdenId(id);
    }

    @PutMapping("/{id}/reembolso")
    public Pago procesarReembolso(@PathVariable String id) {
        Pago pago = repository.findById(id).orElse(null);
        if (pago != null) {
            pago.setStatus("REEMBOLSADO");
            return repository.save(pago);
        }
        return null;
    }
}
