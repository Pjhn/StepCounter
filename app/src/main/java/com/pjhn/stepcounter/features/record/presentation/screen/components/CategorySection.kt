package com.pjhn.stepcounter.features.record.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pjhn.stepcounter.features.record.domain.enums.RecordCategories
import com.pjhn.stepcounter.features.record.domain.enums.RecordCategories.*
import com.pjhn.stepcounter.features.record.presentation.input.IRecordViewModelInput
import com.pjhn.stepcounter.ui.theme.Paddings
import com.pjhn.stepcounter.ui.theme.colors

private val CATEGORY_SECTION_PADDING_HORIZONTAL = Paddings.medium
private val CATEGORY_SECTION_SPACED_BY = Paddings.extra

@Composable
fun CategorySection(
    selectedCategory: State<RecordCategories>,
    input: IRecordViewModelInput
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = CATEGORY_SECTION_PADDING_HORIZONTAL),
        horizontalArrangement = Arrangement.spacedBy(
            CATEGORY_SECTION_SPACED_BY,
            Alignment.CenterHorizontally
        )
    ) {
        entries.forEach { category ->
            CategoryBox(
                category = category,
                isSelected = category == selectedCategory.value,
                onClick = { input.selectCategory(category) })
        }
    }
}

@Composable
fun CategoryBox(
    category: RecordCategories,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable(indication = null, interactionSource = remember {
            MutableInteractionSource()
        }) { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = category.toString(),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            ),
            color = if (isSelected) MaterialTheme.colors.text else MaterialTheme.colors.defaultTextButton
        )
        if (isSelected) {
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .size(width = 20.dp, height = 2.dp)
                    .background(MaterialTheme.colors.brandColor)
            )
        }
    }
}