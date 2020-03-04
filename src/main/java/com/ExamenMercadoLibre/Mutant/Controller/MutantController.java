package com.ExamenMercadoLibre.Mutant.Controller;

import com.ExamenMercadoLibre.Mutant.Entidades.DnaEntity;
import com.ExamenMercadoLibre.Mutant.Entidades.EnumErrorCode;
import com.ExamenMercadoLibre.Mutant.Entidades.ResponseDTO;
import com.ExamenMercadoLibre.Mutant.Excepcion.IncorrectNitrogenBaseException;
import com.ExamenMercadoLibre.Mutant.Excepcion.InvalidDataReceivedException;
import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceMutantException;
import com.ExamenMercadoLibre.Mutant.Service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;





@RestController
public class MutantController {
    @Autowired
    private MutantService mutantsService;

    @RequestMapping(value = "/mutant", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> mutant(@RequestBody DnaEntity dna) {
        ResponseDTO responseDTO=new ResponseDTO();
        ResponseEntity<ResponseDTO> responseEntity;
        boolean isMutant;
        ResponseDTO isDnaValid;
        try {
                if(dna.getDna()!=null){
                    isMutant = mutantsService.isMutant(dna.getDna());

                    if (isMutant) {
                        responseDTO.setMessage("Is Mutant");
                        responseEntity = new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
                    } else {
                        responseDTO.setMessage("Is Human");
                        responseEntity = new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.FORBIDDEN);
                    }
                }
                else{
                    responseDTO.setErrorCode(EnumErrorCode.InvalidDataReceived.getErrorCode());
                    responseDTO.setMessage("La estructura de datos enviada no es correcta,verifique la documentación por favor.");
                    responseEntity = new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
                }


        }
        catch (ServiceMutantException e) {
            responseDTO.setErrorCode(EnumErrorCode.GeneralException.getErrorCode());
            responseDTO.setMessage(e.getMessage());
            responseEntity = new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);

        }
        catch (InvalidDataReceivedException e) {
            responseDTO.setErrorCode(EnumErrorCode.InvalidDataReceived.getErrorCode());
            responseDTO.setMessage(e.getMessage());
            responseEntity = new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (IncorrectNitrogenBaseException e) {
            responseDTO.setErrorCode(EnumErrorCode.IncorrectNitrogenBase.getErrorCode());
            responseDTO.setMessage(e.getMessage());
            responseEntity = new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception  e) {
            responseDTO.setErrorCode(EnumErrorCode.GeneralException.getErrorCode());
            responseDTO.setMessage(e.getMessage());
            responseEntity = new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);

       }

        return responseEntity;
    }


}
