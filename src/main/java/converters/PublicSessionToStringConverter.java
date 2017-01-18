package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.PublicSession;
@Component
@Transactional
public class PublicSessionToStringConverter implements Converter<PublicSession,String>{
	@Override
	public String convert(PublicSession source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
