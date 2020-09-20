package ru.maksimov.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.maksimov.taskmanager.model.Task;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {

    @Bean(name = "store")
    public Map<Long, Task> getStore() {
      return new HashMap();
    }
}
