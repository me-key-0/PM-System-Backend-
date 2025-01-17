package com.projectM.service;

import com.projectM.model.PlanType;
import com.projectM.model.Subscription;
import com.projectM.model.User;

public interface SubscriptionService {

    Subscription createSubscription(User user) throws Exception;

    Subscription getUserSubscription(Long userId) throws Exception;

    Subscription upgradeSubscription(Long userId, PlanType planType) throws Exception;

    boolean isValid(Subscription subscription);
}
