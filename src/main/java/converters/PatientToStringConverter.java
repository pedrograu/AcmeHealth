package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Patient;

@Component
@Transactional
public class PatientToStringConverter implements Converter<Patient, String> {
    @Override
    public String convert(Patient patient) {
        String result;

        if (patient == null)
            result = null;
        else
            result = String.valueOf(patient.getId());

        return result;
    }
}
