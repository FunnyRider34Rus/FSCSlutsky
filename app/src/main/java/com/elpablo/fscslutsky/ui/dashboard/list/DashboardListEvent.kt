package com.elpablo.fscslutsky.ui.dashboard.list

sealed class DashboardListEvent {
    data object NextRequest: DashboardListEvent()
    data class GetVideoByID(val id: Int?, val indexOfPost: Int, val indexOfAttachment: Int): DashboardListEvent()
}