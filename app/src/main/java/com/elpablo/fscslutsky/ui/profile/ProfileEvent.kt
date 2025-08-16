package com.elpablo.fscslutsky.ui.profile

sealed class ProfileEvent {
    data object logout: ProfileEvent()
    data object showAboutApp: ProfileEvent()
    data object showLicensing: ProfileEvent()
    data object hideAboutApp: ProfileEvent()
    data object hideLicensing: ProfileEvent()
}