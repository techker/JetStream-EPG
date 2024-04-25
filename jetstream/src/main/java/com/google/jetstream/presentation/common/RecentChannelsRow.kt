package com.google.jetstream.presentation.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.foundation.PivotOffsets
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.material3.Border
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.CardLayoutDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.StandardCardLayout
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.jetstream.data.models.RecentChannels
import com.google.jetstream.data.repositories.MockData
import com.google.jetstream.presentation.screens.dashboard.rememberChildPadding
import com.google.jetstream.presentation.theme.JetStreamBorderWidth

@OptIn(ExperimentalTvMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun RecentChannelsRow (modifier: Modifier = Modifier,
                       itemDirection: ItemDirection = ItemDirection.Vertical,
                       startPadding: Dp = rememberChildPadding().start,
                       endPadding: Dp = rememberChildPadding().end,
                       title: String? = null,
                       titleStyle: TextStyle = MaterialTheme.typography.headlineLarge.copy(
                           fontWeight = FontWeight.Medium,
                           fontSize = 30.sp
                       ),
                       showItemTitle: Boolean = true,
                       showIndexOverImage: Boolean = false,
                       focusedItemIndex: (index: Int) -> Unit = {},
                       channels: MutableList<RecentChannels>,
                       onChannelClick: (channel: RecentChannels) -> Unit = {}

                       ) {

    Column(
        modifier = modifier.focusGroup()
    ) {
        title?.let { nnTitle ->
            Text(
                text = nnTitle,
                style = titleStyle,
                modifier = Modifier
                    .alpha(1f)
                    .padding(start = startPadding)
                    .padding(vertical = 16.dp)
            )
        }

        AnimatedContent(
            targetState = channels,
            label = "",
        ) { movieState ->
            TvLazyRow(
                modifier = Modifier.focusRestorer(),
                pivotOffsets = PivotOffsets(parentFraction = 0.07f),
                contentPadding = PaddingValues(start = startPadding, end = endPadding),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                movieState.forEachIndexed { index, channel ->
                    item {
                        key(channel.channelID) {
                            RecentChannelsRowItem(
                                modifier = Modifier
                                    .weight(1f),
                                focusedItemIndex = focusedItemIndex,
                                index = index,
                                itemDirection = itemDirection,
                                onRecentChannelClick = onChannelClick,
                                channel = channel,
                                showItemTitle = showItemTitle,
                                showIndexOverImage = showIndexOverImage
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
@OptIn(ExperimentalComposeUiApi::class, ExperimentalTvMaterial3Api::class)
private fun RecentChannelsRowItem(
    modifier: Modifier = Modifier,
    focusedItemIndex: (index: Int) -> Unit,
    index: Int,
    itemDirection: ItemDirection,
    onRecentChannelClick: (movie: RecentChannels) -> Unit,
    channel: RecentChannels,
    showItemTitle: Boolean,
    showIndexOverImage: Boolean
) {
    var isItemFocused by remember { mutableStateOf(false) }

    StandardCardLayout(
        modifier = Modifier
            .onFocusChanged {
                isItemFocused = it.isFocused
                if (isItemFocused) {
                    focusedItemIndex(index)
                }
            }
            .focusProperties {
                if (index == 0) {
                    left = FocusRequester.Cancel
                }
            }
            .then(modifier),
        title = {
            RecentChannelRowItemText(
                showItemTitle = showItemTitle,
                isItemFocused = isItemFocused,
                channel = channel
            )
        },
        imageCard = {
            CardLayoutDefaults.ImageCard(
                onClick = { onRecentChannelClick(channel) },
                shape = CardDefaults.shape(CircleShape),
                border = CardDefaults.border(
                    focusedBorder = Border(
                        border = BorderStroke(
                            width = JetStreamBorderWidth,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = CircleShape
                    )
                ),
                scale = CardDefaults.scale(focusedScale = 1f),
                interactionSource = it
            ) {
                RecentChannelRowItemImage(
                    modifier = Modifier.aspectRatio(itemDirection.aspectRatio),
                    showIndexOverImage = showIndexOverImage,
                    channel = channel,
                )
            }
        },
    )
}
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun RecentChannelRowItemImage(
    showIndexOverImage: Boolean,
    channel: RecentChannels,
    modifier: Modifier = Modifier,
) {
    Box(contentAlignment = Alignment.CenterStart) {
        AsyncImage(
            modifier = modifier
                .height(100.dp)
                .width(100.dp)
                .drawWithContent {
                    drawContent()
                    if (showIndexOverImage) {
                        drawRect(
                            color = Color.Black.copy(
                                alpha = 0.1f
                            )
                        )
                    }
                },
            model = ImageRequest.Builder(LocalContext.current)
                .crossfade(true)
                .data(channel.channelIconSrc)
                .build(),
            contentDescription = "movie poster of ${channel.channelName}",
            contentScale = ContentScale.Fit
        )
    }
}
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun RecentChannelRowItemText(
    showItemTitle: Boolean,
    isItemFocused: Boolean,
    channel: RecentChannels,
    modifier: Modifier = Modifier
) {
    if (showItemTitle) {
        val movieNameAlpha by animateFloatAsState(
            targetValue = if (isItemFocused) 1f else 0f,
            label = "",
        )
        Text(
            text = channel.channelName,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            textAlign = TextAlign.Center,
            modifier = modifier
                .alpha(movieNameAlpha)
                .fillMaxWidth()
                .padding(top = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
@Composable
@Preview(device = Devices.TV_1080p)
fun RecentChannelsRowPreview() {
    RecentChannelsRow(modifier = Modifier.padding(top = 16.dp),
        channels = MockData().createRecentChannels(),
        title = "Recent Channels",
        onChannelClick = {

        })
}