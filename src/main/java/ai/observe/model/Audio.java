package ai.observe.model;

import org.springframework.data.annotation.Id;

public class Audio {

  @Id
  private String id;
  private String textData;

  /**
   * @return id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id
   *     the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return textData
   */
  public String getTextData() {
    return textData;
  }

  /**
   * @param textData
   *     the textData to set
   */
  public void setTextData(String textData) {
    this.textData = textData;
  }
}
