package ai.observe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingController {
  private static final Logger LOG = LoggerFactory.getLogger(PingController.class);

  @RequestMapping(method = RequestMethod.GET)
  public String findAll() {
    return null;
  }

}
