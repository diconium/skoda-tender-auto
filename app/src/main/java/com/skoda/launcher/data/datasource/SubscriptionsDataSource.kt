package com.skoda.launcher.data.datasource

import com.skoda.launcher.data.source.response.VehicleResponse
import retrofit2.Response

/**
 * Interface for the data source that provides subscriptions-related data.
 *
 * This interface defines the contract for obtaining story lists based on a given VIN.
 */
interface SubscriptionsDataSource {

    /**
     * Retrieves the list of stories based on the provided VIN.
     *
     * This function is a suspending function that returns a [Response] containing
     * the result of the API call, which includes either a successful response or an error.
     *
     * @param vin The Vehicle Identification Number used to fetch story lists.
     * @return A [Response] containing [VehicleResponse].
     */
    suspend fun getStoryLists(vin: String): Response<VehicleResponse>
}
