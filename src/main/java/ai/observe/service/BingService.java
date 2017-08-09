package ai.observe.service;

import ai.observe.model.ApplicationResponse;
import ai.observe.model.BingResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BingService {

  private static final Logger LOG = LoggerFactory.getLogger(BingService.class);
  private static final String TOKEN_END_POINT = "https://api.cognitive.microsoft.com/sts/v1.0/issueToken";
  private static final String SPEECH_TO_TEXT_END_POINT = "https://speech.platform.bing.com/speech/recognition/interactive/cognitiveservices/v1?language=en-US";
  private static final int MAX_BUFFER_SIZE = 1024;


  @Value("${bing.subscription.key}")
  private String key;

  /**
   * Method call bing api to convert audio file input stream to text
   * @param multipartFile
   * @return bingResponse
   */
  public ApplicationResponse speechToText(MultipartFile multipartFile) {
    ApplicationResponse applicationResponse = null;
    String token = getAccessToken();
    if(StringUtils.isEmpty(token)) {
      return new ApplicationResponse(HttpStatus.FORBIDDEN, "Invalid Credentials");
    }
    try {
      File file = File.createTempFile("sample", ".wav");
      multipartFile.transferTo(file);
      FileInputStream fileInputStream = new FileInputStream(file);
      URL url = new URL(SPEECH_TO_TEXT_END_POINT);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("POST");
      conn.setChunkedStreamingMode(0);
      conn.setRequestProperty("Accept","application/json;text/xml");
      conn.setRequestProperty("Content-Type", "audio/wav; codec=\"audio/pcm\"; samplerate=16000;");
      conn.setRequestProperty("Authorization", "Bearer "+ getAccessToken());
      conn.setReadTimeout(600000); // 10 Min Timeout
      conn.setDoOutput(true);

      conn.connect();
      bufferedWriteData(fileInputStream, conn.getOutputStream());
      if(HttpStatus.OK.value() == conn.getResponseCode()) {
        String response = getInputStreamData(conn.getInputStream());
        ObjectMapper mapper = new ObjectMapper();
        BingResponse bingResponse = mapper.readValue(response, BingResponse.class);
        applicationResponse = new ApplicationResponse(HttpStatus.OK, bingResponse.getDisplayText());
      } else {
        applicationResponse = new ApplicationResponse(conn.getResponseCode(), conn.getResponseMessage());
      }
    }  catch (Exception e) {
      LOG.error("speechToText - error occurred {}, {}", e, e.getStackTrace());
      applicationResponse = new ApplicationResponse(HttpStatus.INTERNAL_SERVER_ERROR, Arrays.toString(e.getStackTrace()));
    }
    return applicationResponse;
  }

  /**
   * Method call bing auth api to fetch access token
   * @return token
   */
  private String getAccessToken() {
    String responseJson = null;
    try {
      URL url = new URL(TOKEN_END_POINT);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
      conn.setRequestProperty("Ocp-Apim-Subscription-Key", key);
      conn.setFixedLengthStreamingMode(0);
      conn.setDoOutput(true);
      conn.connect();
      LOG.info("getAccessToken - response code {}", conn.getResponseCode());
      if(HttpStatus.OK.value() == conn.getResponseCode()) {
        responseJson = getInputStreamData(conn.getInputStream());
      }
    } catch (Exception e) {
      LOG.error("getAccessToken - error occurred {}, {}", e, e.getStackTrace());
    }
    return responseJson;
  }

  /**
   *
   * @param inputStream
   * @return String representation of input stream data
   * @throws IOException
   */
  private String getInputStreamData(InputStream inputStream) throws IOException {
    StringBuilder outputBuilder = new StringBuilder();
    if (inputStream != null) {
      BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
      String data;
      while((data = in.readLine()) != null) {
        outputBuilder.append(data);
      }
      in.close();
    }
    return outputBuilder.toString();
  }

  /**
   * read and write buffered data
   * @param fileInputStream
   * @param outputStream
   */
  private void bufferedWriteData(FileInputStream fileInputStream, OutputStream outputStream) {
    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
    try {
      Integer bytesAvailable = fileInputStream.available();
      Integer bufferLength = Math.min(bytesAvailable, MAX_BUFFER_SIZE);
      byte[] buffer = new byte[bufferLength];

      Integer bytesRead = fileInputStream.read(buffer, 0, bufferLength);
      while (bytesRead > 0) {
        dataOutputStream.write(buffer, 0, bufferLength);
        bytesAvailable = fileInputStream.available();
        bufferLength = Math.min(bytesAvailable, MAX_BUFFER_SIZE);
        bytesRead = fileInputStream.read(buffer, 0, bufferLength);
      }
      fileInputStream.close();
      dataOutputStream.flush();
      dataOutputStream.close();
    } catch (Exception e) {
      LOG.error("bufferedWriteData - error occurred {}, {}", e, e.getStackTrace());
    }
  }
}
