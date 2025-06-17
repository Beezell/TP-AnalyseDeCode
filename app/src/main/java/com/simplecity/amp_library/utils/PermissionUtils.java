package com.simplecity.amp_library.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.greysonparrelli.permiso.Permiso;

public class PermissionUtils {

    private PermissionUtils() {}

    public interface PermissionCallback {
        void onSuccess();
    }

    private static void simplePermissionRequest(final PermissionCallback callback, String... permissions) {
        Permiso.getInstance().requestPermissions(new Permiso.IOnPermissionResult() {
            @Override
            public void onPermissionResult(Permiso.ResultSet resultSet) {
                if (resultSet.areAllPermissionsGranted()) {
                    callback.onSuccess();
                }
            }

            @Override
            public void onRationaleRequested(Permiso.IOnRationaleProvided callback, String... permissions) {
                callback.onRationaleProvided();
            }
        }, permissions);
    }

    public static boolean hasStoragePermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    public static void RequestStoragePermissions(final PermissionCallback callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            simplePermissionRequest(callback, Manifest.permission.READ_MEDIA_AUDIO);
        } else {
            simplePermissionRequest(callback, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }
}
