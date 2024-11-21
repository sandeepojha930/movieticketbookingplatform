package com.poc.movieticketbookingplatform.service;

import com.poc.movieticketbookingplatform.model.SubscriptionPlan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    public List<SubscriptionPlan> getAllPlans() {
        // Logic to retrieve all subscription plans
        return List.of(
                new SubscriptionPlan(1L, "Basic", 9.99, "monthly"),
                new SubscriptionPlan(2L, "Premium", 19.99, "monthly"),
                new SubscriptionPlan(3L, "Annual", 199.99, "yearly")
        );
    }

    public SubscriptionPlan getPlanById(Long id) {
        // Logic to retrieve a subscription plan by ID
        return new SubscriptionPlan(id, "Premium", 19.99, "monthly");
    }

    public SubscriptionPlan createPlan(SubscriptionPlan plan) {
        // Logic to create a new subscription plan
        return plan;
    }

    public SubscriptionPlan updatePlan(Long id, SubscriptionPlan updatedPlan) {
        // Logic to update an existing subscription plan
        return updatedPlan;
    }

    public void deletePlan(Long id) {
        // Logic to delete a subscription plan
    }
}