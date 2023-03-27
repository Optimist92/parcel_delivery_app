package com.test.mscouriers.service.impl;

import com.test.mscouriers.service.ICourierService;
import com.test.mscouriers.service.IFacadeService;
import com.test.mscouriers.service.IShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacadeServiceImpl implements IFacadeService {

    private final ICourierService courierService;

    private final IShiftService shiftService;
}
