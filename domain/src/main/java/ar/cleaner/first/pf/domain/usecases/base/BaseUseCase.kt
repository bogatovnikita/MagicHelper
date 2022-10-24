package ar.cleaner.first.pf.domain.usecases.base

import ar.cleaner.first.pf.domain.wrapper.CaseResult
import kotlinx.coroutines.flow.Flow

typealias DefaultUseCase<T, E> = () -> Flow<CaseResult<T, E>>

typealias OptimizeUseCase<T> = () -> Flow<T>