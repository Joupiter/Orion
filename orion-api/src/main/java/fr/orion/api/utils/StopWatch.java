package fr.orion.api.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class StopWatch {

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
        log.info("{} end in {}", prefix, getTimeElapsedAsString());
    }

    public void log() {
        log.info("end in {}", getTimeElapsedAsString());
    }

    public String getTimeElapsedAsString() {
        return getTimeElapsed() + "ms";
    }

}