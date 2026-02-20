package org.example;

import java.util.Random;

public class Server {
    private final int id;
    private double baseLatency;
    private final Random random = new Random();

    public Server(int id, double baseLatency) {
        this.id = id;
        this.baseLatency = baseLatency;
    }

    public double getLatency() {
        // Gürültülü (noisy) ortam simülasyonu
        return Math.max(1, baseLatency + (random.nextGaussian() * 10));
    }

    public void setBaseLatency(double newLatency) {
        this.baseLatency = newLatency;
    }

    public int getId() {
        return id;
    }
}