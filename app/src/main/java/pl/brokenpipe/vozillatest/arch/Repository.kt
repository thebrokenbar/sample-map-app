package pl.brokenpipe.vozillatest.arch

import io.reactivex.Maybe
import io.reactivex.Single

interface Repository<DATA, SPEC> {
    fun get(spec: SPEC): Maybe<DATA>
    fun put(data: DATA): Single<DATA> {
        throw NotImplementedError("Not implemented yet")
    }

    fun remove(spec: SPEC) {
        throw NotImplementedError("Not implemented yet")
    }
}