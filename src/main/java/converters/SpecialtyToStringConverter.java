package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Specialty;

@Component
@Transactional
public class SpecialtyToStringConverter implements Converter<Specialty, String> {
	@Override
	public String convert(Specialty specialty) {
		String result;

		if (specialty == null)
			result = null;
		else
			result = String.valueOf(specialty.getId());

		return result;
	}
}
