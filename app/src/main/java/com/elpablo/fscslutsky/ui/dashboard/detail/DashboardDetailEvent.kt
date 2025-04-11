package com.elpablo.fscslutsky.ui.dashboard.detail

sealed class DashboardDetailEvent {
    data class GetPostByID(val id: Int?): DashboardDetailEvent()
    data class GetVideoByID(val id: Int?, val indexOfAttachment: Int): DashboardDetailEvent()
}