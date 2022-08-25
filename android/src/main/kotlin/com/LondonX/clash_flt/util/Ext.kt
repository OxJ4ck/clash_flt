package com.LondonX.clash_flt.util

import com.github.kr328.clash.core.model.*


fun FetchStatus.toMap(): Map<String, Any> {
    return mapOf(
        "action" to action.toDartEnum(),
        "args" to args,
        "progress" to progress,
        "max" to max,
    )
}

fun Provider.toMap(): Map<String, Any?> {
    return mapOf(
        "name" to name,
        "type" to type.toDartEnum(),
        "vehicleType" to vehicleType.toDartEnum(),
        "updatedAt" to updatedAt,
    )
}

fun ProxyGroup.toMap(): Map<String, Any?> {
    return mapOf(
        "type" to type.toDartEnum(),
        "proxies" to proxies.map { it.toMap() },
        "now" to now,
    )
}

fun Proxy.toMap(): Map<String, Any> {
    return mapOf(
        "name" to name,
        "title" to title,
        "subtitle" to subtitle,
        "type" to type.toDartEnum(),
        "delay" to delay,
    )
}

fun String?.toProxySort(): ProxySort {
    return when (this) {
        "title" -> ProxySort.Title
        "delay" -> ProxySort.Delay
        else -> ProxySort.Default
    }
}

fun String?.toProxyType(): Proxy.Type {
    return when (this) {
        "direct" -> Proxy.Type.Direct
        "reject" -> Proxy.Type.Reject
        "shadowsocks" -> Proxy.Type.Shadowsocks
        "shadowsocksR" -> Proxy.Type.ShadowsocksR
        "snell" -> Proxy.Type.Snell
        "socks5" -> Proxy.Type.Socks5
        "http" -> Proxy.Type.Http
        "vmess" -> Proxy.Type.Vmess
        "trojan" -> Proxy.Type.Trojan
        "relay" -> Proxy.Type.Relay
        "selector" -> Proxy.Type.Selector
        "fallback" -> Proxy.Type.Fallback
        "uRLTest" -> Proxy.Type.URLTest
        "loadBalance" -> Proxy.Type.LoadBalance
        else -> Proxy.Type.Unknown
    }
}

fun Enum<*>.toDartEnum(): String {
    val s = this.toString()
    if (s.all { it.isUpperCase() }) {
        return s.lowercase()
    }
    val first = s.first()
    val target = first.lowercase()
    return s.replaceFirst(first.toString(), target)
}