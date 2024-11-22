package com.example.resikelapp.data.repository


import android.util.Log
import com.example.resikelapp.data.model.Community
import com.example.resikelapp.data.model.CommunityData
import com.example.resikelapp.data.model.OrderCommunity

class ResikelRepository {

    private val orderCommunity = mutableListOf<OrderCommunity>()

    init {
        if (orderCommunity.isEmpty()) {
            CommunityData.communities.forEach {
                orderCommunity.add(OrderCommunity(it, 0))
            }
        }
    }

    fun getCommunity():List<Community> {
        return CommunityData.communities
    }

    fun searchCommunity(query: String): List<Community> {
        return CommunityData.communities.filter {
            it.nama.contains(query, ignoreCase = true)
        }
    }

    fun getOrderCommunityById(communityId: String): OrderCommunity {
        return orderCommunity.first {
            it.community.idCommunity == communityId
        }
    }

    fun updateGabungStatus(communityId: String, status: Boolean) {
        val order = orderCommunity.find { it.community.idCommunity == communityId }
        order?.community?.gabungStatus = status
    }
}