package io.javabrains.moviecatalogservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRating {
	
	private String userId;
	private List<Rating> userRating;
	
	
	

}
