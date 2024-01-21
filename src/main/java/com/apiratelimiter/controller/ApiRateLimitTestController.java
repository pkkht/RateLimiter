package com.apiratelimiter.controller;

import com.apiratelimiter.service.TokenBucketService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiRateLimitTestController {

    private final TokenBucketService tokenBucketService;

    private final HttpServletRequest request;

    private ApiRateLimitTestController(TokenBucketService tokenBucketService, HttpServletRequest httpServletRequest){
        this.tokenBucketService = tokenBucketService;
        this.request = httpServletRequest;
    }


    @GetMapping("/limited-tokenbucket")
    public ResponseEntity<Object> getLimited()  {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        tokenBucketService.createTokenBucket(ipAddress);
        int token  = tokenBucketService.trackService(ipAddress);
        if (token == 0) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }

        return new ResponseEntity<>(ipAddress +" This is limited usage only. Be Careful!",HttpStatus.OK);
    }

    @GetMapping("/limited-fixedWindow")
    public ResponseEntity<Object> getLimitedFixedWindow()  {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        tokenBucketService.createTokenBucket(ipAddress);
        int token  = tokenBucketService.trackService(ipAddress);
        if (token == 0) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }

        return new ResponseEntity<>(ipAddress +" This is limited usage only. Be Careful!",HttpStatus.OK);
    }

    @GetMapping("/unlimited")
    public String getUnLimited() {
        return "This is unlimited usage. Forever!";
    }
}
