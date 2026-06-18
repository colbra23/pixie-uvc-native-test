# Pixie UVC Native Minimal APK Test

This is a minimal native Android APK smoke test for Pixie Look.

Goal: confirm that a native Android app can open the EMEET USB-C/UVC webcam directly, without Chrome, Netlify, USB Camera app, or a local HTTP server.

## What this app does

- Opens full-screen landscape.
- Uses the AndroidUSBCamera/AUSBC UVC library.
- Requests USB device permission from Android.
- Attempts to open the USB webcam at 1280 x 720 MJPEG.
- Shows the live camera preview full-screen.

## Important

I could not compile the APK inside ChatGPT because this environment does not include the Android SDK/Gradle build toolchain. This folder is a ready-to-build Android project with a GitHub Actions workflow that will build the APK in the cloud.

## Build the APK with GitHub Actions

1. On GitHub, create a new private repository, for example:
   `pixie-uvc-native-test`

2. Upload all files/folders from this ZIP into that repository.

3. Open the repository in GitHub.

4. Tap/click **Actions**.

5. Choose **Build Pixie UVC APK**.

6. Tap/click **Run workflow**.

7. Wait for the build to finish.

8. Open the finished workflow run.

9. Download the artifact named:
   `pixie-uvc-minimal-debug-apk`

10. Inside it, you should find:
   `pixie-uvc-minimal-debug.apk`

## Install on the tablet

1. Download the APK onto the prototype tablet.
2. Open the APK.
3. Android may ask to allow installs from your browser/files app. Allow it.
4. Install the app.
5. Plug in the EMEET webcam directly by USB-C.
6. Open **Pixie UVC Test**.
7. When Android asks to allow Pixie UVC Test to access the USB device, tap **OK/Allow**.

## Expected result

The app should show the live USB webcam preview full-screen.

## If it fails

Please send me:

- a screenshot of the app screen;
- whether Android asked for USB permission;
- any error text shown in the app;
- whether the EMEET webcam was plugged in before or after opening the app.

## Notes

This is only a camera smoke test. It intentionally does not include onboarding, tutorial videos, Pixie UI screens, or auto-tracking yet.
