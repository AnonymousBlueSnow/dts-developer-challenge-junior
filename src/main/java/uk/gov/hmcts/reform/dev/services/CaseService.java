package uk.gov.hmcts.reform.dev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.models.CaseItem;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.CaseRepository;

/**
 * Service class to handle logic operations with CaseItem
 */
@Service
public class CaseService {

    @Autowired
    private CaseRepository caseRepository;

    public CaseItem findByCaseNumber(String caseNumber){
        return caseRepository.findByCaseNumber(caseNumber.strip());
    }

    public void save(CaseItem newCase) {
        caseRepository.save(newCase);
    }

    public Iterable<CaseItem> getAllCases() {
        return caseRepository.findAll();
    }

    public Iterable<Task> getAllTasks(String caseNumber) {
        return caseRepository.findByCaseNumber(caseNumber).getTasks();
    }

}
