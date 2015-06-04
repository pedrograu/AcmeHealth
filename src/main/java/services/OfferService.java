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

    public void save(Offer offer) {
        checkPrincipal(offer);
        Assert.notNull(offer);
        Date newMoment = new Date(System.currentTimeMillis() - 1000);
        offer.setCreationMoment(newMoment);

        Assert.isTrue(offer.getStartMoment().after(newMoment));
        Assert.isTrue(offer.getStartMoment().before(offer.getFinishMoment()));
        offerRepository.save(offer);

    }

    public boolean canDelete(Offer offer) {

        boolean res = false;

        if (offer.getEnrollees() == 0) {
            res = true;
        }

        return res;
    }

    public void delete(Offer offer) {
        checkPrincipal(offer);
        Assert.notNull(offer);

        Assert.isTrue(offer.getEnrollees() == 0);

        offerRepository.delete(offer);

    }

    public Offer findOneToEdit(int id) {
        Assert.isTrue(id != 0);
        Offer res;
        res = offerRepository.findOne(id);
        return res;
    }

    public void checkPrincipal(Offer offer) {
        Administrator administratorConnect = administratorService.findByPrincipal();
        Assert.isTrue(offer.getAdministrator().equals(administratorConnect));
    }

    //List

    public Collection<Offer> findOwn() {
        Administrator administrator = administratorService.findByPrincipal();
        Collection<Offer> offers = offerRepository.findOwn(administrator.getId());
        return offers;
    }

    public Collection<Offer> findOwnPatient() {
        Patient patient = patientService.findByPrincipal();
        Collection<Offer> offers = offerRepository.findOwnPatient(patient.getId());
        return offers;
    }

    public Collection<Offer> findNotFinished() {
        Date currentMoment = new Date();
        Collection<Offer> result = offerRepository.findNotFinished(currentMoment);
        return result;
    }

    public Collection<Offer> findActive() {
        Date currentMoment = new Date();
        Collection<Offer> result = offerRepository.findActive(currentMoment);
        return result;
    }

    public Collection<Offer> findOrder() {
        Collection<Offer> result = offerRepository.findOrder();
        return result;
    }

    public void hire(Offer o) {

        Patient patientConnect = patientService.findByPrincipal();
        Date currentMoment = new Date();

        Assert.isTrue(o.getAmountPerson() > o.getEnrollees());
        Assert.isTrue(o.getFinishMoment().after(currentMoment));

        if (!o.getPatients().contains(patientConnect)) {
            o.add(patientConnect);
            patientConnect.add(o);

            o.setEnrollees(o.getEnrollees() + 1);
        }

    }

    public Collection<Offer> findNotFinishedOwn() {
        Administrator administrator = administratorService.findByPrincipal();
        Date currentMoment = new Date();
        Collection<Offer> result = offerRepository.findNotFinishedOwn(currentMoment, administrator.getId());
        return result;
    }

    public Collection<Offer> findNotFinishedOwnSpecialist() {
        Specialist specialist = specialistService.findByPrincipal();
        Date currentMoment = new Date();
        Collection<Offer> result = offerRepository.findNotFinishedOwnSpecialist(currentMoment, specialist.getId());
        return result;
    }

    public boolean appointmentValid(Appointment appointment, Offer offer) {
        boolean res = false;
        if (appointment.getOffer().getStartMoment().before(appointment.getStartMoment())
                && appointment.getOffer().getFinishMoment().after(appointment.getStartMoment())) {
            res = true;
        }

        return res;
    }

}
