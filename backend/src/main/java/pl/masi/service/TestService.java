package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.CumulativePermission;
import org.springframework.stereotype.Service;
import pl.masi.dto.ChangePermsRequestDto;
import pl.masi.entity.Test;
import pl.masi.entity.TestAnswer;
import pl.masi.entity.User;
import pl.masi.exception.MasiException;
import pl.masi.repository.TestAnswerRepository;
import pl.masi.repository.TestRepository;
import pl.masi.service.acl.AclManagementService;
import pl.masi.service.base.EntityService;
import pl.masi.validation.ChangePermsRequestValidator;
import pl.masi.validation.TestValidator;
import pl.masi.validation.base.EntityValidator;

import java.util.Optional;

@Service
public class TestService extends EntityService<Test> {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestValidator testValidator;

    @Autowired
    private ChangePermsRequestValidator changePermsRequestValidator;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private UserService userService;

    @Autowired
    private TestAnswerRepository testAnswerRepository;

    private void processTest(Test test) {
        test.getQuestions().forEach(question -> question.setTest(test));
    }

    @Override
    protected void beforeCreate(Test test) {
        processTest(test);
    }

    @Override
    protected void beforeUpdate(Test test) {
        processTest(test);
    }

    @Override
    protected void afterRead(Test test) {
        User currentUser = userService.getCurrentUser();
        if (currentUser.isCandidate()) {
            Optional<TestAnswer> ans = this.testAnswerRepository.findByTestAndUser(test, currentUser);
            test.setSolved(ans.isPresent());
        }
    }

    @Override
    protected JpaRepository<Test, Long> getEntityRepository() {
        return testRepository;
    }

    @Override
    protected EntityValidator<Test> getEntityValidator() {
        return testValidator;
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR') || hasRole('ROLE_REDACTOR')")
    public Test translate(Test test, String targetLang) {
        Test translated = translationService.translate(test, targetLang);
        return create(translated);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR') || hasRole('ROLE_REDACTOR')")
    public void changePerms(ChangePermsRequestDto req) {
        changePermsRequestValidator.validate(req);

        User user = userService.findById(req.getUserId()).get();
        Test test = findById(req.getTestId()).get();

        CumulativePermission perms = new CumulativePermission();
        switch (req.getPermission()){
            case READ:
                perms.set(BasePermission.READ);
                break;
            case ALL:
                perms.set(AclManagementService.OWNER_PERMISSIONS);
                break;
        }

        switch (req.getOperation()) {
            case GRANT:
                aclManagementService.grantPermissions(test, user, perms);
                break;
            case REVOKE:
                throw new MasiException("Unimplemented!");
        }
    }

}
