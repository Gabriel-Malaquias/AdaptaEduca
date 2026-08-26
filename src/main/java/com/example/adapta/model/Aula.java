package com.example.adapta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_aulas")
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Lob
    private String titulo;
    
    @Lob
    private String textoOriginal;
    private String textoSimplificado;
    private boolean aprovado;

    public Aula(){

    }

    

    public Aula(String titulo, String textoOriginal, String textoSimplificado){
        this.titulo = titulo;
        this.textoOriginal = textoOriginal;
        this.textoSimplificado = textoSimplificado;
        this.aprovado = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTextoOriginal() {
        return textoOriginal;
    }

    public void setTextoOriginal(String textoOriginal) {
        this.textoOriginal = textoOriginal;
    }

    public String getTextoSimplificado() {
        return textoSimplificado;
    }

    public void setTextoSimplificado(String textoSimplificado) {
        this.textoSimplificado = textoSimplificado;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }
}
