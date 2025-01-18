package com.projectM.controller;

import com.projectM.model.PlanType;
import com.projectM.model.Subscription;
import com.projectM.model.User;
import com.projectM.service.SubscriptionService;
import com.projectM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Subscription> getSubscription(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        if (jwt != null && jwt.startsWith("Bearer")) {
            jwt = jwt.substring(7);
        }
        User user = userService.findUserProfileByJwt(jwt);

        return ResponseEntity.ok(subscriptionService.getUserSubscription(user.getId()));
    }

    @PatchMapping("/upgrade")
    public ResponseEntity<Subscription> upgradePlan(
            @RequestParam PlanType planType,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        if (jwt != null && jwt.startsWith("Bearer")) {
            jwt = jwt.substring(7);
        }
        User user = userService.findUserProfileByJwt(jwt);

        return ResponseEntity.ok(subscriptionService.upgradeSubscription(user.getId(), planType));
    }

}
