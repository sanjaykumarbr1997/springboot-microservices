package io.javabrains.moviecatalogservice.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.javabrains.moviecatalogservice.dto.CatalogItem;
import io.javabrains.moviecatalogservice.dto.Movie;
import io.javabrains.moviecatalogservice.dto.Rating;
import io.javabrains.moviecatalogservice.dto.UserRating;


@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
//	@Autowired
//	private DiscoveryClient discoveryClient;
// use discovery client for advanced loac balancing
	
//USING WEBCLIENT INSTEAD OF RESTTEMPLATE
//	@Autowired
//	private WebClient.Builder webClientBuilder;
	
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		// get all rated movie id
		UserRating ratings = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/"+userId, UserRating.class);
		
		 
		return ratings.getUserRating().stream().map(rating ->{
			
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
			
			/*
			 * USE WHEN WEB CLIENT IS USED
			 * Movie movie = webClientBuilder.build() .get()
			 * .uri("http://localhost:8081/movies/"+rating.getMovieId()) .retrieve()
			 * .bodyToMono(Movie.class) .block();
			 */
			
			return new CatalogItem(movie.getName(), "Desc",rating.getRating());
		
		})
		.collect(Collectors.toList());
				
		}
		//for each movie id , call movie info service and get details
		
		// put them all together
		
		
}
