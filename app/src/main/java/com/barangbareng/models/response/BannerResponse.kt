package com.barangbareng.models.response

import com.barangbareng.models.Banner

data class BannerResponse(
    var data: Banner = Banner.empty(),
    var status : String = ""
) {
    companion object {
        fun empty(): BannerResponse = BannerResponse()
    }
}