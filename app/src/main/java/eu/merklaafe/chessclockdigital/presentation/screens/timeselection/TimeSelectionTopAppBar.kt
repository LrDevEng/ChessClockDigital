package eu.merklaafe.chessclockdigital.presentation.screens.timeselection

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelectionTopAppBar(
    showDeleteOption: Boolean,
    onBackClick: () -> Unit,
    onDeleteItemsClick: () -> Unit,
    onAddItemClick: () -> Unit,
    onCloseClick: () -> Unit,
    textColor: Color = MaterialTheme.colorScheme.primary
) {
    var isExpanded by remember { mutableStateOf(false) }
    val dividerColor = MaterialTheme.colorScheme.outline

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Time Selection",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = textColor
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "ArrowBack",
                    tint = textColor
                )
            }
        },
        actions = {
            if (showDeleteOption) {
                IconButton(onClick = onCloseClick) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = textColor
                    )
                }
            } else {
                IconButton(onClick = { isExpanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = textColor
                    )
                }
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Column {
                                Text(
                                    text = "Add Time Control",
                                    color = textColor
                                )
                                HorizontalDivider(
                                    thickness = 1.dp,
                                    color = dividerColor
                                )
                            }
                        },
                        onClick = {
                            onAddItemClick()
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Column {
                                Text(
                                    text = "Delete Time Control",
                                    color = textColor
                                )
                                HorizontalDivider(
                                    thickness = 1.dp,
                                    color = dividerColor
                                )
                            }
                        },
                        onClick = {
                            onDeleteItemsClick()
                            isExpanded = false
                        }
                    )
                }
            }
        }
    )
}