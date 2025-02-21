package com.elpablo.fscslutsky.ui.dashboard.list

sealed class DashboardListEvent {
    data class ContentClick(val id: String? = null): DashboardListEvent()
    data object NextRequest: DashboardListEvent()
}