# Changelog

## Version 2.0.0 (2024)

### 🎉 Major Update - Premium Features & Monetization

#### ✨ New Features

**Premium Subscription ($0.99/week)**
- Weekly subscription with automatic renewal
- Beautiful paywall design with clear value proposition
- Restore purchases functionality
- Google Play Billing integration

**Ad-Free Experience**
- Banner ads for free tier users
- Complete ad removal for premium subscribers
- AdMob integration with test ads

**Prayer Times (Premium) 🕌**
- Accurate prayer times based on GPS location
- Automatic location detection
- Beautiful prayer times display with Islamic emojis
- Fallback to Jakarta, Indonesia if location unavailable
- Shows all 5 daily prayers: Fajr, Dhuhr, Asr, Maghrib, Isha

**Dark Mode (Premium) 🌙**
- Eye-friendly dark theme for nighttime use
- Toggle in Settings
- Smooth theme switching

**Enhanced Settings**
- Subscription management
- Premium status display
- Subscription expiry date
- App version information

**Improved UI/UX**
- Premium badge for subscribed users
- Prominent upgrade button for free users
- Menu options for easy navigation
- Professional paywall screen with 6 premium features highlighted

#### 🐛 Bug Fixes
- **Fixed**: MediaPlayer memory leak in PlayActivity
- **Fixed**: targetSdkVersion was lower than minSdkVersion
- **Fixed**: Deprecated jcenter() repository replaced with mavenCentral()
- **Fixed**: Resources not released on activity destroy

#### 🔧 Technical Improvements
- Migrated from android.support.* to AndroidX
- Updated compileSdk from 26 to 34
- Updated targetSdk from 16 to 34
- Updated Gradle plugin from 3.0.1 to 7.4.2
- Updated Gradle wrapper from 4.1 to 7.5
- Added proper ProGuard rules
- Added proper permissions for location, internet, and billing
- Improved code quality and organization

#### 📦 New Dependencies
- Google Play Billing 6.1.0
- Google AdMob 22.6.0
- Google Play Location Services 21.1.0
- AndroidX libraries (AppCompat, CardView, RecyclerView, etc.)

#### 📱 Architecture Changes
- Added PremiumManager for subscription state management
- Added BillingManager for Google Play Billing operations
- Added AdManager for advertisement management
- Added PrayerTimesCalculator utility
- Improved separation of concerns

#### 🎨 UI Enhancements
- New premium activity with attractive paywall
- Updated main activity with premium badge
- Added banner ad containers
- Improved layouts with Material Design
- Added menu with premium features

---

## Version 1.0.0 (2017-2018)

### Initial Release
- Al-Fatihah audio playback
- Complete Juz Amma (chapters 78-114)
- Play, Pause, Stop controls
- Indonesian translation labels
- Basic ListView interface
- Material Design with teal theme
- RTL support for Arabic
- 37 Quranic chapters with verse counts

---

## Migration Notes

### For Developers
If you're upgrading from v1.0.0 to v2.0.0:

1. **AndroidX Migration**: All support libraries have been migrated to AndroidX
2. **Billing Setup**: Configure your Google Play Console with the product ID: `wazma_premium_weekly`
3. **AdMob Setup**: Replace test Ad Unit ID with your production ID in `AdManager.java`
4. **Permissions**: The app now requires location permissions for Prayer Times feature
5. **Gradle**: Update your Android Studio to support Gradle 7.5+

### Breaking Changes
- Minimum SDK remains 21 (Android 5.0)
- Target SDK increased to 34 (Android 14)
- Package imports changed from `android.support.*` to `androidx.*`

---

## Upcoming Features (Roadmap)

- 📖 Tafsir (Quran interpretation)
- 🔖 Bookmarks
- 🧭 Qibla Direction compass
- 🔊 Multiple reciters
- 📝 Arabic text display
- 🔍 Search functionality
- 📲 Share verses
- 🌐 Multiple language support
