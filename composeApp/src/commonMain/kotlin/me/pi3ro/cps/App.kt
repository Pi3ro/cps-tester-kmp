package me.pi3ro.cps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import java.text.DecimalFormat

@Composable
@Preview
fun App()
{
    MaterialTheme {
        var cpsCount by remember { mutableStateOf(0) }
        var score by remember { mutableStateOf(0) }
        var isRunning by remember { mutableStateOf(false) }
        var initialTimer by remember { mutableStateOf(10000L) }
        var timer by remember { mutableStateOf(0L) } // Timer starts in 0 to increase until initial timer
        var showDialog by remember { mutableStateOf(false) }

        val timerOptions = listOf(1000L, 2000L, 5000L, 10000L,
            15000L, 30000L, 60000L)

        LaunchedEffect(isRunning, timer)
        {
            if (isRunning && timer < initialTimer)
            {
                while (timer < initialTimer)
                {
                    delay(50L)
                    timer += 50L
                }
                showDialog = true
                isRunning = false
            }
        }

        if (showDialog)
        {
            val initial = (initialTimer / 1000)

            AlertDialog(
                onDismissRequest = {

                },
                title = { Text(text = "Information") },
                text = {
                    Column {
                        val fCps = if (timer > 0) DecimalFormat("0.00").format(cpsCount / (timer / 1000.0)) else "0.00"

                        Text("CPS: $fCps")
                        Text("Score: $score")
                        Text("Seconds: $initial")
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false
                            cpsCount = 0
                            score = 0
                            timer = 0L
                        }
                    ) {
                        Text("Accept")
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = {
                            showDialog = false
                            cpsCount = 0
                            score = 0
                            timer = 0L
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                val fTimer = DecimalFormat("0.00").format(timer / 1000.0)
                val fCps = if (timer > 0) DecimalFormat("0.00").format(cpsCount / (timer / 1000.0)) else "0.00"

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.Gray, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(text = "Timer: $fTimer", fontSize = 24.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.Gray, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(text = "CPS: $fCps", fontSize = 24.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.Gray, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(text = "Score: $score", fontSize = 24.sp, color = Color.White)
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                timerOptions.forEach { time ->
                    Button(
                        onClick = {
                            timer = 0L
                            initialTimer = time
                        },
                        modifier = Modifier.size(200.dp, 50.dp),
                        enabled = !isRunning
                    ) {
                        Text("${time / 1000} SECOND TEST")
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            Button(
                onClick = {
                    cpsCount++
                    score++
                    isRunning = true
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .size(1200.dp, 360.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(if (isRunning) "Click me!" else "Click here to start playing")
            }
        }
    }
}

