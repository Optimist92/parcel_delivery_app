package com.test.msorders.util;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.time.ZonedDateTime;

@Service
public class PerformanceService {

    private final OperatingSystemMXBean operatingSystemMXBean;

    PerformanceService() {
        this.operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    }

    public Performance getPerformance() {
        Performance performance = new Performance();

        performance.setTime(ZonedDateTime.now().toInstant().toEpochMilli());

        performance.setCommittedVirtualMemorySize(operatingSystemMXBean.getCommittedVirtualMemorySize());

        performance.setTotalSwapSpaceSize(operatingSystemMXBean.getTotalSwapSpaceSize());
        performance.setFreeSwapSpaceSize(operatingSystemMXBean.getFreeSwapSpaceSize());

        performance.setTotalPhysicalMemorySize(operatingSystemMXBean.getTotalMemorySize());
        performance.setFreePhysicalMemorySize(operatingSystemMXBean.getFreeMemorySize());

        performance.setSystemCpuLoad(operatingSystemMXBean.getCpuLoad());
        performance.setProcessCpuLoad(operatingSystemMXBean.getProcessCpuLoad());

        return performance;
    }
}