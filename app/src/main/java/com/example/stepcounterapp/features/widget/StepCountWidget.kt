import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Column
import androidx.glance.layout.padding
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextStyle

object StepCountWidget : GlanceAppWidget() {

    override val stateDefinition = PreferencesGlanceStateDefinition
    val TODAY_STEPS = intPreferencesKey("today_steps")

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        val prefs = currentState<Preferences>()
        val steps = prefs[TODAY_STEPS] ?: 0

        Column(
            GlanceModifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text("STEPS", style = TextStyle(fontSize = 14.sp))
            Text("$steps", style = TextStyle(fontSize = 26.sp))
        }
    }
}
