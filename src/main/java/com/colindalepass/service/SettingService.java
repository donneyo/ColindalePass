package com.colindalepass.service;

import com.colindalepass.entity.Setting;
import com.colindalepass.entity.SettingCategory;
import com.colindalepass.repository.SettingRepository;
import com.colindalepass.setting.EmailSettingBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {
    @Autowired
    private SettingRepository settingRepo;

    public EmailSettingBag getEmailSettings() {
        List<Setting> settings = settingRepo.findByCategory(SettingCategory.MAIL_SERVER);
        settings.addAll(settingRepo.findByCategory(SettingCategory.MAIL_TEMPLATES));

        return new EmailSettingBag(settings);
    }
    public List<Setting> listAllSettings() {
        return (List<Setting>) settingRepo.findAll();
    }


    public List<Setting> getMailServerSettings() {
        return settingRepo.findByCategory(SettingCategory.MAIL_SERVER);
    }


    public List<Setting> getMailTemplateSettings() {
        return settingRepo.findByCategory(SettingCategory.MAIL_TEMPLATES);
    }


    public void saveAll(Iterable<Setting> settings) {
        settingRepo.saveAll(settings);

    }

}
