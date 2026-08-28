package com.example.ui.legal

/**
 * Centralized URLs for the app's externally-hosted legal pages.
 *
 * Replace these placeholders with the real URLs once you host the markdown
 * files (PRIVACY_POLICY.md and TERMS.md at the project root) on your domain.
 */
object LegalLinks {
  const val PRIVACY_POLICY_URL = "https://example.com/eat-better/privacy"
  const val TERMS_OF_SERVICE_URL = "https://example.com/eat-better/terms"
  const val SUPPORT_EMAIL = "support@example.com"
  const val SUPPORT_WEBSITE = "https://example.com/eat-better"

  /**
   * Short, in-app version of the medical-advice disclaimer. Kept in code (not
   * strings.xml) because it is shown inside the app as plain text and is part of
   * the in-app language model rather than a localizable UI label.
   */
  const val MEDICAL_ADVICE_DISCLAIMER =
    "Eat Better is a general wellness tool, not a medical device. " +
      "The scores, badges, and streaks shown in this app are heuristic " +
      "indicators intended to encourage variety and consistency. They are " +
      "not medical advice, are not a substitute for consultation with a " +
      "qualified healthcare professional, and should not be relied on to " +
      "diagnose or treat any condition. If you have a medical condition, " +
      "dietary restriction, allergy, or are pregnant or nursing, please " +
      "consult a qualified professional before changing your diet based on " +
      "this app."
}