package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OfferRepository;
import domain.Administrator;
import domain.Appointment;
import domain.Offer;
import domain.Patient;
import domain.Specialist;

@Service
@Transactional
public class OfferService {

    // Managed repository ---------------------------------------

    @Autowired
    private OfferRepository offerRepository;

    // Services ---------------------------------------

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private SpecialistService specialistService;

    // Constructors ---------------------------------------------

    public OfferService() {
        super();
    }

    // Metodos ---------------------------------------------

    //Crea un offer para el administrador que está logueado en el sistema.
    public Offer create() {

        Offer result = new Offer();
        Administrator administrator = administratorService.findByPrincipal();
        Collection<Appointment> appointments = new HashSet<Appointment>();
        Collection<Patient> patients = new HashSet<Patient>();
        Date fechaActual = new Date();
        result.setCreationMoment(fechaActual);
        result.setAdministrator(administrator);
        result.setAppointments(appointments);
        result.setPatients(patients);
        result.setEnrollees(new Integer(0));

        return result;

    }

    //Guarda un offer en la base de datos
    public void save(Offer offer) {
        checkPrincipal(offer);
        Assert.notNull(offer);
        Date newMoment = new Date(System.currentTimeMillis() - 1000);
        offer.setCreationMoment(newMoment);

        //Comprueba que la fecha de inicio de la oferta es posterior a la fecha actual
        Assert.isTrue(offer.getStartMoment().after(newMoment));
        
        //Comprueba que la fecha de inicio de la oferta es anterior a la fecha de fin de la oferta
        Assert.isTrue(offer.getStartMoment().before(offer.getFinishMoment()));
        offerRepository.save(offer);

    }

    //Comprueba si la oferta se puede eliminar (se puede eliminar si no tiene pacientes inscritos en ella)
    public boolean canDelete(Offer offer) {

        boolean res = false;

        if (offer.getEnrollees() == 0) {
            res = true;
        }

        return res;
    }

    //Elimina una oferta de la base de datos
    public void delete(Offer offer) {
        checkPrincipal(offer);
        Assert.notNull(offer);

        //Comprueba que no tenga pacientes inscritos en ella
        Assert.isTrue(offer.getEnrollees() == 0);

        offerRepository.delete(offer);

    }

    //Devuelve una oferta dado su id.
    public Offer findOneToEdit(int id) {
        Assert.isTrue(id != 0);
        Offer res;
        res = offerRepository.findOne(id);
        return res;
    }

    //Comprueba que la oferta es del administrador que está logueado en el sistema.
    public void checkPrincipal(Offer offer) {
        Administrator administratorConnect = administratorService.findByPrincipal();
        Assert.isTrue(offer.getAdministrator().equals(administratorConnect));
    }

    //Devuelve una collections de offer del administrador que está logueado en el sistema
    public Collection<Offer> findOwn() {
        Administrator administrator = administratorService.findByPrincipal();
        Collection<Offer> offers = offerRepository.findOwn(administrator.getId());
        return offers;
    }

  //Devuelve una collections de offer del paciente que está logueado en el sistema
    public Collection<Offer> findOwnPatient() {
        Patient patient = patientService.findByPrincipal();
        Collection<Offer> offers = offerRepository.findOwnPatient(patient.getId());
        return offers;
    }

  //Devuelve una collections de offer no finalizadas
    public Collection<Offer> findNotFinished() {
        Date currentMoment = new Date();
        Collection<Offer> result = offerRepository.findNotFinished(currentMoment);
        return result;
    }

  //Devuelve una collections de offer activas
    public Collection<Offer> findActive() {
        Date currentMoment = new Date();
        Collection<Offer> result = offerRepository.findActive(currentMoment);
        return result;
    }

    //Devuelve una collections de offer ordenadas por precio
    public Collection<Offer> findOrder() {
        Collection<Offer> result = offerRepository.findOrder();
        return result;
    }

    //Inscribe a un paciente en una oferta
    public void hire(Offer o) {

        Patient patientConnect = patientService.findByPrincipal();
        Date currentMoment = new Date();

        //Comprueba que queda sitio para inscribirse en la oferta
        Assert.isTrue(o.getAmountPerson() > o.getEnrollees());
        
        //Comprueba que la oferta no está finalizada
        Assert.isTrue(o.getFinishMoment().after(currentMoment));

        if (!o.getPatients().contains(patientConnect)) {
            o.add(patientConnect);
            patientConnect.add(o);

            o.setEnrollees(o.getEnrollees() + 1);
        }

    }

   //Devuelve una collections de offer no finalizadas para el administrador que está logueado en el sistema
    public Collection<Offer> findNotFinishedOwn() {
        Administrator administrator = administratorService.findByPrincipal();
        Date currentMoment = new Date();
        Collection<Offer> result = offerRepository.findNotFinishedOwn(currentMoment, administrator.getId());
        return result;
    }

    //Devuelve una collections de offer no finalizadas para el especialista que está logueado en el sistema
    public Collection<Offer> findNotFinishedOwnSpecialist() {
        Specialist specialist = specialistService.findByPrincipal();
        Date currentMoment = new Date();
        Collection<Offer> result = offerRepository.findNotFinishedOwnSpecialist(currentMoment, specialist.getId());
        return result;
    }

    //Comprueba si la cita para el tratamiento en oferta es valida(si la fecha de la cita está contenida entre la fecha de inicio y fin de la oferta)
    public boolean appointmentValid(Appointment appointment, Offer offer) {
        boolean res = false;
        if (appointment.getOffer().getStartMoment().before(appointment.getStartMoment())
                && appointment.getOffer().getFinishMoment().after(appointment.getStartMoment())) {
            res = true;
        }

        return res;
    }

}
