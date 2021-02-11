package com.harikesh.XMeme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harikesh.XMeme.entity.Meme;

/**
 * The Interface MemeRepository.
 * 
 * @author harikesh.pallantla
 */
public interface MemeRepository extends JpaRepository<Meme, Long> {

}
