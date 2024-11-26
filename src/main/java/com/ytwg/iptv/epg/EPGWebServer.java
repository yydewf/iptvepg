package com.ytwg.iptv.epg;

import spark.Spark;

import java.nio.file.*;

public class EPGWebServer {

    public static void main(String[] args) {
        Spark.port(4567);
        Spark.get("/epg/:channelNumber/:date", (req, res) -> {
            String channelNumber = req.params(":channelNumber");
            String date = req.params(":date");
            String jsonFileName = "epg_" + channelNumber + "_" + date + ".json";
            Path jsonFilePath = Paths.get(jsonFileName);
            if (Files.exists(jsonFilePath)) {
                res.type("application/json");
                return new String(Files.readAllBytes(jsonFilePath));
            } else {
                res.status(404);
                return "EPG data not found";
            }
        });
    }
}

