package com.apiratelimiter.controller;

import com.apiratelimiter.service.TokenBucketService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiRateLimitTestController {

    @Autowired
    TokenBucketService tokenBucketService;

    @Autowired
    private HttpServletRequest request;


    @GetMapping("/limited")
    public String getLimited()  {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        tokenBucketService.createTokenBucket(ipAddress);

        return ipAddress +" This is limited usage only. Be Careful!";
    }

    @GetMapping("/unlimited")
    public String getUnLimited() {
        return "This is unlimited usage. Forever!";
    }
}
