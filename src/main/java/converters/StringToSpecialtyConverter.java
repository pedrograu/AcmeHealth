package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SpecialtyRepository;
import domain.Specialty;

@Component
@Transactional
public class StringToSpecialtyConverter implements Converter<String, Specialty> {

    @Autowired
    SpecialtyRepository specialtyRepository;

    @Override
    public Specialty convert(String text) {
        Specialty result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = specialtyRepository.findOne(id);
            }
        } catch (Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}
