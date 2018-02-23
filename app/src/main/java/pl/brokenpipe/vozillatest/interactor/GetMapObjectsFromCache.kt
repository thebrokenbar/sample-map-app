package pl.brokenpipe.vozillatest.interactor

import io.reactivex.Observable
import pl.brokenpipe.vozillatest.arch.UseCase

/**
 * Created by gwierzchanowski on 23.02.2018.
 */
abstract class GetMapObjectsFromCache<T>(
        private val cache: Map<String, T>
): UseCase<String, List<T>> {
    override fun execute(param: String?): Observable<List<T>> {
        return Observable.defer {
            if (param == null) {
                return@defer Observable.just(cache.values.toList())
            } else {
                val mapObject = cache[param]
                        ?: throw NoSuchElementException("There is no map object with id $param")
                return@defer Observable.just(listOf(mapObject))
            }
        }
    }
}