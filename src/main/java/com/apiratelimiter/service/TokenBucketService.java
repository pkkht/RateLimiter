package com.apiratelimiter.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class TokenBucketService {

    static List<TokenBucket> tokenBucketList = new ArrayList<>();

    @Scheduled(fixedRate = 1000)
    public void addToken(){
        tokenBucketList.stream().mapToInt(e -> e.maxTokens < 10 && e.maxTokens >0? ++ e.maxTokens  :10).boxed().collect(Collectors.toList());
    }

    public int trackService(String ipAddress){
        IntStream tokenStream = tokenBucketList.stream().filter(e -> e.ipAddress.equals(ipAddress)).mapToInt(e -> e.maxTokens>0? e.maxTokens -- :0);
        return tokenStream.findFirst().getAsInt();

    }

    public void createTokenBucket(String ipAddress){
        Optional<TokenBucket> optionalTokenBucket = tokenBucketList.stream().filter(e -> e.ipAddress.equals(ipAddress)).findFirst();
        if (optionalTokenBucket.isEmpty()) {
            TokenBucket tokenBucket = new TokenBucket(ipAddress);
            tokenBucketList.add(tokenBucket);
        }
    }

    class TokenBucket{
        String ipAddress;
        int maxTokens;

        TokenBucket(String ipAddress){
            this.ipAddress = ipAddress;
            this.maxTokens = 10;
        }

        @Override
        public String toString(){
            return this.ipAddress + " " + this.maxTokens;
        }
    }
}


