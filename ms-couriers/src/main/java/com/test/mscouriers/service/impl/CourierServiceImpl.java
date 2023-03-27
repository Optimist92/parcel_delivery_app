package com.test.mscouriers.service.impl;

import com.test.mscouriers.domain.Courier;
import com.test.mscouriers.repository.CourierRepository;
import com.test.mscouriers.service.ICourierService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements ICourierService {

    private final CourierRepository courierRepository;

    @Override
    public String getCurrentLocation(Long workNumber) {
        return null;
    }

    @Override
    public String setCurrentLocation(String token, String location) {
        return null;
    }

    @Override
    public List<Courier> findAll() {
        return courierRepository.findAll();
    }

    @Override
    public Courier findByPublicId(Long publicId) {
        return courierRepository.findByUserPublicId(publicId).orElseThrow(() -> new EntityNotFoundException("Courier not found: publicId = " + publicId));
    }

    @Override
    public Courier createCourier(Courier courier) {
        return courierRepository.save(courier);
    }

}