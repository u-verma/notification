package com.notify.client.email.service

interface EmailService {
    fun execute(userList: List<String>)
}