package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int k = 5;
        int totalRequests = 1000;
        double tau = 15.0;
        double alpha = 0.2; // Sabit öğrenme katsayımız (Zamanla değişen ortama adaptasyon için)

        System.out.println("=== SOFTMAX LOAD BALANCER SİMÜLASYONU BAŞLIYOR ===\n");

        List<Server> servers = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            servers.add(new Server(i, 50.0));
        }

        SoftmaxBalancer balancer = new SoftmaxBalancer(k, tau, alpha);
        double totalLatency = 0;

        // --- WARM-UP (ISINMA) ---
        for (int i = 0; i < k; i++) {
            // Sunucu 2'yi başlangıçta çok hızlı (5ms) olarak öğretiyoruz
            double simulatedLatency = (i == 2) ? 5.0 : 150.0;
            balancer.update(i, simulatedLatency);
        }
        System.out.println("[Sistem Durumu]: Algoritma başlangıç için Sunucu 2'ye odaklandı.\n");

        // --- ANA DÖNGÜ ---
        for (int i = 0; i < totalRequests; i++) {

            // KRİTİK OLAY: Sunucu 2 aniden yavaşlıyor
            if (i == 50) {
                System.out.println("\n>>> [KRİTİK OLAY]: Sunucu 2 aniden yavaşladı (Gecikme 500ms oldu) <<<\n");
                servers.get(2).setBaseLatency(500.0);
            }

            int selected = balancer.selectServer();
            double latency = servers.get(selected).getLatency();
            totalLatency += latency;
            balancer.update(selected, latency);

            // İlk isteği ve her 100. isteği ekrana yazdır
            if (i == 0 || i % 100 == 0) {
                System.out.println("İstek #" + i + " | Seçilen Sunucu: " + selected + " | Gecikme: " + String.format("%.2f", latency) + "ms");
            }
        }

        System.out.println("\n=== ANALİZ SONUÇLARI ===");
        System.out.println("Ortalama Gecikme: " + String.format("%.2f", (totalLatency / totalRequests)) + " ms");
    }
}