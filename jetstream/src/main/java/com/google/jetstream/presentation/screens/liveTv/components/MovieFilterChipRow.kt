package com.google.jetstream.presentation.screens.liveTv.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.jetstream.data.models.CategoriesItems
import com.google.jetstream.data.util.createInitialFocusRestorerModifiers


@Composable
fun MovieFilterChipRow(
    filterList: MutableList<CategoriesItems>,
    onSelectedFilterListUpdated: (CategoriesItems) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRestorerModifiers = createInitialFocusRestorerModifiers()

    LazyRow (
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .then(focusRestorerModifiers.parentModifier),
    ) {
        itemsIndexed(filterList) { index, item ->
                MovieFilterChip(
                    label = item.categoryName,
                    isChecked = false,
                    onCheckedChange = {
                        onSelectedFilterListUpdated(item)
                    },
                    modifier = if (index == 0) {
                        focusRestorerModifiers.childModifier
                    } else {
                        Modifier
                    }
                )
            }
    }
}