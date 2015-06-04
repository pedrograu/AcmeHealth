package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.SpecialistRepository;
import domain.Specialist;

public class StringToSpecialistConverter implements Converter<String, Specialist> {

    @Autowired
    SpecialistRepository specialistRepository;

    @Override
    public Specialist convert(String text) {
        Specialist result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = specialistRepository.findOne(id);
            }
        } catch (Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}
