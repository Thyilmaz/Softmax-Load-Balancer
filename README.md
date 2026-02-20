# Softmax tabanlı Dağıtık Sistem Yük Dengeleyici

Bu proje, bir sunucunun aniden yavaşladığı (non-stationary) ortamlarda, trafiği en optimize şekilde yöneten bir **Softmax Load Balancer** simülasyonudur.

## 🚀 Özellikler
- **Softmax Action Selection:** Olasılıksal seçim mekanizması.
- **Numerical Stability:** Üstel hesaplamalarda taşma (overflow) koruması.
- **Adaptive Learning:** `alpha` katsayısı ile değişen sunucu performansına hızlı adaptasyon.
- **Non-Stationary Simulation:** Çalışma anında sunucu performans değişimi testi.

## 🛠 Kullanılan Teknolojiler
- **Dil:** Java 17+
- **IDE:** IntelliJ IDEA
- **AI Tool:** Gemini 3 Flash (Agentic Coding süreci için)

## 📊 Analiz
- **Zaman Karmaşıklığı:** Seçim süreci $O(K)$, Güncelleme süreci $O(1)$.
- **Çözüm:** Round-Robin'in aksine, bu sistem sunucuların "sağlık" durumunu takip eder ve gecikmeyi minimize eder.
