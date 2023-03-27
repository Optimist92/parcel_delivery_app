package com.test.mscouriers.service;

import java.util.UUID;

public interface ICourierService {
    String getCurrentLocation(Long workNumber);

    String setCurrentLocation(String token, String location);
}
