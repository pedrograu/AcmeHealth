package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Comment;
import domain.Offer;
import domain.Specialist;
import domain.Specialty;

import repositories.SpecialtyRepository;

@Service
@Transactional
public class SpecialtyService {
	
	// Managed repository ---------------------------------------
		@Autowired
		private SpecialtyRepository specialtyRepository;
		
		// Services ---------------------------------------
		
		@Autowired
		private AdministratorService administratorService;

	
	
	
	// Constructors ---------------------------------------------

		public SpecialtyService() {
			super();
		}

		// Simple CRUD methods --------------------------------------
		
		public Collection<Specialty> getAll() {

			Collection<Specialty> specialtys = specialtyRepository.getAll();
			return specialtys;
		}
		
		public Specialty findOneToEdit(int specialtyId) {
			Specialty s = specialtyRepository.findOne(specialtyId);
			return s;
		}
		
		public Specialty create() { 
			
			Specialty result = new Specialty();
			
			Collection<Specialist> specialists = new HashSet<Specialist>();
			Administrator administrator = administratorService.findByPrincipal();
			
			result.setAdministrator(administrator);
			result.setSpecialists(specialists);
		
			
			return result;

		}
		
		
		public void save(Specialty specialty) {
			checkPrincipal(specialty);
			Assert.notNull(specialty);
			specialtyRepository.save(specialty);

		}

		
		public void checkPrincipal(Specialty specialty) {
			Administrator administratorConnect = administratorService.findByPrincipal();
			Assert.isTrue(specialty.getAdministrator().equals(administratorConnect));
		}
		

	

}
