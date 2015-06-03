package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MedicalHistoryRepository;
import domain.MedicalHistory;

@Component
@Transactional
public class StringToMedicalHistoryConverter implements Converter<String, MedicalHistory>{
	
	@Autowired
	MedicalHistoryRepository medicalHistoryRepository;

	@Override
	public MedicalHistory convert(String text) {
		MedicalHistory result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = medicalHistoryRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}

