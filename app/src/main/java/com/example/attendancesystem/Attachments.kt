package com.example.attendancesystem

import java.io.Serializable

data class Attachments(
    var _id: String? = null,
    var name: String? = null,
    var originalName: String? = null,
    var attachmentFor: String? = null,
    var type: String? = null,
    var storedIn: String? = null,
    var path: String? = null,
    var key: String? = null,
    var date: String? = null,
    var uploadedBy: String? = null,
    var awsImageUrl: String? = null,
    var isImageLoaded: Boolean = false
) : Serializable