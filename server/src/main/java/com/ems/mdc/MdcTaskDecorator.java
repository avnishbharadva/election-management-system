
package com.ems.mdc;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

@Slf4j
public class MdcTaskDecorator implements TaskDecorator {

        @Override
        public Runnable decorate(Runnable runnable) {
            Map<String, String> contextMap = MDC.getCopyOfContextMap();
            log.info("Context Map : {}",contextMap);
            return () -> {
                try {
                    if (contextMap != null) {
                        MDC.setContextMap(contextMap);
                        log.info("Context Map : {}",contextMap);
                    }
                    runnable.run();
                } finally {
                    MDC.clear();
                }
            };
        }

}
