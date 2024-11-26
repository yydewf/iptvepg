package com.ytwg.iptv.model;

public class Channel {
    private int channelNumber;
    private String channelId;
    private String channelName;

    public Channel(int channelNumber, String channelId, String channelName) {
        this.channelNumber = channelNumber;
        this.channelId = channelId;
        this.channelName = channelName;
    }

    public int getChannelNumber() {
        return channelNumber;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getChannelName() {
        return channelName;
    }
}
