/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.jetstream.presentation.screens.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import com.google.jetstream.presentation.screens.dashboard.rememberChildPadding

@Immutable
data class AccountsSwapSectionData(
    val title: String,
    val value: String? = null,
    val onClick: () -> Unit = {}
)

@Composable
fun AccountsSwapProfile() {
    val childPadding = rememberChildPadding()
    var showDeleteDialog by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val accountsSectionListItems = remember {
        listOf(
            AccountsSwapSectionData(
                title = "Me",
                value = "Last Seen 2 days ago"
            ),
            AccountsSwapSectionData(
                title = "MOM",
                value = "Last Seen 4 days ago"
            ),
            AccountsSwapSectionData(
                title = "DAD",
                value = "Last Seen 1 day ago"
            ),
            AccountsSwapSectionData(
                title = "Kid",
                value = "Last Seen 12 days ago"
            ),
            AccountsSwapSectionData(
                title = "Grand Mother",
                value = "Last Seen 20 days ago"
            ),
            AccountsSwapSectionData(
                title = "Grand Father",
                value = "Last Seen 30 days ago"
            )
        )
    }

    TvLazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = childPadding.start),
        columns = TvGridCells.Fixed(2),
        content = {
            items(accountsSectionListItems.size) { index ->
                AccountsSwapSelectionItem(
                    modifier = Modifier.focusRequester(focusRequester),
                    key = index,
                    accountsSwapSectionData = accountsSectionListItems[index]
                )
            }
        }
    )

    AccountsSectionDeleteDialog(
        showDialog = showDeleteDialog,
        onDismissRequest = { showDeleteDialog = false },
        modifier = Modifier.width(428.dp)
    )
}
