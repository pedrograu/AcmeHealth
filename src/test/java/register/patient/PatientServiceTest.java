package register.patient;


import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.PatientService;
import utilities.PopulateDatabase;
import domain.CreditCard;
import domain.Patient;
import forms.PatientForm;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)

public class PatientServiceTest {

	@Autowired
	private PatientService patientService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}

	@Test
	public void testRegister() {

		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("Visa");
		creditCard.setcVVcode(123);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2015);
		creditCard.setHolderName("Conchi");
		creditCard.setNumber("4362162324728221");

		Patient patient;
		PatientForm patientForm = new PatientForm();
		patientForm.setUsername("patient99");
		patientForm.setName("patient99");
		patientForm.setSurname("patient99");
		patientForm.setEmailAddress("patient2@patient2.com");
		patientForm.setAddress("c/Pedro Leon");
		patientForm.setPhone("666666666");
		patientForm.setCreditCard(creditCard);
		patientForm.setPassword("5ce4d191fd14ac85a1469fb8c29b7a7b");
		patientForm.setSecondPassword("5ce4d191fd14ac85a1469fb8c29b7a7b");
		patientForm.setAvailable(true);

		patient = patientService.reconstruct(patientForm);
		patientService.save(patient);

		Patient patient2 = patientService.findForUsername(patientForm
				.getUsername());
		Assert.notNull(patient2);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testFailDuplicate() {
		Patient patient;

		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("Visa");
		creditCard.setcVVcode(123);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2015);
		creditCard.setHolderName("Conchi");
		creditCard.setNumber("5544701478571115");

		PatientForm patientForm = new PatientForm();
		patientForm.setUsername("patient1");
		patientForm.setName("patient1");
		patientForm.setSurname("patient1");
		patientForm.setEmailAddress("patient1@patient1.com");
		patientForm.setAddress("c/Pedro Leon");
		patientForm.setPhone("666666666");
		patientForm.setCreditCard(creditCard);
		patientForm.setPassword("d5cee333abe432891a0de57d0ee38713");
		patientForm.setSecondPassword("d5cee333abe432891a0de57d0ee38713");
		patientForm.setAvailable(true);

		patient = patientService.reconstruct(patientForm);
		patientService.save(patient);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegisterPasswordNotEqual() {

		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("Visa");
		creditCard.setcVVcode(123);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2015);
		creditCard.setHolderName("Conchi");
		creditCard.setNumber("4362162324728221");

		Patient patient;
		PatientForm patientForm = new PatientForm();
		patientForm.setUsername("patient99");
		patientForm.setName("patient99");
		patientForm.setSurname("patient99");
		patientForm.setEmailAddress("patient2@patient2.com");
		patientForm.setPhone("666666666");
		patientForm.setAddress("c/Pedro Leon");
		patientForm.setCreditCard(creditCard);
		patientForm.setPassword("5ce4d191fd14ac85a1469fb8c29b7a7b");
		patientForm.setSecondPassword("5ce4d191fd14ac85a3333333333b7a7b");
		patientForm.setAvailable(true);

		patient = patientService.reconstruct(patientForm);
		patientService.save(patient);


	}

}
