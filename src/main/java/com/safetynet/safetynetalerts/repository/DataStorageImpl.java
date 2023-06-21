package com.safetynet.safetynetalerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.DataObject;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class DataStorageImpl implements DataStorage {

    @Autowired
    private ObjectMapper objectMapper;

    private DataObject dataObject;

    @Override
    @PostConstruct
    public void initDatas() throws IOException {
        File datas = new ClassPathResource("datas.json").getFile();
        log.info("Data fil address : " + datas.getAbsolutePath());
        this.dataObject = objectMapper.readValue(datas, DataObject.class);
    }

    @Override
    public List<Person> getPersons() {
        return dataObject.getPersons();
    }

    @Override
    public List<Firestation> getFirestations() {
        return dataObject.getFirestations();
    }

    @Override
    public List<MedicalRecord> getMedicalRecords() {
        return dataObject.getMedicalrecords();
    }
}
