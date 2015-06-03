package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Prescription;

@Component
@Transactional
public class PrescriptionToStringConverter implements Converter<Prescription, String> {
	@Override
	public String convert(Prescription prescription) {
		String result;

		if (prescription == null)
			result = null;
		else
			result = String.valueOf(prescription.getId());

		return result;
	}
}
