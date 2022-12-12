package com.example.sukafasta.model

import com.google.firebase.firestore.DocumentId
import java.sql.Timestamp

class Appointment (@DocumentId val id: String, var serviceName: String, var time: String, var date: String) {
    constructor(): this("", "", "", "")
}