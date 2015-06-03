package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MedicalHistory;

@Component
@Transactional
public class MedicalHistoryToStringConverter implements Converter<MedicalHistory, String> {
	@Override
	public String convert(MedicalHistory medicalHistory) {
		String result;

		if (medicalHistory == null)
			result = null;
		else
			result = String.valueOf(medicalHistory.getId());

		return result;
	}
}
