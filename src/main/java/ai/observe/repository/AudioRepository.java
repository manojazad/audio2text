package ai.observe.repository;

import ai.observe.model.Audio;

import org.springframework.data.repository.Repository;

public interface AudioRepository extends Repository<Audio, String> {

  /**
   * Method save audio detail in db
   * @param audio
   * @return
   */
  Audio save(Audio audio);

  /**
   * Method to fetch audio detail by id
   * @param id
   * @return
   */
  Audio findOne(String id);
}
