package com.example.lovecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

private val LovePink = Color(0xFFE75480)
private val SoftPink = Color(0xFFFFE5ED)
private val DeepPink = Color(0xFFB51E55)

private enum class Relationship {
    HUSBAND_WIFE,
    LOVERS
}

private enum class Screen {
    INPUT,
    CALCULATING,
    RESULT
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = LovePink,
                    onPrimary = Color.White,
                    background = Color(0xFFFFF7FA),
                    surface = Color.White
                )
            ) {
                LoveCalculatorApp()
            }
        }
    }
}

@Composable
fun LoveCalculatorApp() {
    var relationship by remember {
        mutableStateOf(Relationship.HUSBAND_WIFE)
    }

    var firstName by remember { mutableStateOf("") }
    var secondName by remember { mutableStateOf("") }

    var screen by remember { mutableStateOf(Screen.INPUT) }
    var percentage by remember { mutableIntStateOf(0) }

    val firstLabel = if (
        relationship == Relationship.HUSBAND_WIFE
    ) "Husband's Name" else "Boyfriend's Name"

    val secondLabel = if (
        relationship == Relationship.HUSBAND_WIFE
    ) "Wife's Name" else "Girlfriend's Name"

    // Process the calculation for exactly five seconds.
    LaunchedEffect(screen) {
        if (screen == Screen.CALCULATING) {
            delay(5000L)

            percentage = if (
                relationship == Relationship.HUSBAND_WIFE &&
                firstName.trim().equals("Tanzim", ignoreCase = true) &&
                secondName.trim().equals("Maria", ignoreCase = true)
            ) {
                100
            } else {
                Random.nextInt(1, 101)
            }

            screen = Screen.RESULT
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF7FA))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "💗",
                fontSize = 58.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Love Calculator",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = DeepPink
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Discover your love percentage!",
                color = Color.Gray,
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )

            Spacer(Modifier.height(30.dp))

            when (screen) {
                Screen.INPUT -> {
                    InputScreen(
                        relationship = relationship,
                        onRelationshipChange = {
                            relationship = it
                        },
                        firstName = firstName,
                        onFirstNameChange = {
                            firstName = it
                        },
                        secondName = secondName,
                        onSecondNameChange = {
                            secondName = it
                        },
                        firstLabel = firstLabel,
                        secondLabel = secondLabel,
                        onFindOut = {
                            screen = Screen.CALCULATING
                        }
                    )
                }

                Screen.CALCULATING -> {
                    CalculatingScreen(
                        firstName = firstName.trim(),
                        secondName = secondName.trim()
                    )
                }

                Screen.RESULT -> {
                    ResultScreen(
                        firstName = firstName.trim(),
                        secondName = secondName.trim(),
                        percentage = percentage,
                        onTryAgain = {
                            firstName = ""
                            secondName = ""
                            relationship = Relationship.HUSBAND_WIFE
                            percentage = 0
                            screen = Screen.INPUT
                        }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Made with ❤️",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun InputScreen(
    relationship: Relationship,
    onRelationshipChange: (Relationship) -> Unit,
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    secondName: String,
    onSecondNameChange: (String) -> Unit,
    firstLabel: String,
    secondLabel: String,
    onFindOut: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Step 1: Choose Relationship",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )

        RelationshipOption(
            title = "Husband & Wife",
            emoji = "💑",
            selected = relationship == Relationship.HUSBAND_WIFE,
            onClick = {
                onRelationshipChange(Relationship.HUSBAND_WIFE)
                onFirstNameChange("")
                onSecondNameChange("")
            }
        )

        RelationshipOption(
            title = "Boyfriend & Girlfriend",
            emoji = "💘",
            selected = relationship == Relationship.LOVERS,
            onClick = {
                onRelationshipChange(Relationship.LOVERS)
                onFirstNameChange("")
                onSecondNameChange("")
            }
        )

        HorizontalDivider(color = SoftPink)

        Text(
            text = "Step 2: Enter Your Names",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            label = { Text(firstLabel) },
            placeholder = { Text("Enter name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )

        OutlinedTextField(
            value = secondName,
            onValueChange = onSecondNameChange,
            label = { Text(secondLabel) },
            placeholder = { Text("Enter name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )

        Button(
            onClick = onFindOut,
            enabled = firstName.isNotBlank() &&
                    secondName.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "💗  Find Out",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun RelationshipOption(
    title: String,
    emoji: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = if (selected) 2.dp else 1.dp,
            color = if (selected) LovePink else SoftPink
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                SoftPink
            } else {
                Color.White
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selected,
                onClick = onClick
            )

            Text(
                text = emoji,
                fontSize = 25.sp
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun CalculatingScreen(
    firstName: String,
    secondName: String
) {
    val transition = rememberInfiniteTransition(
        label = "heartAnimation"
    )

    val heartScale by transition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heartScale"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Step 3: Calculating...",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "💗",
            fontSize = 82.sp,
            modifier = Modifier.scale(heartScale)
        )

        CircularProgressIndicator(
            color = LovePink,
            strokeWidth = 5.dp,
            modifier = Modifier.size(48.dp)
        )

        Text(
            text = "Calculating Love Percentage",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = DeepPink,
            textAlign = TextAlign.Center
        )

        Text(
            text = "$firstName ❤️ $secondName",
            fontSize = 17.sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Please wait... Love is in the air!",
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ResultScreen(
    firstName: String,
    secondName: String,
    percentage: Int,
    onTryAgain: () -> Unit
) {
    val transition = rememberInfiniteTransition(
        label = "resultHeart"
    )

    val heartScale by transition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "resultHeartScale"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "Your Love Result",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .size(190.dp)
                .background(SoftPink, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "❤️",
                    fontSize = 45.sp,
                    modifier = Modifier.scale(heartScale)
                )

                Text(
                    text = "$percentage%",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DeepPink
                )
            }
        }

        Text(
            text = "$firstName ❤️ $secondName",
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Text(
            text = when {
                percentage >= 80 -> "A beautiful love connection! 💕"
                percentage >= 50 -> "Love is in the air! 💖"
                else -> "Every love story is unique! 💗"
            },
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = DeepPink
        )

        Button(
            onClick = onTryAgain,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "🔄  Try Again",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}