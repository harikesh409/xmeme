package com.harikesh.XMeme.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harikesh.XMeme.DTO.MemeDTO;
import com.harikesh.XMeme.DTO.UpdateMemeDTO;
import com.harikesh.XMeme.service.MemeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class MemeController.
 * 
 * @author harikesh.pallantla
 */
@RestController
@Slf4j
@RequestMapping("/memes")
@Api(value = "Meme")
@SwaggerDefinition(
		info = @Info(description = "Meme operations", version = "1.0", title = "Meme Controller"))
@Validated
@CrossOrigin
public class MemeController {

	/** The meme service. */
	@Autowired
	MemeService memeService;

	/**
	 * Gets the meme by id.
	 *
	 * @param id the id
	 * @return the meme by id with status code 200
	 * @return status code 404 if id is not found
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get meme of a particular id", response = MemeDTO.class,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MemeDTO> getMemeById(
			@PathVariable("id") @ApiParam(value = "Unique identifier for the meme.") Long id) {
		logger.trace("Entered getMemeById");
		Optional<MemeDTO> memeById = memeService.getMemeById(id);
		if (memeById.isPresent()) {
			logger.trace("Exited getMemeById");
			return ResponseEntity.ok(memeById.get());
		}
		logger.trace("Exited getMemeById");
		return ResponseEntity.notFound().build();
	}

	/**
	 * Gets all the memes.
	 *
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @return all the memes with status code 200
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get List of all Memes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MemeDTO>> getAllMemes(
			@RequestParam(defaultValue = "0") @ApiParam(
					value = "The page number you want to fetch. Starts from 0.") Integer pageNumber,
			@RequestParam(defaultValue = "100") @ApiParam(
					value = "Number of items to fetch in a single page.") Integer pageSize) {
		logger.trace("Entered getAllMemes");
		List<MemeDTO> memes = memeService.getAllMemes(pageNumber, pageSize);
		logger.trace("Exited getAllMemes");
		return ResponseEntity.ok(memes);
	}

	/**
	 * Adds the meme.
	 *
	 * @param createMemeDTO the create meme DTO
	 * @return the id of the created meme with status code 201
	 * @return status 409 if Meme already exists
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add a meme", response = MemeDTO.class,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 409, message = "Meme already exists")})
	public ResponseEntity<MemeDTO> addMeme(@RequestBody @Valid MemeDTO createMemeDTO) {
		logger.trace("Entered addMeme");
		Optional<MemeDTO> memeDTO = memeService.saveOrUpdate(createMemeDTO);
		if (memeDTO.isPresent()) {
			logger.trace("Exited addMeme");
			return ResponseEntity.status(HttpStatus.CREATED).body(memeDTO.get());
		}
		logger.trace("Exited addMeme");
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}

	/**
	 * Update meme.
	 *
	 * @param id the id
	 * @param updateMemeDTO the update meme DTO
	 * @return status code 204 if updated successfully
	 * @return status code 404 if meme id not found
	 * @return status code 304 if there is no change in the meme details
	 */
	@RequestMapping(method = RequestMethod.PATCH, value = "/{id}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update a meme's caption or url.",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 204, message = "Meme updated Successfully"),
			@ApiResponse(code = 404, message = "Meme Id not found.")})
	public ResponseEntity<?> updateMeme(
			@PathVariable("id") @ApiParam(value = "Unique identifier for the meme.") long id,
			@RequestBody @Valid UpdateMemeDTO updateMemeDTO) {
		logger.trace("Entered updateMeme");
		Optional<MemeDTO> memeOptional = memeService.getMemeById(id);
		if (!memeOptional.isPresent()) {
			logger.trace("Exited updateMeme");
			return ResponseEntity.notFound().build();
		}
		String url = updateMemeDTO.getUrl();
		String caption = updateMemeDTO.getCaption();
		MemeDTO memeDTO = memeOptional.get();
		if ((url == null && caption == null)
				|| (url.equals(memeDTO.getUrl()) && caption.equals(memeDTO.getCaption()))) {
			logger.trace("Exited updateMeme");
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		}
		if (url != null)
			memeDTO.setUrl(url);
		if (caption != null)
			memeDTO.setCaption(caption);

		memeService.saveOrUpdate(memeDTO);
		logger.trace("Exited updateMeme");
		return ResponseEntity.noContent().build();
	}

	/**
	 * Delete meme.
	 *
	 * @param id the id
	 * @return status code 204 on success
	 * @return status code 404 if meme id not found
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ApiOperation("Delete's a meme.")
	@ApiResponses(value = {@ApiResponse(code = 204, message = "Meme deleted Successfully"),
			@ApiResponse(code = 404, message = "Meme Id not found.")})
	public ResponseEntity<?> deleteMeme(
			@PathVariable("id") @ApiParam(value = "Unique identifier for the meme.") long id) {
		logger.trace("Entered deleteMeme");
		Optional<MemeDTO> memeOptional = memeService.getMemeById(id);
		if (!memeOptional.isPresent()) {
			logger.trace("Exited deleteMeme");
			return ResponseEntity.notFound().build();
		}
		memeService.deleteMemeById(id);
		logger.trace("Exited deleteMeme");
		return ResponseEntity.noContent().build();
	}
}
