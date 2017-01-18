package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Essay;
@Component
@Transactional
public class EssayToStringConverter implements Converter<Essay, String>{
	@Override
	public String convert(Essay source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
