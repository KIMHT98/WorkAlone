///*
// * Copyright 2020 Google LLC. All rights reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.ssafy.workalone.mlkit.java;
//
//import android.content.pm.PackageManager;
//import android.os.Build.VERSION_CODES;
//import android.os.Bundle;
//import android.util.Log;
//import android.util.Size;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemSelectedListener;
//import android.widget.ArrayAdapter;
//import android.widget.CompoundButton;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.camera.core.Camera;
//import androidx.camera.core.CameraInfoUnavailableException;
//import androidx.camera.core.CameraSelector;
//import androidx.camera.core.ImageAnalysis;
//import androidx.camera.core.Preview;
//import androidx.camera.lifecycle.ProcessCameraProvider;
//import androidx.camera.view.PreviewView;
//import androidx.core.content.ContextCompat;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory;
//
//import com.google.android.gms.common.annotation.KeepName;
//import com.google.mlkit.common.MlKitException;
//import com.google.mlkit.vision.pose.PoseDetectorOptionsBase;
//import com.ssafy.workalone.R;
//import com.ssafy.workalone.mlkit.CameraXViewModel;
//import com.ssafy.workalone.mlkit.GraphicOverlay;
//import com.ssafy.workalone.mlkit.VisionImageProcessor;
//import com.ssafy.workalone.mlkit.java.posedetector.PoseDetectorProcessor;
//import com.ssafy.workalone.mlkit.java.posedetector.classification.PoseClassifierProcessor;
//import com.ssafy.workalone.mlkit.preference.PreferenceUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Live preview demo app for ML Kit APIs using CameraX.
// */
//@KeepName
//@RequiresApi(VERSION_CODES.LOLLIPOP)
//public final class CameraXLivePreviewActivityJava extends AppCompatActivity
//        implements OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {
//    private static final String TAG = "CameraXLivePreview";
//
//
//    //  private static final String CUSTOM_AUTOML_LABELING = "Custom AutoML Image Labeling (Flower)";
//    private static final String POSE_DETECTION = "Pose Detection";
//
//    private static final String STATE_SELECTED_MODEL = "selected_model";
//
//    private PreviewView previewView;
//    private GraphicOverlay graphicOverlay;
//
//    @Nullable
//    private ProcessCameraProvider cameraProvider;
//    @Nullable
//    private Camera camera;
//    @Nullable
//    private Preview previewUseCase;
//    @Nullable
//    private ImageAnalysis analysisUseCase;
//    @Nullable
//    private VisionImageProcessor imageProcessor;
//    private boolean needUpdateGraphicOverlayImageSourceInfo;
//
//    private String selectedModel = POSE_DETECTION;
//    private int lensFacing = CameraSelector.LENS_FACING_FRONT;
//    private CameraSelector cameraSelector;
//    private PoseClassifierProcessor poseClassifierProcessor;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.d(TAG, "onCreate");
//
//        // 권한 추가
////    poseClassifierProcessor.requestAudioPermissionIfNeeded(this);
//
//        if (savedInstanceState != null) {
//            selectedModel = savedInstanceState.getString(STATE_SELECTED_MODEL, POSE_DETECTION);
//        }
//        cameraSelector = new CameraSelector.Builder().requireLensFacing(lensFacing).build();
//
//        setContentView(R.layout.activity_vision_camerax_live_preview);
//        previewView = findViewById(R.id.preview_view);
//        if (previewView == null) {
//            Log.d(TAG, "previewView is null");
//        }
//        graphicOverlay = findViewById(R.id.graphic_overlay);
//        if (graphicOverlay == null) {
//            Log.d(TAG, "graphicOverlay is null");
//        }
//
////        Spinner spinner = findViewById(R.id.spinner);
//        List<String> options = new ArrayList<>();
//
//        options.add(POSE_DETECTION);
//
//
//        // Creating adapter for spinner
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_style, options);
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // attaching data adapter to spinner
////        spinner.setAdapter(dataAdapter);
////        spinner.setOnItemSelectedListener(this);
//
////        ToggleButton facingSwitch = findViewById(R.id.facing_switch);
////        facingSwitch.setOnCheckedChangeListener(this);
//
//        new ViewModelProvider(this, (ViewModelProvider.Factory) AndroidViewModelFactory.getInstance(getApplication()))
//                .get(CameraXViewModel.class)
//                .getProcessCameraProvider()
//                .observe(
//                        this,
//                        provider -> {
//                            cameraProvider = provider;
//                            bindAllCameraUseCases();
//                        });
////        ImageView settingsButton = findViewById(R.id.settings_button);
////        settingsButton.setOnClickListener(
////                v -> {
////                    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
////                    intent.putExtra(
////                            SettingsActivity.EXTRA_LAUNCH_SOURCE,
////                            SettingsActivity.LaunchSource.CAMERAX_LIVE_PREVIEW);
////                    startActivity(intent);
////                    Intent intent = new Intent(this, MainActivity.class);
////                    // 특정 스크린 경로 전달 (예: "individual-complete")
////                    intent.putExtra("startDestination", "individual-complete");
////                    startActivity(intent);
////                });
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == poseClassifierProcessor.REQUEST_RECORD_AUDIO_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Log.d(TAG, "Audio permission granted");
//                // 권한이 허용된 경우, 여기서 필요한 추가 초기화를 진행할 수 있음
//            } else {
//                Log.e(TAG, "Audio permission denied");
//                Toast.makeText(this, "Audio permission is required for this app to function", Toast.LENGTH_LONG).show();
//                // 권한 거부 시 음성 인식 기능을 비활성화하거나 사용자에게 알림
//            }
//        }
//    }
//
//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle bundle) {
//        super.onSaveInstanceState(bundle);
//        bundle.putString(STATE_SELECTED_MODEL, selectedModel);
//    }
//
//    @Override
//    public synchronized void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//        // An item was selected. You can retrieve the selected item using
//        // parent.getItemAtPosition(pos)
//        selectedModel = parent.getItemAtPosition(pos).toString();
//        Log.d(TAG, "Selected model: " + selectedModel);
//        bindAnalysisUseCase();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//        // Do nothing.
//    }
//
//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        if (cameraProvider == null) {
//            return;
//        }
//        int newLensFacing =
//                lensFacing == CameraSelector.LENS_FACING_FRONT
//                        ? CameraSelector.LENS_FACING_BACK
//                        : CameraSelector.LENS_FACING_FRONT;
//        CameraSelector newCameraSelector =
//                new CameraSelector.Builder().requireLensFacing(newLensFacing).build();
//        try {
//            if (cameraProvider.hasCamera(newCameraSelector)) {
//                Log.d(TAG, "Set facing to " + newLensFacing);
//                lensFacing = newLensFacing;
//                cameraSelector = newCameraSelector;
//                bindAllCameraUseCases();
//                return;
//            }
//        } catch (CameraInfoUnavailableException e) {
//            // Falls through
//        }
//        Toast.makeText(
//                        getApplicationContext(),
//                        "This device does not have lens with facing: " + newLensFacing,
//                        Toast.LENGTH_SHORT)
//                .show();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        bindAllCameraUseCases();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (imageProcessor != null) {
//            imageProcessor.stop();
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (imageProcessor != null) {
//            imageProcessor.stop();
//        }
//    }
//
//    private void bindAllCameraUseCases() {
//        if (cameraProvider != null) {
//            // As required by CameraX API, unbinds all use cases before trying to re-bind any of them.
//            cameraProvider.unbindAll();
//            bindPreviewUseCase();
//            bindAnalysisUseCase();
//        }
//    }
//
//    private void bindPreviewUseCase() {
//        if (!PreferenceUtils.isCameraLiveViewportEnabled(this)) {
//            return;
//        }
//        if (cameraProvider == null) {
//            return;
//        }
//        if (previewUseCase != null) {
//            cameraProvider.unbind(previewUseCase);
//        }
//
//        Preview.Builder builder = new Preview.Builder();
//        Size targetResolution = PreferenceUtils.getCameraXTargetResolution(this, lensFacing);
//        if (targetResolution != null) {
//            builder.setTargetResolution(targetResolution);
//        }
//        previewUseCase = builder.build();
//        previewUseCase.setSurfaceProvider(previewView.getSurfaceProvider());
//        camera =
//                cameraProvider.bindToLifecycle(/* lifecycleOwner= */ this, cameraSelector, previewUseCase);
//    }
//
//    private void bindAnalysisUseCase() {
//        if (cameraProvider == null) {
//            return;
//        }
//        if (analysisUseCase != null) {
//            cameraProvider.unbind(analysisUseCase);
//        }
//        if (imageProcessor != null) {
//            imageProcessor.stop();
//        }
//
//        try {
//            switch (selectedModel) {
//
//                case POSE_DETECTION:
//                    PoseDetectorOptionsBase poseDetectorOptions =
//                            PreferenceUtils.getPoseDetectorOptionsForLivePreview(this);
//                    boolean shouldShowInFrameLikelihood =
//                            PreferenceUtils.shouldShowPoseDetectionInFrameLikelihoodLivePreview(this);
//                    boolean visualizeZ = PreferenceUtils.shouldPoseDetectionVisualizeZ(this);
//                    boolean rescaleZ = PreferenceUtils.shouldPoseDetectionRescaleZForVisualization(this);
//                    boolean runClassification = true;
//                    imageProcessor =
//                            new PoseDetectorProcessor(
//                                    this,
//                                    poseDetectorOptions,
//                                    shouldShowInFrameLikelihood,
//                                    visualizeZ,
//                                    rescaleZ,
//                                    runClassification,
//                                    /* isStreamMode = */ true);
//                    break;
//
//                default:
//                    throw new IllegalStateException("Invalid model name");
//            }
//        } catch (Exception e) {
//            Log.e(TAG, "Can not create image processor: " + selectedModel, e);
//            Toast.makeText(
//                            getApplicationContext(),
//                            "Can not create image processor: " + e.getLocalizedMessage(),
//                            Toast.LENGTH_LONG)
//                    .show();
//            return;
//        }
//
//        ImageAnalysis.Builder builder = new ImageAnalysis.Builder();
//        Size targetResolution = PreferenceUtils.getCameraXTargetResolution(this, lensFacing);
//        if (targetResolution != null) {
//            builder.setTargetResolution(targetResolution);
//        }
//        analysisUseCase = builder.build();
//
//        needUpdateGraphicOverlayImageSourceInfo = true;
//        analysisUseCase.setAnalyzer(
//                // imageProcessor.processImageProxy will use another thread to run the detection underneath,
//                // thus we can just runs the analyzer itself on main thread.
//                ContextCompat.getMainExecutor(this),
//                imageProxy -> {
//                    if (needUpdateGraphicOverlayImageSourceInfo) {
//                        boolean isImageFlipped = lensFacing == CameraSelector.LENS_FACING_FRONT;
//                        int rotationDegrees = imageProxy.getImageInfo().getRotationDegrees();
//                        if (rotationDegrees == 0 || rotationDegrees == 180) {
//                            graphicOverlay.setImageSourceInfo(
//                                    imageProxy.getWidth(), imageProxy.getHeight(), isImageFlipped);
//                        } else {
//                            graphicOverlay.setImageSourceInfo(
//                                    imageProxy.getHeight(), imageProxy.getWidth(), isImageFlipped);
//                        }
//                        needUpdateGraphicOverlayImageSourceInfo = false;
//                    }
//                    try {
//                        imageProcessor.processImageProxy(imageProxy, graphicOverlay);
//                    } catch (MlKitException e) {
//                        Log.e(TAG, "Failed to process image. Error: " + e.getLocalizedMessage());
//                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT)
//                                .show();
//                    }
//                });
//
//        cameraProvider.bindToLifecycle(/* lifecycleOwner= */ this, cameraSelector, analysisUseCase);
//    }
//}
