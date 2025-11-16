# Wazma - Al Quran & Prayer Times

<a href="https://itunes.apple.com/nz/app/wazma-al-quran-murottal/id1389802235?mt=8" style="clear: left; float: left; margin-bottom: 1em; margin-right: 1em;">
<img height="52.5" src="https://housing.umn.edu/sites/housing.umn.edu/files/ww_app-store-badge_150909.png" width="193.8" />
</a><a href="https://play.google.com/store/apps/details?id=motion.studio.jadwalshalat&amp;hl=in">
<img height="49,5" src="https://www.newstalkflorida.com/wp-content/uploads/2017/01/Get_it_on_Google_play.png" width="193.8" />
</a>

**Version 2.0.0** - Now with Premium Features!

Wazma is a comprehensive Islamic app that helps Muslims listen to Quran recitations and access prayer times anywhere in the world.

[<img src="https://lh3.googleusercontent.com/Dnw-i0Lesv2sblDbdtmml4VQrASPe-CxdZA_UqGrYi5ZZrPW9GS9e5KLFeHiU6MKCA=h900" width="286.583" height="475" alt="Google Play"/>](https://play.google.com/store/apps/details?id=motion.studio.jadwalshalat&hl=in)

## Features

### Free Features
- Listen to Quran Juz Amma (37 chapters: Al-Fatihah + Chapters 78-114)
- High-quality audio recitations
- Play, Pause, and Stop controls
- Indonesian chapter names and descriptions
- Clean and intuitive Material Design interface
- RTL support for Arabic text

### Premium Features ($0.99/week)
- **No Ads**: Enjoy distraction-free Quran listening
- **Prayer Times**: Accurate prayer times based on your GPS location for all 5 daily prayers
- **Dark Mode**: Comfortable reading and listening at night
- **Priority Support**: Get help faster
- **Future Features**: Early access to upcoming features like Tafsir, Bookmarks, and Qibla Direction

## What's New in v2.0.0

### Added
- In-app purchase system with weekly subscription ($0.99/week)
- Beautiful paywall with compelling premium features
- Banner ads for free tier (removed for premium users)
- Prayer Times feature with GPS-based calculation
- Dark Mode theme toggle
- Enhanced Settings screen with subscription management
- Premium badge for subscribed users
- Menu system for easy navigation

### Fixed
- MediaPlayer memory leak causing crashes
- Incorrect SDK version configuration
- Deprecated dependencies updated to modern versions
- Resource cleanup issues

### Technical Improvements
- Migrated to AndroidX
- Updated to SDK 34 (Android 14)
- Google Play Billing integration
- AdMob integration
- Modern Gradle 7.5 build system

## Technical Details

### Requirements
- Android 5.0 (API 21) or higher
- Internet connection for ads (free tier)
- Location permission for Prayer Times (premium)

### Permissions
- `INTERNET`: For displaying ads and billing
- `ACCESS_NETWORK_STATE`: For checking connectivity
- `ACCESS_FINE_LOCATION`: For accurate prayer times (premium feature)
- `ACCESS_COARSE_LOCATION`: For approximate prayer times (premium feature)
- `BILLING`: For in-app purchases

### Architecture
- Clean separation of concerns with Manager classes
- MVVM-inspired architecture
- SharedPreferences for local data persistence
- Google Play Billing for subscriptions
- AdMob for monetization

## For Developers

### Building the Project

1. Clone the repository
```bash
git clone https://github.com/AfriwanAhda/AfriwanAhda-Wazma.git
cd AfriwanAhda-Wazma
```

2. Open in Android Studio (Arctic Fox or newer)

3. Replace AdMob test IDs with your production IDs in:
   - `AndroidManifest.xml`: Application AdMob App ID
   - `AdManager.java`: Banner Ad Unit ID

4. Configure Google Play Billing:
   - Create product in Google Play Console
   - Product ID: `wazma_premium_weekly`
   - Type: Auto-renewing subscription
   - Price: $0.99/week

5. Build and run
```bash
./gradlew assembleDebug
```

### Code Structure
```
app/src/main/java/motion/studio/wazma/
├── activity/
│   ├── MainActivity.java         # Main list of Quran chapters
│   ├── PlayActivity.java         # Audio player
│   ├── PremiumActivity.java      # Paywall screen
│   ├── PrayerTimesActivity.java  # Prayer times (premium)
│   └── SettingsActivity.java     # App settings
├── adapter/
│   └── ListAdapter.java          # ListView adapter
├── manager/
│   ├── AdManager.java            # AdMob management
│   ├── BillingManager.java       # Google Play Billing
│   └── PremiumManager.java       # Subscription state
└── utils/
    └── PrayerTimesCalculator.java # Prayer time calculations
```

### Testing In-App Purchases
Use Google Play's test accounts for testing subscriptions without actual charges. See [Google Play Billing Testing Guide](https://developer.android.com/google/play/billing/test).

## Roadmap

### Upcoming Features
- Tafsir (Quran interpretation)
- Bookmarks for favorite chapters
- Qibla Direction compass
- Multiple reciters
- Arabic text display alongside audio
- Search functionality
- Verse sharing
- Widget support
- Notifications for prayer times

## Learning Resources

This project demonstrates:
- ListView with custom adapters
- MediaPlayer audio playback
- Google Play Billing integration
- AdMob advertisement integration
- Location services and GPS
- SharedPreferences for data persistence
- Material Design UI/UX
- AndroidX migration
- Modern Android development practices

# License and Open Source

Wazma is available for free on the Google Play. We however kindly ask you to *NOT* ship this app or slight variations of it under your own name.

# MIT License

Copyright (c) 2017-2024 Afriwan Ahda. All rights reserved.

Permission is hereby granted, free of charge, to any person obtaining a
copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
