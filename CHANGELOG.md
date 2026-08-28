# Changelog

All notable changes to Eat Better will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] — 2026-08-28 — Pre-release hardening

This is the first pre-release build that has been audited and cleaned up for store
submission. The app is functionally the same as the previous internal build; this
release addresses store-readiness gaps identified in `PRE_SALE_CHECKLIST.md`.

### Changed
- **Removed dead Firebase dependencies.** The build no longer pulls in
  `firebase-bom`, `firebase-ai`, or `firebase-appcheck-recaptcha`, and the
  `google-services` and `secrets` Gradle plugins are gone. The runtime code never
  used any Firebase API, so the app's "100% Offline & Private" claim is now
  accurate. `metadata.json` no longer declares the Gemini capability.
- **Fixed the badge unlock engine.** The Badges screen previously showed "0 of 11
  Badges" while Home showed a 7-day streak. The seeder was bypassing
  `checkAndUpdateAchievements()` and writing badge progress directly, leaving
  `currentProgress = 0` everywhere. The seeder now routes through the engine.
  `deleteEntry` and `toggleMealSkipped` now also re-evaluate achievements so
  badges can re-lock if you fall behind.
- **Made backup rules explicit.** `backup_rules.xml` and `data_extraction_rules.xml`
  no longer carry Android Studio's placeholder TODO. The Room database is now
  excluded from Android Auto Backup (so your food logs don't go to Google's
  servers) but allowed for device-to-device transfer (so you can move to a new
  phone without losing your data).
- **Extracted UI strings to `strings.xml`.** Approximately 140 hardcoded
  English strings in the Compose UI now live in `app/src/main/res/values/strings.xml`
  and are accessed via `stringResource()`. This is hygiene for any future
  localization and is required for store review.

### Added
- **Privacy Policy** at `PRIVACY_POLICY.md`.
- **Terms of Service** at `TERMS.md`.
- **README.md**, **LICENSE**, and this **CHANGELOG**.
- A new **"Privacy & About" card in Settings** with four rows: Privacy Policy,
  Terms of Service, About Eat Better (shows version), and Not Medical Advice
  (in-app disclaimer dialog).
- An **in-app medical-advice disclaimer** at the bottom of onboarding step 2.
- A **"Privacy & About" constant file** at
  `app/src/main/java/com/example/ui/legal/LegalLinks.kt` so the policy and
  terms URLs are easy to find and update.
- **Unit tests** for `NutritionScoringEngine` at
  `app/src/test/java/com/example/scoring/NutritionScoringEngineTest.kt`.

### Not done (called out as remaining work)
- Package rename: `applicationId` is still `com.aistudio.eatbetter.kmrwlp` and
  `namespace` is still `com.example`. Must be changed to a real domain before
  store submission.
- Release keystore: no `my-upload-key.jks` exists. Must be generated and stored
  outside of version control before publishing.
- App icon, feature graphic, and store screenshots: still placeholder.
- Bengali UI translations: only food data has Bengali names; UI is English.
- In-app purchase / monetization: not yet wired up.

See `PRE_SALE_CHECKLIST.md` for the full pre-store-submission checklist.

## [Unreleased]
- Initial development builds before the public pre-release.