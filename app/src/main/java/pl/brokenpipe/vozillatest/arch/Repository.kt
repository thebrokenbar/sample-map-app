package pl.brokenpipe.vozillatest.arch

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface Repository<DATA, in SPEC> {
    fun get(spec: SPEC): Observable<DATA>
}