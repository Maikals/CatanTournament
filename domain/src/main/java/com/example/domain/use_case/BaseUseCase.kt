package com.example.domain.use_case

import com.example.domain.entities.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class BaseUseCase<Params, T>(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {
    operator fun invoke(params: Params): Flow<Result<T>> =
        buildCall(params).map {
            Result.Success(it)
        }.flowOn(dispatcher)
            .catch {
                it.printStackTrace()
                Result.Failure
            }

    abstract fun buildCall(params: Params): Flow<T>
}
