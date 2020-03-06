package com.ExamenMercadoLibre.Mutant.Service;

import com.ExamenMercadoLibre.Mutant.DbData.DnaDao;
import com.ExamenMercadoLibre.Mutant.Excepcion.IncorrectNitrogenBaseException;
import com.ExamenMercadoLibre.Mutant.Excepcion.InvalidDataReceivedException;
import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class MutantServiceImpl implements MutantService {
    @Autowired
    private DnaDao DnaDaoService;
    private static final Pattern NITROGENOUS_BASE_PATTERN = Pattern.compile("[atcg]+", Pattern.CASE_INSENSITIVE);


    /**
     * Method that analyzes the AND sequence and verifies if it exists in the database, if it does not exist then it inserts it
     *
     * @param dna String[]
     * @return boolean True(Mutant) / false (Human)
     */
    @Override
    public boolean isMutant(String[] dna) throws ServiceException, InvalidDataReceivedException, IncorrectNitrogenBaseException {
        boolean isMutant;
        DnaAnalyzeServiceImpl DnaAnalize = new DnaAnalyzeServiceImpl();
        try {
            isMutant = DnaAnalize.isMutant(dna);
            String DnaSequence = DnaToString(dna);
            boolean existDb = this.DnaDaoService.ExistDnaSequence(DnaSequence);
            if (!existDb) {
                this.DnaDaoService.InsertDnaSequence(DnaToString(dna), isMutant);
            }

            return isMutant;

        } catch (ServiceException ex) {
            throw new ServiceException(ex.getMessage());
        } catch (InvalidDataReceivedException ex) {
            throw new InvalidDataReceivedException(ex.getMessage());
        } catch (IncorrectNitrogenBaseException ex) {
            throw new IncorrectNitrogenBaseException(ex.getMessage());
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }

    }

    /**
     * Method that prepares the DNA sequence in the format to insert into the database
     * @param dna String[]
     * @return String
     */

    private String DnaToString(String[] dna) {

        StringBuffer sb = new StringBuffer();
        sb.append("[dna:");
        for (int position = 0; position < dna.length; position++) {
            if (position == dna.length - 1) {
                sb.append(dna[position].toUpperCase());
            } else {
                sb.append(dna[position].toUpperCase()).append(" ");
            }
        }
        sb.append("]");

        return sb.toString();
    }
}
