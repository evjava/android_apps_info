package com.evjava.apps_info.ui.compose.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * source: https://github.com/ivaniskandar/compose-fastscroller
 */
@Composable
fun VerticalFastScroller(
    listState: LazyListState,
    isReversed: Boolean,
    thumbColor: Color = MaterialTheme.colors.background,
    topContentPadding: Dp = Dp.Hairline,
    endContentPadding: Dp = Dp.Hairline,
    content: @Composable () -> Unit,
) {
    SubcomposeLayout { constraints ->
        val contentPlaceable = subcompose("content", content).map { it.measure(constraints) }
        val contentHeight = contentPlaceable.maxByOrNull { it.height }?.height ?: 0
        val contentWidth = contentPlaceable.maxByOrNull { it.width }?.width ?: 0

        val scrollerPlaceable = subcompose("scroller") {
            val layoutInfo = listState.layoutInfo
            val showScroller = layoutInfo.visibleItemsInfo.size < layoutInfo.totalItemsCount
            if (!showScroller) return@subcompose

            val thumbTopPadding = with(LocalDensity.current) { topContentPadding.toPx() }
            var thumbOffsetY by remember(thumbTopPadding) { mutableStateOf(thumbTopPadding) }

            val dragInteractionSource = remember { MutableInteractionSource() }
            val isThumbDragged by dragInteractionSource.collectIsDraggedAsState()
            val scrolled = remember {
                MutableSharedFlow<Unit>(
                    extraBufferCapacity = 1,
                    onBufferOverflow = BufferOverflow.DROP_OLDEST,
                )
            }

            val heightPx = contentHeight.toFloat() - thumbTopPadding - listState.layoutInfo.afterContentPadding
            val thumbHeightPx = with(LocalDensity.current) { ThumbLength.toPx() }
            val trackHeightPx = heightPx - thumbHeightPx

            // When thumb dragged
            LaunchedEffect(thumbOffsetY) {
                if (layoutInfo.totalItemsCount == 0 || !isThumbDragged) return@LaunchedEffect
                val scrollRatio = (thumbOffsetY - thumbTopPadding) / trackHeightPx
                val scrollItem = layoutInfo.totalItemsCount * scrollRatio
                val scrollItemRounded = scrollItem.roundToInt()
                val scrollItemSize = layoutInfo.visibleItemsInfo.find { it.index == scrollItemRounded }?.size ?: 0
                val scrollItemOffset = scrollItemSize * (scrollItem - scrollItemRounded)
                listState.scrollToItem(index = scrollItemRounded, scrollOffset = scrollItemOffset.roundToInt())
                scrolled.tryEmit(Unit)
            }

            // When list scrolled
            LaunchedEffect(listState.firstVisibleItemScrollOffset) {
                if (listState.layoutInfo.totalItemsCount == 0 || isThumbDragged) return@LaunchedEffect
                val scrollOffset = computeScrollOffset(state = listState)
                val scrollRange = computeScrollRange(state = listState)
                val proportion = scrollOffset.toFloat() / (scrollRange.toFloat() - heightPx)
                thumbOffsetY = trackHeightPx * proportion + thumbTopPadding
                scrolled.tryEmit(Unit)
            }

            // Thumb alpha
            val alpha = remember { Animatable(0f) }
            val isThumbVisible = alpha.value > 0f
            LaunchedEffect(scrolled, alpha) {
                scrolled.collectLatest {
                    alpha.snapTo(1f)
                    alpha.animateTo(0f, animationSpec = FadeOutAnimationSpec)
                }
            }

            if (alpha.value == 0f) return@subcompose

            val thumbOffsetYFinal =
                if (!isReversed) thumbOffsetY
                else trackHeightPx - thumbOffsetY + thumbTopPadding
            Box(
                modifier = Modifier
                    .offset { IntOffset(0, thumbOffsetYFinal.roundToInt()) }
                    .height(ThumbLength)
                    .padding(horizontal = 6.dp)
                    .padding(end = endContentPadding)
                    .width(ThumbThickness)
                    .alpha(alpha.value)
                    .background(color = thumbColor, shape = ThumbShape)
                    .then(
                        // Recompose opts
                        if (!listState.isScrollInProgress) {
                            Modifier.draggable(
                                interactionSource = dragInteractionSource,
                                orientation = Orientation.Vertical,
                                enabled = isThumbVisible,
                                state = rememberDraggableState { delta ->
                                    val newOffsetY =
                                        if (!isReversed) thumbOffsetY + delta
                                        else thumbOffsetY - delta
                                    thumbOffsetY = newOffsetY.coerceIn(thumbTopPadding, thumbTopPadding + trackHeightPx)
                                },
                            )
                        } else Modifier,
                    ),
            ) {
                Icon(modifier = Modifier.align(Alignment.Center), imageVector = Icons.Default.DragHandle, contentDescription = null)
            }
        }.map { it.measure(constraints.copy(minWidth = 0, minHeight = 0)) }
        val scrollerWidth = scrollerPlaceable.maxByOrNull { it.width }?.width ?: 0

        layout(contentWidth, contentHeight) {
            contentPlaceable.forEach {
                it.placeRelative(0, 0)
            }
            scrollerPlaceable.forEach {
                it.placeRelative(contentWidth - scrollerWidth, 0)
            }
        }
    }
}

private fun computeScrollOffset(state: LazyListState): Int {
    if (state.layoutInfo.totalItemsCount == 0) return 0
    val visibleItems = state.layoutInfo.visibleItemsInfo
    val startChild = visibleItems.first()
    val endChild = visibleItems.last()
    val minPosition = min(startChild.index, endChild.index)
    val maxPosition = max(startChild.index, endChild.index)
    val itemsBefore = minPosition.coerceAtLeast(0)
    val startDecoratedTop = startChild.top
    val laidOutArea = abs(endChild.bottom - startDecoratedTop)
    val itemRange = abs(minPosition - maxPosition) + 1
    val avgSizePerRow = laidOutArea.toFloat() / itemRange
    return (itemsBefore * avgSizePerRow + (0 - startDecoratedTop)).roundToInt()
}

private fun computeScrollRange(state: LazyListState): Int {
    if (state.layoutInfo.totalItemsCount == 0) return 0
    val visibleItems = state.layoutInfo.visibleItemsInfo
    val startChild = visibleItems.first()
    val endChild = visibleItems.last()
    val laidOutArea = endChild.bottom - startChild.top
    val laidOutRange = abs(startChild.index - endChild.index) + 1
    return (laidOutArea.toFloat() / laidOutRange * state.layoutInfo.totalItemsCount).roundToInt()
}

private val ThumbLength = 40.dp
private val ThumbThickness = 40.dp
private val ThumbShape = RoundedCornerShape(ThumbThickness / 2)
private val FadeOutAnimationSpec = tween<Float>(delayMillis = 1000)

private val LazyListItemInfo.top: Int
    get() = offset

private val LazyListItemInfo.bottom: Int
    get() = offset + size