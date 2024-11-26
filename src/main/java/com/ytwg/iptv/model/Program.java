package com.ytwg.iptv.model;

public class Program {
    private String title;
    private String startTime;
    private String endTime;

    public Program(String title, String startTime, String endTime) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
