package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Specialist;

@Component
@Transactional
public class SpecialistToStringConverter implements Converter<Specialist, String> {
    @Override
    public String convert(Specialist specialist) {
        String result;

        if (specialist == null)
            result = null;
        else
            result = String.valueOf(specialist.getId());

        return result;
    }
}
