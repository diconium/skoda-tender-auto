package com.skoda.launcher.domain.usecase

import com.skoda.launcher.data.source.response.NotificationPayload
import com.skoda.launcher.domain.repository.SubscriptionsRepository

/**
 * Use case for handling subscriptions-related operations.
 *
 * @property subscriptionsRepository The repository used to fetch the story list.
 */
class SubscriptionsUseCase(private val subscriptionsRepository: SubscriptionsRepository) {

    /**
     * Calls the subscriptions API to fetch the list of subscriptions.
     *
     * This function is a suspending function and should be called
     * from a coroutine or another suspending function.
     *
     * @return A list of subscriptions fetched from the repository.
     */
    suspend fun callSubscriptionsApi() = subscriptionsRepository.fetchStoryList()

    suspend fun sendNotifications(payload: NotificationPayload) =
        subscriptionsRepository.sendNotifications(payload)

}
