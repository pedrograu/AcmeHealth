package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.PatientNotAccepted;



@Component
@Transactional
public class PatientNotAcceptedToStringConverter implements Converter<PatientNotAccepted, String> {
    @Override
    public String convert(PatientNotAccepted pn) {
        String result;

        if (pn == null)
            result = null;
        else
            result = String.valueOf(pn.getId());

        return result;
    }
}