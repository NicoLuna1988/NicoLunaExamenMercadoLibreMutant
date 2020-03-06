package com.ExamenMercadoLibre.Mutant.Service;

import com.ExamenMercadoLibre.Mutant.DbData.DnaDao;
import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceException;
import com.ExamenMercadoLibre.Mutant.Model.Stats;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class StatsServiceTest {
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }
    @InjectMocks
    private StatsServiceImpl MockStatsService =new StatsServiceImpl(); //Dependent

    @Mock
    private DnaDao MockDnaDao; //Dependence


    @Test
    public void testGetStatsDna() throws ServiceException {
        Mockito.when(MockDnaDao.getDnaHumanCount()).thenReturn(1);
        Mockito.when(MockDnaDao.getDnaMutantCount()).thenReturn(1);
        Stats stastDna2 = MockStatsService.GetStatsDna();

        TestCase.assertTrue(stastDna2.getCount_human_dna()==1 && stastDna2.getCount_mutant_dna()==1);
    }

    @Test
    public void testGetStatsDnaFailMutantCount() throws ServiceException {
        boolean ExInvalidDataReceivedException = false;
        try {
            Mockito.when(MockDnaDao.getDnaMutantCount()).thenThrow(new ServiceException("exception getDnaMutantCount"));
            Stats stastDna2 = MockStatsService.GetStatsDna();
        } catch (ServiceException ex) {
            ExInvalidDataReceivedException = true;
        }
        TestCase.assertTrue(ExInvalidDataReceivedException == true);
    }
    @Test
    public void testGetStatsDnaFailHumanCount() throws ServiceException {
        boolean ExInvalidDataReceivedException = false;
        try {
            Mockito.when(MockDnaDao.getDnaHumanCount()).thenThrow(new ServiceException("exception getDnaHumanCount"));
            Stats stastDna2 = MockStatsService.GetStatsDna();
        } catch (ServiceException ex) {
            ExInvalidDataReceivedException = true;
        }
        TestCase.assertTrue(ExInvalidDataReceivedException == true);
    }
}
