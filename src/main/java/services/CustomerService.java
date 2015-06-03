package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Administrator;
import domain.Customer;
import domain.Profile;

import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;

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

	public Customer findByPrincipal() {
		UserAccount userAccount = LoginService.getPrincipal();
		return findByUserAccount(userAccount);
	}

	public Customer findByUserAccount(UserAccount userAccount) {
		Customer result;

		result = customerRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Collection<Customer> findOnlySpecialist() {
		Collection<Customer> customers = customerRepository.findOnlySpecialist();
		return customers;
	}

	public Collection<Customer> findAll() {
		Collection<Customer> customers = customerRepository.findAll();
		return customers;
	}

	public Customer findOneToEdit(int customerId) {
		Customer customer = customerRepository.findOne(customerId);
		
		return customer;
	}


}
