package com.ExamenMercadoLibre.Mutant.DbData;

import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceException;
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
    /**
     * method that inserts the DNA sequence into the database
     * @Params DnaSequence String
     * @Params isMutant boolean
     * @return boolean
     */
    public boolean InsertDnaSequence(String DnaSequence, boolean isMutant) throws ServiceException {
        try {
            Integer result = jdbcTemplate.update("INSERT INTO Dna (DnaSequence, IsMutant) VALUES (?, ?)", DnaSequence, isMutant);
            return result != null && result > 0;
        }
        catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
    /**
     * Method that looks for the existence of a DNA sequence in the database
     * @Params DnaSequence String
     * @return boolean
     */
    @Override
    public boolean ExistDnaSequence(String DnaSequence) throws ServiceException {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Dna WHERE DnaSequence = ?", new Object[]{DnaSequence}, Integer.class);
            return result != null && result > 0;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
    /**
     * Method that returns the amount of mutants
     * @return int
     */
    @Override
    public int getDnaHumanCount() throws ServiceException {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Dna WHERE IsMutant=false;", Integer.class);

            return result;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
    /**
     * Method that returns the amount of mutants
     * @return int
     */
    @Override
    public int getDnaMutantCount() throws ServiceException {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Dna WHERE IsMutant=true;", Integer.class);
            return result;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

}
