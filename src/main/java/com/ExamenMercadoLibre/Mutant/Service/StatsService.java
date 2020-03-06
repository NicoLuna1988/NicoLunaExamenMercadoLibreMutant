package com.ExamenMercadoLibre.Mutant.Service;

import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceException;
import com.ExamenMercadoLibre.Mutant.Model.Stats;


public interface StatsService {

    Stats GetStatsDna() throws ServiceException;

}
