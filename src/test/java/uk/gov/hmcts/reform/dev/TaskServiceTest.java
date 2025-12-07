package uk.gov.hmcts.reform.dev;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.hmcts.reform.dev.models.CaseItem;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.CaseRepository;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;
import uk.gov.hmcts.reform.dev.services.CaseService;
import uk.gov.hmcts.reform.dev.services.TaskService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CaseRepository caseRepository;

    @Mock
    private CaseService caseService;

    @InjectMocks
    private TaskService taskService;

    TaskServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNewTask() {
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        CaseItem caseItem = new CaseItem();
        when(caseService.findByCaseNumber("ABC12345")).thenReturn(caseItem);
        Task createdTask = taskService.createNewTask("t", "d", "O", dueDate, "ABC12345");

        assertNotNull(createdTask);
        assertEquals("t", createdTask.getTitle());
        assertEquals("d", createdTask.getDescription());
        assertEquals(caseItem, createdTask.getParentCase());

        verify(taskRepository, times(1)).save(createdTask);
    }

    @Test
    void testTaskExists() {
        when(taskRepository.existsById(2)).thenReturn(true);
        assertTrue(taskService.taskExists("2"));
    }
}
