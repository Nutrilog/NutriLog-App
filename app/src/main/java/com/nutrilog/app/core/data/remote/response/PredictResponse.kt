package com.nutrilog.app.core.data.remote.response

import com.google.gson.annotations.SerializedName
import com.nutrilog.app.core.domain.common.StatusResponse
import com.nutrilog.app.core.domain.model.Nutrition

data class PredictResponse(
    @field:SerializedName("status")
    val status: StatusResponse,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("data")
    val data: Nutrition,
)
