package bn.masoud.jetpackcomposesample

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import bn.masoud.jetpackcomposesample.ui.theme.JetpackComposeSampleTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import bn.masoud.jetpackcomposesample.data.Message
import bn.masoud.jetpackcomposesample.data.SampleData
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeSampleTheme {
                Conversation(SampleData.conversationSample)
            }
        }
    }

    @Composable
    fun MessageCard(msg: Message) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape))

            Spacer(modifier = Modifier.width(8.dp))

            // We keep track if the message is expanded or not in this
            // variable
            var isExpanded by remember { mutableStateOf(false) }

            // We toggle the isExpanded variable when we click on this Column
            Column() {
             Text(text = msg.author,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2)

                Spacer(modifier = Modifier.height(4.dp))

                // surfaceColor will be updated gradually from one color to the other
                val surfaceColor: Color by animateColorAsState(
                    if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
                )

                Surface(shape = MaterialTheme.shapes.medium,
                    elevation = 1.dp,
                    // surfaceColor color will be changing gradually from primary to surface
                    color = surfaceColor,
                    // animateContentSize will change the Surface size gradually
                    modifier = Modifier
                        .animateContentSize()
                        .padding(1.dp)
                        .clickable { isExpanded = !isExpanded }
                ) {
                    Text(text = msg.body,
                        style = MaterialTheme.typography.body2,
                        // If the message is expanded, we display all its content
                        // otherwise we only display the first line
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        modifier = Modifier.padding(all = 4.dp))
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

    @Preview
    @Composable
    fun PreviewConversation() {
        JetpackComposeSampleTheme {
            Conversation(SampleData.conversationSample)
        }
    }

    @Preview(
        name = "Light Mode"
    )
    @Preview(
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true,
        name = "Dark Mode"
    )
    @Composable
    fun PreviewMessageCard() {
        JetpackComposeSampleTheme {
            MessageCard(msg = Message("Mina", "Hey Mona!"))
        }
    }
}