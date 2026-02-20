package org.example;

import java.util.Arrays;

public class SoftmaxBalancer {
    private final double[] qValues;
    private final double tau;   // Sıcaklık (Keşif/Sömürü dengesi)
    private final double alpha; // Öğrenme Katsayısı (Yeni veriye verilecek önem)

    public SoftmaxBalancer(int k, double tau, double alpha) {
        this.qValues = new double[k];
        this.tau = tau;
        this.alpha = alpha;

        // Başlangıçta tüm sunuculara eşit ve yüksek bir Q değeri veriyoruz (İyimser başlangıç)
        Arrays.fill(this.qValues, 10.0);
    }

    public int selectServer() {
        // NÜMERİK STABİLİTE: Üstel hesaplamada taşmayı (overflow) önlemek için maxQ çıkarılır.
        double maxQ = Arrays.stream(qValues).max().orElse(0);
        double[] expValues = new double[qValues.length];
        double sumExp = 0;

        for (int i = 0; i < qValues.length; i++) {
            expValues[i] = Math.exp((qValues[i] - maxQ) / tau);
            sumExp += expValues[i];
        }

        double rand = Math.random();
        double cumulative = 0;
        for (int i = 0; i < qValues.length; i++) {
            cumulative += (expValues[i] / sumExp);
            if (rand <= cumulative) return i;
        }
        return qValues.length - 1;
    }

    public void update(int serverIdx, double latency) {
        // Gecikme ne kadar azsa ödül o kadar büyük olur
        double reward = 1000.0 / Math.max(0.1, latency);

        // NON-STATIONARY UPDATE (Zamanla Değişen Ortam İçin):
        // Counts'a bölmek yerine sabit alpha katsayısı ile güncelliyoruz.
        qValues[serverIdx] += alpha * (reward - qValues[serverIdx]);
    }
}