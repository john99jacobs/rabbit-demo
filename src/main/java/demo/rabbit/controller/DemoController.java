package demo.rabbit.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DemoController {

    @Value("${tomcat.server.name}")
    private String serverName;
    
    @Autowired
    private EhCacheCacheManager cacheManager;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        model.addAttribute("batchSize", 1000);
        return "tools/load";
    }
    
    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public String load(Locale locale, Model model) {
        return "redirect:/";
    }    
    
    @RequestMapping(value="/load", method=RequestMethod.POST)
    public String loadPost(Model model, @RequestParam(required = false, value = "batchSize") String batchSize) {
        String message = "Success";
        int elements = 0;
        try {
            elements = Integer.valueOf(batchSize);
        } catch (NumberFormatException e) {
            message = "Invalid batch size";
        }
        for (int i = 0; i < elements; i++) {
            Cache cache = cacheManager.getCache("demo");
            cache.put(i, "Johnny Football");
        }
        model.addAttribute("message", message);
        model.addAttribute("batchSize", batchSize);
        return "tools/load";
    }
    
    @RequestMapping(value="/update", method=RequestMethod.GET)
    public String update(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("batchSize", 1000);
        return "tools/update";
    }
    
    @RequestMapping(value= "/update", method = RequestMethod.POST) 
    public String updatePost(Model model, @RequestParam(required = false, value = "batchSize") String batchSize) {
        String message = "Success";
        int elements = 0;
        try {
            elements = Integer.valueOf(batchSize);
        } catch (NumberFormatException e) {
            message = "Invalid batch size";
        }
        for (int i = 0; i < elements; i++) {
            Cache cache = cacheManager.getCache("demo");
            cache.put(i, "Tony Romo");
        }
        model.addAttribute("message", message);
        model.addAttribute("batchSize", batchSize);
        return "tools/update";
    }

    @RequestMapping(value="/evict", method=RequestMethod.GET)
    public String evict(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("batchSize", 1000);
        return "tools/evict";
    }
    
    @RequestMapping(value= "/evict", method = RequestMethod.POST) 
    public String evictPost(Model model, @RequestParam(required = false, value = "batchSize") String batchSize) {
        String message = "Success";
        int elements = 0;
        try {
            elements = Integer.valueOf(batchSize);
        } catch (NumberFormatException e) {
            message = "Invalid batch size";
        }
        for (int i = 0; i < elements; i++) {
            Cache cache = cacheManager.getCache("demo");
            cache.evict(i);
        }
        model.addAttribute("message", message);
        model.addAttribute("batchSize", batchSize);
        return "tools/evict";
    }
    
    @RequestMapping(value= "/evictAll", method = RequestMethod.GET) 
    public String evictAll(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "tools/evict";
    }

    @RequestMapping(value= "/evictAll", method = RequestMethod.POST) 
    public String evictAllPost(Model model) {
        Cache cache = cacheManager.getCache("demo");
        cache.clear();
        model.addAttribute("message", "Success");
        return "tools/evict";
    }

    @ModelAttribute("currentServerName")
    public String getCurrentServerName() {
        return serverName;
    }
}
