package com.harikesh.XMeme.DTO;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * The Class UpdateMemeDTO.
 * 
 * @author harikesh.pallantla
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class UpdateMemeDTO {

	/** The meme image url. */
	@ApiModelProperty(value = "Image URL of the meme.", example = "http://placeimg.com/640/480")
	@URL(message = "Enter a valid URL")
	@Size(max = 700, message = "Maximum of 700 characters are allowed")
	private String url;

	/** The caption for the meme. */
	@ApiModelProperty(value = "Caption for the meme.", example = "Sample caption")
	@Size(max = 255, message = "Maximum of 255 characters are allowed")
	private String caption;
}
