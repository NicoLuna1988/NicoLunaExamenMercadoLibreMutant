package com.ExamenMercadoLibre.Mutant.Service;

import com.ExamenMercadoLibre.Mutant.DbData.DnaDaoImpl;
import com.ExamenMercadoLibre.Mutant.Excepcion.IncorrectNitrogenBaseException;
import com.ExamenMercadoLibre.Mutant.Excepcion.InvalidDataReceivedException;
import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceException;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


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
    private MutantServiceImpl MockMutantService; //Dependent

    @Mock
    private DnaDaoImpl dao; //Dependence

    @Test
    public void testIsMutant_Mutant() throws ServiceException, InvalidDataReceivedException, IncorrectNitrogenBaseException {

        boolean result = MockMutantService.isMutant(dnaMutant);

        TestCase.assertTrue(result);
    }

    @Test
    public void testIsMutant_Human() throws ServiceException, InvalidDataReceivedException, IncorrectNitrogenBaseException {

        boolean result = MockMutantService.isMutant(dnaHuman);

        TestCase.assertFalse(result);
    }

    @Test
    public void testIsMutant_invalidDNA_InvalidLength() throws ServiceException, InvalidDataReceivedException, IncorrectNitrogenBaseException {
        boolean ExInvalidDataReceivedException = false;
        try {
            boolean result = MockMutantService.isMutant(invalidDNA_InvalidLength);
        } catch (InvalidDataReceivedException ex) {
            ExInvalidDataReceivedException = true;
        }
        TestCase.assertTrue(ExInvalidDataReceivedException == true);
    }
    @Test
    public void testIsMutant_invalidDNA_InvalidNitrogenBase() throws ServiceException, InvalidDataReceivedException, IncorrectNitrogenBaseException {
        boolean ExIncorrectNitrogenBaseException = false;
        try {
            boolean result = MockMutantService.isMutant(invalidDNA_InvalidNitrogenBase);
        } catch (IncorrectNitrogenBaseException ex) {
            ExIncorrectNitrogenBaseException = true;
        }
        TestCase.assertTrue(ExIncorrectNitrogenBaseException == true);
    }
    @Test
    public void testIsMutant_invalidDNA_InvalidLength2() throws ServiceException, InvalidDataReceivedException, IncorrectNitrogenBaseException {
        boolean ExInvalidDataReceivedException = false;
        try {
            boolean result = MockMutantService.isMutant(invalidDNA_InvalidLength2);
        } catch (InvalidDataReceivedException ex) {
            ExInvalidDataReceivedException = true;
        }
        TestCase.assertTrue(ExInvalidDataReceivedException == true);
    }
    @Test
    public void testIsMutant_invalidDNA_invalidDNA_Empy() throws ServiceException, InvalidDataReceivedException, IncorrectNitrogenBaseException {
        boolean ExInvalidDataReceivedException = false;
        try {
            boolean result = MockMutantService.isMutant(invalidDNA_Empy);
        } catch (InvalidDataReceivedException ex) {
            ExInvalidDataReceivedException = true;
        }
        TestCase.assertTrue(ExInvalidDataReceivedException == true);
    }


}

