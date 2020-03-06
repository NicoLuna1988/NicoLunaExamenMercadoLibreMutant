package com.ExamenMercadoLibre.Mutant.Controller;

import com.ExamenMercadoLibre.Mutant.Model.DnaSequence;
import com.ExamenMercadoLibre.Mutant.Excepcion.IncorrectNitrogenBaseException;
import com.ExamenMercadoLibre.Mutant.Excepcion.InvalidDataReceivedException;
import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceException;
import com.ExamenMercadoLibre.Mutant.Model.Stats;
import com.ExamenMercadoLibre.Mutant.Service.MutantServiceImpl;
import com.ExamenMercadoLibre.Mutant.Service.StatsServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import javafx.stage.StageStyle;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * class for the test MutantController
 */

public class MutantControllerTest {

    private static final String URI_Mutant = "/mutant";
    private static final String URI_Stats = "/stats";
    Gson gson = new Gson();
    private MockMvc mockMvc;
    @InjectMocks
    private MutantController mutantControllerTest;

    @Mock
    private StatsServiceImpl mockStatsService;
    @Mock
    private MutantServiceImpl mockMutantservice;

    private String[] dnaMutant = new String[]{"ATGCGA", "CAGTGC", "TTATGG", "AGAAGG", "CCCGTA", "TCGCTG"};
    private String[] dnaHuman = new String[]{"ATGCCA", "CAGTGC", "TTCTGG", "AGAAGG", "CCCGTA", "TCGCTG"};
    private String[] invalidDNA_InvalidLength= new String[]{"AT", "CAGTGC", "TTCTGG", "AGAAGG", "TCGCTG"};


    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mutantControllerTest)
                .build();
    }

    /**
     * Test the controller with a mutant sequence
     */
    @Test
    public void testDnaIsMutant() throws Exception {

        Mockito.when(mockMutantservice.isMutant(dnaMutant)).thenReturn(true);
        DnaSequence dna = new DnaSequence();
        dna.setDna(dnaMutant);

        ResultActions resultActions = mockMvc.perform(post(URI_Mutant).contentType(MediaType.APPLICATION_JSON).content(gson.toJson(dna)));

        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(result.getResponse().getContentAsString());
        boolean responseok = node.get("ok").asBoolean();
        String message = node.get("message").asText().toString();
        Assert.isTrue(responseok == true, "Status 200");
        Assert.isTrue(message.equals("Is Mutant"), "Is Mutant");
    }
    /**
     * Test the controller with an invalid sequence
     */
    @Test
    public void testInvalidDna() throws Exception {

        //Genero una InvalidDataReceivedException
        Mockito.doThrow(new InvalidDataReceivedException("Test InvalidDataReceivedException")).when(mockMutantservice).isMutant(invalidDNA_InvalidLength);


        DnaSequence dna = new DnaSequence();
        dna.setDna(invalidDNA_InvalidLength);
        ResultActions resultActions = mockMvc.perform(post(URI_Mutant).contentType(MediaType.APPLICATION_JSON).content(gson.toJson(dna)));
        MvcResult result = resultActions.andExpect(status().isInternalServerError()).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(result.getResponse().getContentAsString());
        boolean responseok = node.get("ok").asBoolean();
        Assert.isTrue(responseok == false, "Status 500");

    }
    /**
     * Test the controller with a human sequence
     */
    @Test
    public void testDnaIsHuman() throws Exception {

        Mockito.when(mockMutantservice.isMutant(dnaHuman)).thenReturn(false);
        DnaSequence dna = new DnaSequence();
        dna.setDna(dnaHuman);

        ResultActions resultActions = mockMvc.perform(post(URI_Mutant)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE).content(gson.toJson(dna)));

        MvcResult result = resultActions.andExpect(status().isForbidden()).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(result.getResponse().getContentAsString());
        boolean responseok = node.get("ok").asBoolean();
        String message = node.get("message").asText().toString();
        Assert.isTrue(responseok == true, "Status 403");
        Assert.isTrue(message.equals("Is Human"), "Is Human");
    }


    /**
     * Test the GetStatics
     */
    @Test
    public void testGetStatics() throws Exception {
        Mockito.when(mockStatsService.GetStatsDna()).thenReturn(new Stats());
        ResultActions resultActions = mockMvc.perform(get(URI_Stats));
        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(result.getResponse().getContentAsString());
        Assert.isTrue(!result.getResponse().getContentAsString().isEmpty(),"The response should be a json");
    }

}
