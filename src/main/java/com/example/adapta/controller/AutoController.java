package com.example.adapta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.adapta.model.Aula;
import com.example.adapta.service.AulaService;
import com.example.adapta.dto.AprovacaoRequestDTO;

@RestController
@RequestMapping("/api/aulas")
@CrossOrigin(origins = "*")
public class AutoController {
    private AulaService service = null;

    public AutoController(AulaService service){
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<Aula> uploadMaterial(@RequestParam("file") MultipartFile file){
        System.out.println(">>> Requisição de Upload recebida!");
        System.out.println("Nome do Arquivo: " + file.getOriginalFilename());
        System.out.println("Tamanho: " + file.getSize() + "bytes");
        
        Aula aula = service.processarEGravarArquivo(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(aula);
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<Aula> aprovarMaterial(@PathVariable Long id, @RequestBody AprovacaoRequestDTO dto){
        Aula aulaAprovada = service.aprovarAula(id, dto.getTextoSimplificado());
        return ResponseEntity.ok(aulaAprovada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aula> obterAula(@PathVariable Long id){
        Aula aula = service.buscarPorId(id);
        return ResponseEntity.ok(aula);
    }

    @GetMapping
    public ResponseEntity<List<Aula>> listarTodasAulas(){
        List<Aula> aulas = service.listarTodas();
        return ResponseEntity.ok(aulas);
    }
}
