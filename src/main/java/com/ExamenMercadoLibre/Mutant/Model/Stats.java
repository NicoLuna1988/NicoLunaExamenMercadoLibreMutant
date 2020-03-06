package com.ExamenMercadoLibre.Mutant.Model;


public class Stats{
    private long count_mutant_dna;
    private long count_human_dna;
    private double ratio;
    public Stats() {

    }
    public Stats(long mutants, long humans, double ratio) {
        this.count_mutant_dna = mutants;
        this.count_human_dna = humans;
        this.ratio = ratio;
    }

    public long getCount_mutant_dna() {
        return count_mutant_dna;
    }

    public void setCount_mutant_dna(long count_mutant_dna) {
        this.count_mutant_dna = count_mutant_dna;
    }

    public long getCount_human_dna() {
        return count_human_dna;
    }

    public void setCount_human_dna(long count_human_dna) {
        this.count_human_dna = count_human_dna;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }


    public String toString() {
        return "[mutants: " + count_mutant_dna + ", humans: " + count_human_dna + ", ratio: " + ratio + "]";
    }
}
