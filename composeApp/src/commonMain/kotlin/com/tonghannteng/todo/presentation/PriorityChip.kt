package com.tonghannteng.todo.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tonghannteng.todo.domain.Priority
import com.tonghannteng.todo.presentation.PriorityColors.getColor

@Composable
fun PriorityChip(
    priority: Priority,
    size: PriorityChipSize = PriorityChipSize.Medium,
    isCompleted: Boolean = false,
    isSelected: Boolean = false,
    onSelect: ((Priority) -> Unit)? = null,
) {
    val padding = size.toPadding()
    val shouldUseOutline = if (onSelect != null) !isSelected else isCompleted
    val chipColor = if (shouldUseOutline) MaterialTheme.colorScheme.outline else priority.getColor()

    Text(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .then(
                if (onSelect != null) Modifier.clickable { onSelect(priority) }
                else Modifier
            )
            .background(chipColor)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
                color = chipColor
            )
            .padding(
                horizontal = padding.horizontal,
                vertical = padding.vertical
            ),
        text = priority.name,
        style = TextStyle(
            fontWeight = FontWeight.Medium
        ),
        color = Color.White
    )
}

/**
 * Defines padding options for PriorityChip component
 */
data class PriorityChipPadding(
    val horizontal: Dp,
    val vertical: Dp,
) {
    companion object {
        val Small = PriorityChipPadding(horizontal = 6.dp, vertical = 2.dp)
        val Medium = PriorityChipPadding(horizontal = 8.dp, vertical = 4.dp)
        val Large = PriorityChipPadding(horizontal = 12.dp, vertical = 6.dp)
    }
}

enum class PriorityChipSize {
    Small,
    Medium,
    Large;

    fun toPadding(): PriorityChipPadding = when (this) {
        Small -> PriorityChipPadding.Small
        Medium -> PriorityChipPadding.Medium
        Large -> PriorityChipPadding.Large
    }
}

object PriorityColors {
    // Light theme colors
    private val lightNone = Color(0xFF000000)
    private val lightLow = Color(0xFF2E7D32)
    private val lightMedium = Color(0xFFEF6C00)
    private val lightHigh = Color(0xFFC62828)

    // Dark theme colors
    private val darkNone = Color(0xFFFFFFFF)
    private val darkLow = Color(0xFF4CAF50)
    private val darkMedium = Color(0xFFFF9800)
    private val darkHigh = Color(0xFFE53935)

    @Composable
    fun Priority.getColor(): Color {
        val isDark = isSystemInDarkTheme()
        return when (this) {
            Priority.NONE -> if (isDark) darkNone else lightNone
            Priority.LOW -> if (isDark) darkLow else lightLow
            Priority.MEDIUM -> if (isDark) darkMedium else lightMedium
            Priority.HIGH -> if (isDark) darkHigh else lightHigh
        }
    }
}

@Preview
@Composable
private fun PriorityChipPreview() {
    PriorityChip(
        priority = Priority.HIGH,
        isCompleted = true
    )
}
