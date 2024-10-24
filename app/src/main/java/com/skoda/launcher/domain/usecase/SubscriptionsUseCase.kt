package com.skoda.launcher.domain.usecase

import com.skoda.launcher.domain.repository.SubscriptionsRepository

/**
 * Use case for handling subscriptions-related operations.
 *
 * @property storiesRepository The repository used to fetch the story list.
 */
class SubscriptionsUseCase(private val storiesRepository: SubscriptionsRepository) {

    /**
     * Calls the subscriptions API to fetch the list of stories.
     *
     * This function is a suspending function and should be called
     * from a coroutine or another suspending function.
     *
     * @return A list of stories fetched from the repository.
     */
    suspend fun callSubscriptionsApi() = storiesRepository.fetchStoryList()
}
