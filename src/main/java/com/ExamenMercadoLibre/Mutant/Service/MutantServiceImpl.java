package com.ExamenMercadoLibre.Mutant.Service;

import com.ExamenMercadoLibre.Mutant.Entidades.EnumErrorCode;
import com.ExamenMercadoLibre.Mutant.Entidades.ResponseDTO;
import com.ExamenMercadoLibre.Mutant.Excepcion.IncorrectNitrogenBaseException;
import com.ExamenMercadoLibre.Mutant.Excepcion.InvalidDataReceivedException;
import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceMutantException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class MutantServiceImpl implements MutantService {
    private static final Pattern NITROGENOUS_BASE_PATTERN = Pattern.compile("[atcg]+", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean isMutant(String[] dna) throws ServiceMutantException, InvalidDataReceivedException, IncorrectNitrogenBaseException {
        boolean isMutant;
        DnaAnalyzeServiceImpl DnaAnalize = new DnaAnalyzeServiceImpl();
        final String MUTANT = "Mutant";
        final String HUMAN = "Human";

        try {
            isMutant = DnaAnalize.isMutant(dna);

            return isMutant;

        } catch (ServiceMutantException ex) {
            throw new ServiceMutantException(ex.getMessage());
        } catch (InvalidDataReceivedException ex) {
            throw new InvalidDataReceivedException(ex.getMessage());
        } catch (IncorrectNitrogenBaseException ex) {
            throw new IncorrectNitrogenBaseException(ex.getMessage());
        } catch (Exception ex) {
            throw new ServiceMutantException(ex.getMessage());
        }


    }

    @Override
    public ResponseDTO isDnaValid(String[] dna) {
        int dnaSequenceLength = dna.length;
        Pattern pattern = Pattern.compile("[acgt]+", Pattern.CASE_INSENSITIVE);
        ResponseDTO responseDTO = new ResponseDTO();

        for (int position = 0; position < dnaSequenceLength; position++) {
            if (dna[position].length() < 4) {
                responseDTO.setErrorCode(EnumErrorCode.InvalidDataReceived.getErrorCode());
                responseDTO.setMessage("no tiene los cartacteres minimos para determinar si es mutante o humano");
                break;
            } else if (dna[position].length() != dnaSequenceLength) {
                responseDTO.setErrorCode(EnumErrorCode.InvalidDataReceived.getErrorCode());
                responseDTO.setMessage("El tamaño bases nitrogendas de la secuencia no coincide con el total de secuencias");
                break;
            } else if (!pattern.matcher(dna[position]).matches()) {
                responseDTO.setErrorCode(EnumErrorCode.IncorrectNitrogenBase.getErrorCode());
                responseDTO.setMessage("EL Dna contiene una base nnitrogenada no valida");
                break;
            }
        }

        return responseDTO;
    }

}
