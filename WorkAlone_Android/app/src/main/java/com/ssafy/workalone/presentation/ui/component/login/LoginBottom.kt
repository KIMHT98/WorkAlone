package com.ssafy.workalone.presentation.ui.component.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun LoginBottomView(navController: NavController) {
    var showNameDialog by remember { mutableStateOf(false) }
    var showWeightDialog by remember { mutableStateOf(false) }

    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 20.dp, end = 20.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            Color.Black
        )
    ) {
        Icon(
            Icons.Default.Info,
            contentDescription = "Login Logo"
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = "Work Alone 시작하기")
    }

    Spacer(modifier = Modifier.padding(5.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            LoginInfoText("위 버튼을 누르면 ", TextDecoration.None)
            LoginInfoText("서비스 이용약관", TextDecoration.Underline)
            LoginInfoText("과", TextDecoration.None)
        }
        Row {
            LoginInfoText("개인정보처리방침", TextDecoration.Underline)
            LoginInfoText("에 ", TextDecoration.None)
            LoginInfoText("동의하시게 됩니다.", TextDecoration.None)
        }
    }
}

