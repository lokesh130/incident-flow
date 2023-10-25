package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.entities.Alert;

import java.util.List;

public interface AlertService {
    List<Alert> findAll();

    Alert findById(int alertId);
}
