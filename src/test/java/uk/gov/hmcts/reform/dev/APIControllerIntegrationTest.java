package uk.gov.hmcts.reform.dev;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.hmcts.reform.dev.models.CaseItem;
import uk.gov.hmcts.reform.dev.repositories.CaseRepository;
import java.time.LocalDateTime;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class APIControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CaseRepository caseRepository;

    @Test
    void testGetExampleCase() throws Exception {
        mvc.perform(get("/get-example-case"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.caseNumber").value("ABC12345"))
            .andExpect(jsonPath("$.title").value("Case Title"))
            .andExpect(jsonPath("$.description").value("Case Description"))
            .andExpect(jsonPath("$.status").value("Case Status"));
    }

    @Test
    void testCreateNewTaskSuccessForDB() throws Exception {
        //Testing database operation works without error
        caseRepository.save(new CaseItem(1, "ABC12345", "Case Title", "Case Description", "Case Status", LocalDateTime.now(), null));

    }
    @Test
    void testCreateNewTaskSuccessForAPI() throws Exception {
        //Testing API side
        mvc.perform(post("/createNewTask")
                        .param("title", "Case Title")
                        .param("caseNumber", "ABC12345")
                        .param("description", "Case Description")
                        .param("status", "Case Status")
                        .param("dueDateTime", "2026-03-02T12:00"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Case Title"))
            .andExpect(jsonPath("$.description").value("Case Description"))
            .andExpect(jsonPath("$.status").value("Case Status"))
            .andExpect(jsonPath("$.dueDateTime").value(containsString("2026-03-02T12:00")));
    }

    @Test
    void testCreateTask_InvalidDateFormat() throws Exception {
        mvc.perform(post("/createNewTask")
                        .param("title", "Case Title")
                        .param("caseNumber", "ABC12345")
                        .param("description", "Case Description")
                        .param("status", "Case Status")
                        .param("dueDateTime", "wrong format"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("The dueDateTime format must be: yyyy-MM-dd'T'HH:mm")));
    }

    @Test // Testing 300 chars when limit is 255
    void testCreateTask_maxInputLimit() throws Exception {
        mvc.perform(post("/createNewTask")
                        .param("title", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, quis gravida magna mi a libero. Fusce vulputate eleifend sapien. Vestibulum purus quam, scelerisque ut, mollis sed, nonummy id, metus. Nullam accumsan lorem in dui. Cras ultricies mi eu turpis hendrerit fringilla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; In ac dui quis mi consectetuer lacinia. Nam pretium turpis et arcu. Duis arcu tortor, suscipit eget, imperdiet nec, imperdiet iaculis, ipsum. Sed aliquam ultrices mauris. Integer ante arcu, accumsan a, consectetuer eget, posuere ut, mauris. Praesent adipiscing. Phasellus ullamcorper ipsum rutrum nunc. Nunc nonummy metus. Vestibulum volutpat pretium libero. Cras id dui. Aenean ut")
                        .param("caseNumber", "ABC12345")
                        .param("description", "Case Description")
                        .param("status", "Case Status")
                        .param("dueDateTime", "wrong format"))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void testCreateTask_PastDate() throws Exception {
        mvc.perform(post("/createNewTask")
                        .param("title", "Case Title")
                        .param("caseNumber", "ABC12345")
                        .param("description", "Case Description")
                        .param("status", "Case Status")
                        .param("dueDateTime", "2020-01-01T12:00"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("Due Date is before now, please enter a future date")));
    }

    @Test
    void testCreateTask_CaseNumberNotFound() throws Exception {
        mvc.perform(post("/createNewTask")
                        .param("title", "Case Title")
                        .param("description", "Case Description")
                        .param("status", "Case Status")
                        .param("dueDateTime", "2020-01-01T12:00")
                        .param("caseNumber", "NotACaseNumber"))
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(containsString("Please enter a valid Case Number")));
    }

}
