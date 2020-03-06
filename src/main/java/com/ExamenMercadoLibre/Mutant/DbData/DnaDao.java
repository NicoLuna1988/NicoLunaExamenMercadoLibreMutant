package com.ExamenMercadoLibre.Mutant.DbData;

import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceException;

public interface DnaDao {
     boolean InsertDnaSequence(String DnaSequence, boolean isMutant) throws ServiceException;
     boolean ExistDnaSequence(String DnaSequence) throws ServiceException;
     int getDnaHumanCount() throws ServiceException;
     int getDnaMutantCount() throws ServiceException;
}
