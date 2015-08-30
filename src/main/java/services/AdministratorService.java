package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

    // Managed repository ---------------------------------------

    @Autowired
    private AdministratorRepository administratorRepository;

    // Supporting services --------------------------------------

    // Constructors ---------------------------------------------

    public AdministratorService() {
        super();
    }

    // Simple CRUD methods --------------------------------------

    //Obtiene el administrador logueado en el sistema
    public Administrator findByPrincipal() {
        UserAccount userAccount = LoginService.getPrincipal();
        return findByUserAccount(userAccount);
    }

    //Obtiene el administrador pasandole como parametro de entrada un userAccount
    public Administrator findByUserAccount(UserAccount userAccount) {

        Administrator result;

        result = administratorRepository.findByUserAccountId(userAccount.getId());

        return result;
    }

}
