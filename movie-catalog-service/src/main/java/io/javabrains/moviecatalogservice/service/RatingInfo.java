package io.javabrains.moviecatalogservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.javabrains.moviecatalogservice.dto.Rating;
import io.javabrains.moviecatalogservice.dto.UserRating;


@Service
public class RatingInfo {

	
	@Autowired
	private RestTemplate restTemplate;
	
//	@HystrixCommand(fallbackMethod = "getFallbackCatalogRating",
//			commandProperties = {
//				@HystrixProperty(name ="execution.isolation.thread.timeoutInMilliseconds",value = "2000"),
//				@HystrixProperty(name ="circuitBreaker.requestVolumeThreshold",value = "5"),
//				@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value="50"),
//				@HystrixProperty(name="circuitBreaker,sleepWindowInMilliseconds",value="5000")
//			}
//			)
	
	
	//Bulk head design pattern
//	@HystrixCommand(
//	fallbackMethod = "getFallbackCatalogRating",
//	threadPoolKey= "movieInfoPool",
//	threadPoolProperties= {
//			@HystrixProperty(name="coreSize", value="20"),
//			@HystrixProperty(name="maxQueueSize",value="10")
//			}
//
//	)
	
	@HystrixCommand(fallbackMethod = "getFallbackCatalogRating")
	public UserRating getUserRatings(@PathVariable("userId") String userId) {
		return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);
	}

	public UserRating getFallbackCatalogRating(@PathVariable("userId") String userId) {

		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setUserRating(Arrays.asList(new Rating("0", 0)));
		return userRating;
	}

}
