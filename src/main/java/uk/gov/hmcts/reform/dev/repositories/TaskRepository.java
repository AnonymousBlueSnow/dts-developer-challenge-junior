package uk.gov.hmcts.reform.dev.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.dev.models.Task;

/**
 * TaskRepository to handle operations with the Task table in the database
 */
@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
}
