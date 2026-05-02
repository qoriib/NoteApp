# Note App KMP with AI Assistant

Proyek Note App berbasis Kotlin Multiplatform (KMP) yang mendukung Android dan iOS, dilengkapi dengan fitur Database lokal (SQLDelight), Dependency Injection (Koin), dan integrasi AI (Gemini).

## Fitur Utama
- **Local Database**: Penyimpanan catatan menggunakan SQLDelight 2.0.1.
- **Dependency Injection**: Seluruh komponen di-inject menggunakan Koin.
- **Platform Features**: 
  - Real-time Network Monitoring (Expect/Actual).
  - Device Info display (Expect/Actual).
- **AI Assistant**: Integrasi dengan Gemini AI untuk membantu pengguna (Chatbot).

## Fitur AI: Smart Assistant
Aplikasi ini memiliki tab **AI Assistant** yang memungkinkan pengguna berinteraksi dengan model AI Gemini 1.5 Flash.

### Kemampuan AI:
1. **Chatbot**: Tanya jawab umum terkait catatan atau topik lainnya.
2. **Context Aware**: Dirancang untuk membantu merapikan tulisan atau meringkas catatan (dapat dikembangkan lebih lanjut).
3. **Responsif**: Dilengkapi dengan *loading state* dan *proper error handling* jika koneksi terputus atau API error.

### Cara Konfigurasi AI:
1. Dapatkan API Key di [Google AI Studio](https://aistudio.google.com/).
2. Buka file `composeApp/src/commonMain/kotlin/edu/learning/noteapp/di/Koin.kt`.
3. Ganti `"YOUR_GEMINI_API_KEY"` dengan API Key Anda pada bagian `GeminiService`.

## Teknologi yang Digunakan
- **Compose Multiplatform**: Untuk UI yang konsisten di Android & iOS.
- **SQLDelight**: Database lokal lintas platform.
- **Koin**: Dependency Injection.
- **Ktor**: Networking untuk akses Gemini API.
- **Kotlinx Serialization**: Untuk parsing data JSON.

## Cara Menjalankan
1. Pastikan Anda memiliki Android Studio versi terbaru.
2. Lakukan **Gradle Sync**.
3. Pilih run configuration `composeApp` untuk Android atau buka folder `iosApp` di Xcode untuk menjalankan iOS.
