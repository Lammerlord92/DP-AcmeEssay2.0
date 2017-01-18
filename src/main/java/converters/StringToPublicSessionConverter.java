package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.AuthorRepository;
import repositories.PublicSessionRepository;
import domain.Author;
import domain.PublicSession;
@Component
@Transactional
public class StringToPublicSessionConverter implements Converter<String,PublicSession>{
	@Autowired
	private PublicSessionRepository sessionRepository;
	
	@Override
	public PublicSession convert(String text) {
		PublicSession result;
		int id;
		try{
			if(StringUtils.isEmpty(text))
				result=null;
			else{
				id=Integer.valueOf(text);
				result=sessionRepository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}
}
