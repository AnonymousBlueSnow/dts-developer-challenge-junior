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
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CaseServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CaseRepository caseRepository;

    @InjectMocks
    private CaseService caseService;

    @Mock
    private TaskService taskService;

    CaseServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByCaseNumber(){
        CaseItem caseItem = new CaseItem();
        when(caseRepository.findByCaseNumber("2")).thenReturn(caseItem);
        assertEquals(caseService.findByCaseNumber("2"), caseItem);
    }

    @Test
    void testGetAllCases(){
        when(caseRepository.findAll()).thenReturn(Arrays.asList(new CaseItem(), new CaseItem()));
        Iterable<CaseItem> cases = caseService.getAllCases();
        assertNotNull(cases);
    }

    @Test
    void testGetAllTasks(){
        CaseItem caseItem = mock(CaseItem.class);
        when(caseRepository.findByCaseNumber("2")).thenReturn(caseItem);
        when(caseItem.getTasks()).thenReturn(Arrays.asList(new Task(), new Task()));

        Iterable<Task> tasks = caseService.getAllTasks("2");
        assertNotNull(tasks);
    }



}
