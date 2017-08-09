package ai.observe.controller;


import ai.observe.model.ApplicationResponse;
import ai.observe.model.Audio;
import ai.observe.service.AudioService;
import ai.observe.service.BingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/audio")
@Component
public class AudioController {

  @Autowired
  private BingService bingService;

  @Autowired
  private AudioService audioService;

  /**
   * @param id
   * @param uploadfile
   * @return
   */
  @RequestMapping(value = "{id}", method = RequestMethod.POST)
  public ResponseEntity<Object> create(@PathVariable("id") String id,  @RequestParam("file") MultipartFile uploadfile) {
    ApplicationResponse applicationResponse;
    if(uploadfile.isEmpty()) {
      new ApplicationResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Please Select a valid wav file");
      applicationResponse = new ApplicationResponse(HttpStatus.BAD_REQUEST, "Please Select a valid wav file");
    }
    if(!"audio/wav".equalsIgnoreCase(uploadfile.getContentType())) {
      applicationResponse =  new ApplicationResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Please Select a valid wav file");
    }
    applicationResponse = bingService.speechToText(uploadfile);
    if(applicationResponse != null && applicationResponse.getCode().equals(HttpStatus.OK.value())) {
      Audio audio = new Audio();
      audio.setId(id);
      audio.setTextData(applicationResponse.getMessage());
      audioService.saveAudio(audio);
      return new ResponseEntity<Object>(audio, HttpStatus.OK);
    }
    return new ResponseEntity<Object>(applicationResponse, applicationResponse.getHttpStatus());
  }

  /**
   * This return the text data of a previously converted audio file
   * @param id
   * @return
   */
  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  public ResponseEntity<Object> findById(@PathVariable("id") String id) {
    Audio audio = audioService.getAudioById(id);
    return new ResponseEntity<Object>(audio, audio != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
  }

}
