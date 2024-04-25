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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import androidx.tv.material3.surfaceColorAtElevation
import com.google.jetstream.R

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun AccountsSwapSelectionItem(
    modifier: Modifier = Modifier,
    key: Any?,
    accountsSwapSectionData: AccountsSwapSectionData,
) {
    key(key) {
        Surface(
            onClick = accountsSwapSectionData.onClick,
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
                .aspectRatio(2f),
            colors = ClickableSurfaceDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
            ),
            shape = ClickableSurfaceDefaults.shape(shape = MaterialTheme.shapes.extraSmall),
            scale = ClickableSurfaceDefaults.scale(focusedScale = 1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.default_card),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Adjust the scaling of the image
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = accountsSwapSectionData.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 15.sp
                    ),
                    color = Color.White,
                )
                Spacer(modifier = Modifier.padding(vertical = 2.dp))
                accountsSwapSectionData.value?.let { nnValue ->
                    Text(
                        text = nnValue,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        color = Color.White,
                        modifier = Modifier.alpha(0.75f)
                    )
                }
            }
        }
    }
}

@Composable
@Preview()
fun AccountsSwapSelectionItemPreview() {
    AccountsSwapSelectionItem(modifier = Modifier,1, AccountsSwapSectionData(
        title = "Grand Father",
        value = "Last Seen 30 days ago"
    ))
}