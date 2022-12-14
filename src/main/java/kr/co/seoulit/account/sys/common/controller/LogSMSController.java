package kr.co.seoulit.account.sys.common.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import org.json.JSONArray;
import org.json.JSONObject;
import tw.com.mitake.sms.MitakeSms;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class LogSMSController extends UnsynchronizedAppenderBase<ILoggingEvent> {
    private static final String DEFAULT_TITLE = "LogbackSMS";
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd:HHmmss");
    private static final String FORMAT_MESSAGE = "%s - %s,%s,%s,%s,%s:%s,%s.%s,%d";

    private String title = DEFAULT_TITLE;

    private String accessKey;
    private String secretKey;
    private String serviceId;
    private String to;
    private String from;
    private String content;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    // https://api.ncloud-docs.com/docs/common-ncpapi
    public static String makeSignature(String url, String timestamp, String method, String accessKey, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        String space = " ";                    // one space
        String newLine = "\n";                 // new line


        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey;
        String encodeBase64String;
        try {

            signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
            encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            encodeBase64String = e.toString();
        }


        return encodeBase64String;
    }
    /*
     * https://api.ncloud-docs.com/docs/ko/ai-application-service-sens-smsv2
        {
            "type":"(SMS | LMS | MMS)",
            "contentType":"(COMM | AD)",
            "countryCode":"string",
            "from":"string",
            "subject":"string",
            "content":"string",
            "messages":[
                {
                    "to":"string",
                    "subject":"string",
                    "content":"string"
                }
            ],
            "files":[
                {
                    "name":"string",
                    "body":"string"
                }
            ],
            "reserveTime": "yyyy-MM-dd HH:mm",
            "reserveTimeZone": "string",
            "scheduleCode": "string"
        }
     */
    public void sendSMS(String msg) {
        String hostNameUrl = "https://sens.apigw.ntruss.com";     		// ????????? URL
        String requestUrl= "/sms/v2/services/";                   		// ?????? URL
        String requestUrlType = "/messages";                      		// ?????? URL
        String accessKey = this.getAccessKey();                     	// ????????? ???????????? ????????? ???????????? ???????????? ?????? ?????????			// Access Key : https://www.ncloud.com/mypage/manage/info > ????????? ?????? > Access Key ID
        String secretKey = this.getSecretKey();  // 2??? ????????? ?????? ??????????????? ???????????? service secret key	// Service Key : https://www.ncloud.com/mypage/manage/info > ????????? ?????? > Access Key ID
        String serviceId = this.getServiceId();       // ??????????????? ????????? SMS ????????? ID							// service ID : https://console.ncloud.com/sens/project > Simple & ... > Project > ????????? ID
        String method = "POST";											// ?????? method
        String timestamp = Long.toString(System.currentTimeMillis()); 	// current timestamp (epoch)
        requestUrl += serviceId + requestUrlType;
        String apiUrl = hostNameUrl + requestUrl;

        // JSON ??? ????????? body data ??????
        JSONObject bodyJson = new JSONObject();
        JSONObject toJson = new JSONObject();
        JSONArray  toArr = new JSONArray();

        //toJson.put("subject","");							// Optional, messages.subject	?????? ????????? ??????, LMS, MMS????????? ?????? ??????
        //toJson.put("content","sms test in spring 111");	// Optional, messages.content	?????? ????????? ??????, SMS: ?????? 80byte, LMS, MMS: ?????? 2000byte
        toJson.put("to",this.getTo());						// Mandatory(??????), messages.to	????????????, -??? ????????? ????????? ?????? ??????
        toArr.put(toJson);

        bodyJson.put("type","SMS");							// Madantory, ????????? Type (SMS | LMS | MMS), (????????? ??????)
        //bodyJson.put("contentType","");					// Optional, ????????? ?????? Type (AD | COMM) * AD: ?????????, COMM: ????????? (default: COMM) * ????????? ????????? ?????? ??? ?????? ?????? ????????? ?????? ?????????????????? (??? 50???)??? ???????????????.
        //bodyJson.put("countryCode","82");					// Optional, ?????? ????????????, (default: 82)
        bodyJson.put("from",this.getFrom());					// Mandatory, ????????????, ?????? ????????? ??????????????? ?????? ??????
        //bodyJson.put("subject","");						// Optional, ?????? ????????? ??????, LMS, MMS????????? ?????? ??????
        bodyJson.put("content",msg);	// Mandatory(??????), ?????? ????????? ??????, SMS: ?????? 80byte, LMS, MMS: ?????? 2000byte
        bodyJson.put("messages", toArr);					// Mandatory(??????), ?????? ????????? ?????? (messages.XXX), ?????? 1,000???

        //String body = bodyJson.toJSONString();
        String body = bodyJson.toString();

        System.out.println(body);

        try {
            URL url = new URL(apiUrl);

            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("content-type", "application/json");
            con.setRequestProperty("x-ncp-apigw-timestamp", timestamp);
            con.setRequestProperty("x-ncp-iam-access-key", accessKey);
            con.setRequestProperty("x-ncp-apigw-signature-v2", makeSignature(requestUrl, timestamp, method, accessKey, secretKey));
            con.setRequestMethod(method);
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.write(body.getBytes());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            System.out.println("responseCode" +" " + responseCode);
            if(responseCode == 202) { // ?????? ??????
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else { // ?????? ??????
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            System.out.println(response.toString());

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private String transformStackTrace(ILoggingEvent event) {
        IThrowableProxy throwableProxy = event.getThrowableProxy();
        StackTraceElement stackTraceElement = throwableProxy.getStackTraceElementProxyArray()[0].getStackTraceElement();

        String time = DEFAULT_DATE_FORMAT.format(new Date(event.getTimeStamp()));
        String threadName = event.getThreadName();
        String level = event.getLevel().toString().substring(0, 1);
        String logger = event.getLoggerName();
        String exception = throwableProxy.getClassName();
        String msg = throwableProxy.getMessage();
        String className = stackTraceElement.getClassName();
        String method = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();

        return String.format(FORMAT_MESSAGE, title, time, threadName, level, logger, exception, msg, className, method, lineNumber);
    }
    private void createIssue(ILoggingEvent event) {
        String msg = transformStackTrace(event);
        sendSMS(msg);
    }

    @Override
    public void append(ILoggingEvent event) {
        createIssue(event);
    }

}

