package com.ExamenMercadoLibre.Mutant.DbData;

import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceMutantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DnaDaoImpl implements DnaDao {
    public DnaDaoImpl() {
        super();
    }

    // we are autowiring jdbc template,
    // using the properties we have configured spring automatically
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public boolean InsertDnaSequence(String DnaSequence, boolean isMutant) throws ServiceMutantException {
        try {
            Integer result = jdbcTemplate.update("INSERT INTO Dna (DnaSequence, IsMutant) VALUES (?, ?)", DnaSequence, isMutant);
            return result != null && result > 0;
        } catch (Exception ex) {
            throw new ServiceMutantException(ex.getMessage());
        }

    }

    @Override
    public boolean ExistDnaSequence(String DnaSequence) throws ServiceMutantException {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Dna WHERE DnaSequence = ?", new Object[]{DnaSequence}, Integer.class);
            return result != null && result > 0;
        } catch (Exception ex) {
            throw new ServiceMutantException(ex.getMessage());
        }
    }

    @Override
    public int getDnaHumanCount() throws ServiceMutantException {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Dna WHERE IsMutant=false;", Integer.class);

            return result;
        } catch (Exception ex) {
            throw new ServiceMutantException(ex.getMessage());
        }
    }

    @Override
    public int getDnaMutantCount() throws ServiceMutantException {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Dna WHERE IsMutant=true;", Integer.class);
            return result;
        } catch (Exception ex) {
            throw new ServiceMutantException(ex.getMessage());
        }
    }

}
