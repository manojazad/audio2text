package ai.observe.model;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ApplicationResponse {
  private String message;
  private Integer code;

  @JsonIgnore
  private HttpStatus httpStatus;

  public ApplicationResponse(HttpStatus status, String message) {
    this.code = status.value();
    this.message = message;
    this.httpStatus = status;
  }

  public ApplicationResponse(Integer code, String message) {
    this.code = code;
    this.message = message;
    this.httpStatus = HttpStatus.valueOf(code);
  }

  /**
   * @return message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message
   *     the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return code
   */
  public Integer getCode() {
    return code;
  }

  /**
   * @param code
   *     the code to set
   */
  public void setCode(Integer code) {
    this.code = code;
  }

  /**
   * @return httpStatus
   */
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  /**
   * @param httpStatus
   *     the httpStatus to set
   */
  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }
}
