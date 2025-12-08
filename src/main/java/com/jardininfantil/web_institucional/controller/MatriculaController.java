package com.jardininfantil.web_institucional.controller;

import com.jardininfantil.web_institucional.dto.common.Response;
import com.jardininfantil.web_institucional.dto.matricula.MatriculaRequest;
import com.jardininfantil.web_institucional.dto.matricula.MatriculaResponse;
import com.jardininfantil.web_institucional.service.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Response<MatriculaResponse>> crearMatricula(@Valid @RequestBody MatriculaRequest request) {
        MatriculaResponse matricula = matriculaService.crearMatricula(request);
        Response<MatriculaResponse> response = Response.<MatriculaResponse>builder()
                .responseCode(HttpStatus.CREATED.value())
                .responseMessage("Matrícula creada exitosamente")
                .data(matricula)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<MatriculaResponse>> obtenerMatricula(@PathVariable Long id) {
        MatriculaResponse matricula = matriculaService.obtenerMatricula(id);
        Response<MatriculaResponse> response = Response.<MatriculaResponse>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Matrícula encontrada")
                .data(matricula)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/obtenerTodas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<List<MatriculaResponse>>> listarTodasMatriculas() {
        List<MatriculaResponse> matriculas = matriculaService.listarTodasMatriculas();
        Response<List<MatriculaResponse>> response = Response.<List<MatriculaResponse>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Lista de matrículas obtenida")
                .data(matriculas)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<Response<List<MatriculaResponse>>> listarMatriculasPorEstudiante(
            @PathVariable Long estudianteId) {
        List<MatriculaResponse> matriculas = matriculaService.listarMatriculasPorEstudiante(estudianteId);
        Response<List<MatriculaResponse>> response = Response.<List<MatriculaResponse>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Matrículas del estudiante obtenidas")
                .data(matriculas)
                .build();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/me")
    public ResponseEntity<Response<List<MatriculaResponse>>> misMatriculas( Authentication authentication) {
        List<MatriculaResponse> matriculas = matriculaService.misMatriculas(authentication);
        Response<List<MatriculaResponse>> response = Response.<List<MatriculaResponse>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Lista de matriculas obtenida")
                .data(matriculas)
                .build();
        return ResponseEntity.ok(response);
    }
    

    @PutMapping("/{id}/actualizar")
    public ResponseEntity<Response<MatriculaResponse>> actualizarMatricula(
            @PathVariable Long id,
            @Valid @RequestBody MatriculaRequest request) {
        MatriculaResponse matricula = matriculaService.actualizarMatricula(id, request);
        Response<MatriculaResponse> response = Response.<MatriculaResponse>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Matrícula actualizada exitosamente")
                .data(matricula)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Response<MatriculaResponse>> cancelarMatricula(@PathVariable Long id) {
        MatriculaResponse matricula = matriculaService.cancelarMatricula(id);
        Response<MatriculaResponse> response = Response.<MatriculaResponse>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Matrícula cancelada")
                .data(matricula)
                .build();
        return ResponseEntity.ok(response);
    }
}
