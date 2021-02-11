package com.harikesh.XMeme.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import lombok.NoArgsConstructor;

/**
 * Instantiates a new mapper util.
 */
@NoArgsConstructor
/**
 * Utility class for ModelMapper
 * 
 * @author harikesh.pallantla
 *
 */
public class MapperUtil {

	/** The model mapper. */
	private static ModelMapper modelMapper = new ModelMapper();

	/**
	 * Utility method to map list of elements to a particular class.
	 *
	 * @param <S> the generic type of source class
	 * @param <T> the generic type target class
	 * @param source the source
	 * @param targetClass the target class
	 * @return the list
	 */
	public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
		return source.stream().map(element -> modelMapper.map(element, targetClass))
				.collect(Collectors.toList());
	}
}
