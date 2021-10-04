package io.javabrains.ratingsdataservice.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserRating {
	
	private List<Rating> userRating;
	
	
	

}
