package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PrescriptionRepository;
import domain.Prescription;

@Component
@Transactional
public class StringToPrescriptionConverter implements Converter<String, Prescription>{
	
	@Autowired
	PrescriptionRepository prescriptionRepository;

	@Override
	public Prescription convert(String text) {
		Prescription result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = prescriptionRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}

