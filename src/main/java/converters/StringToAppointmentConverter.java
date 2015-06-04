package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.AppointmentRepository;
import domain.Appointment;

@Component
@Transactional
public class StringToAppointmentConverter implements Converter<String, Appointment> {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Override
    public Appointment convert(String text) {
        Appointment result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = appointmentRepository.findOne(id);
            }
        } catch (Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}
