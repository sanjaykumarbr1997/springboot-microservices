package io.javabrains.ratingsdataservice.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.javabrains.ratingsdataservice.dto.Rating;
import io.javabrains.ratingsdataservice.dto.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {
	
	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		return new Rating(movieId, 5);
	}
	
	@RequestMapping("users/{userId}")
	public UserRating getUserRatings(@PathVariable("userId") String userId) {
		
		List<Rating> ratings = Arrays.asList(
				new Rating("1234", 4),
				new Rating("5678", 5),
				new Rating("9101", 3)
				
				);
		
		UserRating userRating = new UserRating();
		userRating.setUserRating(ratings);
		return userRating;
	}
	
	

}
