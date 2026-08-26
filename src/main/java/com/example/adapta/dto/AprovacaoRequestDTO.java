package com.example.adapta.dto;

public class AprovacaoRequestDTO{
    private String textoSimplificado;

    public AprovacaoRequestDTO(){

    }

    public AprovacaoRequestDTO(String textoSimplificado){
        this.textoSimplificado = textoSimplificado;
    }

    public String getTextoSimplificado() {
        return textoSimplificado;
    }

    public void setTextoSimplificado(String textoSimplificado) {
        this.textoSimplificado = textoSimplificado;
    }

}