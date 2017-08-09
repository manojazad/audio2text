package ai.observe.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BingResponse {
  @JsonProperty("RecognitionStatus")
  private String recognitionStatus;
  @JsonProperty("DisplayText")
  private String displayText;
  @JsonProperty("Offset")
  private Long offset;
  @JsonProperty("Duration")
  private Long duration;

  /**
   * @return recognitionStatus
   */
  public String getRecognitionStatus() {
    return recognitionStatus;
  }

  /**
   * @param recognitionStatus
   *     the recognitionStatus to set
   */
  public void setRecognitionStatus(String recognitionStatus) {
    this.recognitionStatus = recognitionStatus;
  }

  /**
   * @return displayText
   */
  public String getDisplayText() {
    return displayText;
  }

  /**
   * @param displayText
   *     the displayText to set
   */
  public void setDisplayText(String displayText) {
    this.displayText = displayText;
  }

  /**
   * @return offset
   */
  public Long getOffset() {
    return offset;
  }

  /**
   * @param offset
   *     the offset to set
   */
  public void setOffset(Long offset) {
    this.offset = offset;
  }

  /**
   * @return duration
   */
  public Long getDuration() {
    return duration;
  }

  /**
   * @param duration
   *     the duration to set
   */
  public void setDuration(Long duration) {
    this.duration = duration;
  }
}
