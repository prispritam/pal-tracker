package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    private String port;
    private String memoryLimit;
    private String cfInstanceIndex;
    private String cfInstanceAddress;
    @GetMapping("/env")
    public Map getEnv(){

        Map<String, String> envMap = new HashMap();
        envMap.put("PORT",port);
        envMap.put("MEMORY_LIMIT",memoryLimit);
        envMap.put("CF_INSTANCE_INDEX", cfInstanceIndex);
        envMap.put("CF_INSTANCE_ADDR", cfInstanceAddress);

        return envMap;

    }

    public EnvController(@Value("${port:NOT SET}") String port, @Value("${memory.limit:NOT SET}") String memoryLimit, @Value("${cf.instance.index:NOT SET}") String cfInstanceIndex, @Value("${cf.instance.addr:NOT SET}") String cfInstanceAddress ){

        this.port = port;
        this.memoryLimit = memoryLimit;
        this.cfInstanceIndex = cfInstanceIndex;
        this.cfInstanceAddress = cfInstanceAddress;


    }
}
