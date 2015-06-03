package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PatientRepository;
import domain.Patient;

@Component
@Transactional
public class StringToPatientConverter implements Converter<String, Patient>{
	
	@Autowired
	PatientRepository patientRepository;

	@Override
	public Patient convert(String text) {
		Patient result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = patientRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}

