package com.carlodips.simpleloginregistration.ui.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.carlodips.simpleloginregistration.R

@Composable
fun CardUI(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(dimensionResource(id = R.dimen.card_corner_rounded)),
    onClick: () -> Unit = {},
    isEnabled: Boolean = false,
    elevation: Dp = 4.dp,
    colors: CardColors = CardDefaults.elevatedCardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
    ),
    content: @Composable ColumnScope.() -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .clickable(
                onClick = { onClick.invoke() },
                enabled = isEnabled
            ),
        shape = shape,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation),
        colors = colors,
        content = content
    )
}