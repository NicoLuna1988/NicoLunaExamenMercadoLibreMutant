package com.ExamenMercadoLibre.Mutant.Controller;

import com.ExamenMercadoLibre.Mutant.Model.DnaSequence;
import com.ExamenMercadoLibre.Mutant.Model.EnumErrorCode;
import com.ExamenMercadoLibre.Mutant.Model.ResponseDTO;
import com.ExamenMercadoLibre.Mutant.Excepcion.IncorrectNitrogenBaseException;
import com.ExamenMercadoLibre.Mutant.Excepcion.InvalidDataReceivedException;
import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceException;
import com.ExamenMercadoLibre.Mutant.Model.Stats;
import com.ExamenMercadoLibre.Mutant.Service.MutantService;
import com.ExamenMercadoLibre.Mutant.Service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller for mutant api
 * @author Nicolás Andres Luna
 */
@RestController
@CrossOrigin
public class MutantController {
    @Autowired
    private MutantService mutantsService;
    @Autowired
    StatsService statsService;
    private Stats stats;

    /**
     * Method that health check of the load balancer
     */
    @GetMapping(value = "status")
    String checkStatus() {
        return "ok";
    }


    /**
     * Method that analyzes a DNA sequence and determines if it is human or mutant
     * @param dna String[]
     * @return is mutant 200(OK) / is Human 403(FORBIDDEN)
     */
    @RequestMapping(value = "/mutant", method = RequestMethod.POST,produces = "application/json; charset-UTF-8")
    public ResponseEntity<ResponseDTO> mutant(@RequestBody DnaSequence dna) {
        ResponseDTO responseDTO=new ResponseDTO();
        ResponseEntity<ResponseDTO> responseEntity;
        boolean isMutant;
        try {
            if (dna.getDna() != null) {
                isMutant = mutantsService.isMutant(dna.getDna());

                if (isMutant) {
                    responseDTO.setMessage("Is Mutant");
                    responseEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
                } else {
                    responseDTO.setMessage("Is Human");
                    responseEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.FORBIDDEN);
                }
            } else {
                responseDTO.setErrorCode(EnumErrorCode.InvalidDataReceived.getErrorCode());
                responseDTO.setMessage("The data structure sent is not correct, please verify the documentation.");
                responseEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (ServiceException e) {
            responseDTO.setErrorCode(EnumErrorCode.GeneralException.getErrorCode());
            responseDTO.setMessage(e.getMessage());
            responseEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (InvalidDataReceivedException e) {
            responseDTO.setErrorCode(EnumErrorCode.InvalidDataReceived.getErrorCode());
            responseDTO.setMessage(e.getMessage());
            responseEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IncorrectNitrogenBaseException e) {
            responseDTO.setErrorCode(EnumErrorCode.IncorrectNitrogenBase.getErrorCode());
            responseDTO.setMessage(e.getMessage());
            responseEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            responseDTO.setErrorCode(EnumErrorCode.GeneralException.getErrorCode());
            responseDTO.setMessage(e.getMessage());
            responseEntity = new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return responseEntity;
    }

    /**
     * Method that returns the statistics of the sequences analyzed
     * @return a Json data whit the statistics 200(OK)
     */
    @RequestMapping(value = "/stats", method = RequestMethod.GET, produces = "application/json; charset-UTF-8")
    public ResponseEntity<Stats> getStatics() {

        try {
            stats = statsService.GetStatsDna();
            return new ResponseEntity<>(stats, HttpStatus.OK);
        } catch (ServiceException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found", ex);
        }
    }

}


