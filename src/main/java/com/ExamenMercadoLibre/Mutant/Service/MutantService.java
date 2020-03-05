package com.ExamenMercadoLibre.Mutant.Service;


import com.ExamenMercadoLibre.Mutant.Model.ResponseDTO;
import com.ExamenMercadoLibre.Mutant.Excepcion.IncorrectNitrogenBaseException;
import com.ExamenMercadoLibre.Mutant.Excepcion.InvalidDataReceivedException;
import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceMutantException;


public interface MutantService {

    boolean isMutant(String[] dna) throws ServiceMutantException, InvalidDataReceivedException, IncorrectNitrogenBaseException;// throws ServiceException, InputValidationException;
    ResponseDTO isDnaValid(String[] dna);

}

