package com.ExamenMercadoLibre.Mutant.Service;

import com.ExamenMercadoLibre.Mutant.DbData.DnaDao;
import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceException;
import com.ExamenMercadoLibre.Mutant.Model.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private DnaDao dnaDaoService;
    private int count_Human;
    private int count_Mutant;
    private double ratio;

    /**
     * Method that returns an object with the statistical values ​​of the DNA sequences analyzed
     * @return Stats
     */
    @Override
    public Stats GetStatsDna() throws ServiceException {
        Stats stats = new Stats();
        try {
            count_Human = dnaDaoService.getDnaHumanCount();
            count_Mutant = dnaDaoService.getDnaMutantCount();
            ratio = Math.round((count_Human > 0 ? count_Mutant / count_Human : 0) * 100.0) / 100.0;

            stats.setCount_human_dna(count_Human);
            stats.setCount_mutant_dna(count_Mutant);
            stats.setRatio(ratio);

            return stats;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}
