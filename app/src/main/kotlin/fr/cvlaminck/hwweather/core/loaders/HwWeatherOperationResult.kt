package fr.cvlaminck.hwweather.core.loaders

class HwWeatherOperationResult<T> private constructor() {

    private var operationResult: T? = null;
    private var failureCause: Exception? = null;

    constructor(result: T): this() {
        operationResult = result;
    }

    constructor(failureCause: Exception): this() {
        this.failureCause = failureCause;
    }

    val failed: Boolean
        get(): Boolean = failureCause != null;

    val result: T
        get(): T {
            if (failed) {
                throw IllegalStateException("Cannot get result from a failed operation");
            }
            return operationResult as T;
        }

    val cause: Exception
        get(): Exception {
            if (!failed) {
                throw IllegalStateException("Cannot get failure cause since this operation has succeeded");
            }
            return failureCause as Exception;
        }
}