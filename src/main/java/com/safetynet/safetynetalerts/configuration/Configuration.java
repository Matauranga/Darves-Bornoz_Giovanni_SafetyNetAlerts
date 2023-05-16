/*package com.safetynet.safetynetalerts.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.repository.DataStorage;
import com.safetynet.safetynetalerts.repository.DataStorageImpl;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public DataStorage getDataStorage() throws IOException  {
        var dataStorage = new DataStorageImpl();
        dataStorage.initDatas();
        return dataStorage;

    }

}
*/