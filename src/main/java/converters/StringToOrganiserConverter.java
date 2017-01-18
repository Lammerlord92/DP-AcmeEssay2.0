package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.OrganiserRepository;

import domain.Organiser;

@Component
@Transactional
public class StringToOrganiserConverter implements Converter<String, Organiser>{
	@Autowired
	private OrganiserRepository organiserRepository;
	
	@Override
	public Organiser convert(String text) {
		Organiser result;
		int id;
		try{
			if(StringUtils.isEmpty(text))
				result=null;
			else{
				id=Integer.valueOf(text);
				result=organiserRepository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}
}
