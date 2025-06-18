package com.example.xplorer.navigator

enum class XplorerScreens(val route: String) {
    Landing("landing"),
    Register("register"),
    Login("login"),
    Home("home"),
    Profile("profile"),
    Favorite("favorite"),
    Country("country/{countryId}" ),
    City("city");

    fun withArgs(vararg args: String): String =
        buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
}