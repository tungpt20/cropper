Image Cropper
============================
**Clone from** https://github.com/ArthurHub/Android-Image-Cropper.git

## Usage
*For a working implementation, please have a look at the Sample Project*

1. Include the library
    Add it in your root build.gradle at the end of repositories:
    ```
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
    ```

    ```
    dependencies {
        implementation 'com.github.tungpt20:cropper:0.1.0'
    }
    ```

    Add permissions to manifest
    ```
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    ```
    Add this line to your Proguard config file
    ```
    -keep class androidx.appcompat.widget.** { *; }
    ```

### Using Activity
2. Add `CropImageActivity` into your AndroidManifest.xml
    ```xml
    <activity
        android:name="dev.tino.imagecropper.CropImageActivity"
        android:theme="@style/Theme.AppCompat.NoActionBar" />
    ```

3. Start `CropImageActivity` using builder pattern from your activity
    ```java
    // start picker to get image for cropping and then use the image in cropping activity
    CropImage.activity()
        .setGuidelines(CropImageView.Guidelines.ON)
        .setCropShape(CropImageView.CropShape.RECTANGLE)
        .setAspectRatio(1, 1)
        .start(this));

    // start cropping activity for pre-acquired image saved on the device
    CropImage.activity(imageUri)
    .start(this);

    // for fragment (DO NOT use `getActivity()`)
    CropImage.activity()
    .start(getContext(), this);
    ```

4. Override `onActivityResult` method in your activity to get crop result
    ```java
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    ```

### Using View
2. Add `CropImageView` into your activity
    ```xml
    <!-- Image Cropper fill the remaining available height -->
    <dev.tino.imagecropper.CropImageView
        android:id="@+id/cropImageView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
    ```

3. Set image to crop
    ```java
    cropImageView.setImageUriAsync(uri);
    // or (prefer using uri for performance and better user experience)
    cropImageView.setImageBitmap(bitmap);
    ```

4. Get cropped image
    ```java
    // subscribe to async event using cropImageView.setOnCropImageCompleteListener(listener)
    cropImageView.getCroppedImageAsync();
    // or
    Bitmap cropped = cropImageView.getCroppedImage();
    ```