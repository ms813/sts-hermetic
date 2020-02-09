package com.ms813.sts.hermetic.alchemy.quicken;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class QuickenPureSanguisActionTest {

    private static final Logger logger = LoggerFactory.getLogger(QuickenPureSanguisActionTest.class);

    @Test
    public void log() {
        for (int x = 1; x < 5; x++) {
            for (int n = 1; n < 17; n++) {
                double log = QuickenPureSanguisAction.log(n - 2, 2);
                int fn;
                if (Double.isNaN(log) || Double.isInfinite(log)) {
                    fn = 0;
                } else {
                    fn = (int) Math.ceil(log + 2 + x);
                }

                logger.info("VULN:      n={} x={} fn={}", n, x, Math.ceil(QuickenPureSanguisAction.log(n, 2) + x));
                logger.info("STRANGLE:  n={} x={} fn={}", n, x, fn);
            }
        }
    }
}