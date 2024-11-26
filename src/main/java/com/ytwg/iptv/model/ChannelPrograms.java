package com.ytwg.iptv.model;

import java.util.List;

public class ChannelPrograms {
    private Channel channel;
    private String date;
    private List<Program> programs;

    public ChannelPrograms(Channel channel, String date, List<Program> programs) {
        this.channel = channel;
        this.date = date;
        this.programs = programs;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getDate() {
        return date;
    }

    public List<Program> getPrograms() {
        return programs;
    }
}
