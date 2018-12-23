package com.synway.StandardComparision.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.synway.StandardComparision.pojo.LoggerMessage;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.Date;

/**
 * Xcloud-Api By IDEA
 * Created by LaoWang on 2018/8/25.
 */
@Service
public class LogFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        LoggerMessage loggerMessage = new LoggerMessage(
                event.getMessage()
                , DateFormat.getDateTimeInstance().format(new Date(event.getTimeStamp())),
                event.getThreadName(),
                event.getLoggerName(),
                event.getLevel().levelStr
        );
        LoggerQueue.getInstance().push(loggerMessage);
        return FilterReply.ACCEPT;
    }
}
