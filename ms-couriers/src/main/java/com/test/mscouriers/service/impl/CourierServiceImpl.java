package com.test.mscouriers.service.impl;

import com.test.mscouriers.service.ICourierService;
import org.springframework.stereotype.Service;

@Service
public class CourierServiceImpl implements ICourierService {
    @Override
    public String getCurrentLocation(Long workNumber) {
        return null;
    }

    @Override
    public String setCurrentLocation(String token, String location) {
        return null;
    }
}
