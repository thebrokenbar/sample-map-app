package pl.brokenpipe.vozillatest.arch

import io.reactivex.Observable

interface UseCase<in PARAM, RESULT> {
    fun execute(param: PARAM? = null): Observable<RESULT>
}