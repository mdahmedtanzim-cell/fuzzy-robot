# Love Calculator - Android Native App (Jetpack Compose)

This is a complete, ready-to-run native Android application written in Kotlin using **Jetpack Compose** and **Material 3**.

---

## 📱 Features
- **Clean Compose Material 3 UI**: Soft romantic color scheme (#E75480, #FFE5ED, #B51E55).
- **Relationship Selector**: Choose between **Husband & Wife** 💑 and **Boyfriend & Girlfriend** 💘.
- **5-Second Heart Animation**: Smooth infinite transition pulse with a Material 3 circular progress indicator.
- **Easter Egg**: When relationship is **Husband & Wife**, with first name **"Tanzim"** and second name **"Maria"** (case-insensitive, trims leading/trailing whitespace), the love percentage is guaranteed **100%**!
- **Random Love Generator**: For all other valid combinations, calculates an authentic love score between 1% and 100%.
- **Try Again**: Instant reset flow to check another couple.

---

## 🚀 How to Open and Run in Android Studio

1. **Extract this ZIP**:
   Extract all contents to a folder on your computer.
2. **Open in Android Studio**:
   - Open Android Studio.
   - Click **Open** and select the extracted folder.
   - Wait for Gradle to finish initial sync (it will download Gradle 8.4 and Android dependencies automatically).
3. **Run on Device or Emulator**:
   - Click the green **Run** button (Shift + F10).

---

## 📦 How to Generate an Installable APK (.apk)

### Method 1: Using Android Studio
1. In Android Studio top menu:
   **Build** -> **Build Bundle(s) / APK(s)** -> **Build APK(s)**.
2. Once build completes, click the **locate** popup link.
3. Your installable file is:
   `app/build/outputs/apk/debug/app-debug.apk`
4. Transfer this APK to your phone via USB or Google Drive and tap to install!

### Method 2: Command Line
```bash
# On Linux / macOS:
./gradlew assembleDebug

# On Windows:
gradlew.bat assembleDebug
```
Output APK: `app/build/outputs/apk/debug/app-debug.apk`
