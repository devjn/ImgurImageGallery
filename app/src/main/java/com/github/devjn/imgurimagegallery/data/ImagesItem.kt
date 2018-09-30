package com.github.devjn.imgurimagegallery.data

import java.io.Serializable

data class ImagesItem(
	val commentCount: Any? = null,
	val inMostViral: Boolean? = null,
	val adType: Int? = null,
	val link: String? = null,
	val description: Any? = null,
	val section: Any? = null,
	val title: String? = null,
	val type: String? = null,
	val points: Any? = null,
	val score: Any? = null,
	val datetime: Int? = null,
	val hasSound: Boolean? = null,
	val favoriteCount: Any? = null,
	val id: String? = null,
	val inGallery: Boolean? = null,
	val vote: Any? = null,
	val views: Int? = null,
	val height: Int = 0,
	val downs: Any? = null,
	val bandwidth: Long? = null,
	val nsfw: Any? = null,
	val isAd: Boolean? = null,
	val adUrl: String? = null,
	val tags: List<Any?>? = null,
	val accountId: Any? = null,
	val size: Int? = null,
	val width: Int = 0,
	val accountUrl: Any? = null,
	val animated: Boolean? = null,
	val ups: Any? = null,
	val favorite: Boolean? = null
) : Serializable
