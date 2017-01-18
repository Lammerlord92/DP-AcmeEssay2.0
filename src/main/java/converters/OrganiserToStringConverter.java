package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Organiser;
@Component
@Transactional
public class OrganiserToStringConverter implements Converter<Organiser, String>{
	@Override
	public String convert(Organiser source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
