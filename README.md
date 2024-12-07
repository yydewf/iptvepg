# iptvepg

## 功能
抓取北京IPTV的电视直播频道节目单,生成xmltv及json格式节目单。支持Tivimate、Kodi、DIYP、TVBox等IPTV播放应用。  

## 节目单抓取运行方式
java -jar iptvepg.jar  

注意：默认运行环境需要能够访问北京联通IPTV内网节目单服务器。建议根据需要及可用节目单服务器修改代码。  

## 配置文件(config.properties)说明
output.directory=C:\\tmp: 节目单文件保存目录，默认C:\tmp，请修改成实际节目单保存目录，比如 /var/www/epg  
keep.json=0: 是否保存支持json接口的节目单文件(by 频道 by 日期)。默认不保存，只生成xmltv文件  
epg.old=6: 抓取保存历史节目单天数，默认前6天  
epg.new=2: 抓取保存未来节目单天数，默认后2天  
epg.keep=10: 节目单总保存天数，默认保存10天。运行时自动清除过期节目单文件  

## 电子节目单服务
xmltv节目单文件(e.xml及e.xml.gz)可以直接链接到web服务器目录下访问即可。支持Tivimate、Kodi等应用。 e.g., https://xxx.xxx.xxx/e.xml.gz  
json接口节目单可使用epg.php提供，需要部署到支持php的web服务器。默认节目单目录为/var/www/epg。支持DIPY、Tivibox等应用。 e.g., https://xxx.xxx.xxx/epg.php?ch={name}&date={date}  

## 频道列表

**央视**: CCTV1, CCTV2, CCTV3, CCTV4, CCTV4欧洲, CCTV4美洲, CCTV5, CCTV5+, CCTV6, CCTV7, CCTV8, CCTV9, CCTV10, CCTV11, CCTV12, CCTV13, CCTV14, CCTV15, CCTV16, CCTV17, CCTV4K  
**北京**: 北京卫视, 北京新闻, 北京影视, 北京财经, 北京生活, 北京文艺, 北京纪实科教, 北京体育休闲, 北京国际, 北京卡酷少儿  
**卫视**: 安徽卫视, 东方卫视, 广东卫视, 黑龙江卫视, 湖南卫视, 江苏卫视, 辽宁卫视, 重庆卫视, 江西卫视, 河南卫视, 山东卫视, 深圳卫视, 天津卫视, 浙江卫视, 东南卫视, 河北卫视, 湖北卫视, 贵州卫视, 吉林卫视, 甘肃卫视, 广西卫视, 海南卫视, 内蒙古卫视, 宁夏卫视, 新疆卫视, 青海卫视, 西藏卫视, 厦门卫视, 四川卫视, 山东教育, 山西卫视, 陕西卫视, 云南卫视, 兵团卫视, 三沙卫视, CETV1, CETV2, CETV3, CETV4  
**影视**: 淘BABY, 淘电影, 淘剧场, 淘娱乐, 萌宠TV, 爱上4K, 4K超清, 都市剧场, 动漫秀场, 动作影院, 家庭影院, 喜剧影院, 经典电影, 红色影院, 热播剧场, 武侠剧场, 城市剧场, 军旅剧场, 古装剧场, 经典剧场, 魅力时尚, 光影, 星影, 精选, 金鹰卡通, 嘉佳卡通, 少儿动画, 动画  
**其它**: 纪实人文, 金鹰纪实, 茶频道, 快乐垂钓, 游戏风云, 魅力足球, 乐游, 法治天地, 生活时尚, 金色学堂, 中国交通, 睛彩竞技, 睛彩篮球, 睛彩青少, 睛彩广场舞, 财富天下, 爱生活, 武术, 地理, 国学, 好学生, 鉴赏, 解密, 美人, 美妆, 墨宝, 戏曲, 音乐现场, 早教, 足球  
