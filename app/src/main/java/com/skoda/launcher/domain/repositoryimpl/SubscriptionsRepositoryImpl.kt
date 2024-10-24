package com.skoda.launcher.domain.repositoryimpl


import com.skoda.launcher.data.source.remote.config.APIConfig.VIN_NO
import com.skoda.launcher.data.datasource.SubscriptionsDataSource
import com.skoda.launcher.domain.repository.SubscriptionsRepository
import com.skoda.launcher.data.source.base.BaseApiResponse
import com.skoda.launcher.data.source.response.ApiResult
import com.skoda.launcher.data.source.response.VehicleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Implementation of the [SubscriptionsRepository] interface that fetches story lists
 * from a data source.
 *
 * @property storiesDataSource The data source used to retrieve story lists.
 */
class SubscriptionsRepositoryImpl(private val storiesDataSource: SubscriptionsDataSource) :
    BaseApiResponse(), SubscriptionsRepository {

    /**
     * Fetches the list of stories as a [Flow] of [ApiResult].
     *
     * This function is a suspending function that emits the result of the API call
     * using the [safeApiCall] utility. The resulting flow is executed on the IO dispatcher.
     *
     * @return A [Flow] containing [ApiResult] of [VehicleResponse].
     */
    override suspend fun fetchStoryList(): Flow<ApiResult<VehicleResponse>> {
        return flow {
            emit(
                safeApiCall {
                    storiesDataSource.getStoryLists(VIN_NO)
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}
