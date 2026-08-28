# Eat Better

A calm, offline-first Android nutrition companion that helps users make healthier
food choices without calorie obsession, shame, or medical framing.

> Eat Better is for general wellness only and is not a substitute for medical or
> dietary advice. See `PRIVACY_POLICY.md` and `TERMS.md`.

## What it does

- **Daily nutrition score** that gently reflects variety and consistency, not
  strict calorie or macro targets.
- **Library of 81 wholesome foods** with English + Bengali names, portion sizes,
  per-portion kcal / carbs / protein / fat / fiber / sugar / sodium, and an
  explanation of why each food was given its score.
- **Mindful streaks** and **badges** that celebrate small daily wins
  ("Nurtured Body", "30-Day Flow", "Local Flavors") — no streaks broken if you
  skip a day.
- **Weekly trend chart** that shows your score across the last seven days.
- **Share card** that summarizes your day without exposing personal data.

## What it does not do

- It does **not** call any remote server. There is no analytics, no advertising,
  no AI in the runtime path, no crash reporting, and no account system.
- It does **not** count calories obsessively or push macro targets. The score is
  deliberately not labeled as "good" or "bad".
- It does **not** give medical advice. See `TERMS.md` and the in-app disclaimer.

## Architecture

- **UI**: 100% Jetpack Compose + Material 3. No XML layouts.
- **Persistence**: Room (SQLite) for foods, daily records, favorites, achievements,
  and user settings. No remote database.
- **State management**: Single `AndroidViewModel` exposing a `StateFlow<UiState>`
  composed from repository flows.
- **No third-party runtime SDKs**: Firebase, Auth, location, camera, and analytics
  deps are not used. (See `app/build.gradle.kts` — most "AI Studio template"
  dependencies were removed during the pre-release hardening pass.)
- **Testing**: JUnit + Robolectric for unit tests, Roborazzi for screenshot
  regression tests, and Compose UI tests on the JVM.

## How to build

Prerequisites:

- JDK 17+ (or use the JDK bundled with Android Studio)
- Android Studio Ladybug or newer
- Android SDK with platforms `android-36` and `android-37` installed

From the project root:

```
gradle :app:assembleDebug
```

The resulting APK lands at `app/build/outputs/apk/debug/app-debug.apk`.

## How to install and run

```
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell monkey -p com.aistudio.eatbetter.kmrwlp -c android.intent.category.LAUNCHER 1
```

## Project layout

```
app/src/main/java/com/example/
├── MainActivity.kt              # Single activity, hosts Compose
├── data/
│   ├── local/                   # Room database, DAOs, entities, seed data
│   └── repository/              # NutritionRepository — single source of truth
├── model/                       # Plain Kotlin data classes
├── scoring/
│   └── NutritionScoringEngine.kt  # Pure-Kotlin, fully unit-tested
└── ui/
    ├── EatBetterApp.kt          # Top-level nav + Scaffold
    ├── components/              # Reusable Compose widgets
    ├── legal/LegalLinks.kt      # Privacy Policy / Terms URLs
    ├── screens/                 # One file per top-level screen
    ├── theme/                   # Material 3 theme + colors + typography
    └── viewmodel/               # EatBetterViewModel
```

## Pre-release hardening

This repo went through a pre-sale audit. See `PRE_SALE_CHECKLIST.md` for the
full report, including what was fixed and what still needs to be done before
this can be sold in a public store.

## Privacy

See `PRIVACY_POLICY.md`. The short version: the app stores your data on your
device only, never uploads it anywhere, declares no runtime permissions, and
includes no analytics SDK.

## License

Proprietary — see `LICENSE`. All Rights Reserved. Contact `support@example.com`
if you wish to use any portion of this software.