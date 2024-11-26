<?php
function removeSuffixAndTrim($string) {
    $suffixes = array("HD", "HDR", "高清"); // 可能的后缀
    foreach ($suffixes as $suffix) {
        if (substr($string, -strlen($suffix)) === $suffix) {
            // 如果字符串以后缀结尾，去除后缀
            $string = substr($string, 0, -strlen($suffix));
            break; // 只移除一个后缀
        }
    }
    return trim($string);
}

if (!isset($_GET['ch']) || !isset($_GET['date'])) {
    http_response_code(400);
    echo json_encode(array("error" => "Missing channel or date parameter"));
    exit;
}

$channel1 = htmlspecialchars($_GET['ch']);
$channel2 = str_replace(' ','',$channel1);
$channel3 = mb_strtoupper($channel2, 'UTF-8');
$channel = removeSuffixAndTrim($channel3);


$date = htmlspecialchars($_GET['date']);
$jsonFileName = "/var/www/epg/epg_" . str_replace('-','',$channel) . "_" . str_replace('-','',$date) . ".json";

if (file_exists($jsonFileName)) {
    header('Content-Type: application/json');
    readfile($jsonFileName);
} else {
    http_response_code(404);
    echo json_encode(array("error" => "EPG data not found"));
}
?>
