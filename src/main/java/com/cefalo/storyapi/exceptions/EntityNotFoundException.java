package com.cefalo.storyapi.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.util.StringUtils;

@SuppressWarnings("serial")
public class EntityNotFoundException extends RuntimeException {
	
	public EntityNotFoundException(Class class1, String... serachParamsMap) {
		super(EntityNotFoundException.generateMessage(class1.getSimpleName(), toMap(String.class, String.class, serachParamsMap)));
	}

	private static String generateMessage(String entity, Map<String, String> searchParams) {
		return StringUtils.capitalize(entity) + " not found for " + searchParams;
	}
	
	private static <K, V> Map<K, V> toMap(Class<K> keytype, Class<V> valueType, Object... entries) {
		if(entries.length % 2 == 1) throw new IllegalArgumentException("Invalid Entries");
		return IntStream.range(0, entries.length / 2).map(i -> i*2)
				.collect(HashMap ::  new, 
						(m, i) -> m.put(keytype.cast(entries[i]), valueType.cast(entries[i+1])), 
						Map :: putAll);
	}
	
}
