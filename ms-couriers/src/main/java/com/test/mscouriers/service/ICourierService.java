package com.test.mscouriers.service;

import com.test.mscouriers.domain.Courier;

import java.util.List;

public interface ICourierService {
    String getCurrentLocation(Long workNumber);

    String setCurrentLocation(String token, String location);

    List<Courier> findAll();

    Courier findByPublicId(Long publicId);

    Courier createCourier(Courier courier);
}
