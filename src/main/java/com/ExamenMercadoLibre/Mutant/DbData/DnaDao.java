package com.ExamenMercadoLibre.Mutant.DbData;

import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceMutantException;

public interface DnaDao {
     boolean InsertDnaSequence(String DnaSequence, boolean isMutant) throws ServiceMutantException;
     boolean ExistDnaSequence(String DnaSequence) throws ServiceMutantException;
     int getDnaHumanCount() throws ServiceMutantException;
     int getDnaMutantCount() throws ServiceMutantException;
}
