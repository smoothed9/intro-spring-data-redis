package com.sudeepnm.redis.introspringdataredis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class IntroSpringDataRedisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository repository;

    @Test
    void personList_shouldReturnNoneFound_whenNoDataExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persons"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void savePerson_shouldReturnCreatedId() throws Exception {
        Mockito.when(repository.save(any(Person.class))).thenReturn(Person.builder().id("1").build());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/save")
                                .param("firstName", "first")
                                .param("lastName", "last"))
                .andExpect(status().isOk())
                .andReturn();
        Mockito.verify(repository).save(any(Person.class));
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().equals("1"));
    }

    @Test
    void personsByFirstName_shouldReturnListOfPersons() throws Exception {
        Mockito.when(repository.findByFirstname("first"))
                .thenReturn(asList(
                        Person.builder().id("1").firstname("first").build(),
                        Person.builder().id("2").firstname("first").build()
                ));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/personsByFirstName/first"))
                .andExpect(status().isOk())
                .andReturn();
        Mockito.verify(repository).findByFirstname("first");

        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();
        List personList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);

        Assert.assertThat(personList.size(), IsEqual.equalTo(2));
    }
}
