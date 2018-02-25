package pl.brokenpipe.vozillatest.view.databinding

import timber.log.Timber
import kotlin.reflect.KClass

object BindingResourceMapper {

    lateinit var bindableResourceClass: KClass<*>

    private val resourceCache = HashMap<String, Int>()

    fun preLoadCache() = bindableResourceClass.java.declaredFields
            .filter { !it.isSynthetic && it.name != "serialVersionUID" }
            .forEach {
                try {
                    resourceCache[it.name] = it.getInt(bindableResourceClass)
                } catch (e: IllegalArgumentException) {
                    Timber.i("DataBinding resource could not be read: ${it.name}")
                }
            }

    fun getBindableResourceIdByPropertyName(propertyName: String): Int {
        return resourceCache.getOrPut(propertyName) {
            try {
                val field = bindableResourceClass.java.getDeclaredField(propertyName)
                return@getOrPut field.getInt(bindableResourceClass)
            } catch (e: NoSuchFieldError) {
                throw getBindingResourceMapper(propertyName)
            } catch (e: IllegalAccessException) {
                throw getBindingResourceMapper(propertyName)
            } catch (e: IllegalArgumentException) {
                throw getBindingResourceMapper(propertyName)
            }
        }
    }

    private fun getBindingResourceMapper(resourceName: String) =
            DataBindingException("Could not find BR.$resourceName")
}

