package eu.merklaafe.chessclockdigital.presentation.generic

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import eu.merklaafe.chessclockdigital.model.IncrementType

@Composable
fun IncrementTypeDropdownMenu (
    incrementType: IncrementType = IncrementType.None,
    itemSelected: (IncrementType) -> Unit,
    color: Color = MaterialTheme.colorScheme.primary
) {
    var isExpanded by remember { mutableStateOf(false) }
    val dividerColor = MaterialTheme.colorScheme.outline

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.primary)
                .padding(5.dp)
                .clickable {
                    isExpanded = true
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = incrementType.toString(),
                color = color
            )
            if (isExpanded) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Arrow Up",
                    tint = color
                )
            } else {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Arrow Down",
                    tint = color
                )
            }
        }


        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Column {
                        Text(
                            text = IncrementType.None.toString(),
                            color = color
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = dividerColor
                        )
                    }
                },
                onClick = {
                    itemSelected(IncrementType.None)
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Column {
                        Text(
                            text = IncrementType.Fischer.toString(),
                            color = color
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = dividerColor
                        )
                    }
                },
                onClick = {
                    itemSelected(IncrementType.Fischer)
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Column {
                        Text(
                            text = IncrementType.Bronstein.toString(),
                            color = color
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = dividerColor
                        )
                    }
                },
                onClick = {
                    itemSelected(IncrementType.Bronstein)
                    isExpanded = false
                }
            )
        }
    }
}


