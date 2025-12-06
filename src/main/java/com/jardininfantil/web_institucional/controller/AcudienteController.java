package com.jardininfantil.web_institucional.controller;

import com.jardininfantil.web_institucional.dto.common.Response;
import com.jardininfantil.web_institucional.dto.acudiente.AcudienteRequest;
import com.jardininfantil.web_institucional.dto.acudiente.AcudienteResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.jardininfantil.web_institucional.service.AcudienteService; 
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/acudientes")
public class AcudienteController{

    private final AcudienteService acudienteService; 
    
    public AcudienteController(
        AcudienteService acudienteService  
    ){
        this.acudienteService = acudienteService;
    }
    
    @PostMapping
    public ResponseEntity<Response<AcudienteResponse>> crearAcudiente(
        @Valid @RequestBody AcudienteRequest request,
        Authentication authentication
    ) {
       AcudienteResponse acudiente = acudienteService.crearAcudiente(request ,authentication);
       Response<AcudienteResponse> response = Response.<AcudienteResponse>builder()
               .responseCode(HttpStatus.CREATED.value())
               .responseMessage("Acudiente creado exitosamente")
               .data(acudiente)
               .build();
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
   }
}
