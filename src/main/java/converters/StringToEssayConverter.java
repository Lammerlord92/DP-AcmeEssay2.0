package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.EssayRepository;

import domain.Essay;

@Component
@Transactional
public class StringToEssayConverter implements Converter<String, Essay>{
	@Autowired
	private EssayRepository essayRepository;
	
	@Override
	public Essay convert(String text) {
		Essay result;
		int id;
		try{
			if(StringUtils.isEmpty(text))
				result=null;
			else{
				id=Integer.valueOf(text);
				result=essayRepository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}
}
