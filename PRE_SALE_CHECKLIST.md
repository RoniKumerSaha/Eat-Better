# Eat Better — Pre-Sale Audit

A walkthrough of what I observed while running the app on an emulator, and what needs to be done before this can be sold publicly.

---

## TL;DR — the app itself

What works well:

- **Polished, calm design** with custom typography, sage-green palette, score ring, and a clean bottom nav (Home / Library / Progress / Badges / Settings).
- **Bangladesh-first content**: 81 foods, bilingual labels (English + Bengali), Local/Saved tags, native ingredient names (কলা, ভেটকি, ভুনা খিচুড়ি, কাচ্চি বিরিয়ানি, etc.).
- **Real local persistence**: Room/SQLite database for foods, favorites, daily records, achievements, user settings. State survives a restart (Home still showed Today=90/Logged meals after I closed and reopened the app).
- **Solid nutrition model**: per-food 0–10 score, portion selector (½ banana → 1 → 2) with kcal/protein/fiber, breakdown of carbs/protein/fat per portion.
- **Gamification that feels supportive, not shamed-based**: "Nurturing", "Mindful Insights", badges ("Nurtured Body", "30-Day Flow"), streak counter, share card.
- **Onboarding + personalization**: Profile name, wellness goals (4 options), Active Meal Schedules toggles for Breakfast/Lunch/Dinner/Snacks.
- **Sane defaults**: sensible animations, edge-to-edge, RTL support declared (`android:supportsRtl="true"`).
- **Test infrastructure in place**: Robolectric + Roborazzi screenshot tests, Compose UI tests, instrumented tests, debug & release buildTypes both configured.

What needs attention before you charge money for it — in order of severity.

---

## 🔴 BLOCKER — must do before any sale

### 1. Legal & policy assets (none exist)
Searched the entire `app/src` and root: there is **no Privacy Policy, no Terms of Service, no EULA, no data-deletion instructions, no contact/support email, no developer identity page**.

Google Play and Apple App Store both require:
- A public **Privacy Policy URL** linked from the Store listing and surfaced in the app (Settings screen is the obvious place).
- For Health/Medical-adjacent apps (you say "no medical framing", but it scores food and shows nutrition), the app **must declare it is not medical advice** and you should have a Terms of Service that says so.
- A **contactable developer email** or website (App Store requires "Support URL").

**Action**: write three short pages and host them (GitHub Pages is fine). Add links in `Settings & Privacy` screen. Update `strings.xml` instead of the current single `app_name` string.

### 2. Package name is a placeholder
```
applicationId = "com.aistudio.eatbetter.kmrwlp"
namespace    = "com.example"
```
`kmrwlp` looks like an AI-Studio-generated suffix, and `com.example` is explicitly forbidden by both Google Play and Apple. You cannot publish under `com.example`.

**Action**: pick a real domain you own (e.g. `app.eatbetter.app`), rename the `namespace` and `applicationId`, update all package declarations in `src/main/java/com/example/**` to match, then do a clean rebuild. Verify `MainActivity` etc. compile.

### 3. Release signing key is missing
`my-upload-key.jks` is referenced in `signingConfigs.release` but does not exist. Any `:app:assembleRelease` will fail. And once you publish under a release key, **you cannot change it** without forcing every user to uninstall.

**Action**: generate a 25-year keystore with a strong password, store the passwords in your secret manager (NOT in git), back up the `.jks` file in two physical locations, and set `KEYSTORE_PATH`/`STORE_PASSWORD`/`KEY_PASSWORD` env vars in your CI. The current `debug.keystore` I created at the repo root is for dev only — make sure it's in `.gitignore` (it is) and that you do **not** use it for a Play upload.

