import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pjhn.stepcounter.ui.theme.colors

@Composable
fun AchievementIndicator(
    progress: Float,
    size: Dp = 64.dp,
    stroke: Dp = 10.dp,
    label: String? = null
) {
    Box(
        modifier = Modifier
            .size(size)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.large
            )
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            progress = progress.coerceIn(0f, 1f),
            strokeWidth = stroke,
            color = MaterialTheme.colors.indicator,
            trackColor = MaterialTheme.colors.surface
        )

        if (label != null) {
            Text(text = label, style = MaterialTheme.typography.titleSmall)
        }
    }
}
