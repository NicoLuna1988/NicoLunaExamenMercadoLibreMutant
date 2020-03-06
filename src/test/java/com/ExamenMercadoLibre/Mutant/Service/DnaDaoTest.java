package com.ExamenMercadoLibre.Mutant.Service;

import com.ExamenMercadoLibre.Mutant.DbData.DnaDaoImpl;
import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceException;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class DnaDaoTest {
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks
    private DnaDaoImpl service = new DnaDaoImpl(); //Dependent


    @Autowired
    @Mock
    JdbcTemplate jdbcTemplate;

    @Test
    public void testGetDnaHumanCount() throws ServiceException {

        Mockito.when(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Dna WHERE IsMutant=false;", Integer.class)).thenReturn(1);
        int result = service.getDnaHumanCount();
        TestCase.assertTrue(result==1);
    }
    @Test
    public void testGetDnaMutantCount() throws ServiceException {
        Mockito.when(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Dna WHERE IsMutant=true;", Integer.class)).thenReturn(1);
        int result = service.getDnaMutantCount();
        TestCase.assertTrue(result==1);
    }
    @Test
    public void testExistDnaSequence() throws ServiceException {

        Mockito.when(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Dna WHERE DnaSequence = ?",new Object[]{"Test"}, Integer.class)).thenReturn(1);

        boolean result = service.ExistDnaSequence("Test");
        TestCase.assertTrue(result);
    }
    @Test
    public void testInsertDnaSequence() throws ServiceException {

        Mockito.when(jdbcTemplate.update("INSERT INTO Dna (DnaSequence, IsMutant) VALUES (?, ?)", "Test", true)).thenReturn(1);
        boolean result = service.InsertDnaSequence("Test",true);
        TestCase.assertTrue(result);
    }

}