### 4. Firebase is referenced but not configured
- `firebase-bom`, `firebase-ai`, `firebase-appcheck-recaptcha` are listed as dependencies.
- `google-services` Gradle plugin is applied.
- `MissingGoogleServicesStrategy.WARN` is set (so builds don't fail — Play will still reject a missing `google-services.json`).
- **There is no `google-services.json` anywhere in the repo**.

Worse, `metadata.json` declares `"majorCapabilities": ["MAJOR_CAPABILITY_SERVER_SIDE_GEMINI_API"]` — i.e. you are telling Google this app calls Gemini on a server. That's an obligation, not an option: every AI-assisted response shown to a user must be reviewed for safety, and you'll be asked to declare the data flow in the Play Console.

**Decision needed** (pick one and remove the other):
- **(A) Drop Firebase** — keep the app fully offline (your Settings page already says "100% Offline & Private, never uploaded to any remote server"). Delete the Firebase deps and the `com.google.gms.google-services` plugin, delete the `metadata.json` capability flag, and your legal surface area gets much smaller.
- **(B) Wire up Firebase properly** — add `google-services.json`, configure App Check for production (reCAPTCHA Enterprise keys), enable Gemini safety settings, declare the privacy policy and "How your data is used" disclosure required for AI features, and add a moderation layer for AI outputs shown to users.

Right now you're shipping *neither*, which is the worst of both worlds.

### 5. Backup rules are a TODO stub
`backup_rules.xml` and `data_extraction_rules.xml` are still the auto-generated Android Studio template comments. For an app storing **local food logs**, you must explicitly decide whether those logs are auto-backed-up to the user's Google account.

**Action**: either
- exclude the Room database from cloud backup if logs are private-by-design, **and document that exclusion in the privacy policy**, or
- include them and tell the user their food diary follows their Google account.

Do not leave the placeholder file — Google reviewers will notice.

---

## 🟠 HIGH — needed before a clean review

### 6. Missing Data Safety / Play Console disclosures
Google Play Console "Data safety" section asks about every data type you collect. Right now the app claims offline-only — but the form will still ask. You need to commit in writing to:

- **No data collected / no data shared** (if you stay offline), or
- Declare the exact fields, purposes, retention, and user controls (if you wire AI).

### 7. No app icon, no splash, no Play Store graphics
- `ic_launcher_foreground.xml` / `ic_launcher_background.xml` are stock Android Studio vector stubs. **Generate a real adaptive icon** (1024×1024 foreground + 1024×1024 background, monochrome variant for themed icons).
- No feature graphic (1024×500), no screenshots (min 4, ideally 6–8), no short promo video — Play Store requires all of these.
- The `<style name="Theme.MyApplication" parent="android:Theme.DeviceDefault.NoActionBar" />` is the default. Add a real splash (`Theme.SplashScreen`) and a proper Material 3 theme.

### 8. Localized strings only English
Bengali is *displayed* in food data, but every UI label ("Save Preferences", "Why this score?", "Snack Pending...", etc.) is hardcoded in the Kotlin composables.

Search for hardcoded strings: I saw `"Settings & Privacy"`, `"Active Meal Schedules"`, `"Breakfast"`, `"Lunch"`, etc. all embedded in composables.

**Action**: extract every user-visible string to `strings.xml`, add a `values-bn/strings.xml` for Bengali, and ideally `values-hi/` (Hindi) since you're targeting South Asia. Use `stringResource()` in composables. Without this, your bilingual food list becomes a mono-lingual app for everyone but Bangladeshis.

### 9. `versionCode` is `1` and `versionName` is `"1.0"`
Fine for the first release, but make sure you have a process to bump these — Play Store will reject duplicates.

### 10. Crash reporting / analytics
There is none. For an app you're selling, you need:
- Firebase Crashlytics or Sentry (offline-OK if you're staying local — use Sentry with on-device buffering).
- A `LOGGER.tag` strip in release builds — currently `libs.logging.interceptor` is on OkHttp, but there are no global log gates. Logs in a release APK can leak user-entered names or food picks.

### 11. "0 of 11 Badges" while Home shows a 7-day streak
I saw the Badges screen report zero unlocked while Home shows an active 7-day streak and high daily scores. Either the seed/sample data doesn't trigger the badge engine, or the unlock criteria aren't being evaluated when records are pre-populated.

**Action**: this is a real product bug — gamification has to *work* to be a feature. Either fix the achievement evaluation logic or delete the badge until it works. Shipping an empty "earn badges" panel is worse than no badges.

### 12. Monetization
There is no in-app purchase, no subscription, no ads SDK. That's fine — but if "sell this app" means a paid download, you also need:
- A way to gate features (play-licensing or RevenueCat).
- Receipt of sale: registered merchant account on Google Play / App Store.
- A clear refund policy linked from the listing.

For Play Store the simplest model is "free + IAP" using `com.android.billingclient:billing-ktx`.

---

## 🟡 MEDIUM — quality issues I'd hit in review

### 13. Compose preview surface is unbranded
`@Preview` is used in places, but I didn't see consistent design-system previews. Add `@Preview(uiMode = UI_MODE_NIGHT_YES)` for dark mode.

### 14. Accessibility
Did not see `contentDescription` on every decorative icon, nor `Modifier.semantics { }` grouping on the score ring / meal cards. For a paid app this is a Play Store and ADA compliance risk.

### 15. No screenshot tests for the major flows
Roborazzi is wired up, but I didn't see it running on CI. Add screenshot regression tests for: Home (empty / populated), Onboarding, Food Detail, Library, Progress, Badges, Settings, Share Card.

### 16. No unit tests covering the nutrition scoring engine
`NutritionScoringEngine.kt` is the heart of the product and is untested, in the test source set. Add tests for: portion scaling, score floor/ceiling, streak reset rules, badge unlock conditions.

### 17. UI dump artifacts
You generate a UI dump via `uiautomator dump` during automated flows? Make sure that path is not committed. (I cleaned up the `ui_dump.xml` I created locally — just a note.)

### 18. `local.properties` was checked in
`local.properties` contains `sdk.dir` which is the Android SDK path. It's in `.gitignore`, but I see it present in your working tree. Make sure it is not pushed to the public repo. Use a per-developer file.

### 19. `requestFramePermissions: []`
`metadata.json` declares zero required runtime permissions. Good — keep it that way as long as you stay offline. If you add location, camera, or notifications later, update both this and the AndroidManifest.

### 20. README and contributor docs
There is no README, no LICENSE, no CONTRIBUTING, no CHANGELOG. The repo currently reads like a code drop, not a product. Add at minimum:
- `README.md` (what it does, who it's for, screenshots, how to build)
- `LICENSE` (proprietary or OSS — your choice, but make it explicit)
- `CHANGELOG.md` (per release)

---

## 🟢 NICE TO HAVE — polish

- Onboarding shows up the first time but I didn't see a "skip for now" or "try sample data" — add one so journalists can preview quickly.
- The "Share Card" flow exists (`ShareCardDialog`) but I didn't see it wired into a share intent — make sure the share button actually opens Android's share sheet with the rendered image.
- Settings has no "About" / "Version" / "Open source licenses" — required by many store guidelines.
- Add a "Rate this app" link in Settings (Play Store in-app review API).
- Add a one-time "How is my score calculated?" modal triggered from the "Why this score?" link — the screen currently only renders text, not a real explanation screen.
- Dark mode: theme exists, but verify on dark emulator (I tested light only).
- Tablet / large-screen support: declare `android:resizeableActivity="true"` and test on a tablet emulator.
- Localization: plan for at least `bn`, `en`, and one more (e.g. `hi`).

---

## Suggested minimum-ship order

1. Pick **offline-only** OR **Firebase AI** — remove the half-built other. (~1 day)
2. Rename `com.example` and `applicationId` to your real domain, regenerate everything. (~half a day)
3. Generate release keystore, store passwords in CI secrets, document recovery. (~1 hour)
4. Write Privacy Policy + Terms + Contact, host on a public URL, add links in Settings. (~1 day, lawyer recommended)
5. Replace placeholder launcher icon with branded adaptive icon. (~half a day)
6. Generate Play Store listing assets (icon, feature graphic, 6–8 screenshots). (~1 day)
7. Extract hardcoded strings to `strings.xml` + Bengali locale. (~1 day)
8. Fix the badge engine to actually evaluate against pre-populated records. (~half a day)
9. Wire crash reporting + release logging. (~half a day)
10. Add a "No medical advice" disclaimer in the onboarding completion flow. (~1 hour)
11. Add in-app purchase / monetization. (~1–2 days)

After that: open the Play Console, fill out Data Safety, Content Rating, App Category, target audience, and submit for review.

---

## Quick file pointers for the work above

- `app/build.gradle.kts` — `applicationId`, `namespace`, `signingConfigs.release`, Firebase block.
- `app/src/main/AndroidManifest.xml` — add `<meta-data>` blocks for the Data Safety form, declare any new permissions.
- `app/src/main/res/values/strings.xml` — currently only `app_name`. All UI strings should move here.
- `app/src/main/res/xml/backup_rules.xml`, `data_extraction_rules.xml` — fill in or explicitly exclude.
- `app/src/main/res/mipmap-anydpi-v26/` — replace stub vectors with branded adaptive icon.
- `app/src/main/java/com/example/ui/screens/SettingsScreen.kt` — add Privacy/Terms/Contact links and About/Version section.
- `metadata.json` — remove `MAJOR_CAPABILITY_SERVER_SIDE_GEMINI_API` if you stay offline; keep it only if you ship Firebase AI properly.
- New files: `PRIVACY_POLICY.md`, `TERMS.md`, real launcher icon assets, Play Store screenshots.