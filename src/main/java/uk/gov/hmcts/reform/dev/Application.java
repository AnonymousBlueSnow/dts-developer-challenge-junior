package uk.gov.hmcts.reform.dev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.gov.hmcts.reform.dev.models.CaseItem;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.services.CaseService;
import uk.gov.hmcts.reform.dev.services.TaskService;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * Main source to run application from
 */

@SpringBootApplication
@SuppressWarnings("HideUtilityClassConstructor") // Spring needs a constructor, its not a utility class
public class Application implements CommandLineRunner {

    @Autowired
    private CaseService caseService;

    @Autowired
    private TaskService taskService;

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... arg) {

        //Populates new dummy data to make it easy to use and test the web application
        CaseItem newCase = new CaseItem();
        newCase.setCaseNumber("ABC12345");
        newCase.setTitle("Stolen bike");
        newCase.setDescription("Bike was stolen in the ABC area");
        newCase.setStatus("In progress");
        newCase.setCreatedDate(LocalDateTime.of(2022, Month.NOVEMBER, 28, 12, 0));
        caseService.save(newCase);

        CaseItem newCase2 = new CaseItem();
        newCase2.setCaseNumber("DEF12346");
        newCase2.setTitle("Attempted burglary");
        newCase2.setDescription("London house was broken into, but nothing stolen due to dog");
        newCase2.setStatus("New");
        newCase2.setCreatedDate(LocalDateTime.of(2023, Month.DECEMBER, 28, 12, 0));
        caseService.save(newCase2);

        CaseItem newCase3 = new CaseItem();
        newCase3.setCaseNumber("GHI12347");
        newCase3.setTitle("Pizzas thrown on cars");
        newCase3.setDescription("Unidentified person throwing pizzas at 2am in a new location every day");
        newCase3.setStatus("Pending 3rd party");
        newCase3.setCreatedDate(LocalDateTime.of(2024, Month.MARCH, 28, 12, 0));
        caseService.save(newCase3);

        Task newTask1 =  new Task();
        newTask1.setTitle("Track CCTV");
        newTask1.setDescription("Ask around the neighbourhood for digital evidence of suspect");
        newTask1.setStatus("In progress");
        newTask1.setDueDateTime((LocalDateTime.of(2026, Month.DECEMBER, 28, 12, 0)));
        newTask1.setParentCase(newCase);
        taskService.save(newTask1);

        Task newTask2 =  new Task();
        newTask2.setTitle("Visit burgled home to give advice");
        newTask2.setDescription("Communicate our progress with the residents and give house security advice");
        newTask2.setStatus("In progress");
        newTask2.setDueDateTime((LocalDateTime.of(2026, Month.NOVEMBER, 28, 12, 0)));
        newTask2.setParentCase(newCase2);
        taskService.save(newTask2);

        Task newTask3 =  new Task();
        newTask3.setTitle("Record all the pizza hitting locations");
        newTask3.setDescription("Track the pizza hits so we can predict where they will be next");
        newTask3.setStatus("Task 2");
        newTask3.setDueDateTime((LocalDateTime.of(2027, Month.NOVEMBER, 28, 12, 0)));
        newTask3.setParentCase(newCase3);
        taskService.save(newTask3);

        System.out.println("Reached end of dummy code creation");


    }
}
