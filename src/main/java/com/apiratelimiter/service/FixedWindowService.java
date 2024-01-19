package com.apiratelimiter.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class FixedWindowService {

    final static int THRESHOLD = 60;

    final static int WINDOW_SIZE = 60;

    static int requestCounter;


    public int trackService(){


    return 0;
    }

    Date roundToDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
