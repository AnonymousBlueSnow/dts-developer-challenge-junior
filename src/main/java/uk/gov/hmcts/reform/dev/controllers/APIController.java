package uk.gov.hmcts.reform.dev.controllers;

import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.dev.models.CaseItem;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.services.CaseService;
import uk.gov.hmcts.reform.dev.services.TaskService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.springframework.http.ResponseEntity.ok;


/**
 * Controller for handling API endpoints only - can also access through http://localhost:8080/swagger-ui/index.html
 */
@RestController
public class APIController {

    @Autowired
    private CaseService caseService;

    @Autowired
    private TaskService taskService;


    /**
     * Gets a prefilled example case
     * @return a 200 OK response and the JSON format of the new created case
     */
    @GetMapping(value = "/get-example-case", produces = "application/json")
    @ResponseBody
    public ResponseEntity<CaseItem> getExampleCase() {
        return ok(new CaseItem(1, "ABC12345", "Case Title",
                               "Case Description", "Case Status", LocalDateTime.now(), null
        ));
    }

    /**
     * Creates, validates, and stores a new task
     *
     * @param title - Required Task Title (<255 chars)
     * @param description - Optional description (<2000 chars)
     * @param status - Required Task status (<255 chars)
     * @param dueDateTime - Required dueDateTime In yyyy-MM-dd'T'HH:mm format
     * @param caseNumber - Required CaseNumber (<255 chars)
     * @return - Returns a 200OK response and the new created task details
     * if there aren't any errors, or it returns an 400 error list if there are
     */
    @PostMapping(value = "/createNewTask")
    @ResponseBody
    public ResponseEntity<Object> createNewTask(@RequestParam @Size(max = 255,message = "Title cannot be longer than 255 characters.") String title, @RequestParam(required = false) @Size(max = 2000, message = "Description cannot exceed 2000 characters.") String description, @RequestParam @Size(max = 255,message = "Status cannot be longer than 255 characters.") String status, @RequestParam String dueDateTime, @RequestParam @Size(max = 255,message = "Too many characters entered for the case number")String caseNumber){

        //Errors collection
        StringBuilder errorList = new StringBuilder();

        if (title==null || title.isBlank()){
            errorList.append("Title is required\n");
        }

        if (status==null || status.isBlank()){
            errorList.append("Status is required\n");
        }

        LocalDateTime parsedDueDateTime = null;
        try {
            parsedDueDateTime = LocalDateTime.parse(dueDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

            if (parsedDueDateTime.isBefore(LocalDateTime.now())){
                errorList.append("Due Date is before now, please enter a future date\n");
            }
        } catch (Exception e) {
            errorList.append("The dueDateTime format must be: yyyy-MM-dd'T'HH:mm\n");
        }

        if (caseNumber==null || caseNumber.isBlank()){
            errorList.append("Case Number is required\n");
        }
        else if (caseService.findByCaseNumber(caseNumber)==null){
            errorList.append("Please enter a valid Case Number\n");
        }

        if (errorList.isEmpty()){
            Task newTask = taskService.createNewTask(title, description, status, parsedDueDateTime, caseNumber);
            return ok(newTask);
        }
        else{
            errorList.insert(0, "Task Creation failed. Please see the below error(s) and fix:\n");
            return ResponseEntity.badRequest().body(errorList.toString());
        }
    }
}
