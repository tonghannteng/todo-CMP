package com.tonghannteng.todo.presentation.screen.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tonghannteng.todo.domain.Priority
import com.tonghannteng.todo.presentation.component.InfoCard
import com.tonghannteng.todo.presentation.component.LoadingCard
import com.tonghannteng.todo.presentation.component.PriorityColors.getColor
import com.tonghannteng.todo.presentation.component.TaskCard
import com.tonghannteng.todo.util.DisplayResult
import com.tonghannteng.todo.util.Resource
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToTask: (String?) -> Unit
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val allTasks by viewModel.allTasks.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val priorityFilter by viewModel.priorityFilter.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var searchBarOpened by remember { mutableStateOf(false) }
    var dropDownMenuOpened by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    AnimatedContent(
                        targetState = searchBarOpened
                    ) { isOpened ->
                        if (isOpened) {
                            TextField(
                                modifier = Modifier.height(56.dp),
                                value = searchQuery,
                                onValueChange = { viewModel.updateSearchQuery(it) },
                                placeholder = { Text(text = "Search...") },
                                shape = RoundedCornerShape(size = 99.dp),
                                textStyle = TextStyle(
                                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                                ),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                )
                            )
                        } else {
                            Text(text = "To Do")
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(Resource.Icon.HAMBURGER_MENU),
                            contentDescription = "Hamburger menu icon"
                        )
                    }
                },
                actions = {
                    AnimatedContent(
                        targetState = searchBarOpened
                    ) { isOpened ->
                        if (isOpened) {
                            IconButton(onClick = {
                                searchBarOpened = false
                                viewModel.updateSearchQuery(query = "")
                            }) {
                                Icon(
                                    painter = painterResource(Resource.Icon.CLOSE),
                                    contentDescription = "Close icon"
                                )
                            }
                        } else {
                            Row {
                                Box {
                                    Box (contentAlignment = Alignment.TopEnd) {
                                        IconButton(onClick = { dropDownMenuOpened = true }) {
                                            Icon(
                                                painter = painterResource(Resource.Icon.SORT),
                                                contentDescription = "Sort icon"
                                            )
                                        }
                                    }
                                    if (priorityFilter != Priority.NONE) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .clip(CircleShape)
                                                .background(color = MaterialTheme.colorScheme.error)
                                        )
                                    }

                                    DropdownMenu(
                                        expanded = dropDownMenuOpened,
                                        shape = RoundedCornerShape(size = 12.dp),
                                        onDismissRequest = { dropDownMenuOpened = false },
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                                    ) {
                                        Priority.entries.forEach { priority ->
                                            DropdownMenuItem(
                                                modifier = Modifier.background(
                                                    if (priorityFilter == priority && priority != Priority.NONE) MaterialTheme.colorScheme.outlineVariant
                                                            else MaterialTheme.colorScheme.surfaceVariant
                                                ),
                                                text = { Text(text = if (priority.name == "None") "All" else priority.name) },
                                                leadingIcon = {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(16.dp)
                                                            .clip(CircleShape)
                                                            .background(color = priority.getColor())
                                                    )
                                                },
                                                onClick = {
                                                    dropDownMenuOpened = false
                                                    viewModel.updatePriorityFilter(priority = priority)
                                                }
                                            )
                                        }
                                    }
                                }

                                IconButton(onClick = { searchBarOpened = true }) {
                                    Icon(
                                        painter = painterResource(Resource.Icon.SEARCH),
                                        contentDescription = "Search icon"
                                    )
                                }
                            }
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navigateToTask(null) },
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp)
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(Resource.Icon.ADD),
                    contentDescription = "Plus icon"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "New Task")
            }
        }
    ) { paddingValues ->
        allTasks.DisplayResult(
            modifier = Modifier.padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            ),
            onLoading = { LoadingCard() },
            onSuccess = { tasks ->
                if (tasks.isNotEmpty()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(all = 12.dp)
                    ) {
                        items(
                            items = tasks,
                            key = { it.id }
                        ) {
                            TaskCard(
                                task = it,
                                onClick = navigateToTask,
                                onComplete = {
                                    val isCompleted = !it.isCompleted
                                    val result =
                                        viewModel.markTaskAsCompleted(
                                            task = it.copy(isCompleted = isCompleted)
                                        )

                                    if (result.isSuccess()) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = if (isCompleted) "Task marked as completed"
                                                else "Task marked as not completed"
                                            )
                                        }
                                    }
                                },
                                onDelete = {
                                    val result = viewModel.removeTask(taskId = it.id)

                                    if (result.isSuccess()) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Task removed successfully"
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    }
                } else {
                    InfoCard(message = "Empty tasks.")
                }
            },
            onError = { message ->
                InfoCard(
                    message = message,
                    lightModeIcon = Resource.Image.WARNING_LIGHT,
                    darkModeIcon = Resource.Image.WARNING_DARK
                )
            }
        )
    }
}
