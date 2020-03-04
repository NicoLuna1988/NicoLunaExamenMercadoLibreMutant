package com.ExamenMercadoLibre.Mutant.Service;


import com.ExamenMercadoLibre.Mutant.Excepcion.IncorrectNitrogenBaseException;
import com.ExamenMercadoLibre.Mutant.Excepcion.InvalidDataReceivedException;
import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceMutantException;

public interface DnaAnalyzeService {
    boolean isMutant(String[] dna) throws ServiceMutantException, InvalidDataReceivedException, IncorrectNitrogenBaseException;

}
