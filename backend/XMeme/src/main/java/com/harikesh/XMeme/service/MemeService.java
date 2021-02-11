package com.harikesh.XMeme.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.harikesh.XMeme.DTO.MemeDTO;
import com.harikesh.XMeme.entity.Meme;
import com.harikesh.XMeme.repository.MemeRepository;
import com.harikesh.XMeme.util.MapperUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class MemeService.
 * 
 * @author harikesh.pallantla
 */
@Service
@Slf4j
public class MemeService {

	/** The meme repository. */
	@Autowired
	MemeRepository memeRepository;

	/** The model mapper. */
	private ModelMapper modelMapper = new ModelMapper();

	/**
	 * Instantiates a new meme service.
	 */
	public MemeService() {
		super();
		modelMapper.getConfiguration().setFieldMatchingEnabled(false)
				.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setSkipNullEnabled(true);
	}

	/**
	 * Gets the meme by id.
	 *
	 * @param id the id
	 * @return the meme by id
	 */
	public Optional<MemeDTO> getMemeById(long id) {
		logger.trace("Entered getMemeById");
		logger.debug("Input Meme ID: {}", id);
		Optional<Meme> meme = memeRepository.findById(id);
		Optional<MemeDTO> memeDTO = Optional.empty();
		if (meme.isPresent()) {
			logger.info("Meme details with ID {} found", id);
			MemeDTO map = modelMapper.map(meme.get(), MemeDTO.class);
			memeDTO = Optional.of(map);
		}
		logger.trace("Exited getMemeById");
		return memeDTO;
	}

	/**
	 * Gets all the memes.
	 *
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @return the all memes
	 */
	public List<MemeDTO> getAllMemes(int pageNumber, int pageSize) {
		logger.trace("Entered getAllMemes");
		logger.debug("Page Number: {} Page Size: {}", pageNumber, pageSize);
		Pageable sortByCreatedDesc =
				PageRequest.of(pageNumber, pageSize, Sort.by("createdOn").descending());
		Page<Meme> pageMemes = memeRepository.findAll(sortByCreatedDesc);
		List<Meme> memesList = pageMemes.getContent();
		List<MemeDTO> memes = MapperUtil.mapList(memesList, MemeDTO.class);
		logger.trace("Exited getAllMemes");
		return memes;
	}

	/**
	 * Save or update a meme.
	 *
	 * @param newMeme the new meme
	 * @return the optional
	 */
	public Optional<MemeDTO> saveOrUpdate(MemeDTO newMeme) {
		logger.trace("Entered addMeme");
		Optional<MemeDTO> memeDTO = Optional.empty();
		try {
			Meme meme = modelMapper.map(newMeme, Meme.class);
			Meme savedMeme = memeRepository.save(meme);
			MemeDTO dto = new MemeDTO();
			dto.setId(savedMeme.getId());
			memeDTO = Optional.of(dto);
		} catch (DataIntegrityViolationException e) {
			logger.info("Meme already exists");
		}
		logger.trace("Exited addMeme");
		return memeDTO;
	}

	
	/**
	 * Delete meme by id.
	 *
	 * @param id the id
	 */
	public void deleteMemeById(long id) {
		logger.trace("Entered deleteMeme");
		logger.debug("ID: {}", id);
		memeRepository.deleteById(id);
		logger.trace("Exited deleteMeme");
	}

}
