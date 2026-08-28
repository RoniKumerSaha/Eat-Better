# Privacy Policy — Eat Better

Last updated: 2026-08-28

Eat Better ("we", "our", "the app") is an offline-first Android nutrition companion. This policy describes what data the app handles, how it handles it, and the choices you have.

## Summary

- The app **does not collect, transmit, or sell** any personal data to any server.
- All food logs, daily scores, badges, favorites, and profile preferences live in a local SQLite (Room) database on your device.
- The app runs **fully offline**. No analytics SDK, no advertising SDK, no crash reporting SDK, no remote configuration, no remote AI calls.
- There is **no account** and **no sign-in**.

## What the app stores on your device

- Your display name (optional, set during onboarding).
- The wellness goals and meal-schedule toggles you select.
- A history of foods you log, the portion sizes, the meal type, and the calculated daily score for each day.
- The badges you have unlocked and the daily challenges you have completed.
- Your favorite foods.

This data is stored in a local Room database at the app's private storage path (`/data/data/com.aistudio.eatbetter.kmrwlp/databases/`) and is only readable by the Eat Better app.

## Auto Backup

By default, **Android Auto Backup is disabled** for this app (see `app/src/main/res/xml/backup_rules.xml`). Your data is not copied to Google's servers as part of Android's backup feature. You can still move your data to a new device using Android's device-to-device transfer ("Switch to a new phone"), which copies the local database directly between devices.

## Permissions

The app declares **no runtime permissions** in the Android manifest. It does not access your camera, microphone, location, contacts, photos, or any other device sensor.

## Children

The app is not targeted at children under 13. We do not knowingly collect data from children.

## Medical disclaimer

Eat Better is a **general wellness tool**, not a medical device. The scores and badges it shows are **heuristic indicators** intended to encourage variety and consistency in your diet. **They are not medical advice, are not a substitute for consultation with a qualified healthcare professional, and should not be relied on to diagnose or treat any condition.** If you have a medical condition, dietary restriction, allergy, or are pregnant or nursing, please consult a qualified professional before changing your diet based on this app.

## Changes to this policy

If we ever change what the app stores or does with your data, we will update this policy and bump the version number of the app. If we ever introduce any network connectivity or remote data storage, this policy will explicitly call that out before that version is released.

## Contact

If you have any questions about this policy, please contact us at:

- Email: `support@example.com`
- Website: https://example.com/eat-better

(Replace these placeholders with your real contact details before publishing.)