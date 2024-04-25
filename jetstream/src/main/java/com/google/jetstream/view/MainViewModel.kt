package com.google.jetstream.view

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.jetstream.R
import com.google.jetstream.data.models.ChannelRowItems
import com.google.jetstream.data.models.ProgramRowItems
import com.google.jetstream.data.repositories.MockData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MainViewModel: ViewModel() {
    val isPositionSet = MutableLiveData<Boolean>()
    private val selectedFilterList = MutableStateFlow(FilterList())
    /**
     * Start by gathering info then sets ready
     */
    val uiState: StateFlow<HomeScreenUiState> = combine(
        MockData().createChannelsFlow(),
        MockData().createProgramsFlow(),
    ) {
        channelList, programsList ->
        HomeScreenUiState.Ready(channelList, programsList)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeScreenUiState.Loading
    )

    /**
     * HashMap for storing time position X
     */
    val startTimePositions = mutableStateMapOf<String, Float>()
    var timeNowPosition:Float = 1F

    fun updateSelectedFilterList(filterList: FilterList) {
        selectedFilterList.value = filterList
    }

}
@Immutable
data class FilterList(val items: List<FilterCondition> = emptyList()) {
    fun toIdList(): List<Int> {
        if (items.isEmpty()) {
            return FilterCondition.None.idList
        }
        return items.asSequence().map {
            it.idList
        }.fold(emptyList()) { acc, ints ->
            acc + ints
        }
    }
}

@Immutable
enum class FilterCondition(val idList: List<Int>, @StringRes val labelId: Int) {
    None((0..28).toList(), R.string.favorites_unknown),
}
sealed interface HomeScreenUiState {
    data object Loading : HomeScreenUiState
    data object Error : HomeScreenUiState
    data class Ready(
        val channelList: MutableList<ChannelRowItems>,
        val programsList: MutableList<ProgramRowItems>,
    ) : HomeScreenUiState
}