package uk.gov.hmcts.reform.dev.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.dev.models.CaseItem;

/**
 * CaseRepository to handle operations with the CaseItem table in the database
 */
@Repository
public interface CaseRepository extends CrudRepository<CaseItem, Integer> {
    CaseItem findByCaseNumber(String caseNumber);
}
