package com.test.mscouriers.service;

import com.test.mscouriers.domain.Shift;

public interface IShiftService {

    Shift startShift(String token);

    Shift endShift(String token);
}
