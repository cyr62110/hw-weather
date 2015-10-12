package fr.cvlaminck.hwweather.core.model.weather

public enum class TemperatureUnit private constructor(
        private val conversionFactorFromCelsius: Double,
        private val zeroDegreeCelsiusOffset: Double,
        val symbol: String
) {
    CELSIUS(1.0, 0.0, "°C"),
    KELVIN(1.0, 273.15, "°K"),
    FAHRENHEIT(1.8, -32.0, "°F");

    fun convertFromCelsius(temperatureInCelsius: Double) =
            (temperatureInCelsius + zeroDegreeCelsiusOffset) * conversionFactorFromCelsius;
}