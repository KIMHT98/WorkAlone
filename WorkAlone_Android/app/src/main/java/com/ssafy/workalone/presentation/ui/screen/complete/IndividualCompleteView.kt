package com.ssafy.workalone.presentation.ui.screen.complete

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ssafy.workalone.R
import com.ssafy.workalone.data.local.ExerciseInfoPreferenceManager
import com.ssafy.workalone.data.local.MemberPreferenceManager
import com.ssafy.workalone.data.local.SettingsPreferenceManager
import com.ssafy.workalone.presentation.navigation.Screen
import com.ssafy.workalone.presentation.ui.component.bottombar.CustomButton
import com.ssafy.workalone.presentation.ui.component.complete.ConfettiAnimation
import com.ssafy.workalone.presentation.ui.component.complete.IndividualExerciseRecord
import com.ssafy.workalone.presentation.ui.component.topbar.CloseButton
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue300
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray300
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import com.ssafy.workalone.presentation.viewmodels.video.AWSS3ViewModel
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import kotlin.math.roundToInt

//운동 완료 화면(개별형)
@Composable
fun IndividualCompleteView(
    navController: NavController,
    awsViewModel: AWSS3ViewModel = viewModel()
) {
    val context = LocalContext.current
    val exerciseManager = ExerciseInfoPreferenceManager(context)
    val settingManager = SettingsPreferenceManager(context)
    val videoUri = Uri.parse(exerciseManager.getFileUrl())


    val uploadInProgress = remember { mutableStateOf(false) }
    val uploadResponse by awsViewModel.uploadVideoResponse.collectAsState()
    val awsUrl by awsViewModel.awsUrl.collectAsState()



    // TODO 운동 결과 바꾸자 이제
    val exerciseCount = "3 X 5 세트"
    val exerciseDuration: Int = 1808
    val exerciseType: String = "푸쉬업"
    val memberManager = MemberPreferenceManager(context)
    val weight = memberManager.getWeight()
    val calorie: Int =
        if (exerciseType == "스쿼트") {
            (6.0 * weight * (exerciseDuration / 3600.0)).roundToInt()
        } else if (exerciseType == "푸쉬업") {
            (4.0 * weight * (exerciseDuration / 3600.0)).roundToInt()
        } else if (exerciseType == "윗몸 일으키기") {
            (4.0 * weight * (exerciseDuration / 3600.0)).roundToInt()
        } else if (exerciseType == "플랭크") {
            (3.0 * weight * (exerciseDuration / 3600.0)).roundToInt()
        } else {
            0
        }

    //전체 화면
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WalkOneGray300)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            // 운동 인증 완료 문구 & 이미지
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = WalkOneGray50)
                    .padding(25.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End // Row의 끝에 배치
                ) {
                    CloseButton(navController)
                }

                Text(
                    text = "운동 인증 완료!",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.work),
                    contentDescription = "Workout Complete Icon",
                    modifier = Modifier.size(120.dp)
                )
            }

            //운동기록
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = WalkOneGray50)
                    .padding(25.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "운동 기록",
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    //운동 기록 표
                    IndividualExerciseRecord(
                        exerciseCount,
                        exerciseDuration,
                        calorie
                    )
                }
                Column {
                    if (settingManager.getRecordingMode()) {
                        CustomButton(
                            text = if (uploadInProgress.value) "업로드 중"
                            else if (uploadResponse == true) "업로드 완료"
                            else "운동 영상 업로드",
                            backgroundColor = WalkOneBlue300,
                            borderColor = WalkOneBlue300,
                            onClick = {
                                uploadInProgress.value = true
                                awsViewModel.getAwsUrlRequest()
                                Log.d("File Uri", "$videoUri")
                            },
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    //확인 버튼
                    CustomButton(
                        text = "확인",
                        onClick = {
                            Log.d("Object Url", "${awsUrl?.objectUrl}")// TODO 운동 결과에 같이 보내기
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
        ConfettiAnimation()
    }
    LaunchedEffect(awsUrl) {
        val file = getFileFromContentUri(videoUri, context)
        awsUrl?.let {
            if (file != null) {
                awsViewModel.uploadVideo(it.preSignedUrl, file)
            }
        }
    }
}

fun getFileFromContentUri(contentUri: Uri?, context: Context): File? {
    if (contentUri == null || contentUri.toString().isEmpty()) {
        return null
    }
    return try {
        val file = File(context.filesDir, "temp_video.mp4")
        context.contentResolver.openInputStream(contentUri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        file
    } catch (e: FileNotFoundException) {
        Log.e("File Error", "Content provider not found for URI: $contentUri", e)
        null
    }
}

