package uk.gov.hmcts.reform.dev;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.hmcts.reform.dev.models.CaseItem;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.CaseRepository;
import java.time.LocalDateTime;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WebAppControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CaseRepository caseRepository;

    @Test
    void testGetCases() throws Exception {
        mvc.perform(get("/cases"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("Cases"));
    }

    @Test
    void testCreateTaskForm() throws Exception {
        caseRepository.save(new CaseItem(2, "ABC123", "Case Title", "Case Description", "Case Status", LocalDateTime.now(), null));

        mvc.perform(get("/createTask/ABC123"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("task"))
            .andExpect(model().attributeExists("currentDateTime"));
    }

    @Test
    void testPostCreateTaskForm() throws Exception{
            mvc.perform(post("/createTask/ABC123")
                        .param("title", "Task title")
                        .param("status", "Task status")
                        .param("dueDateTime", "2027-02-25T10:00")
                        .param("caseNumber", "ABC123"))
            .andExpect(status().is3xxRedirection());

    }


    @Test
    void testGetCaseTasks() throws Exception {
        caseRepository.save(new CaseItem(2, "ABC123", "Case Title", "Case Description", "Case Status", LocalDateTime.now(), null));

        mvc.perform(get("/cases/ABC123"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("Tasks"))
            .andExpect(model().attributeExists("Case"));
    }
}
