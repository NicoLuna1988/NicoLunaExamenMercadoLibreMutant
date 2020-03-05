package com.ExamenMercadoLibre.Mutant.Service;

import com.ExamenMercadoLibre.Mutant.Excepcion.IncorrectNitrogenBaseException;
import com.ExamenMercadoLibre.Mutant.Excepcion.InvalidDataReceivedException;
import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceMutantException;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(MockitoJUnitRunner.class)
public class MutantServiceTest {

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    private String[] dnaMutant = new String[]{"ATGCGA", "CAGTGC", "TTATGG", "AGAAGG", "CCCGTA", "TCGCTG"};
    private String[] dnaHuman = new String[]{"ATGCCA", "CAGTGC", "TTCTGG", "AGAAGG", "CCCGTA", "TCGCTG"};
    private String[] invalidDNA_InvalidNitrogenBase = new String[]{"kkkkkk", "kkkkkk", "CAGTGC", "TTCTGG", "AGAAGG", "TCGCTG"};
    private String[] invalidDNA_InvalidLength = new String[]{"AT", "CAGTGC", "TTCTGG", "AGAAGG", "TCGCTG"};
    private String[] invalidDNA_InvalidLength2 = new String[]{"ATGCCA", "CAGTGC", "TTCTGG", "AGAAGG", "TCGCTG"};
    private String[] invalidDNA_Empy = new String[]{};

    @InjectMocks
    private MutantServiceImpl service;

    //@Mock
    //private DBServiceDAOImpl dao;

    @Ignore("not yet ready , Please ignore.")
    @Test
    public void testIsMutant_Mutant() throws ServiceMutantException, InvalidDataReceivedException, IncorrectNitrogenBaseException {

        boolean result = service.isMutant(dnaMutant);

        TestCase.assertTrue(result);
    }
    @Ignore("not yet ready , Please ignore.")
    @Test
    public void testIsMutant_Human() throws ServiceMutantException, InvalidDataReceivedException, IncorrectNitrogenBaseException {

        // GIVEN a dna of a mutant subject
//            String[] dna = dnaMutant1;
//            Mutant mutant = new Mutant(dna);
//            Mockito.doNothing().when(dao).insert(Mockito.eq(mutant));

        // WHEN is mutant service is executed
        boolean result = service.isMutant(dnaHuman);

        // THEN the return is true
        TestCase.assertTrue(result == false);
    }

    @Test
    public void testIsMutant_invalidDNA_InvalidLength() throws ServiceMutantException, InvalidDataReceivedException, IncorrectNitrogenBaseException {
        boolean ExInvalidDataReceivedException = false;
        try {
            boolean result = service.isMutant(invalidDNA_InvalidLength);
        } catch (InvalidDataReceivedException ex) {
            ExInvalidDataReceivedException = true;
        }
        TestCase.assertTrue(ExInvalidDataReceivedException == true);
    }
    @Test
    public void testIsMutant_invalidDNA_InvalidNitrogenBase() throws ServiceMutantException, InvalidDataReceivedException, IncorrectNitrogenBaseException {
        boolean ExIncorrectNitrogenBaseException = false;
        try {
            boolean result = service.isMutant(invalidDNA_InvalidNitrogenBase);
        } catch (IncorrectNitrogenBaseException ex) {
            ExIncorrectNitrogenBaseException = true;
        }
        TestCase.assertTrue(ExIncorrectNitrogenBaseException == true);
    }
    @Test
    public void testIsMutant_invalidDNA_InvalidLength2() throws ServiceMutantException, InvalidDataReceivedException, IncorrectNitrogenBaseException {
        boolean ExInvalidDataReceivedException = false;
        try {
            boolean result = service.isMutant(invalidDNA_InvalidLength2);
        } catch (InvalidDataReceivedException ex) {
            ExInvalidDataReceivedException = true;
        }
        TestCase.assertTrue(ExInvalidDataReceivedException == true);
    }
    @Test
    public void testIsMutant_invalidDNA_invalidDNA_Empy() throws ServiceMutantException, InvalidDataReceivedException, IncorrectNitrogenBaseException {
        boolean ExInvalidDataReceivedException = false;
        try {
            boolean result = service.isMutant(invalidDNA_Empy);
        } catch (InvalidDataReceivedException ex) {
            ExInvalidDataReceivedException = true;
        }
        TestCase.assertTrue(ExInvalidDataReceivedException == true);
    }


}

