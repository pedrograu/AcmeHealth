
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.TimetableRepository;
import domain.Prescription;
import domain.Timetable;

@Component
@Transactional
public class StringToTimetableConverter implements Converter<String, Timetable>{
	
	@Autowired
	TimetableRepository timetableRepository;

	@Override
	public Timetable convert(String text) {
		Timetable result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = timetableRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}

