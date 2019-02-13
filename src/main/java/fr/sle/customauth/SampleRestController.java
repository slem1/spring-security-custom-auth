package fr.sle.customauth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author slemoine
 */
@RequestMapping("/")
@RestController
public class SampleRestController {

    @GetMapping
    public String itWorks() {
        return "It works";
    }
}
