package org.apache.dubbo.rpc.cluster.loadbalance.statistics;

import org.apache.dubbo.common.config.ConfigurationUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


class CpuUsageServiceImplTest {

    @Test
    public void testCallBack() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        List<Float> results = new ArrayList<>();
        long timeToLive = Long.parseLong(ConfigurationUtils.getProperty("time.to.live"));
        long collectCpuUsageInMill = Long.parseLong(ConfigurationUtils.getProperty("mill.to.collect.cpu.usage"));
        CpuUsageServiceImpl service = new CpuUsageServiceImpl(timeToLive, collectCpuUsageInMill);
        service.addListener("foo.bar", (ip, cpu) -> {
            results.add(cpu);
            latch.countDown();
        });

        latch.await(4, TimeUnit.SECONDS);
        Assertions.assertFalse(results.isEmpty());
    }
}