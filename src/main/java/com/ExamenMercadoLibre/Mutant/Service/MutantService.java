package com.ExamenMercadoLibre.Mutant.Service;


import com.ExamenMercadoLibre.Mutant.Model.ResponseDTO;
import com.ExamenMercadoLibre.Mutant.Excepcion.IncorrectNitrogenBaseException;
import com.ExamenMercadoLibre.Mutant.Excepcion.InvalidDataReceivedException;
import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceException;


public interface MutantService {
    boolean isMutant(String[] dna) throws ServiceException, InvalidDataReceivedException, IncorrectNitrogenBaseException;// throws ServiceException, InputValidationException;
}

