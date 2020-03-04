package com.ExamenMercadoLibre.Mutant.Controller;

import com.ExamenMercadoLibre.Mutant.Entidades.DnaEntity;
import com.ExamenMercadoLibre.Mutant.Entidades.ResponseDTO;
import com.ExamenMercadoLibre.Mutant.Excepcion.IncorrectNitrogenBaseException;
import com.ExamenMercadoLibre.Mutant.Excepcion.InvalidDataReceivedException;
import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceMutantException;
import com.ExamenMercadoLibre.Mutant.Service.MutantService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MutantControllerTest {

    private static final String URI = "/mutant";
    Gson gson = new Gson();
    private MockMvc mockMvc;
    @InjectMocks
    private MutantController mutantControllerTest;

    @Mock
    private MutantService mockMutantservice;

    private String[] dnaMutant = new String[]{"ATGCGA", "CAGTGC", "TTATGG", "AGAAGG", "CCCGTA", "TCGCTG"};
    private String[] dnaHuman = new String[]{"ATGCCA", "CAGTGC", "TTCTGG", "AGAAGG", "CCCGTA", "TCGCTG"};
    private String[] invalidDNA_InvalidNitrogenBase = new String[]{"kkkkkk", "kkkkkk", "CAGTGC", "TTCTGG", "AGAAGG", "TCGCTG"};
    private String[] invalidDNA_InvalidLength= new String[]{"AT", "CAGTGC", "TTCTGG", "AGAAGG", "TCGCTG"};
    private String[] invalidDNA_InvalidLength2= new String[]{"ATGCCA", "CAGTGC", "TTCTGG", "AGAAGG", "TCGCTG"};
    private String[] invalidDNA_Empy=new String[]{};

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mutantControllerTest)
                .build();
    }

    @Test
    public void testDnaIsMutant() throws Exception {

        mockIsMutantService(dnaMutant, true);
        DnaEntity dna = new DnaEntity();
        dna.setDna(dnaMutant);

        ResultActions resultActions = mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(gson.toJson(dna)));

        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(result.getResponse().getContentAsString());
        boolean responseok = node.get("ok").asBoolean();
        String message = node.get("message").asText().toString();
        Assert.isTrue(responseok == true, "Status 200");
        Assert.isTrue(message.equals("Is Mutant"), "Is Mutant");
    }
    // @Ignore("not yet ready , Please ignore.")
    @Test
    public void testInvalidDna() throws Exception {

        //Genero una InvalidDataReceivedException
        Mockito.doThrow(new InvalidDataReceivedException("any message")).when(mockMutantservice).isMutant(invalidDNA_InvalidLength);


        DnaEntity dna = new DnaEntity();
        dna.setDna(invalidDNA_InvalidLength);
        ResultActions resultActions = mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(gson.toJson(dna)));
        MvcResult result = resultActions.andExpect(status().isInternalServerError()).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(result.getResponse().getContentAsString());
        boolean responseok = node.get("ok").asBoolean();
        Assert.isTrue(responseok == false, "Status 500");

    }

    @Test
    public void testDnaIsHuman() throws Exception {

        mockIsMutantService(dnaHuman, false);
        DnaEntity dna = new DnaEntity();
        dna.setDna(dnaHuman);

        ResultActions resultActions = mockMvc.perform(post(URI)
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


    private void mockIsMutantService(String[] dna, boolean expectedResult) throws ServiceMutantException, InvalidDataReceivedException, IncorrectNitrogenBaseException {
        Mockito.when(mockMutantservice.isMutant(dna)).thenReturn(expectedResult);
    }
}
