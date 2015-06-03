package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.OfferRepository;
import domain.Offer;

@Component
@Transactional
public class StringToOfferConverter implements Converter<String, Offer>{
	
	@Autowired
	OfferRepository offerRepository;

	@Override
	public Offer convert(String text) {
		Offer result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = offerRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}

