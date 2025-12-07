package uk.gov.hmcts.reform.dev.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Model class of Tasks
 */
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Column (length = 2000)
    private String description=null; //Optional
    private String status;
    private LocalDateTime dueDateTime; //In yyyy-MM-dd'T'HH:mm format
    @ManyToOne
    private CaseItem parentCase;

    public Task(String title, String description, String status, LocalDateTime dueDateTime, CaseItem caseItem) {
        this.title=title;
        this.description=description;
        this.status=status;
        this.dueDateTime=dueDateTime;
        this.parentCase = caseItem;
    }

}
