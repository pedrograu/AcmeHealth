package converters;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Timetable;


@Component
@Transactional
public class TimetableToStringConverter implements Converter<Timetable, String> {
	
	@Override
	public String convert(Timetable timetable) {
		String result;

		if (timetable == null)
			result = null;
		else
			result = String.valueOf(timetable.getId());

		return result;
	}



}
