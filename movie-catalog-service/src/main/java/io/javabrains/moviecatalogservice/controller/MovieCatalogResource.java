package io.javabrains.moviecatalogservice.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.javabrains.moviecatalogservice.dto.CatalogItem;
import io.javabrains.moviecatalogservice.dto.Movie;
import io.javabrains.moviecatalogservice.dto.Rating;
import io.javabrains.moviecatalogservice.dto.UserRating;
import io.javabrains.moviecatalogservice.service.MovieInfo;
import io.javabrains.moviecatalogservice.service.RatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	MovieInfo movieInfo;

	@Autowired
	RatingInfo ratingInfo;

//	@Autowired
//	private DiscoveryClient discoveryClient;
// use discovery client for advanced loac balancing

//USING WEBCLIENT INSTEAD OF RESTTEMPLATE
//	@Autowired
//	private WebClient.Builder webClientBuilder;

	@RequestMapping("/{userId}")
	// @HystrixCommand(fallbackMethod = "getFallbackCatalog")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		// get all rated movie id
		UserRating ratings = ratingInfo.getUserRatings(userId);
		System.out.println(ratings+"RRRR");
		System.out.println(ratings.getUserRating()+"see");
		
		

		return ratings.getUserRating().stream()
				.map(rating -> movieInfo.getCatalogItem(rating))
				.collect(Collectors.toList());

	}
	/*
	 * USE WHEN WEB CLIENT IS USED Movie movie = webClientBuilder.build() .get()
	 * .uri("http://localhost:8081/movies/"+rating.getMovieId()) .retrieve()
	 * .bodyToMono(Movie.class) .block();
	 */

	public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId) {

		return Arrays.asList(new CatalogItem("No movie", "", 0));

	}

}
