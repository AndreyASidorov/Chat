package ru.Sidorov.ChatSimbirsoft.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.Sidorov.ChatSimbirsoft.service.MySecretKeys;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope("singleton")
public class MySecretKeyImpl implements MySecretKeys {
    private Map<String, String> keyMap = new HashMap<>();

    @Override
    public Map<String, String> getMySecretKey() {
        return keyMap;
    }
}
