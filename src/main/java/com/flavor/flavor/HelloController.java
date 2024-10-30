package com.flavor.flavor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping(value = "/hello", produces = "text/html")
    public String sayHello() {
        return """
            <html>
                <head>
                    <title>Flavor</title>
                </head>
                <body style="display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0;">
                    <h1 style="color: #191970; font-size: 200px;">Flavor</h1>
                </body>
            </html>
            """;
    }
}
