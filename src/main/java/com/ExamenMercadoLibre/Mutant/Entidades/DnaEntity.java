package com.ExamenMercadoLibre.Mutant.Entidades;

public class DnaEntity {
    private String[] dna;
    public DnaEntity() {

    }
    public DnaEntity(String[] dna) {
        this.dna = dna;
    }

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }
}
