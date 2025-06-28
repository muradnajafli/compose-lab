package com.muradnajafli.composelab.spin_wheel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.muradnajafli.composelab.R

@Composable
fun SpinWheelExampleUsage(
    modifier: Modifier = Modifier
) {
    var isSpinning by rememberSaveable { mutableStateOf(false) }
    var isResultVisible by rememberSaveable { mutableStateOf(false) }
    var result by rememberSaveable { mutableStateOf<Pair<Color, String>?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA)),
        contentAlignment = Alignment.Center
    ) {
        SpinWheel(
            sections = mapOf(
                Color(0xFFe8515d) to "absolutely nothing",
                Color(0xFFe360ef) to "nope",
                Color(0xFF785bf3) to "nothing",
                Color(0xFF4981f5) to "nothing",
                Color(0xFF97d66b) to "haha no",
                Color(0xFF71c3ea) to "null :d",
                Color(0xFF97d66b) to "no reward",
                Color(0xFFed9f55) to "still nothing",
            ),
            iconVector = ImageVector.vectorResource(R.drawable.ic_gift),
            spinTimes = 17,
            centerBrush = Brush.sweepGradient(
                colors = listOf(
                    Color(0xFF4f49ef),
                    Color(0xFF8925f5),
                    Color(0xFF4f49ef),
                    Color(0xFF8925f5),
                    Color(0xFF4f49ef),
                    Color(0xFF8925f5),
                    Color(0xFF4f49ef)
                ),
            ),
            borderBrush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFF896BF4),
                    Color(0xFFDF5FF3),
                    Color(0xFF7EE5F0),
                    Color(0xFF896BF4)
                ),
                start = Offset(0f, 0f),
                end = Offset(600f, 600f)
            ),
            isSpinning = isSpinning,
            onResult = {
                result = it
                isResultVisible = true
                isSpinning = false
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .background(
                    brush = if (!isSpinning) {
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF8925f5),
                                Color(0xFF4f49ef)
                            )
                        )
                    } else {
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF8925f5).copy(alpha = 0.4f),
                                Color(0xFF4f49ef).copy(alpha = 0.4f)
                            )
                        )
                    },
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable(
                    onClick = { isSpinning = true },
                    enabled = !isSpinning,
                    indication = ripple(color = Color.White.copy(0.2f)),
                    interactionSource = remember { MutableInteractionSource() }
                )
                .padding(vertical = 16.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isSpinning) "Spinning..." else "Spin!",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        RewardDialog(
            isVisible = isResultVisible,
            onDismiss = { isResultVisible = false },
            onClaim = { },
            reward = result?.second ?: "???",
            backgroundColor = result?.first ?: Color.White
        )

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onClaim: () -> Unit,
    reward: String = "???",
    backgroundColor: Color = Color.White
) {
    if (isVisible) {
        BasicAlertDialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .fillMaxWidth()
                        .padding(24.dp)
                        .padding(top = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Congratulations! 🎉",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFE8F5E8))
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Your reward:",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = reward,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32),
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            onClaim()
                            onDismiss()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = backgroundColor,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Claim Now",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .background(
                            color = backgroundColor,
                            shape = CircleShape
                        )
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_gift),
                        contentDescription = "Reward",
                        modifier = Modifier.size(40.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}