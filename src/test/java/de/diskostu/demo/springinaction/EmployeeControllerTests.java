package de.diskostu.demo.springinaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mvc;


    @Test
    @WithMockUser
    public void givenNoEmployee_whenCreateEmployee_thenEmployeeCreated() throws Exception {
        mvc.perform(post("/employees")
                   .content(new ObjectMapper().writeValueAsString(new Employee("Hans", "Last")))
                   .with(SecurityMockMvcRequestPostProcessors.csrf())
                   .contentType(MediaType.APPLICATION_JSON)
                   .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.firstName").value("Hans"))
           .andExpect(jsonPath("$.lastName").value("Last"));
    }


    /**
     * TODO: make this test independent of all other tests. When this test runs, make sure the database is empty.
     */
    @Test
    @WithMockUser
    public void givenEmployee_whenDeleteEmployee_thenEmployeeDeleted() throws Exception {
        mvc.perform(delete("/employees/1")
                   .with(SecurityMockMvcRequestPostProcessors.csrf())
                   .contentType(MediaType.APPLICATION_JSON)
                   .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isNotFound());
    }
}
