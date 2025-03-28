package com.elpablo.fscslutsky.ui.dashboard.list

import com.elpablo.fscslutsky.domain.model.News

sealed class DashboardListEvent {
    data class OnCardClick(val news: News): DashboardListEvent()
    data object BottomSheetDismiss: DashboardListEvent()
    data object NextRequest: DashboardListEvent()
}