package ai.observe.service;

import ai.observe.model.Audio;
import ai.observe.repository.AudioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AudioService {

  @Autowired
  private AudioRepository audioRepository;

  public Audio saveAudio(Audio audio) {
    return audioRepository.save(audio);
  }

  public Audio getAudioById(String id) {
    return audioRepository.findOne(id);
  }
}
