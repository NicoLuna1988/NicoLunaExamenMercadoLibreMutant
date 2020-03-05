package com.ExamenMercadoLibre.Mutant.Service;

import com.ExamenMercadoLibre.Mutant.DbData.DnaDao;
import com.ExamenMercadoLibre.Mutant.Model.EnumErrorCode;
import com.ExamenMercadoLibre.Mutant.Model.ResponseDTO;
import com.ExamenMercadoLibre.Mutant.Excepcion.IncorrectNitrogenBaseException;
import com.ExamenMercadoLibre.Mutant.Excepcion.InvalidDataReceivedException;
import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceMutantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class MutantServiceImpl implements MutantService {
    @Autowired
    private DnaDao DnaDaoService;
    private static final Pattern NITROGENOUS_BASE_PATTERN = Pattern.compile("[atcg]+", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean isMutant(String[] dna) throws ServiceMutantException, InvalidDataReceivedException, IncorrectNitrogenBaseException {
        boolean isMutant;
        DnaAnalyzeServiceImpl DnaAnalize = new DnaAnalyzeServiceImpl();
        try {
            isMutant = DnaAnalize.isMutant(dna);
            String DnaSequence=DnaToString(dna);
            boolean existDb= this.DnaDaoService.ExistDnaSequence(DnaSequence);
            if(!existDb){
                this.DnaDaoService.InsertDnaSequence(DnaToString(dna),isMutant);
            }

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

    private String DnaToString(String[] dna) {

        StringBuffer sb = new StringBuffer();
        sb.append("[dna:");
        for (int position = 0; position < dna.length; position++) {
                if (position==dna.length -1){
                    sb.append(dna[position].toUpperCase());
                }else {
                    sb.append(dna[position].toUpperCase()).append(" ");
                }
        }
        sb.append("]");

        return sb.toString();
    }
}
