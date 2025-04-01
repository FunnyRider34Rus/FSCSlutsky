package com.elpablo.fscslutsky.ui.dashboard.list

import com.elpablo.fscslutsky.domain.model.News

sealed class DashboardListEvent {
    data object NextRequest: DashboardListEvent()
}