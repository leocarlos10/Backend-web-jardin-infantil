package com.jardininfantil.web_institucional.controller;

import com.jardininfantil.web_institucional.dto.caracterizacion.CaracterizacionResponse;
import com.jardininfantil.web_institucional.dto.caracterizacion.CaracterizacionRequest;
import com.jardininfantil.web_institucional.dto.common.Response;
import com.jardininfantil.web_institucional.service.AcudienteService;
import com.jardininfantil.web_institucional.service.CaracterizacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
 
@RestController
@RequestMapping("/api/v1/caracterizacion")
public class CaracterizacionController {

    private final CaracterizacionService caracterizacionService;

    public CaracterizacionController(
        CaracterizacionService caracterizacionService
    ) {
        this.caracterizacionService = caracterizacionService;
    }

    @PostMapping
    public ResponseEntity<
        Response<CaracterizacionResponse>
    > createCaracterizacion(@RequestBody CaracterizacionRequest request) {
        CaracterizacionResponse caracterizacion =
            caracterizacionService.crearCaracterizacion(request);

        Response<CaracterizacionResponse> response = Response.<
                CaracterizacionResponse
            >builder()
            .responseCode(HttpStatus.OK.value())
            .responseMessage("SUCCESS")
            .data(caracterizacion)
            .build();
            
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
