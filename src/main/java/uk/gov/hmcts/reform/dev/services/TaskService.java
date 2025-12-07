package uk.gov.hmcts.reform.dev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.models.CaseItem;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.CaseRepository;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.time.LocalDateTime;

/**
 * Service class to handle logic operations with Task
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private CaseService caseService;

    /**
     * Saves new task and matches it to its case in the database
     * @param title - Task Title
     * @param description - Task Description
     * @param status - Task Status
     * @param dueDateTime - Task due time in yyyy-MM-dd'T'HH:mm format
     * @param caseNumber - Parent case
     * @return the new populated task object
     */
    public Task createNewTask(String title, String description, String status, LocalDateTime dueDateTime,  String caseNumber) {
        CaseItem foundCase = caseService.findByCaseNumber(caseNumber);
        Task newTask = new Task(title,description, status, dueDateTime, foundCase);
        taskRepository.save(newTask);
        return newTask;
    }

    public void save(Task newTask1) {
        taskRepository.save(newTask1);
    }

    public boolean taskExists(String newTaskId) {
        return taskRepository.existsById(Integer.valueOf(newTaskId));
    }
}
