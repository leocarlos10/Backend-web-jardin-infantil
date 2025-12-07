package com.jardininfantil.web_institucional.controller;

import com.jardininfantil.web_institucional.dto.common.Response;
import com.jardininfantil.web_institucional.dto.familiar.FamiliarRequest;
import com.jardininfantil.web_institucional.dto.familiar.FamiliarResponse;
import com.jardininfantil.web_institucional.service.FamiliarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/familiar")
public class FamiliarController {

    private final FamiliarService familiarService;

    public FamiliarController(FamiliarService familiarService) {
        this.familiarService = familiarService;
    }

    @PostMapping
    public ResponseEntity<Response<FamiliarResponse>> createCaracterizacion(
      @Valid  @RequestBody FamiliarRequest request
    ) {
        FamiliarResponse familiar = familiarService.crearFamiliar(request);

        Response<FamiliarResponse> response = Response.<FamiliarResponse>builder()
            .responseCode(HttpStatus.OK.value())
            .responseMessage("SUCCESS")
            .data(familiar)
            .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}


