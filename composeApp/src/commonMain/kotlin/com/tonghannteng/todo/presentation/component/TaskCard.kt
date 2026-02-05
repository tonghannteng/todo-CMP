package com.tonghannteng.todo.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tonghannteng.todo.domain.Priority
import com.tonghannteng.todo.domain.TodoTask
import com.tonghannteng.todo.util.Alpha

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    task: TodoTask,
    onClick: (String) -> Unit,
    onComplete:() -> Unit,
    onDelete:() -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick(task.id) },
        colors = CardDefaults.cardColors(
            containerColor = lerp(
                start = MaterialTheme.colorScheme.surfaceContainer,
                stop = MaterialTheme.colorScheme.errorContainer,
                fraction = 1f
            )
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(all = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.alpha(if (task.isCompleted) Alpha.HALF else Alpha.FULL),
                    text = task.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (task.description.isNotEmpty()) {
                    Text(
                        modifier = Modifier.alpha(if (task.isCompleted) Alpha.HALF else Alpha.FULL),
                        text = task.description,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            PriorityChip(
                priority = task.priority,
                isCompleted = task.isCompleted
            )
        }
    }
}

@Preview
@Composable
private fun TaskCardPreview() {
    TaskCard(
        task = TodoTask(
            title = "Example title",
            description = "Some random text.",
            isCompleted = false,
            priority = Priority.LOW
        ),
        onClick = {},
        onComplete = {},
        onDelete = {}
    )
}
