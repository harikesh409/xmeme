package com.harikesh.XMeme.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class MemeDTO.
 * 
 * @author harikesh.pallantla
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class MemeDTO {

	/** The unique identifier. */
	@ApiModelProperty(value = "Unique identifier for a meme. This value is auto generated",
			accessMode = AccessMode.READ_ONLY, hidden = true)
	private long id;

	/** The author name. */
	@NotBlank(message = "Name is mandatory")
	@Size(max = 255, message = "Maximum of 255 characters are allowed.")
	@ApiModelProperty(value = "Name of the user that posted the meme.", required = true,
			example = "Harikesh")
	private String name;

	/** The meme image url. */
	@NotBlank(message = "URL is mandatory")
	@URL(message = "Enter a valid URL")
	@Size(max = 700, message = "Maximum of 700 characters are allowed")
	@ApiModelProperty(value = "Image URL of the meme.", required = true,
			example = "https://ichef.bbci.co.uk/images/ic/704xn/p072ms6r.jpg")
	private String url;

	/** The caption for the meme. */
	@NotBlank(message = "Caption is mandatory")
	@Size(max = 255, message = "Maximum of 255 characters are allowed")
	@ApiModelProperty(value = "Caption for the meme.", required = true, example = "Sample caption")
	private String caption;
}
