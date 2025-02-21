package com.elpablo.fscslutsky.ui.dashboard

import com.elpablo.fscslutsky.data.model.News

sealed class DashboardListEvent {
    data class OnCardClick(val news: News): DashboardListEvent()
    data object BottomSheetDismiss: DashboardListEvent()
    data object NextRequest: DashboardListEvent()
}