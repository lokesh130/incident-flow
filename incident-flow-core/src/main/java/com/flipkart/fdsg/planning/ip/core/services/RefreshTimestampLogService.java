package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.entities.RefreshTimestampLog;

import java.sql.Timestamp;

public interface RefreshTimestampLogService {
    void addRefreshTimestamp(Long refreshTimestamp);

    Long getLatestRefreshTimestamp();
}
