package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Customer;

@Service
@Transactional
public class CustomerService {
    // Managed repository ---------------------------------------

    @Autowired
    private CustomerRepository customerRepository;

    // Supporting services --------------------------------------

    // Constructors ---------------------------------------------
    public CustomerService() {
        super();
    }

    //Devuelve el customer que se encuentra logueado en el sistema
    public Customer findByPrincipal() {
        UserAccount userAccount = LoginService.getPrincipal();
        return findByUserAccount(userAccount);
    }

    //Devuelve el customer dado un userAccount
    public Customer findByUserAccount(UserAccount userAccount) {
        Customer result;

        result = customerRepository.findByUserAccountId(userAccount.getId());

        return result;
    }

    //Devuelve los customers de tipo especialista
    public Collection<Customer> findOnlySpecialist() {
        Collection<Customer> customers = customerRepository.findOnlySpecialist();
        return customers;
    }

    //Devuelve todos los customers
    public Collection<Customer> findAll() {
        Collection<Customer> customers = customerRepository.findAll();
        return customers;
    }

    //Devuelve un customer dado su id
    public Customer findOneToEdit(int customerId) {
        Customer customer = customerRepository.findOne(customerId);

        return customer;
    }

}
