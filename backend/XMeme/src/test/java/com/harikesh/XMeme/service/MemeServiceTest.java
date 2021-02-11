package com.harikesh.XMeme.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.harikesh.XMeme.DTO.MemeDTO;
import com.harikesh.XMeme.entity.Meme;
import com.harikesh.XMeme.repository.MemeRepository;
import com.harikesh.XMeme.util.MemeTestUtil;
import io.github.benas.randombeans.api.EnhancedRandom;;

/**
 * The Class MemeServiceTest.
 * 
 * @author harikesh.pallantla
 */
@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class MemeServiceTest {

	/** The meme repository. */
	@Mock
	MemeRepository memeRepository;

	/** The meme service. */
	@InjectMocks
	private MemeService memeService;


	/** The meme test util. */
	private MemeTestUtil memeTestUtil = new MemeTestUtil();

	/**
	 * Adds the meme test.
	 */
	@Test
	public void addMeme() {
		Meme mockMeme = memeTestUtil.getRandomMeme();
		MemeDTO mockMemeDTO = memeTestUtil.convertMemeToMemeDTO(mockMeme);
		when(memeRepository.save(Mockito.any(Meme.class))).thenReturn(mockMeme);
		Optional<MemeDTO> savedMeme = memeService.saveOrUpdate(mockMemeDTO);
		assertThat(savedMeme).isNotEmpty();
		assertThat(savedMeme.get().getId()).isNotNull();
		verify(memeRepository, times(1)).save(Mockito.isA(Meme.class));
	}

	/**
	 * Gets the meme by id test.
	 *
	 * @return the meme by id
	 */
	@Test
	public void getMemeById() {
		Meme mockMeme = memeTestUtil.getRandomMeme();
		MemeDTO mockMemeDTO = memeTestUtil.convertMemeToMemeDTO(mockMeme);
		when(memeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockMeme));
		Optional<MemeDTO> memeById = memeService.getMemeById(mockMemeDTO.getId());
		assertThat(memeById).isNotEmpty();
		MemeDTO memeDTO = memeById.get();
		assertThat(memeDTO.getId()).isEqualTo(mockMemeDTO.getId());
		assertThat(memeDTO.getName()).isEqualTo(mockMemeDTO.getName());
		assertThat(memeDTO.getCaption()).isEqualTo(mockMemeDTO.getCaption());
		assertThat(memeDTO.getUrl()).isEqualTo(mockMemeDTO.getUrl());
		verify(memeRepository, times(1)).findById(mockMemeDTO.getId());
	}

	/**
	 * Gets the all memes test.
	 *
	 */
	@Test
	public void getAllMemes() {
		int pageSize = 100;
		List<Meme> memes = EnhancedRandom.randomListOf(pageSize, Meme.class);
		Page<Meme> pageImpl = new PageImpl<Meme>(memes, PageRequest.of(0, pageSize), pageSize);
		when(memeRepository.findAll(Mockito.isA(Pageable.class))).thenReturn(pageImpl);
		List<MemeDTO> allMemes = memeService.getAllMemes(0, pageSize);
		assertThat(allMemes).isNotEmpty();
		assertThat(allMemes.size()).isEqualTo(pageSize);
		verify(memeRepository, times(1)).findAll(Mockito.isA(Pageable.class));
	}

	/**
	 * Delete meme by id test.
	 */
	@Test
	public void deleteMemeById() {
		when(memeRepository.findById(0L)).thenReturn(Optional.empty());
		memeService.deleteMemeById(0L);
		verify(memeRepository, times(1)).deleteById(0L);
		Optional<MemeDTO> memeById = memeService.getMemeById(0L);
		assertThat(memeById).isEmpty();
		verify(memeRepository, times(1)).findById(0L);
	}
}
