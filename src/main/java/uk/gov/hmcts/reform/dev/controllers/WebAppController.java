package uk.gov.hmcts.reform.dev.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.server.ResponseStatusException;
import uk.gov.hmcts.reform.dev.models.CaseItem;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.services.CaseService;
import uk.gov.hmcts.reform.dev.services.TaskService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Controller for handling the web application endpoints
 */
@Controller
public class WebAppController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CaseService caseService;

    /**
     * Displays a list of cases
     * @param model - Passes all the case objects from the database to the jsp
     * @return - The Cases jsp
     */
    @GetMapping("/cases")
    public String getCases(Model model){
        model.addAttribute("Cases", caseService.getAllCases());
        return "Cases";
    }

    /**
     * Displays a form for the user to create a new task for a case
     *
     * @param caseNumber - The case that the task is going to be created for
     * @param model - Passes in a new Task object to be filled in
     *                Passes in the current dateTime to be set as the minimum time entry requirement.
     * @return - Either a form to populate the new Task object or throws a 404 to the error page
     * if invalid caseNumber entered in the URL
     */
    @GetMapping("/createTask/{caseNumber}")
    public String createTask(@PathVariable String caseNumber, Model model){
        Task newTask = new Task();
        CaseItem  caseItem = caseService.findByCaseNumber(caseNumber);
        if (caseItem == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found");
        }
        newTask.setParentCase(caseItem);
        model.addAttribute("task", newTask);
        model.addAttribute("currentDateTime", LocalDateTime.now().withSecond(0).withNano(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))); //check
        return "CreateTaskForm";
    }


    /**
     * Handles the saving of the new task to the database
     * @param task - The task object completed from the submitted task creation form
     * @param result - Identifies any backend validation issues
     * @param caseNumber - The case that the new task has been created for
     * @return - If there are any backend issues with the entries, the user is redirected back to the form
     *          - If there are no issues, the user is redirected to the cases list page with a successful task creation indicator
     */
    @PostMapping("/createTask/{caseNumber}")
    public String createTask(@Valid @ModelAttribute("task") Task task,  BindingResult result,@PathVariable String caseNumber){
        if (result.hasErrors()) {
            return "CreateTaskForm";
        }
        Task newTask = taskService.createNewTask(task.getTitle().strip(), task.getDescription(), task.getStatus().strip(), task.getDueDateTime(), caseNumber);
        return "redirect:/cases/"+caseNumber+"?newTaskId="+String.valueOf(newTask.getId());
    }


    /**
     * Shows the list of tasks for a certain case
     * @param caseNumber - Identifies the specific case to view
     * @param newTaskId - An indicator of the new task creation which allows a success message to appear near to it
     * @param model - Passes in the matching case, all of its tasks, and (if it exists) the id of the new task created
     * @return - Returns the Tasks jsp which lists all the tasks and their details
     */
    @GetMapping("/cases/{caseNumber}")
    public String getCaseTasks(@PathVariable String caseNumber, @RequestParam(required=false, defaultValue = "-1") String newTaskId, Model model){
        CaseItem caseItem = caseService.findByCaseNumber(caseNumber);
        if (caseItem == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found");
        }
        model.addAttribute("Tasks", caseService.getAllTasks(caseNumber));
        model.addAttribute("Case", caseItem);
        if ( !newTaskId.equals("-1") && taskService.taskExists(newTaskId) ){
            model.addAttribute("newTaskId", newTaskId);
        }
        return "Tasks";
    }

}
