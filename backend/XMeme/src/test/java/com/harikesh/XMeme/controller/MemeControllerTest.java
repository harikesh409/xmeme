package com.harikesh.XMeme.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harikesh.XMeme.DTO.MemeDTO;
import com.harikesh.XMeme.DTO.UpdateMemeDTO;
import com.harikesh.XMeme.entity.Meme;
import com.harikesh.XMeme.service.MemeService;
import com.harikesh.XMeme.util.MapperUtil;
import com.harikesh.XMeme.util.MemeTestUtil;
import io.github.benas.randombeans.api.EnhancedRandom;

/**
 * The Class MemeControllerTest.
 * 
 * @author harikesh.pallantla
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = MemeController.class)
public class MemeControllerTest {

	/** The mock mvc. */
	@Autowired
	private MockMvc mockMvc;

	/** The meme service. */
	@MockBean
	private MemeService memeService;

	/** The meme test util. */
	private MemeTestUtil memeTestUtil = new MemeTestUtil();

	/** The object mapper. */
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Gets the meme by id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void getMemeById() throws Exception {
		Meme mockMeme = memeTestUtil.getRandomMeme();
		MemeDTO mockMemeDTO = memeTestUtil.convertMemeToMemeDTO(mockMeme);
		when(memeService.getMemeById(Mockito.anyLong())).thenReturn(Optional.of(mockMemeDTO));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/memes/{id}", mockMeme.getId())
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(header().string(HttpHeaders.ETAG, Matchers.notNullValue()))
				.andExpect(jsonPath("$.*", hasSize(4))).andReturn();
		MemeDTO memeDTO =
				objectMapper.readValue(result.getResponse().getContentAsString(), MemeDTO.class);
		memeTestUtil.memeDTOAssertions(mockMemeDTO, memeDTO);
		verify(memeService, times(1)).getMemeById(Mockito.anyLong());
	}

	/**
	 * Should return 404 when get by id.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldReturn404WhenGetById() throws Exception {
		final long memeId = 1L;
		when(memeService.getMemeById(Mockito.anyLong())).thenReturn(Optional.empty());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/memes/{id}", memeId)
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
		verify(memeService, times(1)).getMemeById(Mockito.anyLong());
	}

	/**
	 * Gets all the memes test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void getAllMemes() throws Exception {
		List<Meme> memes = EnhancedRandom.randomListOf(100, Meme.class);
		List<MemeDTO> memeDTOList = MapperUtil.mapList(memes, MemeDTO.class);
		when(memeService.getAllMemes(Mockito.anyInt(), Mockito.anyInt())).thenReturn(memeDTOList);
		RequestBuilder requestBuilder =
				MockMvcRequestBuilders.get("/memes").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(header().string(HttpHeaders.ETAG, Matchers.notNullValue()))
				.andExpect(jsonPath("$", hasSize(memeDTOList.size())));
		verify(memeService, times(1)).getAllMemes(Mockito.anyInt(), Mockito.anyInt());
	}

	/**
	 * Gets all the memes when no memes are present test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void getAllMemesEmptyTest() throws Exception {
		when(memeService.getAllMemes(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(new ArrayList<MemeDTO>());
		RequestBuilder requestBuilder =
				MockMvcRequestBuilders.get("/memes").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(header().string(HttpHeaders.ETAG, Matchers.notNullValue()))
				.andExpect(jsonPath("$", hasSize(0)));
		verify(memeService, times(1)).getAllMemes(Mockito.anyInt(), Mockito.anyInt());
	}

	/**
	 * Adds the meme test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void addMeme() throws Exception {
		MemeDTO randomMemeDTO = memeTestUtil.getRandomMemeDTO();
		MemeDTO memeDTO = new MemeDTO();
		memeDTO.setId(randomMemeDTO.getId());
		when(memeService.saveOrUpdate(Mockito.any())).thenReturn(Optional.of(memeDTO));
		RequestBuilder requestBuilder =
				MockMvcRequestBuilders.post("/memes").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(randomMemeDTO));
		mockMvc.perform(requestBuilder).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.*", hasSize(1)));
		verify(memeService, times(1)).saveOrUpdate(Mockito.any());
	}

	/**
	 * Should return 409 when add meme with same data.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldReturn409WhenAddMemeWithSameData() throws Exception {
		MemeDTO randomMemeDTO = memeTestUtil.getRandomMemeDTO();
		when(memeService.saveOrUpdate(Mockito.any())).thenReturn(Optional.empty());
		RequestBuilder requestBuilder =
				MockMvcRequestBuilders.post("/memes").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(randomMemeDTO));
		mockMvc.perform(requestBuilder).andExpect(status().isConflict());
		verify(memeService, times(1)).saveOrUpdate(Mockito.any());
	}

	/**
	 * Update meme test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateMeme() throws Exception {
		MemeDTO randomMemeDTO = memeTestUtil.getRandomMemeDTO();
		MemeDTO memeDTO = new MemeDTO();
		memeDTO.setId(randomMemeDTO.getId());
		when(memeService.saveOrUpdate(Mockito.any())).thenReturn(Optional.of(memeDTO));
		when(memeService.getMemeById(Mockito.anyLong())).thenReturn(Optional.of(randomMemeDTO));
		UpdateMemeDTO updateDTO = memeTestUtil.getRandomUpdateDTO();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/memes/{id}", memeDTO.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(updateDTO));
		mockMvc.perform(requestBuilder).andExpect(status().isNoContent());
		verify(memeService, times(1)).saveOrUpdate(Mockito.any());
		verify(memeService, times(1)).getMemeById(Mockito.anyLong());
	}

	/**
	 * Should return 404 when meme ID not found update.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldReturn404WhenMemeIDNotFoundUpdate() throws Exception {
		when(memeService.getMemeById(Mockito.anyLong())).thenReturn(Optional.empty());
		UpdateMemeDTO updateDTO = memeTestUtil.getRandomUpdateDTO();
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.patch("/memes/{id}", Mockito.anyLong()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(updateDTO));
		mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
		verify(memeService, times(1)).getMemeById(Mockito.anyLong());
		verify(memeService, times(0)).saveOrUpdate(Mockito.any());
	}

	/**
	 * Should return 400 when invalid request update.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldReturn400WhenInvalidRequestUpdate() throws Exception {
		MemeDTO randomMemeDTO = memeTestUtil.getRandomMemeDTO();
		MemeDTO memeDTO = new MemeDTO();
		memeDTO.setId(randomMemeDTO.getId());
		when(memeService.saveOrUpdate(Mockito.any())).thenReturn(Optional.of(memeDTO));
		when(memeService.getMemeById(Mockito.anyLong())).thenReturn(Optional.of(randomMemeDTO));
		UpdateMemeDTO updateDTO = new UpdateMemeDTO();
		updateDTO.setUrl("invalid url");
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.patch("/memes/{id}", randomMemeDTO.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(updateDTO));
		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
		verify(memeService, times(0)).saveOrUpdate(Mockito.any());
		verify(memeService, times(0)).getMemeById(Mockito.anyLong());
	}

	/**
	 * Should return 304 when no changes update.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldReturn304WhenNoChangesUpdate() throws Exception {
		MemeDTO randomMemeDTO = memeTestUtil.getRandomMemeDTO();
		when(memeService.getMemeById(randomMemeDTO.getId())).thenReturn(Optional.of(randomMemeDTO));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.patch("/memes/{id}", randomMemeDTO.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new UpdateMemeDTO()));
		mockMvc.perform(requestBuilder).andExpect(status().isNotModified());
		verify(memeService, times(0)).saveOrUpdate(Mockito.any());
		verify(memeService, times(1)).getMemeById(randomMemeDTO.getId());
	}

	/**
	 * Delete meme by id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteMemeById() throws Exception {
		MemeDTO randomMemeDTO = memeTestUtil.getRandomMemeDTO();
		when(memeService.getMemeById(randomMemeDTO.getId())).thenReturn(Optional.of(randomMemeDTO));
		doNothing().when(memeService).deleteMemeById(randomMemeDTO.getId());
		RequestBuilder requestBuilder =
				MockMvcRequestBuilders.delete("/memes/{id}", randomMemeDTO.getId())
						.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isNoContent());
		verify(memeService, times(1)).getMemeById(randomMemeDTO.getId());
		verify(memeService, times(1)).deleteMemeById(randomMemeDTO.getId());
	}

	/**
	 * Should return 404 when id not found delete meme test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void shouldReturn404whenIdNotFoundDeleteMeme() throws Exception {
		when(memeService.getMemeById(Mockito.anyLong())).thenReturn(Optional.empty());
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/memes/{id}", Mockito.anyLong()).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
		verify(memeService, times(1)).getMemeById(Mockito.anyLong());
		verify(memeService, times(0)).deleteMemeById(Mockito.anyLong());
	}
}
