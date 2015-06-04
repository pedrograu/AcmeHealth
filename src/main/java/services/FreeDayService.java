package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FreeDayRepository;
import domain.FreeDay;
import domain.Specialist;
import forms.FreeDayForm;

@Service
@Transactional
public class FreeDayService {

    // Managed repository ---------------------------------------
    @Autowired
    private FreeDayRepository freeDayRepository;

    // Managed service ---------------------------------------

    @Autowired
    private SpecialistService specialistService;

    // Constructors ---------------------------------------------

    public FreeDayService() {
        super();
    }

    public Collection<FreeDay> getFreeDaysForSpecialist(Specialist s) {

        Collection<FreeDay> freeDays = freeDayRepository.getFreeDaysForSpecialist(s.getId());

        return freeDays;
    }

    public FreeDay recontructor(FreeDayForm freeDayForm) {
        FreeDay result = create();
        result.setId(freeDayForm.getId());
        result.setVersion(freeDayForm.getVersion());
        result.setDescription(freeDayForm.getDescription());
        result.setFinishMoment(freeDayForm.getFinishMoment());
        result.setStartMoment(freeDayForm.getStartMoment());

        return result;
    }

    public FreeDay create() {
        Specialist specialistConnect = specialistService.findByPrincipal();

        FreeDay result = new FreeDay();
        result.setSpecialist(specialistConnect);

        return result;
    }

    public void save(FreeDay freeDay) {
        checkPrincipal(freeDay);
        Date currentMoment = new Date();
        Assert.notNull(freeDay);
        Calendar start = new GregorianCalendar();
        start.setTime(freeDay.getStartMoment());
        Calendar finish = new GregorianCalendar();
        finish.setTime(freeDay.getFinishMoment());
        Assert.isTrue(start.before(finish));
        Assert.isTrue(currentMoment.before(freeDay.getStartMoment()));
        freeDayRepository.save(freeDay);

    }

    public FreeDay findOneToEdit(int id) {
        Assert.isTrue(id != 0);
        FreeDay res;
        res = freeDayRepository.findOne(id);
        return res;
    }

    public void delete(FreeDay freeDay) {
        checkPrincipal(freeDay);
        freeDayRepository.delete(freeDay);

    }

    public Collection<FreeDay> findAllFreeDays() {
        Collection<FreeDay> res = freeDayRepository.findAllFreeDays();
        return res;
    }

    public void checkPrincipal(FreeDay freeDay) {
        Specialist specialistConnect = specialistService.findByPrincipal();
        Assert.isTrue(freeDay.getSpecialist().equals(specialistConnect));
    }

}
