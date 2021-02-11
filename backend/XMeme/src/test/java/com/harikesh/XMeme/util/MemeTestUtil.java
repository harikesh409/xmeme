package com.harikesh.XMeme.util;

import static org.assertj.core.api.Assertions.assertThat;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import com.harikesh.XMeme.DTO.MemeDTO;
import com.harikesh.XMeme.DTO.UpdateMemeDTO;
import com.harikesh.XMeme.entity.Meme;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.randomizers.net.UrlRandomizer;
import io.github.benas.randombeans.randomizers.text.StringRandomizer;

/**
 * The Class MemeTestUtil. Utility class for test cases.
 * 
 * @author harikesh.pallantla
 */
public class MemeTestUtil {

	/** The model mapper. */
	private ModelMapper modelMapper = null;

	/** The enhanced random. */
	private EnhancedRandom enhancedRandom = null;

	/** The string randomizer. */
	private StringRandomizer stringRandomizer = null;

	/** The url randomizer. */
	private UrlRandomizer urlRandomizer = null;

	/**
	 * Instantiates a new meme test util.
	 */
	public MemeTestUtil() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setFieldMatchingEnabled(false)
				.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setSkipNullEnabled(true);
		enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
		stringRandomizer = new StringRandomizer(255);
		urlRandomizer = UrlRandomizer.aNewUrlRandomizer();
	}

	/**
	 * Gets the random meme.
	 *
	 * @return the random meme
	 */
	public Meme getRandomMeme() {
		Meme meme = enhancedRandom.nextObject(Meme.class, "deleted", "url");
		String url = urlRandomizer.getRandomValue().toString();
		meme.setUrl(url);
		return meme;
	}

	/**
	 * Gets the random memeDTO.
	 *
	 * @return the random memeDTO
	 */
	public MemeDTO getRandomMemeDTO() {
		Meme meme = enhancedRandom.nextObject(Meme.class, "deleted", "url");
		String url = urlRandomizer.getRandomValue().toString();
		meme.setUrl(url);
		return convertMemeToMemeDTO(meme);
	}

	/**
	 * Gets the random updateDTO.
	 *
	 * @return the random updateDTO
	 */
	public UpdateMemeDTO getRandomUpdateDTO() {
		UpdateMemeDTO updateMemeDTO = new UpdateMemeDTO();
		updateMemeDTO.setCaption(stringRandomizer.getRandomValue());
		String url = urlRandomizer.getRandomValue().toString();
		updateMemeDTO.setUrl(url);
		return updateMemeDTO;
	}

	/**
	 * Convert meme DTO to meme.
	 *
	 * @param dto the MemeDTO
	 * @return the converted meme
	 */
	public Meme convertMemeDTOtoMeme(MemeDTO dto) {
		return modelMapper.map(dto, Meme.class);
	}

	/**
	 * Convert meme to meme DTO.
	 *
	 * @param meme the meme
	 * @return the meme DTO
	 */
	public MemeDTO convertMemeToMemeDTO(Meme meme) {
		return modelMapper.map(meme, MemeDTO.class);
	}

	/**
	 * Meme assertions.
	 *
	 * @param actual the actual meme
	 * @param expected the expected meme
	 */
	public void memeAssertions(Meme actual, Meme expected) {
		assertThat(actual).isNotNull();
		assertThat(expected).isNotNull();
		assertThat(actual.getId()).isNotNull();
		assertThat(actual.getName()).isEqualTo(expected.getName());
		assertThat(actual.getCaption()).isEqualTo(expected.getCaption());
		assertThat(actual.getUrl()).isEqualTo(expected.getUrl());
		assertThat(actual.isDeleted()).isFalse();
	}

	/**
	 * Meme DTO assertions.
	 *
	 * @param actual the actual meme
	 * @param expected the expected meme
	 */
	public void memeDTOAssertions(MemeDTO actual, MemeDTO expected) {
		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
}
