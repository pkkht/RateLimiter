package com.apiratelimiter.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class TokenBucketService {

    static List<TokenBucket> tokenBucketList = new ArrayList<>();

    @Scheduled(fixedRate = 1000)
    public void execute(){
        System.out.println("Code is ");
    }

    public void trackService(String ipAddress){
        tokenBucketList.forEach(tb -> tb.maxTokens >0?tb.maxTokens--:0);
    }

    public void createTokenBucket(String ipAddress){
        TokenBucket tokenBucket = new TokenBucket(ipAddress);
        tokenBucketList.add(tokenBucket);
    }

    class TokenBucket{
        String ipAddress;
        int maxTokens;

        TokenBucket(String ipAddress){
            this.ipAddress = ipAddress;
            this.maxTokens = 10;
        }
    }
}


