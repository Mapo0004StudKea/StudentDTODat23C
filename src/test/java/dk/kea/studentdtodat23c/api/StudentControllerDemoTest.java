package dk.kea.studentdtodat23c.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.kea.studentdtodat23c.dto.StudentRequestDTO;
import dk.kea.studentdtodat23c.dto.StudentResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerDemoTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // For serializing/deserializing JSON

    @Test
    void shouldCreateStudent() throws Exception {

        // Prepare a StudentRequestDTO
        StudentRequestDTO request = new StudentRequestDTO("Jane Smith", "securePass", LocalDate.of(1995, 5, 20), LocalTime.of(14, 45));
        String jsonRequest = objectMapper.writeValueAsString(request);

        // Perform POST request and validate
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jane Smith"))
                .andExpect(jsonPath("$.bornDate").value("1995-05-20"))
                .andExpect(jsonPath("$.bornTime").value("14:45:00"));
    }
}