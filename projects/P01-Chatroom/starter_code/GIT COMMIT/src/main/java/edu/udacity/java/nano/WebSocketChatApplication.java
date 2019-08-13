package edu.udacity.java.nano;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@RestController
public class WebSocketChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketChatApplication.class, args);
    }

    /**
     * Login Page
     */
    @GetMapping("/")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    /**
     * Chatroom Page
     */
    @GetMapping("/index")
    public ModelAndView index(String username, HttpServletRequest request) throws UnknownHostException {

            if (StringUtils.isEmpty(username)) {
                username = "Ritu";
            }
            ModelAndView mav = new ModelAndView("/chat");
            mav.addObject("username", username);
            mav.addObject("webSocketUrl", "ws://"+
                    InetAddress.getLocalHost().getHostAddress()+":"+request.getServerPort()+request.getContextPath()+"/chat");
            return mav;

    }
}
