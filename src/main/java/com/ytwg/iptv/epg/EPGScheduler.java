package com.ytwg.iptv.epg;

import com.ytwg.iptv.model.Channel;
import com.ytwg.iptv.model.Program;
import com.ytwg.iptv.model.ChannelPrograms;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.GZIPOutputStream;


import org.json.JSONArray;
import org.json.JSONObject;

public class EPGScheduler {
    private static final List<Channel> CHANNELS = Arrays.asList(
            new Channel(1, "00000001000000050000000000000152", "CCTV1"),
            new Channel(2, "00000001000000050000000000000477", "CCTV2"),
            new Channel(3, "01010001000000050000000000000370", "CCTV3"),
            new Channel(4, "00000001000000050000000000000573", "CCTV4"),
            new Channel(5, "01010001000000050000000000000371", "CCTV5"),
            new Channel(6, "01010001000000050000000000000372", "CCTV6"),
            new Channel(7, "00000001000000050000000000000485", "CCTV7"),
            new Channel(8, "01010001000000050000000000000375", "CCTV8"),
            new Channel(9, "00000001000000050000000000000478", "CCTV9"),
            new Channel(10, "00000001000000050000000000000480", "CCTV10"),
            new Channel(11, "00000001000000050000000000000583", "CCTV11"),
            new Channel(12, "00000001000000050000000000000482", "CCTV12"),
            new Channel(13, "00000001000000050000000000000602", "CCTV13"),
            new Channel(14, "00000001000000050000000000000483", "CCTV14"),
            new Channel(15, "00000001000000050000000000000587", "CCTV15"),
            new Channel(16, "01010001000000050000000000000389", "CCTV16"),
            new Channel(17, "00000001000000050000000000000580", "CCTV17"),
            new Channel(18, "00000001000000050000000000000153", "CCTV5+"),
            new Channel(21, "01010001000000050000000000000021", "北京卫视"),
            new Channel(22, "01010001000000050000000000000020", "北京文艺"),
            new Channel(23, "01010001000000050000000000000407", "北京纪实科教"),
            new Channel(24, "01010001000000050000000000000364", "北京影视"),
            new Channel(25, "01010001000000050000000000000408", "北京财经"),
            new Channel(26, "01010001000000050000000000000019", "北京体育休闲"),
            new Channel(27, "01010001000000050000000000000409", "北京生活"),
            new Channel(29, "01010001000000050000000000000365", "北京新闻"),
            new Channel(34, "01010001000000050000000000000328", "萌宠TV"),
            new Channel(35, "01010001000000050000000000000397", "卡酷少儿"),
            new Channel(36, "01010001000000050000000000000308", "北京淘BABY"),
            new Channel(37, "01010001000000050000000000000017", "北京淘剧场"),
            new Channel(38, "01010001000000050000000000000015", "北京淘电影"),
            new Channel(39, "01010001000000050000000000000326", "北京淘娱乐"),
            new Channel(40, "01010001000000050000000000000014", "4K超清"),
            new Channel(41, "00000001000000050000000000000195", "湖南卫视"),
            new Channel(44, "00000001000000050000000000000198", "江苏卫视"),
            new Channel(45, "00005000000000000000000000000263", "东方卫视"),
            new Channel(46, "00000001000000050000000000000265", "浙江卫视"),
            new Channel(47, "00000001000000050000000000000267", "湖北卫视"),
            new Channel(48, "00000001000000050000000000000266", "天津卫视"),
            new Channel(50, "00000001000000050000000000000470", "山东卫视"),
            new Channel(51, "00000001000000050000000000000472", "辽宁卫视"),
            new Channel(52, "00000001000000050000000000000473", "安徽卫视"),
            new Channel(53, "00000001000000050000000000000196", "黑龙江卫视"),
            new Channel(55, "00000001000000050000000000000577", "贵州卫视"),
            new Channel(56, "00000001000000050000000000000588", "东南卫视"),
            new Channel(57, "00000001000000050000000000000609", "重庆卫视"),
            new Channel(58, "00000001000000050000000000000608", "江西卫视"),
            new Channel(59, "00000001000000050000000000000631", "河南卫视"),
            new Channel(66, "01010001000000050000000000000460", "睛彩竞技"),
            new Channel(67, "01010001000000050000000000000461", "睛彩篮球"),
            new Channel(68, "01010001000000050000000000000462", "睛彩青少"),
            new Channel(69, "01010001000000050000000000000463", "睛彩广场舞"),
//            new Channel(70, "00600637000000050000000001367707","魅力足球"),
            new Channel(72, "00000001000000050000000000000445", "广东卫视"),
            new Channel(73, "00000001000000050000000000000575", "河北卫视"),
            new Channel(74, "00000001000000050000000000000197", "深圳卫视"),
//            new Channel(75, "00000001100000051406111514059258", "纪实人文"),
            new Channel(76, "00000001000000050000000000000568", "CETV1"),
            new Channel(77, "00000001000000050000000000000569", "金鹰纪实"),
            new Channel(78, "00000001000000050000000000000619", "吉林卫视"),
            new Channel(79, "01010001000000050000000000000457", "中国交通"),
            new Channel(81, "01010001000000050000000000004491", "北京卫视4K"),
            new Channel(85, "00000001000000050000000000000626", "CCTV4K"),
//            new Channel(90, "00600637000000050000000001555886", "动漫秀场"),
//            new Channel(91, "00600637000000050000000001555881", "都市剧场"),
//            new Channel(92, "00600637000000050000000004492967", "法治天地"),
//            new Channel(93, "00600637000000050000000001555894", "游戏风云"),
//            new Channel(94, "00600637000000050000000001555893", "生活时尚"),
//            new Channel(95, "00600637000000050000000001368437", "乐游"),
//            new Channel(96, "00600637000000050000000004492970", "金色学堂"),
            new Channel(131, "01010001000000050000000000000011", "北京国际"),
            new Channel(139, "00005000000000000000000000000248", "山东教育"),
            new Channel(142, "00000001000000050000000000000069", "四川卫视"),
            new Channel(152, "00000001000000050000000000000086", "广西卫视"),
            new Channel(154, "00000001000000050000000000000090", "陕西卫视"),
            new Channel(155, "00000001000000050000000000000099", "山西卫视"),
            new Channel(156, "00000001000000050000000000000098", "内蒙古卫视"),
//            new Channel(157, "00000001000000050000000000000096", "青海卫视"),
            new Channel(158, "00000001000000050000000000000088", "海南卫视"),
            new Channel(159, "00000001000000050000000000000082", "宁夏卫视"),
            new Channel(160, "00000001000000050000000000000100", "西藏卫视"),
            new Channel(161, "00000001000000050000000000000097", "新疆卫视"),
            new Channel(162, "00000001000000050000000000000603", "甘肃卫视"),
            new Channel(163, "00000001000000050000000000000589", "三沙卫视"),
            new Channel(164, "00000001000000050000000000000060", "云南卫视"),
            new Channel(165, "00000001000000050000000000000183", "财富天下"),
            new Channel(166, "00005000000000000000000000000244", "厦门卫视"),
            new Channel(167, "00000001000000050000000000000462", "兵团卫视"),
//            new Channel(168, "01010001000000050000000000000338", "南方卫视"),
            new Channel(169, "00000001000000050000000000000151", "金鹰卡通"),
            new Channel(170, "00000001000000050000000000000448", "嘉佳卡通"),
            new Channel(172, "00005000000000000000000000000251", "CETV2"),
//            new Channel(173, "00005000000000000000000000000250", "CETV3"),
            new Channel(174, "00005000000000000000000000000252", "CETV4"),
            new Channel(202, "00000001000000050000000000000108", "热播剧场"),
            new Channel(203, "00000001000000050000000000000109", "经典电影"),
            new Channel(204, "00000001000000050000000000000106", "魅力时尚"),
            new Channel(207, "00000001000000050000000000000103", "少儿动画"),
            new Channel(320, "01010001000000050000000000000345", "快乐垂钓"),
            new Channel(321, "01010001000000050000000000000346", "茶"),
            new Channel(341, "00000001000000050000000000000611", "CCTV4欧洲"),
            new Channel(342, "00000001000000050000000000000612", "CCTV4美洲"),
            new Channel(343, "00000001000000050000000000000613", "CGTN"),
            new Channel(344, "00000001000000050000000000000614", "CGTN纪录"),
            new Channel(351, "00000001000000050000000000000499", "城市剧场"),
            new Channel(352, "00000001000000050000000000000500", "军旅剧场"),
            new Channel(353, "00000001000000050000000000000501", "古装剧场"),
            new Channel(354, "00000001000000050000000000000502", "音乐现场"),
            new Channel(355, "00000001000000050000000000000498", "地理"),
            new Channel(356, "00000001000000050000000000000503", "美妆"),
            new Channel(357, "00000001000000050000000000000504", "美人"),
            new Channel(358, "00000001000000050000000000000505", "精选"),
            new Channel(359, "00000001000000050000000000000506", "解密"),
            new Channel(360, "00000001000000050000000000000507", "军事"),
            new Channel(361, "00000001000000050000000000000509", "国学"),
            new Channel(362, "00000001000000050000000000000510", "戏曲"),
            new Channel(363, "00000001000000050000000000000511", "早教"),
            new Channel(364, "00000001000000050000000000000512", "动画"),
            new Channel(365, "00000001000000050000000000000513", "好学生"),
            new Channel(366, "00000001000000050000000000000514", "鉴赏"),
            new Channel(367, "00000001000000050000000000000515", "墨宝"),
            new Channel(368, "00000001000000050000000000000516", "光影"),
            new Channel(369, "00000001000000050000000000000518", "经典剧场"),
            new Channel(370, "00000001000000050000000000000519", "爱生活"),
            new Channel(371, "00000001000000050000000000000520", "武术"),
            new Channel(372, "00000001000000050000000000000521", "红色影院"),
            new Channel(373, "00000001000000050000000000000522", "足球"),
            new Channel(374, "00000001000000050000000000000523", "武侠剧场"),
//            new Channel(375, "00000001000000050000000000000524", "喜剧影院"),
            new Channel(376, "00000001000000050000000000000525", "动作影院"),
            new Channel(377, "00000001000000050000000000000526", "家庭影院"),
            new Channel(378, "00000001000000050000000000000527", "星影"),
            new Channel(406, "00000001000000050000000000000635", "广东卫视4K"),
            new Channel(408, "00000001000000050000000000000637", "深圳卫视4K")
//            new Channel(401, "00000001000000050000000000000545", "爱上4K")
    );

