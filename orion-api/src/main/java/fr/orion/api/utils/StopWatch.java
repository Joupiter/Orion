package fr.orion.api.utils;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
public class StopWatch {

    static Logger logger = LoggerFactory.getLogger(StopWatch.class);

    private long startTime, endTime;

    public StopWatch(boolean defaultStart) {
        Utils.ifTrue(defaultStart, this::start);
    }

    public StopWatch() {
        this(false);
    }

    public void start() {
       setStartTime(System.currentTimeMillis());
    }

    public void stopAndLog() {
        stop();
        log();
    }

    public void stop() {
        setEndTime(System.currentTimeMillis());
    }

    public long getTimeElapsed() {
        return getEndTime() - getStartTime();
    }

    public void log(String prefix) {
        logger.info("{} end in {}", prefix, getTimeElapsedAsString());
    }

    public void log() {
        logger.info("end in {}", getTimeElapsedAsString());
    }

    public String getTimeElapsedAsString() {
        return getTimeElapsed() + "ms";
    }

}
