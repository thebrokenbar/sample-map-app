package pl.brokenpipe.vozillatest.arch

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by gwierzchanowski on 19.02.2018.
 */
interface Arch {
    interface View

    interface Presenter

    interface UseCase<in PARAM, RESULT> {
        fun execute(param: PARAM? = null): Observable<RESULT>
    }

    interface Repository<DATA, SPEC> {
        fun get(spec: SPEC): Maybe<DATA>
        fun put(data: DATA): Single<DATA> {
            throw NotImplementedError("Not implemented yet")
        }

        fun remove(spec: SPEC) {
            throw NotImplementedError("Not implemented yet")
        }
    }
}