    private static final String EPG_API_URL_TEMPLATE = "http://210.13.21.3/schedules/%s_%s.json";
    private static final String CCTV_EPG_API_URL_TEMPLATE = "https://api.cntv.cn/epg/epginfo?serviceId=shiyi&d=%s&c=%s";
    private static String OUTPUT_DIRECTORY;
    private static int KEEP_JSON;
    private static int OLD_EPG_DAYS;
    private static int NEW_EPG_DAYS;
    private static int KEEP_EPG_DAYS;

    public static void main(String[] args) {
        try {
            loadConfig();
        } catch (IOException e) {
            System.err.println("Failed to load configuration: " + e.getMessage());
            return;
        }

        try {
            generateEPG();
            deleteOldFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void loadConfig() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = EPGScheduler.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new FileNotFoundException("Configuration file 'config.properties' not found in the classpath");
            }
            properties.load(input);
            OUTPUT_DIRECTORY = properties.getProperty("output.directory");
            if (OUTPUT_DIRECTORY == null || OUTPUT_DIRECTORY.isEmpty()) {
                throw new IllegalArgumentException("Output directory is not specified in the configuration file");
            }
            KEEP_JSON = Integer.parseInt(properties.getProperty("keep.json"));

            OLD_EPG_DAYS = Integer.parseInt(properties.getProperty("epg.old"));
            if (OLD_EPG_DAYS < 0) OLD_EPG_DAYS = 0;
            NEW_EPG_DAYS = Integer.parseInt(properties.getProperty("epg.new"));
            if (NEW_EPG_DAYS < 0) NEW_EPG_DAYS = 0;
            KEEP_EPG_DAYS = Integer.parseInt(properties.getProperty("epg.keep"));
            if (KEEP_EPG_DAYS < OLD_EPG_DAYS) KEEP_EPG_DAYS = OLD_EPG_DAYS;
        }
    }

    private static void generateEPG() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        List<String> dates = new ArrayList<>();
        for (int i = -OLD_EPG_DAYS; i <= NEW_EPG_DAYS; i++) {
            calendar.add(Calendar.DATE, i);
            dates.add(sdf.format(calendar.getTime()));
            calendar.add(Calendar.DATE, -i);  // reset calendar to today
        }

        List<ChannelPrograms> allChannelPrograms = new ArrayList<>();

        for (Channel channel : CHANNELS) {
            for (String date : dates) {
                String epgData = null;
                List<Program> programs = null;
                try {
                    if (channel.getChannelNumber() == 18) {
                        epgData = fetchEPGDataCCTV("cctv5plus", date);
                        programs = parseEPGDataCCTV(epgData, "cctv5plus", date);
                    } else {
                        epgData = fetchEPGData(channel.getChannelId(), date);
                        programs = parseEPGData(epgData);
                    }
                    if (KEEP_JSON == 1) saveAsJSON(channel, date, programs);
                    allChannelPrograms.add(new ChannelPrograms(channel, date, programs));
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }

        saveAsXML(allChannelPrograms);
    }

    private static String fetchEPGData(String channelId, String date) throws Exception {
        String apiUrl = String.format(EPG_API_URL_TEMPLATE, channelId, date);
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();

        return content.toString();
    }

    private static String fetchEPGDataCCTV(String channelId, String date) throws Exception {
        String apiUrl = String.format(CCTV_EPG_API_URL_TEMPLATE, date, channelId);
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();

        return content.toString();
    }

    private static List<Program> parseEPGData(String epgData) {
        List<Program> programs = new ArrayList<>();
        JSONArray jsonArray = new JSONObject(epgData).getJSONArray("schedules");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonProgram = jsonArray.getJSONObject(i);
            String title = jsonProgram.getString("title");
            String startTime = jsonProgram.getString("starttime");
            String endTime = jsonProgram.getString("endtime");
            programs.add(new Program(title, startTime, endTime));
        }
        return programs;
    }

    private static List<Program> parseEPGDataCCTV(String epgData, String name, String date) {
        List<Program> programs = new ArrayList<>();
        JSONArray jsonArray = new JSONObject(epgData).getJSONObject(name).getJSONArray("program");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonProgram = jsonArray.getJSONObject(i);
            String title = jsonProgram.getString("t");
            String startTime = date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6,8) + " " + jsonProgram.getString("showTime") + ":00";
            String endTime;
            if (i == jsonArray.length() - 1) {
                String nextDate = nextDay(date);
                endTime = nextDate.substring(0,4) + "-" + nextDate.substring(4,6) + "-" + nextDate.substring(6,8) + " 00:00:00";
            } else {
                JSONObject jsonProgramN = jsonArray.getJSONObject(i+1);
                endTime = date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6,8) + " " + jsonProgramN.getString("showTime") + ":00";
            }
            programs.add(new Program(title, startTime, endTime));
        }
        return programs;
    }

    private static String nextDay(String dayStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = dateFormat.parse(dayStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            return dateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            return dayStr;
        }
    }

    private static void saveAsXML(List<ChannelPrograms> allChannelPrograms) throws Exception {
        String xmlFileName = OUTPUT_DIRECTORY + "/e.xml";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlFileName))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<tv>\n");
            for (Channel channel : CHANNELS) {
                writer.write(String.format("  <channel id=\"%s\">\n", channel.getChannelNumber()));
                writer.write(String.format("    <display-name lang=\"zh\">%s</display-name>\n", channel.getChannelName()));
                writer.write("  </channel>\n");
            }

            for (Channel channel : CHANNELS) {
                for (ChannelPrograms cp : allChannelPrograms) {
                    if (cp.getChannel().getChannelId().equals(channel.getChannelId())) {
                        for (Program program : cp.getPrograms()) {
                            writer.write(String.format("  <programme start=\"%s +0800\" stop=\"%s +0800\" channel=\"%s\">\n", program.getStartTime().replaceAll("[-: ]", ""), program.getEndTime().replaceAll("[-: ]", ""), channel.getChannelNumber()));
                            writer.write(String.format("    <title>%s</title>\n", program.getTitle().replaceAll("&","&amp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\"","&quot;").replaceAll("'","&apos;")));
                            writer.write("  </programme>\n");
                        }
                    }
                }
            }
            writer.write("</tv>\n");
        }

        compressGzipFile(xmlFileName, xmlFileName + ".gz");
    }

    private static void saveAsJSON(Channel channel, String date, List<Program> programs) throws Exception {
        String jsonFileName = OUTPUT_DIRECTORY + "/epg_" + channel.getChannelName() + "_" + date + ".json";
        JSONObject jsonObject = new JSONObject();

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        Date date2 = sdf2.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date2);
        String date1 = sdf1.format(calendar.getTime());

        jsonObject.put("date", date1);
        jsonObject.put("channel_name", channel.getChannelName());
        jsonObject.put("url", "http://165.140.240.30/epg.php");
        JSONArray jsonArray = new JSONArray();
        for (Program program : programs) {
            JSONObject jsonProgram = new JSONObject();
            jsonProgram.put("title", program.getTitle());
            jsonProgram.put("desc", "");
            jsonProgram.put("start", program.getStartTime().split(" ")[1].substring(0,5));
            jsonProgram.put("end", program.getEndTime().split(" ")[1].substring(0,5));
            jsonArray.put(jsonProgram);
        }
        jsonObject.put("epg_data", jsonArray);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFileName))) {
            writer.write(jsonObject.toString(4)); // 4 is the number of spaces for indentation
        }
    }

    private static void deleteOldFiles() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -KEEP_EPG_DAYS);
        String keepDaysAgo = sdf.format(calendar.getTime());

        Files.list(Paths.get(OUTPUT_DIRECTORY))
                .filter(path -> {
                    String fileName = path.getFileName().toString();
                    return fileName.startsWith("epg_") && fileName.endsWith(".json");
                })
                .forEach(path -> {
                    String datePart = path.getFileName().toString().split("_")[2].replace(".json", "");
                    if (datePart.compareTo(keepDaysAgo) < 0) {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private static void compressGzipFile(String sourceFile, String outputFile) {
        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(outputFile);
             GZIPOutputStream gzipOS = new GZIPOutputStream(fos)) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                gzipOS.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
