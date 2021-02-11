package com.harikesh.XMeme.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.PersistenceException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.harikesh.XMeme.entity.Meme;
import com.harikesh.XMeme.util.MemeTestUtil;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;

/**
 * The Class MemeRepositoryTest.
 * 
 * @author harikesh.pallantla
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
public class MemeRepositoryTest {

	/** The entity manager. */
	@Autowired
	TestEntityManager entityManager;

	/** The meme repository. */
	@Autowired
	MemeRepository memeRepository;

	/** The enhanced random. */
	private EnhancedRandom enhancedRandom =
			EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();

	/** The meme test util. */
	private MemeTestUtil memeTestUtil = new MemeTestUtil();

	/**
	 * Save meme test.
	 */
	@Test
	public void saveMemeTest() {
		Meme actualMeme = enhancedRandom.nextObject(Meme.class, "id", "deleted");
		Meme meme = entityManager.persist(actualMeme);
		memeTestUtil.memeAssertions(meme, actualMeme);
	}

	/**
	 * Required attributes test.
	 */
	@Test
	public void requiredAttributesTest() {
		assertThrows(PersistenceException.class, () -> {
			Meme meme = entityManager.persist(new Meme());
			assertThat(meme).isNull();
		});
	}

	/**
	 * Gets the meme by id test.
	 *
	 * @return the meme test
	 */
	@Test
	public void getMemeByIdTest() {
		Meme actualMeme = enhancedRandom.nextObject(Meme.class, "id", "deleted");
		Meme savedMeme = entityManager.persist(actualMeme);
		Meme meme = entityManager.find(Meme.class, savedMeme.getId());
		memeTestUtil.memeAssertions(meme, savedMeme);
	}

	/**
	 * Delete meme by id test.
	 */
	@Test
	public void deleteMemeBYIdTest() {
		Meme actualMeme = enhancedRandom.nextObject(Meme.class, "id", "deleted");
		Meme savedMeme = entityManager.persist(actualMeme);
		memeTestUtil.memeAssertions(actualMeme, savedMeme);
		entityManager.remove(savedMeme);
		Meme meme = entityManager.find(Meme.class, savedMeme.getId());
		assertThat(meme).isNull();
	}

}
