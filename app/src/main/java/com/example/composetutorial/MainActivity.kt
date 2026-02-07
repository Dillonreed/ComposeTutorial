package com.example.composetutorial

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

const val deviceId = "id:Pixel 10"

@Preview(
    name = "Light Mode",
    device = deviceId,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode",
    device = deviceId,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class PreviewBothThemes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                Surface(Modifier.fillMaxSize()) {
                    Conversation(SampleData.conversationSample)
                }
            }
        }
    }
}

data class Message(
    val author: String,
    val body: String
)

@Composable
fun MessageCard(message: Message) {
    // Add padding around the message
    Row(Modifier.padding(8.dp)) {
        Image(
            painter = painterResource(R.drawable.outline_android_24),
            contentDescription = "Android Logo",
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
                .border(
                    width = 1.5.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        )

        // Add horizontal space between the image and the column
        Spacer(Modifier.width(8.dp))

        // Keep track of if the message is expanded
        var isExpanded by remember { mutableStateOf(false) }

        // Toggle the isExpanded variable when column is clicked
        Column(Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = message.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            // Add a vertical space between the author and message texts
            Spacer(Modifier.height(4.dp))

            // Message colour is updated gradually from one colour to the other based on if the
            // column is expanded

            val surfaceColor by animateColorAsState(
                if (!isExpanded) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
            )

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 15.dp,
                color = surfaceColor,
                // animateContentSize will change the surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = message.body,
                    modifier = Modifier.padding(4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@PreviewBothThemes
@Composable
fun PreviewMessageCard() {
    ComposeTutorialTheme {
        Surface {
            MessageCard(
                Message(
                    author = "Android",
                    body = "Jetpack Compose"
                )
            )
        }
    }
}

@PreviewBothThemes
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
        Surface(Modifier.fillMaxSize()) {
            Conversation(SampleData.conversationSample)
        }
    }
}