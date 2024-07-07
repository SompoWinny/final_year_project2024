package com.example.beansdeseasesdetector.Model

class AllUsers(role: String, email: String, password: String, token: String?, userId: String) {
    private var Jina: String? =
        null;
    private var Email:kotlin.String? = null;
    private var Password:kotlin.String? = null;
    private var Token:kotlin.String? = null;
    private var userUID:kotlin.String? = null;

    fun Users() {}

    fun Users(
        jina: String,
        email: String,
        password: String,
        token: String,
        userUid: String,

    ) {
        Jina = jina
        Email = email
        Password = password
        Token = token
        userUID = userUid
    }

    fun getJina(): String {
        return Jina!!
    }

    fun setJina(jina: String) {
        Jina = jina
    }

    fun getEmail(): String {
        return Email!!
    }

    fun setEmail(email: String) {
        Email = email
    }

    fun getPassword(): String {
        return Password!!
    }

    fun setPassword(password: String) {
        Password = password
    }


    fun getToken(): String {
        return Token!!
    }

    fun setToken(token: String) {
        Token = token
    }

    fun getUserUid(): String {
        return userUID!!
    }

    fun setUserUid(userUid: String) {
        userUID = userUid
    }
}