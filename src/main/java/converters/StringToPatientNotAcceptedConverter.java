package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PatientNotAcceptedRepository;
import domain.PatientNotAccepted;

@Component
@Transactional
public class StringToPatientNotAcceptedConverter implements Converter<String, PatientNotAccepted> {

    @Autowired
    PatientNotAcceptedRepository patientNotAcceptedRepository;

    @Override
    public PatientNotAccepted convert(String text) {
    	PatientNotAccepted result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = patientNotAcceptedRepository.findOne(id);
            }
        } catch (Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}
